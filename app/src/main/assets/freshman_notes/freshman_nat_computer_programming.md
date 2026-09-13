# Disclaimer

The content provided in these notes is for educational and informational purposes only. While every effort has been made to ensure accuracy, the author makes no warranties or guarantees regarding the completeness, reliability, or suitability of the information contained herein. Users are encouraged to verify all information with original sources and consult qualified professionals before making decisions based on this material. This content may not be reproduced, distributed, or transmitted without prior written permission from Tamhero.

---

# Introduction to Computer Programming (Python) Lecture Notes

---

## Chapter 1: Introduction to Python and Programming Concepts

### 1.1 What is Python?
Python is a simple, high-level, interpreted, general-purpose, dynamic, and object-oriented programming language created by **Guido van Rossum** and first released in 1991.

- **Interpreted Language**: Code is executed line-by-line by the Python interpreter without requiring a separate compilation step. This enables rapid prototyping, testing, and debugging.
- **Cross-Platform / Portable**: Python programs run seamlessly on Windows, macOS, Linux, Unix, and mobile platforms without code modifications.
- **High-Level Language**: Python abstracts low-level details (like manual memory management, register allocation, and hardware architecture), allowing programmers to write clear, expressive code using English-like syntax.
- **Dynamic Typing**: Variable types are determined automatically at runtime rather than during variable declaration.

### 1.2 Why Learn Python & Where is it Used?
- **Readability and Simplicity**: Python syntax is clean, concise, and easy for beginners to learn, avoiding curly braces `{}` and semicolons `;`.
- **Extensive Standard Library & Ecosystem**:
  - **Data Science & AI/ML**: NumPy, Pandas, Matplotlib, SciPy, TensorFlow, PyTorch, Scikit-learn.
  - **Web Development**: Django, Flask, FastAPI, Pyramid.
  - **Computer Vision & Image Processing**: OpenCV, Pillow, Scikit-image.
  - **Game Development**: Pygame.
  - **GUI Applications**: Tkinter, PyQt, Kivy.
  - **Automation & Scripting**: Requests, BeautifulSoup, Selenium.

### 1.3 Python Basic Syntax, Identifiers and Keywords
- **Indentation**: Python uses whitespace indentation (standard 4 spaces) to define code blocks (loops, functions, classes, conditional branches), replacing `{}` from C/C++/Java.
- **Comments**:
  - Single-line comments start with `#`.
  - Multi-line docstrings or block comments use triple quotes (`'''` or `"""`).
- **Identifiers**: Names used to identify variables, functions, classes, and modules.
  - Must start with a letter (`a-z`, `A-Z`) or an underscore (`_`).
  - Cannot start with a digit.
  - Can contain alphanumeric characters and underscores (`a-z`, `A-Z`, `0-9`, `_`).
  - Identifiers are case-sensitive (`age`, `Age`, and `AGE` are distinct).
  - Keywords (such as `if`, `else`, `while`, `for`, `def`, `class`, `import`, `return`, `True`, `False`, `None`) cannot be used as identifier names.

---

## Chapter 2: Variables, Data Types, and Operators

### 2.1 Variables and Assignment
In Python, a variable is a named reference/pointer to an object in memory. Variables are created automatically when assigned a value using the assignment operator `=`.
```python
x = 10          # Integer
name = "Abebe"  # String
gpa = 3.85      # Float
is_passed = True # Boolean
```

### 2.2 Built-in Python Data Types
1. **Numeric Types**:
   - `int`: Arbitrary-precision integers (e.g., `42`, `-7`).
   - `float`: Double-precision floating-point numbers (e.g., `3.14159`, `-0.001`).
   - `complex`: Complex numbers with real and imaginary parts (e.g., `3 + 4j`).
2. **Sequence Types**:
   - `str`: Immutable sequence of Unicode characters (`"Hello, Ethiopia"`).
   - `list`: Mutable, ordered collection of heterogeneous elements (`[1, 2.5, "AAU", True]`).
   - `tuple`: Immutable, ordered collection (`(10, 20, 30)`).
   - `range`: Immutable sequence of numbers generated on demand (`range(0, 10, 2)`).
3. **Mapping Type**:
   - `dict`: Key-value pairs with unique hashable keys (`{"name": "Kebede", "id": "UGR/1234/16"}`).
4. **Set Types**:
   - `set`: Unordered collection of unique elements (`{1, 2, 3, 4}`).
   - `frozenset`: Immutable version of a set.
