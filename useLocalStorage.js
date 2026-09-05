import { useState, useEffect, useCallback } from 'react';

/**
 * Custom hook to handle caching of user study progress, notes, and session state
 * in modern responsive applications. This ensures high offline resilience by reading
 * from and persisting to localStorage.
 *
 * @param {string} key - The cache key to persist in localStorage
 * @param {any} initialValue - The fallback value if no cached entry exists
 * @returns {[any, Function, boolean]} - State, setter function, and syncStatus flag (true if successfully persisted)
 */
export function useLocalStorage(key, initialValue) {
  // Read initial cached state from localStorage safely to prevent crash on SSR
  const readCachedValue = useCallback(() => {
    if (typeof window === 'undefined') {
      return initialValue;
    }
    
    try {
      const item = window.localStorage.getItem(key);
      if (item !== null) {
        return JSON.parse(item);
      }
    } catch (error) {
      console.warn(`[Offline Cache] Error reading key "${key}" from localStorage:`, error);
    }
    
    // If initialValue is a function, execute it to match useState API
    return typeof initialValue === 'function' ? initialValue() : initialValue;
  }, [key, initialValue]);

  // Maintain local in-memory state
  const [storedValue, setStoredValue] = useState(readCachedValue);
  const [offlineSynced, setOfflineSynced] = useState(true);

  // Return a lazy-updated setter function that saves state both locally and persistently 
  const setValue = useCallback((value) => {
    try {
      // Allow value to be a function to match the standard useState dispatch API
      const valueToStore = value instanceof Function ? value(storedValue) : value;
      
      // Update our in-memory state
      setStoredValue(valueToStore);
      setOfflineSynced(true);

      // Write persistently to local storage
      if (typeof window !== 'undefined') {
        window.localStorage.setItem(key, JSON.stringify(valueToStore));
        
        // Dispatch custom event to notify other instances of this hook across tabs
        window.dispatchEvent(new Event('local-storage-sync'));
      }
    } catch (error) {
      console.error(`[Offline Cache] Critical error writing key "${key}" to local storage:`, error);
      setOfflineSynced(false);
    }
  }, [key, storedValue]);

  // Sync state between browser tabs/views in real-time
  useEffect(() => {
    const handleSync = () => {
      setStoredValue(readCachedValue());
    };

    if (typeof window !== 'undefined') {
      window.addEventListener('storage', handleSync);
      window.addEventListener('local-storage-sync', handleSync);
    }
    
    return () => {
      if (typeof window !== 'undefined') {
        window.removeEventListener('storage', handleSync);
        window.removeEventListener('local-storage-sync', handleSync);
      }
    };
  }, [readCachedValue]);

  return [storedValue, setValue, offlineSynced];
}
