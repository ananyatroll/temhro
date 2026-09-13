# Introduction to Computer Science — Complete Notes

> **Disclaimer:** These study materials are compiled and curated by **Tamhero** for educational purposes.
> Content has been restructured for clarity and self-study. All rights belong to their respective original authors.
> For queries, contact: **info@tamhero.com**

---
## Chapter 1: What is Computer Science and Computation

Lecture 0: Hello World


# WHAT ARE COMPUTERS?
Until the 1940s the word “computer” was a job title
computers were people trained to  carry out mathematical operations
they followed a set of precise instructions required to create mathematical tables used for navigation, astronomy, business


# WHAT IS COMPUTER SCIENCE?
“Computer Science is no more about computers than astronomy is about telescopes”

Edsger Dijkstra (1930 - 2002)


# WHAT IS COMPUTER SCIENCE?
Computer Science is the study of computers (??)
This leaves aside the theoretical work in CS, which does not make use of real computers, but of formal models of computers
Actually, the early work in CS took place before the development of the first computer


# WHAT IS COMPUTER SCIENCE?
Computer Science is the study of how to write computer programs (programming) (??)

Programming is a big part of CS.. ..but it is not the most important part.


First computer: 1940s

For example, consider the fact that the first computer was built in 1943….


Ada Lovelace

First computer: 1940s
First computer program: 1843

…but the first computer program was written in 1843, by Ada Lovelace.
Writing a computer program is about using logic to creatively solve a problem...
Not just about the technology that carries out that program.

# COMPUTATION


# COMPUTATION

Computation is a sequence of well-defined operations that lead from an initial starting point to desired final outcome

Notice No “computer” in the definition

# CS
Computer science is the study of computation
investigating problems that can be solved computationally
Using programming languages to describe computations


# CONT …
machines that carry out computations
theoretical limits of computation (what is or is not computable)
computational solutions to problems in math, science, medicine, business, education…


# HISTORY


# COMPUTING MACHINES

 Fixed-program computers
 Stored-program computers


# BABBAGE’S MACHINES


Difference Engine
Analytical Engine
Charles Babbage (1791-1871)

Father of computing


Could be given data to run operations in sequence
Had memory


At the age of 17, Ada met Charles Babbage and became fascinated by his machines
Programming language
The First Programmer

# BOOLEAN


IBM’s Harvard mark I
3million connections
800km of wire
3 additions/subtractions per second


Electro mechanical machine( The Bombe)


# BIRTH OF MODERN COMPUTING


# COMPUTATIONAL THINKING


# WHAT CAN A COMPUTER DO?


# PROGRAMS

A program is a sequence of instructions that specifies how to perform a computation.


# C

Unix, great tool for system programming

# hello, world


# WHERE IS COMPUTING BEING USED?

---

## Chapter 2: Programming Languages, Compilers and Execution

# Beauty and joy of programming
Lecture 02: Programming Languages

# Languages
 Formal languages
 Natural languages

# Programming Constructs
 Syntax
 Static Semantics
 Semantics

# Syntax
A Language defines which strings of characters are symbols and well formed.
English: Cat dog boy
    	      <noun> <noun> <noun>
Math: 3.2  3.2
	      <operand> <operator> <operand>

x
x

# Static Semantics
 Defines which syntactically valid strings have a meaning
English: I are good.
    	      <pronoun> <verb> <adjective>
Math: 3/’abc’
	      <operand> <operator> <operand>

# Semantics
 A language associates a meaning with each syntactically correct symbols with NO static semantic errors. (i.e ambiguity)
English: I cannot praise this student too highly

# Formal Vs Natural
 Ambiguity
 Redundancy
 Literalness

Literalness: full of idioms and metaphor


# Programming Languages
Computers are programmable machines
Programmable?
Instructions can be stored in a file on the hard drive, and then loaded into main memory and executed on demand

# Common things in all programming

Math

# 0		 1


# 0	 0	 0	 0

# 0	 0	 0	 1

# 0	 0	 1	 0

# 0	 0	 1	 1

# 1	 1	 1	 1


# ASCII
| Letter | ASCII Code | Binary | Letter | ASCII Code | Binary |
| --- | --- | --- | --- | --- | --- |
| a | 097 | 01100001 | A | 065 | 01000001 |
| b | 098 | 01100010 | B | 066 | 01000010 |
| c | 099 | 01100011 | C | 067 | 01000011 |
| d | 100 | 01100100 | D | 068 | 01000100 |

American Standard Code for Information Interchange


#FF0000

# Machine Languages
Translators
 Interpreters
 Compilers

	0 & 1

# Programming language types
 Low level
 High level

# Programming language types
 Interpreted
 Compiled

# Algorithms
A finite list of instruction that describes a computation that when executed on a provided set of inputs in order to produce a desired output

# Bugs & Debugging

# Erroneous Program
Might Crash
Might keep running and running and never stops
Might run to completion and produce an answer that might be correct or incorrect

# Types of errors
 Syntax Error
 Runtime Error
 Semantic Error

# Syntax error

Syntax: structure of a program and the rules about that structure

# Runtime Errors (Exceptions)

Error that appears after the program has started running.

# Semantic Error
The program runs perfectly fine but the computer will not show error messages, but will not do the right thing.


# Grace Hopper


# The Best and Worst…
Computers do exactly what they are told and cannot understand what the programmer “intended” to write.

# python

# python
 Guido Van Rossum
 Philosophy
 Code readability
 few lines of code

# Python
 General Purpose
 Interpreted
 High level

# First Python Program

>>> print(‘hello, world’)

---

## Chapter 3: Variables, Data Types and Expressions

# Variables & Types

# Variables
 A variable is a name that refers to a value which is used to refer to the value later

	x = 3
	message = ‘hello’