5. **Boolean Type**:
   - `bool`: `True` or `False`.
6. **None Type**:
   - `NoneType`: Represents the absence of a value (`None`).

### 2.3 Type Conversion (Casting)
- **Implicit Conversion**: Python automatically converts smaller data types to larger ones (e.g., adding `int` and `float` yields `float`).
- **Explicit Conversion**:
  - `int("123")` -> 123
  - `float(5)` -> 5.0
  - `str(450)` -> "450"
  - `list((1, 2, 3))` -> [1, 2, 3]

### 2.4 Operators in Python
- **Arithmetic Operators**: `+` (addition), `-` (subtraction), `*` (multiplication), `/` (float division), `//` (floor division), `%` (modulus), `**` (exponentiation).
- **Comparison (Relational) Operators**: `==`, `!=`, `<`, `>`, `<=`, `>=`.
- **Logical Operators**: `and`, `or`, `not`.
- **Assignment Operators**: `=`, `+=`, `-=`, `*=`, `/=`, `//=`, `%=`, `**=`.
- **Membership Operators**: `in`, `not in` (checks if a value exists in a sequence).
- **Identity Operators**: `is`, `is not` (checks memory identity `id(a) == id(b)`).
- **Operator Precedence**: Parentheses `()` -> Exponentiation `**` -> Unary `+`, `-` -> Multiplication/Division `*`, `/`, `//`, `%` -> Addition/Subtraction `+`, `-` -> Comparisons -> Logical `not`, `and`, `or`.

---

## Chapter 3: Control Structures and Decision Making

### 3.1 Selection / Decision Making
Decision-making structures evaluate conditional expressions to determine the flow of program execution.

1. **`if` statement**:
```python
score = 85
if score >= 50:
    print("Passed")
```

2. **`if-else` statement**:
```python
score = 42
if score >= 50:
    print("Passed")
else:
    print("Failed")
```

3. **`if-elif-else` ladder**:
```python
mark = 88
if mark >= 90:
    grade = 'A+'
elif mark >= 85:
    grade = 'A'
elif mark >= 80:
    grade = 'A-'
elif mark >= 75:
    grade = 'B+'
elif mark >= 70:
    grade = 'B'
elif mark >= 65:
    grade = 'B-'
elif mark >= 50:
    grade = 'C'
else:
    grade = 'F'
```

4. **Nested `if` Statements**:
`if` statements placed inside another `if` or `else` block to test hierarchical conditions.

5. **Conditional Expressions (Ternary Operator)**:
```python
status = "Adult" if age >= 18 else "Minor"
```

---

## Chapter 4: Loops and Iteration

### 4.1 Repetition Structures
Loops allow repeated execution of statements as long as a condition remains true or across items of an iterable sequence.

### 4.2 The `while` Loop
Executes a block of code repeatedly as long as the condition evaluates to `True`.
```python
count = 1
while count <= 5:
    print(f"Iteration {count}")
    count += 1
```
- **Sentinel Loops**: Use a specific input value (e.g., `-1` or `'quit'`) to signal loop termination.
- **Infinite Loops**: Occur when the condition never becomes `False`. Controlled with `break`.

### 4.3 The `for` Loop and `range()` Function
The `for` loop iterates directly over elements of any sequence (strings, lists, tuples, ranges, dictionaries).
```python
for i in range(1, 11): # Generates numbers 1 to 10
    print(i * i)

fruits = ["Mango", "Banana", "Orange", "Avocado"]
for fruit in fruits:
    print(fruit)
```

### 4.4 Loop Control Statements
- **`break`**: Immediately terminates the innermost loop and transfers control to the statement after the loop.
- **`continue`**: Skips the remainder of the current loop iteration and proceeds to the next iteration.
- **`pass`**: A null statement that acts as a syntactic placeholder where code is required but no action is needed.
- **`else` clause with loops**: Executes only when the loop completes normally without encountering a `break`.

### 4.5 Nested Loops
Loops inside loops (e.g., iterating through 2D grids, matrices, or printing patterns).
```python
for row in range(1, 4):
    for col in range(1, 4):
        print(f"({row},{col})", end=" ")
    print()
```

---

## Chapter 5: Functions and Modular Programming

