package com.example.ui.components

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

/**
 * An Android Jetpack Compose representation of the requested custom `useLocalStorage` hook.
 * It replicates key-value persistent storage syncing in modern Compose by reading and
 * writing values directly to SharedPreferences, securing seamless offline-first capability.
 *
 * Usage:
 *     val (noteBookmark, setNoteBookmark) = rememberLocalStorageState("note_bookmark_unit1", false)
 */
@Composable
fun <T> rememberLocalStorageState(
    key: String,
    defaultValue: T,
    onValueSaved: ((T) -> Unit)? = null
): MutableState<T> {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("offline_study_local_cache", Context.MODE_PRIVATE)
    }

    // Read cached value or yield default
    val initialValue = remember(key) {
        try {
            when (defaultValue) {
                is String -> sharedPreferences.getString(key, defaultValue) as T
                is Int -> sharedPreferences.getInt(key, defaultValue) as T
                is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
                is Float -> sharedPreferences.getFloat(key, defaultValue) as T
                is Long -> sharedPreferences.getLong(key, defaultValue) as T
                else -> defaultValue
            }
        } catch (e: Exception) {
            defaultValue
        }
    }

    val state = remember(key) { mutableStateOf(initialValue) }

    // Capture state adjustments and write to SharedPreferences asynchronously
    LaunchedEffect(key, state.value) {
        val editor = sharedPreferences.edit()
        when (val currentValue = state.value) {
            is String -> editor.putString(key, currentValue)
            is Int -> editor.putInt(key, currentValue)
            is Boolean -> editor.putBoolean(key, currentValue)
            is Float -> editor.putFloat(key, currentValue)
            is Long -> editor.putLong(key, currentValue)
        }
        editor.apply()
        onValueSaved?.invoke(state.value)
    }

    return state
}