# Variables
More formally a variable is a named place in the memory where a programmer can store data and later retrieve the data using the variable “name”
Programmers get to choose the names of the variables

# Variables
You can change the contents of a variable in a later statement
x = 12.2
y = 14

12.2
100

x
14
x = 100
y

# Python Variable Name Rules
Must start with a letter or underscore _
Must consist of letters and numbers and underscores
Case Sensitive
Good:    spam    eggs   spam23    _speed
Bad:       23spam     #sign  var.12
Different:    spam   Spam   SPAM

# Reserved Words
You can not use reserved words as variable names / identifiers
and   del   for   is   raise
assert   elif   from   lambda   return
break   else   global   not   try
class   except   if   or   while
continue   exec   import   pass   yield
def   ﬁnally   in   print

Like a dog .... food ... food ...

# Named Constants
They are similar to variables: a memory location that’s been given a name.
Unlike variables their contents shouldn’t change.

	>>> PI = 3.14

# Literals
Literal/unnamed constant/magic number: not given a name, the value that you see is literally the value that you have.

>>> afterTax = 100000 – (100000 * 0.2)

# Expressions
An expression is a combination of values, variables, and operators.

17
x
x +17

# Sentences or Lines
x = 2

x = x + 2

print(x)
Assignment Statement

Assignment with expression

Print statement

# Assignment statement
An assignment statement assigns a value to a variable

	x = 5

	x=x+1

5
x
6
x

# Basic Form

<variable> = <expr>

# Assignment Statements
We assign a value to a variable using the assignment statement (=)
An assignment statement consists of an expression on the right hand side and  a variable to store the result

x = 3.9   *   x   *   (  1   -   x  )

0.6
x
A variable is a memory location used to store a value (0.6).

0.6
0.6
x = 3.9   *   x   *   (  1   -   x  )

0.4

Right side is an expression.  Once expression is evaluated, the result is placed in (assigned to)  x.
0.93

A variable is a memory location used to store a value.  The value stored in a variable can be updated by replacing the old value (0.6) with a new value (0.93).
0.6    0.93

x

x = 3.9   *   x   *   (  1   -   x  )

Right side is an expression.  Once expression is evaluated, the result is placed in (assigned to)  x.
0.93

# Numeric Expressions
| Operator | Operation |
| --- | --- |
| + | Addition |
| - | Subtraction |
| \* | Multiplication |
| / | Division |
| \*\* | Power |
| % | Remainder |
Because of the lack of mathematical symbols on computer keyboards - we use “computer-speak” to express the classic math operations
Asterisk is multiplication
Exponentiation (raise to a power) looks different from in math.

# Numeric Expressions
| Operator | Operation |
| --- | --- |
| + | Addition |
| - | Subtraction |
| \* | Multiplication |
| / | Division |
| \*\* | Power |
| % | Remainder |
>>> xx = 2
>>> xx = xx + 2
>>> print xx
4
>>> yy = 440 * 12
>>> print(yy)
5280
>>> zz = yy / 1000
>>> print(zz)
5
>>> jj = 23
>>> kk = jj % 5
>>> print(kk)
3
>>>print(4 ** 3)
64
4 R 3

5
23
20

3

# Order of Evaluation
When we string operators together - Python must know which one to do first
This is called “operator precedence”
Which operator “takes precedence” over the others
x = 1 + 2 * 3 - 4 / 5 ** 6

# Operator Precedence Rules
Highest precedence rule to lowest precedence rule
Parenthesis are always respected
Exponentiation (raise to a power)
Multiplication, Division, and Remainder
Addition and Subtraction
Left to right
Parenthesis
Power
Multiplication
Addition
Left to Right

1 + 2 ** 3 / 4 * 5
>>> x = 1 + 2 ** 3 / 4 * 5
>>> print(x)
11
>>>

1 + 8 / 4 * 5

1 + 2 * 5

Parenthesis
Power
Multiplication
Addition
Left to Right

1 + 10

11


1 + 2 ** 3 / 4 * 5
>>> x = 1 + 2 ** 3 / 4 * 5
>>> print(x)
11
>>>

1 + 8 / 4 * 5

Note 8/4 goes before 4*5 because of the left-right rule.

1 + 2 * 5

1 + 10
Parenthesis
Power
Multiplication
Addition
Left to Right

11

# Operator Precedence
When writing code - use parenthesis
When writing code - keep mathematical expressions simple enough that they are easy to understand
Break long series of mathematical operations up to make them more clear

Parenthesis
Power
Multiplication
Addition
Left to Right

# What does “Type” Mean?
In CS and programming, a data type(simply type) is a classification of data which tells the compiler or interpreter how the programmer intends to use the data.

# Numeric Types
Whole numbers are represented using the integer (int for short) data type.

Numbers that can have fractional parts are represented as floating point (or float) values.

# Other Numeric Types
 Complex numbers
 Long integers
 Doubles

# String
String is a sequence of characters.

‘hello’
‘a’
‘1’
‘1 + 3’
“’”

# Boolean (bool)

True (1)
 False (0)

# Types in Python
In Python variables, literals, and constants have a “type”
Python knows the difference between an integer number and a string
For example “+” means “addition” if something is a number and “concatenate” if something is a string
>>> ddd = 1 + 4
>>> print(ddd)
5
>>> eee = 'hello ' + 'there'
>>> print(eee)
hello there

# Several Types of Numbers
Numbers have two main types
Integers are whole numbers: -14, -2, 0, 1, 100, 401233
Floating Point Numbers have decimal parts:  -2.5 , 0.0, 98.6, 14.0
There are other number types - they are variations on float and integer
>>> xx = 1
>>> type (xx)
<type 'int'>
>>> temp = 98.6
>>> type(temp)
<type 'float'>