### 5.1 Function Definition and Invocation
A function is a reusable, self-contained block of organized code that performs a specific task.
- **`def` keyword**: Used to declare a function.
- **Function Signature**: Function name and parameters.
- **Return Statement**: Sends computation results back to the caller using `return`. If omitted, functions return `None` (Void functions vs Fruitful functions).

```python
def calculate_area(length, width):
    """Calculates and returns the area of a rectangle."""
    return length * width

result = calculate_area(5.0, 3.2)
print(f"Area: {result}")
```

### 5.2 Parameter Passing and Argument Types
- **Positional Arguments**: Matched to parameters by position.
- **Keyword Arguments**: Passed by specifying parameter names (`calculate_area(width=3.2, length=5.0)`).
- **Default Arguments**: Parameters with default fallback values if omitted in the call.
- **Arbitrary Arguments**:
  - `*args`: Collects positional arguments into a tuple.
  - `**kwargs`: Collects keyword arguments into a dictionary.

### 5.3 Variable Scope
- **Local Scope**: Variables declared inside a function are accessible only within that function.
- **Global Scope**: Variables defined at the top-level script are accessible throughout the file.
- **`global` keyword**: Used inside a function to modify a global variable.

### 5.4 Recursive Functions
A recursive function is one that calls itself to solve smaller instances of the same problem until reaching a base case.
```python
def factorial(n):
    if n <= 1: # Base case
        return 1
    return n * factorial(n - 1) # Recursive step
```

---

## Chapter 6: Data Structures (Strings, Lists, Tuples, Dictionaries)

### 6.1 Strings
- **Indexing & Slicing**: Zero-based indexing (`s[0]`), negative indexing from the end (`s[-1]`), slicing (`s[start:end:step]`).
- **Immutability**: Characters cannot be altered in-place.
- **String Methods**: `upper()`, `lower()`, `strip()`, `split()`, `join()`, `replace()`, `find()`, `startswith()`, `endswith()`, `count()`.

### 6.2 Lists
- **Mutable Ordered Sequences**: Elements can be added, modified, or removed.
- **Operations**:
  - `append(item)`: Adds element at end.
  - `insert(index, item)`: Inserts element at specified index.
  - `extend(iterable)`: Appends all items from iterable.
  - `remove(item)`: Removes first matching item.
  - `pop(index)`: Removes and returns element at index.
  - `sort()`: In-place sorting.
  - `reverse()`: In-place reversal.

### 6.3 Tuples
- **Immutable Ordered Sequences**: Defined with parentheses `()`.
- Faster than lists and can be used as dictionary keys because they are hashable.

### 6.4 Dictionaries (Key-Value Mappings)
- **Hash-table based associative array**: Stores mappings of `{key: value}`.
- **Operations**:
  - `d[key]`: Access value or assign new key-value pair.
  - `d.get(key, default)`: Safely retrieves a value without raising `KeyError`.
  - `d.keys()`, `d.values()`, `d.items()`: Returns views of keys, values, and tuples of `(key, value)`.

### 6.5 Sets
- Unordered collection of unique items. Supports mathematical set operations: union `|`, intersection `&`, difference `-`, symmetric difference `^`.

---

## Chapter 7: File Handling and Exception Handling

### 7.1 File Handling in Python
Python provides the built-in `open()` function to interact with files on disk.
```python
handle = open(filename, mode)
```
- **File Access Modes**:
  - `'r'`: Read mode (default). Raises error if file doesn't exist.
  - `'w'`: Write mode. Overwrites existing file or creates a new one.
  - `'a'`: Append mode. Adds data to the end of file.
  - `'r+'`: Read and write mode.
  - `'b'`: Binary mode (e.g., `'rb'`, `'wb'`).

### 7.2 The `with` Statement (Context Manager)
Automatically manages resource cleanup and guarantees file closure even if exceptions occur:
```python
with open("students.txt", "r") as file:
    for line in file:
        print(line.strip())
```

### 7.3 Exception Handling
Exceptions are runtime errors that disrupt the normal flow of instruction execution.
```python
try:
    num1 = int(input("Enter numerator: "))
    num2 = int(input("Enter denominator: "))
    result = num1 / num2
    print(f"Result: {result}")
except ZeroDivisionError:
    print("Error: Cannot divide by zero!")
except ValueError:
    print("Error: Invalid integer input!")
except Exception as e:
    print(f"Unexpected error: {e}")
else:
    print("Operation completed successfully.")
finally:
    print("Cleanup completed.")
```