# Mixing Integer and Floating
>>> print(99 // 100)
0
>>> print(99 / 100.0)
0.99
>>> print(99.0 // 100)
0.0
>>> print(1 + 2 * 3 // 4.0 - 5)
-3.0
>>>
When you perform an operation where one operand is an integer and the other operand is a floating point the result is a floating point
The integer is converted to a floating point before the operation

# Type Matters
>>> eee = 'hello ' + 'there'
>>> eee = eee + 1
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: cannot concatenate 'str' and 'int' objects

>>> type(eee)
<type 'str'>
>>> type('hello')
<type 'str'>
Python knows what “type” everything is
Some operations are prohibited
You cannot “add 1” to a string
We can ask Python what type something is by using the type() function.

# Type Conversions
>>> print(float(99 // 100))
0.99
>>> i = 42
>>> type(i)
<type 'int'>
>>> f = float(i)
>>> print(f)
42.0
When you put an integer and floating point in an expression the integer is implicitly converted to a float
You can control this with the built in functions int() and float()
X

# String Conversions
>>> sval = '123'
>>> type(sval)
<type 'str'>
>>> print(sval + 1)
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: cannot concatenate 'str' and 'int'
>>> ival = int(sval)
>>> type(ival)
<type 'int'>
>>> print(ival + 1)
124
You can also use int() and float() to convert between strings and integers
You will get an error if the string does not contain numeric characters

# Mnemonic Variable Names
Since we programmers are given a choice in how we choose our variable names, there is a bit of “best practice”
We name variables to help us remember what we intend to store in them (“mnemonic” = “memory aid”)

x1q3z9ocd = 35.0
x1q3z9afd = 12.50
x1q3p9afd = x1q3z9ocd * x1q3z9afd
print x1q3p9afd
a = 35.0
b = 12.50
c = a * b
print c
hours = 35.0
rate = 12.50
pay = hours * rate
print pay
What is this code doing?

---

## Chapter 4: Boolean Expressions, Logic and Functions

# Boolean Expressions

# <class 'bool'>
True
 False

# Boolean Algebra

# Boolean Expressions
Boolean expressions are expressions that evaluate to one of two Boolean values: True or False.

# Boolean Expressions
Some languages use 0 or 1
Others have a data type
Python has literals True and False

# Boolean Expressions
>>> 2 < 3
True
>>> 3 < 2
False
>>> 5 - 1 > 2 + 1
True

# Boolean Expressions
>>> 3 == 3
True
>>> 3 + 5 == 4 + 4
True
>>> 3 == 5 - 3
False

# Boolean Expressions
>>> 3 <= 4
True
>>> 3 >= 4
False
>>> 3 != 4
True

# Comparison operators


# Logical Operators
Boolean expressions can be combined together using Boolean operators and, or, and not to form larger Boolean expressions.

# Boolean Expressions
>>> 2 < 3 and 4 > 5
False
>>> 2 < 3 and True
True

# Operator Precedence
a and b or c

(a and b) or c
not
a and (b or c)

# Functions

# Function
Function is a named sequence of statements that performs a computation.

# Functions
Functions are “self contained” modules of code that accomplish a specific task.

# Functions

“take in” data  process it  return result

  sqrt ()
4
(an integer)
2

# Function components
 Name of the function
 The sequence of statements that perform a computation

# Abstraction

# Function types
There are two kinds of functions in Python.
Built-in functions that are provided as part of Python type(), float(), int() ...
User-defined functions that we define ourselves and then use

# Built-in Functions
2
abs(-2)

max(7,8,12)
12

Reserved words

# “Calling” functions
Argument

>>> type(32)

Argument

# Arguments
An argument is a value we pass into the function as its input when we call the function
We use arguments so we can direct the function to do different kinds of work when we call it at different times
We put the arguments in parenthesis after the name of the function

# Modules


A file that contains a collection of related functions

# Import
Includes predefined functions in workspace
>>> import math
>>> math.sqrt(20)

A file that contains a collection of related functions

# Math module
>>> math.exp(math.log(10))
>>> math.cos(60)

# Definitions and Uses
Once we have defined a function, we can call (or invoke) it as many times as we like
This is the store and reuse pattern

# Function Definition(Python)

def function_name (parameters):
	statements

A parameter is a variable which we use in the function definition that is a “handle”
Parameter: 0 or more

# Say hello twice
def hello_twice():
	print(‘hello’)
	print(‘hello’)

>>> hello_twice()

def print_chorus():
 print(“Girls hit your hallelujah …Woo!.”)
	print(“Girls hit your hallelujah …Woo!.”)
 print(“Cause uptown funk gon’ give it to you”)

>>> print(‘Break it down’)
>>> print_chorus()
>>> print_chorus()

# Square function
def square(num):
	print(num*num)

>>> square()
>>> square(3)

---

## Chapter 5: Control Flow and Conditional Execution

# Conditionals

# Variables: Scratch


# Review
def print_twice(param):
	print(param)
	print(param)

# Review
>>> print_twice(1)
>>> print_twice(‘test’)
>>> print_twice(math.pi)
>>> print_twice(‘test ’ * 4)

# Flow of Execution
Function definitions do not alter the flow of execution of the program, but statements inside the function are not executed until the function is called.

# Review: Flow of Execution
Function calls are like a detour in the flow of execution.

instead of going to the next statement, the flow jumps to the first line of the called function, executes all the statements there, and then comes back to pick up where it left off.

# Flowcharts

|  | The start or end of the program. There may be more than one way to complete the algorithm and there may be more than one end box. |
| --- | --- |
|  | A process, that is doing something for example calculating something. |
|  | An input or output, for example: Input num1 |
|  | A decision, YES or NO, or a choice of paths, for example: Is it a weekday? |
|  | When a flowchart will not fit onto a single page we use this shape to show how the sections of the flowchart connect together. |

Flowcharts are used to plan programs before they are created.

# Branching programs

# Review: Boolean Expressions
A Boolean expression is an expression that evaluates to produce a result which is a Boolean value.

# Review: Boolean Expressions
>>> 4%2 == 0
>>> 12 and True
True
True

# Review: Boolean Expressions
>>> 1 == True
>>> 12 == True
True
False

# Truth tables


# ‘till now…
Straight line programs: executed one statement after another in order in which they appear and stop when they are out of statements


# Conditionals

# Branching programs

The simplest branching statement is a conditional


# Conditional Execution
Writing programs, we almost always need the ability to check conditions and change the behavior of the program accordingly.

# Conditional statements
Three parts
 A test: an expression that evaluates to True/False
 A block of code that is executed to True
 [Optional] block of code that is executed if the test evaluates to False

Code
Test
True
Block
False
Block
Code

# Simple conditional statement

if(<conditional statements>):
	<block of code>

# Tea brewing algorithm
Wait 5min
if (					):
	turn off the stove
tea boils

# Indentation
 Python uses indentation to delineate blocks of code
 Most other programming languages use {}

# example
Start
Input number
Divisible by two Pseudocode
Accept integer input from user

If input divisible by two:
	output ‘YES’

Is Divisible?
False
True
output ‘YES’
End

# Example
>>> dividend, divisor = eval(input(‘Enter number: ’))
>>> print(dividend/divisor)

What problem do you anticipate?

# Example

>>> dividend, divisor = eval(input(‘Enter number: ’))
>>> if (divisor != 0):
		print(dividend/divisor)


# Cases

if(1):
	print(‘one’)

if(0):
	print(‘zero’)

# Alternative Executions
if(<conditional statements>):
	<if block>
else:
	<else block>

# Odd–Even program
Input: x

if (x is divisible by 2):
	print(‘x is even’)
else:
	print(‘x is odd’)

# Chained conditionals
if(<conditional statements>):
	<if block>
elif(<conditional statements>):
	<elif block>
else:
	<else block>

Divisible by 2,3,6

# Nested Conditionals
if(<conditional statements>):
	if(<conditional statements>):
		<if block>
	else:
		<else block>
else:
	<else block>

Combination of moves in games

# Nested Conditionals

if 0 < x:
	if x % 2 == 0:
		print(‘x is positive even number’)

# Common Errors

# Common Errors
if((Value > 0) or (Value <= 10)):
	print(Value)

if((Value > 0) and (Value <= 10)):
	print(Value)

# Common Errors

if((Value < 0) and (Value > 10)):
	print(Value)

# Floating point

if((1.11 - 1.10) == (2.11 -2.10)):
	print(‘’)

---

## Chapter 6: Iteration, While Loops and Repetition

# Conditionals

# Review: Branching programs

The simplest branching statement is a conditional

# Review: Conditional Execution
Writing programs, we almost always need the ability to check conditions and change the behavior of the program accordingly.

Code
Test
True
Block
False
Block
Code

# Grade
Score >=85 			==> A
Score >=70 but <85 	==> B
Score >=50 but <70 	==> C
Score >=40 but <50 	==> D
Score < 40 				==> F

# Defensive Programming
Defensive programming is a form of defensive design intended to ensure the continuing function of a piece of software under unforeseen circumstances.

# Input

>>> name = input(‘Enter your name: ’)

# function
Check the validity of passed arguments

def add(a,b):
	print(a+b)

# assert
def add(a,b):
	# check if a is int/float
	# check if b is int/float
	print(a+b)

# Updating variables
>>> x = 5
# increment by one
>>> x = x + 1
# decrement by one
>>> x = x - 1
>>> x += 1
>>> x += 1

# Case study: Average of three numbers
 Average of 5
 Average of 20
 Average of 1000
 Average of unknown number

# CASE Study: Find factors of a given number
 Given the number is 2
 Given the number is 10
 Given the number is 124,989    …

# Iteration

#
Computers/ Robots/ Computing machines are often used to automate repetitive tasks.

Repeating identical or similar
tasks without making errors is something that computers do well and people do
poorly.

# Repetition Structures
The  repetition structure causes a statement or set of statements to execute repeatedly.

# Iteration
A generic iteration mechanism begins with a test.
If the test evaluates to True, program executes the body once and then goes back to reevaluate the test.
CHECK

Statements

# loops
 A loop repeats a sequence of statements
 Two types
 while loop
 for loop

# The while loop
The while Loop is a Pretest Loop, which means it tests its condition before performing an iteration.

# The while loop

while(condition):
	<statements>


# The while loop
More formally, here is the flow of execution for a while statement:
Evaluate the condition, yielding True or False.
If the condition is false, exit the while statement and continue execution at the next statement.
If the condition is true, execute the body

# Example 1

Print numbers from 1 to 10

# Example 2: Accumulator Loop

Add numbers from 1 to 100

# break statement
Sometimes you don’t know it’s time to end a loop until you get half way through the body.

Roomba

# break statement

When break statement is executed, the current loop iteration is stopped and the loop is exited.


# continue statement
When continue statement is executed, the current loop iteration is skipped and the loop execution resumes with the next iteration of the current, innermost loop statement.

# pass statement
In Python, every def statement, if statement, or loop statement must have a body (i.e., a nonempty indented code block).

# pass statement
if n % 2 == 0:
	pass
else:
	print(n)

# Infinite loops

while(True):
	print(‘hi’)


# Infinite loops

x = 1
while(x > 0):
	x += 1

# Common pitfalls

# Unintentional infinite loop
x = 10
while(x > 0):
	x += 1

# Off-by-one error(OB1)
A logic error that occurs in programming when an iterative loop iterates one time too many or too few.

# OB1
Arises when programmer makes mistakes such as
 Fails to take account that a sequence starts at zero rather than one
 Using “less than or equal to” in place of “less than” …

---

## Chapter 7: For Loops, Ranges and Advanced Functions

# Review
Computing machines are often used to automate repetitive tasks.

Repeating identical or similar
tasks without making errors is something that computers do well and people do
poorly.

# Review: loops
 A loop repeats a sequence of statements
 Two types
 while loop
 for loop

# Sentinel Loops
A sentinel loop continues to process data until reaching a special value that signals the end.
This special value is called the sentinel.
4

# from … import …
>>> from math import sqrt

>>> from math import *

>>> sqrt(25)
Difference? 	Problem?

n = 0

n > 0 ?
What does this loop do?

Yes

n = 0
while (n > 0):
    print(‘Hi’)
print('Dry off!‘)
No
print(‘Hi’)

print('Dry off!‘)

# range
>>> range(1)
>>> range(25)
>>> range(2,10,2)

# The for Loop

for <variable> in range(<number of times>):
	<statement-1>
   <statement-2>
   …
   <statement-n>

loop body

# Using the Loop Variable
The loop variable picks up the next value in a sequence on each pass through the loop
The expression range(n) generates a sequence of ints from 0 through n – 1

>>> for x in range(5):
		print(x)

# Counting from x through n
The expression range(low, high) generates a sequence of ints from low through high – 1

for x in range(1, 6):
	print(x)

# Accumulator Loop: Summation
Compute and print the sum of the numbers between 1 and 5, inclusive

total = 0
for n in range(1, 6):
   total = total + n
print(total)

total += n

# Nested loops

for <variable1> in range(<number of times>):
	for <variable2> in range(<number of times>):
		<statement-1>
   		<statement…>

# Nested loops

while (<Condition>):
	while (<Condition>):
		<statement-1>
   	<statement…>

# Break & continue
for letter in 'Python':        	if letter == 'h':    		break   	print(letter)

for letter in 'Python':
 	if letter == 'h':      		continue 	print(letter)

# Yet another import Statement

>>> import turtle as t

>>> bob = t.Turtle()

# Fruitful functions

# Functions
The importance of functions:
Break your code into separate, independent parts that will work together to solve the ultimate problem (DECOMPOSITION).
Hide the details of your computation as long as you know what it produces (ABSTRACTION)

# Cont…
The advantages of functions:
Break your code into simpler independent modules
These modules can be reused as many times as you like
And they need to be debugged only once
Keep your code more organized and easier to understand

# Function python
Defining functions:


# Void Functions
In several programming languages derived from C and Algol68, is the type for the result of a function that returns normally, but does not provide a result value to its caller.

# Void Functions

def greeting(name):
	print(‘hi ’,name)

greeting(‘Haven’)
greeting(‘Robel’)

def maximum(x,y):
	if(x > y):
		print(x)
	else:
		print(y)
maximum(2,4)

# <type ‘Nonetype’>
Void functions might display something on the screen or have some other effect, but they don’t have a return value.

If you try to assign the result to a variable, you get a special value called None.

# Fruitful functions

A function that returns a value to the caller

# Return vs print

def greeting(name):
	print(‘hi ’,name)

greeting(‘Haven’)
greeting(‘Robel’)

def maximum(x,y):
	if(x > y):
		return x
	else:
		return y
maximum(2,4)

# Dead code
When a return statement executes, the function terminates without executing any subsequent statements.
Code that appears after a return statement, or any other place the flow of execution can never reach, is called dead code.

# scope
A variable can have something called a ‘scope’.  This refers to its accessibility.

# Scope
A variable can be declared in one of two ways…
When it is accessible from all parts of a program
(ie. Anywhere!)

A GLOBAL Variable.
When it is accessible ONLY within a function/procedure.

A LOCAL Variable.

# Example programs

name = ‘Mary’
print(name)

def Greeting():
    name = 'Mary Poppins‘
    print(name)

print(name)

# Global Variables
name = 'Mary Poppins‘

def Greeting():
	print(name)

Greeting()

# Global vs Local
num = 6

def multi():
	num = 6
	num = num * 3
	print(‘local variable: ’, num)

multi()
print(‘Global: ’, num)

Local: ?
Global: ?
18
6

# Best programming practices

Avoid use of global variables in favour of local variables.

# Optional(Default) Parameters
def print_name(fname, lname, reverse):
	if(reverse):
		print(lname, ‘, ’, fname)
	else:
		print(fname, lname)

# Function calling: positional
The most common method, which is the only one we have used thus far, is called positional—the first parameter is bound to the first actual argument, the second to the second

# Function calling: keyword arguments
Python also supports keyword arguments, in which formals are bound to actuals using the name of the formal parameter

# Boolean Functions
Functions that return Boolean values
Convenient for hiding complicated tests

# Boolean functions
def is_divisible_by_2(num):
	if(num % 2 == 0)
		return True
	else:
		return False

# Boolean functions
def is_divisible_by_2(num):
	if(num % 2 == 0)
		return True
	return False

# Debugging in Functions
 Preconditions
 Postconditions

Postcondition: effect of the function/side effects

# Common Problems
 Indentation issues
 Parameter naming and passing

---

## Chapter 8: Recursion and String Manipulation

# FUNCTIONS


# OPTIONAL PARAMETERS
def func_name(par1, par2 = 5):
	return par1 + par2

>>> func_name(4,9)
>>> func_name(3)


# REVIEW: FUNCTION CALLING:

Positional
Keyword arguments


# REVIEW: BOOLEAN FUNCTIONS
Functions that return Boolean values

def is_divisible_by_2(num):
	return (num % 2 == 0)


# REVIEW: DEBUGGING IN FUNCTIONS
 Preconditions
 Postconditions

Postcondition: effect of the function/side effects

# UNKNOWN NUMBER OF PARAMETERS
def add(*params):
	total = 0
	for i in params:
		total += I
	return total

>>> add(1,2,4)
>>> add(1,2,3,5,4,3)


# RECURSION
A technique that solves problem by solving smaller versions of the same problem!

Induction proof


#


# RECURSION
When you turn this into a program, you end up with functions that call themselves (i.e., recursive functions)

Induction proof

# FACTORIAL

n! = n*(n-1)!

f(n) = n * f(n-1)


# FACTORIAL


# RECURSION PARTS
Base case
Recursive Call


# FIBONACCI SEQUENCE

0, 1, 1, 2, 3, 5, 8, 13…


# FIBONACCI SEQUENCE

f(n) = f(n-1) + f(n-2)
	  where f(0) = 1
  f(1) = 1


# INFINITE RECURSION


# STRINGS


# STRING DATA-TYPE
A string is a sequence of characters
A string literal uses quotes  'Hello' or “Hello”
For strings, + means “concatenate”
When a string contains numbers, it is still a string


# TRAVERSING
Computations on string start at the beginning, select each character in turn, do something to it, and continue until the end. This pattern of processing is called a traversal.

Elegant way

# TRAVERSAL: FOR LOOP
for letter in 'banana' :
    print(letter)
b
a
n
a
n
a
The iteration variable “iterates” though the string and the block (body) of code is executed once for each value in the sequence


# LOOKING INSIDE STRINGS

We can get at any single character in a string using an index specified in square brackets

>>> fruit = 'banana‘
>>> print(fruit[1])
b
a
n
a
n
a
0
1
2
3
4
5


# INDEXING OPERATOR
The expression in brackets is called an index

The index value must be an integer and starts at zero

>>> fruit = 'banana'
>>> letter = fruit[1]
>>> print(letter)
a
>>> n = 3
>>> w = fruit[n - 1]


# INDEXING OPERATOR
b
a
n
a
n
a
0
1
2
3
4
5
>>> print(fruit[-1])


# LENGTH OF A STRING
The len function, when applied to a string, returns the number of characters in a string

>>> fruit = "banana"
>>> len(fruit)
6


# TRAVERSAL: WHILE LOOP
fruit = ‘banana’

ctr = 0
while (				 ):	print(		    ):
	ctr+=1
ctr < len(fruit)
fruit[ctr]


# SLICING STRINGS
A substring of a string is obtained by taking a slice.
Need to get continuous section of a string using a colon operator.


# SLICING

>>> s = 'Monty Python'
>>> print(s[0:2])
Mo

M
o
n
t
y

P
y
t
h
o
n
0
1
2
3
4
5
6
7
8
9
10
11
print(s[:2])

If we leave off the first number or the last number of the slice, it is assumed to be the beginning or end of the string respectively

# SLICING

>>> print s[8:]
thon
>>> print s[:]
Monty Python

M
o
n
t
y

P
y
t
h
o
n
0
1
2
3
4
5
6
7
8
9
10
11


# A CHARACTER TOO FAR
>>> zot = 'abc'
>>> print(zot[5])

Traceback (most recent call last):
File "<stdin>", line 1, in <module> IndexError: string index out of range


# A CHARACTER TOO FAR
>>> zot = 'abc'
>>> print(zot[len(zot)])

Traceback (most recent call last):
File "<stdin>", line 1, in <module> IndexError: string index out of range

---

## Chapter 9: Data Structures — Lists and Sequences

# STRINGS


# REVIEW: UNKNOWN NUMBER OF PARAMETERS
def add(*params):
	total = 0
	for i in params:
		total += I
	return total

>>> add(1,2,4)
>>> add(1,2,3,5,4,3)


# REVIEW: RECURSION


# LOOPING AND COUNTING

Write a function that can determine the number of occurrence of given letter inside a string?


# SEARCH PROBLEM


# SEARCHING INSIDE A STRING

Find the first occurrence of a given letter inside a string


# STRING METHODS
A method is similar to a function

It takes arguments and returns a value but the syntax is different.


# EXAMPLES
>>> word = ‘test’
>>> new_word = word.lower ()
>>> print(new_word)
>>> print(word)

Method call: Invocation

# EXAMPLES
>>> word = ‘test test’
>>> i = word.find(‘s’)
>>> print(i)


# EXAMPLES
>>> word = ‘test test’
>>> i = word.find(‘es’)
>>> print(i)


# EXAMPLES
>>> word = ‘test test’
>>> i = word.find(‘es’, 3)
>>> print(i)


# EXAMPLES
>>> word = ‘test test’
>>> i = word.find(‘t’, 1, 3)
>>> print(i)


# THE in OPERATOR
>>> ‘n’ in ‘banana’
True
>>> ‘seed’ in ‘banana’
False


# EXAMPLE

Write a function that can determine letters that are shared by two words?


# DATA STRUCTURES
Data Structures In Python


# WHAT ARE DATA STRUCTURES?
In computer science, a data structure is a particular way of organizing data in a computer so that it can be used efficiently.


# LISTS
A List is a kind of Collection
A collection allows us to put many values in a single “variable”
A collection is nice because we can carry many values around in one convenient package.


# LIST

friends_cast = [‘Chandler’, ‘Phoebe’, ‘Joey’]

marks = [12, 15, 9, 12, 20, 19]


# LIST CONSTANTS
 List constants are surrounded by square brackets and the elements in the list are separated by commas.
 A list element can be any Python object - even another list
 A list can be empty


# EXAMPLES
>>> print([1, 24, 76] )
[1, 24, 76.0]
>>> print(['red', 'yellow', 'blue'] )
['red', 'yellow', 'blue']


# EXAMPLES
>>> print([ 1, [5, 6], 7] )
[1, [5, 6], 7]
>>> print( [] )
[]


# GO THROUGH THE LIST
for i in [5, 4, 3, 2, 1] :
    print(i)
print('Blastoff!‘)

5
4
3
2
1
Blastoff!


# LIST TRAVERSAL

Simpsons= [‘Homer', ‘Bart', ‘Lisa’, ‘Marge’]

for character in Simpsons:
     print(character, ‘is in the Simpsons family’)


# HOW LONG IS A LIST?
The len() function takes a list as a parameter and returns the number of elements in the list
Actually len() tells us the number of elements of any set or sequence (i.e. such as a string...)


# LIST TRAVERSAL
car_makers = [‘Ford’, ‘Toyota’, ‘BMW’]

for car in range(len(car_makers)):
     print(car_makers[car])


# THE in OPERATOR

>>> ‘Ford’ in [‘Ford’, ‘Toyota’, ‘BMW’]
True


# LIST OPERATIONS
>>> a = [1, 2, 3]
>>> b = [4, 5, 6]
>>> c = a + b
>>> c
[1, 2, 3, 4, 5, 6]


# LIST OPERATIONS
>>> [0] * 4
[0, 0, 0, 0]
>>> [1, 2, 3] * 3
[1, 2, 3, 1, 2, 3, 1, 2, 3]


# LIST OPERATIONS
>>> a_list = ["a", "b", "c", "d", "e", "f"]
>>> a_list[1:3]
['b', 'c']
>>> a_list[:4]
['a', 'b', 'c', 'd']


# LOOKING INSIDE LISTS
Just like strings, we can get at any single element in a list using an index specified in square brackets

‘Homer’
‘Bart'
‘Lisa’
‘Marge’
0
1
2
3

‘Bart', ‘Lisa’, ‘Marge’

# UPDATING LISTS
friends_cast = [‘Chandler’, ‘Phoebe’, ‘Joey’]

friends_cast[0] = ‘Monica’

print(friends_cast)


# MUTABILITY: LIST VS STRINGS
Lists are mutable
Strings are immutable


# STRING
>>> my_string = "TEST"
>>> my_string[2] = "X"

Traceback (most recent call last):
  File "<pyshell#2>", line 1, in <module>
    my_string[2] = 'x'
TypeError: 'str' object does not support item assignment


# DEEP COPY & SHALLOW COPY
>>> game_of_thrones = [‘Targaryen’, ‘Stark’]
>>> got_families = game_of_thrones
>>> got_families[0] = ‘Baratheon’
>>> print(game_of_thrones)

---

## Chapter 10: Data Structures — Dictionaries and Tuples

# Data structures

# Review: What are data structures?
In computer science, a data structure is a particular way of organizing data in a computer so that it can be used efficiently.


# Review: lists
A List is a kind of Collection
A collection allows us to put many values in a single “variable”
A collection is nice because we can carry many values around in one convenient package.

# Review: List operations
>>> a_list = ["a", "b", "c", "d", "e", "f"]
>>> a_list[1:3]
['b', 'c']
>>> a_list[:4]
['a', 'b', 'c', 'd']

# Review: Looking Inside Lists
Just like strings, we can get at any single element in a list using an index specified in square brackets

‘Homer’
‘Bart'
‘Lisa’
‘Marge’
0
1
2
3

‘Bart', ‘Lisa’, ‘Marge’

# List arguments

Write a function that determines the length of a list

# List append method
>>> t1 = [1, 2]
>>> t1.append(3)
>>> print(t1)
[1, 2, 3]

# List insert method
>>> l = [1, 2]
>>> l.insert (2,4)
>>> print(l)
[1, 2, 4]

# List insert method
>>> l = [1, 2]
>>> l.insert (23,4)
>>> print(l)
[1, 2, 4]

# List insert method
>>> l = [1, 2]
>>> l.insert (1,4)
>>> print(l)
[1, 4, 2]

# List methods
>>> t1 = [1, 2]
>>> t2 = t1.append(3)
>>> print(t1)
[1,2,3]
>>> print(t2)
None

# ‘is’ operator
Given two strings

a = ‘banana’
b = ‘banana’

We know that a and b both refer to a string.
Are they referring to the same string?


# ‘is’ operator
>>> a = 'banana'
>>> b = 'banana'
>>> a == b
True

# ‘is’ operator
>>> a = 'banana'
>>> b = 'banana'
>>> a is b
True

# ‘is’ operator
>>> a = [1,2]
>>> b = [1,2]
>>> a == b
True

# ‘is’ operator
>>> a = [1,2]
>>> b = [1,2]
>>> a is b
False

In this case we would say that the two lists are equivalent, because they have the same elements, but not identical

# Debugging
t = [1,2]
# add element 4 to list t
t.append([4])
t = t.append(4)
t + [4]
t = t + 4

# Dictionary

# Dictionary
Dictionary is like a list, but more general.
In a list, the indices have to be integers;  in a dictionary they can be (almost) any type.

# Dictionary
A dictionary is a mapping between KEYS and set of Values.
		key-value pair

# Dictionary
Lists index their entries based on the position in the list.
Dictionaries are like bags - no order
So we index the things we put in the dictionary with a “lookup tag”

# Dictionary
>>> eng2sp = {}
>>> eng2sp = dict()
>>> print(eng2sp)
{}

# Dictionary
>>> eng2sp[‘one’] = ‘uno’
>>> print(eng2sp)
{'one': 'uno'}

# Dictionary

>>> eng2sp = {'one': 'uno', 'two': 'dos', 'three': 'tres'}
>>> print(eng2sp)
{'three': 'tres', 'two': 'dos', 'one': 'uno'}

# Dictionary
The order of the key-value pairs is not the same. In fact, if you type the same example on your computer, you might get a different result. In general, the order of items in a dictionary is unpredictable.

# Dictionary
>>> item_price = {}
>>> item_price[‘milk’] = 10
>>> print(item_price[‘milk’])
10

# Dictionary
>>> item_price = {}
>>> item_price[‘milk’] = 10
>>> print(item_price[‘sugar’])
KeyError: ‘sugar’

# Dictionary
>>> number_of_days = {‘January’: 31, ‘February’: 28, ‘March’: 31}
>>> number_of_days[‘February’]

# Debugging
Key Error
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
KeyError: 3

# Iteration on Dictionaries
Even though dictionaries are not stored in order, we can write a for loop that goes through all the entries in a dictionary - actually it goes through all of the keys in the dictionary and looks up the values

# iteration
eng2sp = {'one': 'uno', 'two': 'dos', 'three': 'tres'}

for k in eng2sp:
	print(k)

# Iteration: Values
eng2sp = {'one': 'uno', 'two': 'dos', 'three': 'tres'}

for k in eng2sp:
	print(eng2sp[k])

# ‘in’ operator
>>> eng2sp = {'one': 'uno', 'two': 'dos', 'three': 'tres'}

>>> ‘one’ in eng2sp
True
>>> ‘uno’ in eng2sp
False

# Demo: character counter(Histogram)

# What data types can not be keys

# Retrieving lists of Keys and Values
>>> counts.keys()
>>> counts.values()
>>> counts.items()

---

## Chapter 11: File Processing, Exceptions and Defensive Programming

# Data Structure

# Review: Dictionary
A dictionary is a mapping between KEYS and set of Values.
		key-value pair

# Review
>>> eng2sp[‘one’] = ‘uno’
>>> print(eng2sp)
{'one': 'uno'}

# Tuples

# Tuples
 a sequence of values
 are indexed by integers
>>> t = ‘a’, ‘b’, ‘c’

# Tuples

 Tuples are Immutable

>>> t = (‘a’, ‘b’, ‘c’)

# Tuples
>>> t1 = ()
>>> t2 = (1)
= (1,)

# Tuple Assignment

a, b = 2, 3

# Return values

def divmod(a,b):
	return a//b, a%b

# Dictionaries & Tuples
>>> d = {‘a’: 12, ‘b’: 18}
>>> t = d.items()
[(‘b’,18),(‘a’,12)]

# Looping
for key, value in d.items():
	print(key, ‘==>’, value)

# Files
Lab07: File and Exceptions

What
Next?
  Software
Input
and Output
Devices
 Central
 Processing
 Unit

 Secondary
 Memory

if x< 3: print

  Main
  Memory

# Persistence?
 Programs we wrote so far: Transient
 Others: Persistent
 Web servers
 Operating Systems

# File
Every computer system uses FILES to save things from one computation to the other

# File Processing
Simplest way for programs to maintain their data is by reading and writing text files

# File processing
Each operating system comes with its own file system for creating and accessing files but Python achieves OS-independence by accessing files through something called FILE HANDLE.

# Opening a File
Before we can read/write the contents of the file we must tell Python which file we are going to work with and what we will be doing with the file

# Opening a File
This is done with the open() function
open() returns a “file handle” - a variable used to perform operations on the file

# open()
handle = open(filename, mode)

filename is a string
mode is optional and should be 'r' if we are planning reading the file and 'w' if we are going to write to the file
returns a handle use to manipulate the file

# open modes
 r
 w
 a
 rb
 wb

# Text file processing
A text file can be thought of as a sequence of lines
A text file has newlines at the end of each line

# File handle as a sequence
A file handle open for read can be treated as a sequence of strings where each line in the file is a string in the sequence
We can use the for statement to iterate through a sequence
Remember - a sequence is an ordered set

# File handle as a sequence
xfile = open('mbox.txt‘, ‘r’)
for line in xfile:
    print(line)
xfile.close()

# open()
mode read(‘r’)
mode write(‘w’)
fh.read()
fh.readline()
fh.readlines()

fh.write(s)
fh.writelines(l)

# close()

# IOError: [Errno 2]
>>> fhand = open('stuff.txt')
Traceback (most recent call last):
File "<stdin>", line 1, in <module>
IOError: [Errno 2]
No such file or directory: 'stuff.txt'

# PermissionError: [Errno 13]
>>> fout = open('/etc/passwd', 'w')
PermissionError: [Errno 13]
Permission denied: '/etc/passwd'

# Exception
 Exception is ‘something that does not conform to the norm’
 The error message: stack traceback or traceback

# Exceptions
 Exceptions are run time errors
 Unhandled exception: an exception that causes a program to terminate… raise

# Exception Handling
 Dealing with the exception

try:
	<body>
except <exceptionType>:
	<handle>
Code that may raise an exception

# Sample code

success_failure_ratio = num_success/num_failure
print(‘success/failure = ’, success_failure_ratio)
		What problem do you anticipate?

# Division by zero
try:
	success_failure_ratio = num_success/num_failure
	print(‘success/failure = ’, success_failure_ratio)
except IOError:
	print(‘Division by zero’)

ZeroDivisionError:

# Back to opening file
try:
	open(file_name, ‘r’)
except IOError:
	print(‘File’, file_name, ‘does not exist’)

# The try ... except Clause
try:
	<body>
except <exceptionType1>:
	<handle1>
except <excetpionType2> as e:
	<handle2>
finally:
	<finalize_process>

# Best practices

# Defensive programming
x,y = eval(input(‘Enter integers x,y for the ratio x/y: ’)
try:
	print(‘x,y = ’, x/y)
except ZeroDivisionError:
	print(‘File’, file_name, ‘does not exist’)
except:
	raise ValueError(‘Bad Arguments’)

# Defensive programming
x,y = eval(input(‘Enter x,y for the ratio x/y: ’)
assert type(x)==int and type(y) == int
try:
	print(‘x,y = ’, x/y)
except ZeroDivisionError:
	print(‘File’, file_name, ‘does not exist’)

# Unit testing

if __name__ ==  ‘__main__’:
	test()

---

*© Tamhero. All rights reserved.*
