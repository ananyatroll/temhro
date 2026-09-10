# Disclaimer

The content provided in these notes is for educational and informational purposes only. While every effort has been made to ensure accuracy, the author makes no warranties or guarantees regarding the completeness, reliability, or suitability of the information contained herein. Users are encouraged to verify all information with original sources and consult qualified professionals before making decisions based on this material. This content may not be reproduced, distributed, or transmitted without prior written permission from Tamhero.

---










Out lines
| 1 Propositional     | Logic and         | Set Theory   |
| ------------------- | ----------------- | ------------ |
| Logical             | connectives       |              |
| Compound            | (or complex)      | propositions |
| Tautology           | and contradiction |              |
| 2 Open propositions | and               | quantifiers  |
Quantifiers
| Argument      | and Validity |     |
| ------------- | ------------ | --- |
| 3 The concept | of a set     |     |


| Propositional | Logic | and Set | Theory |     |
| ------------- | ----- | ------- | ------ | --- |
Definition
A proposition (or statement) is a sentence which has a truth value
| (either       | True or False | but   | not both).        |                  |
| ------------- | ------------- | ----- | ----------------- | ---------------- |
| The following | sentences     |       | are propositions. |                  |
|               | (a) Athlet    | Haile | Gebresilasie      | is an Ethiopian. |
(b) 2+3=7
| The following | sentences |     | are not propositions. |     |
| ------------- | --------- | --- | --------------------- | --- |
|               | (a) Shut  | up. |                       |     |
(b) x+3=7
Every proposition has a truth value, namely true (denoted by T )
or false (denoted by F). Propositions are denoted by small letters
| such | as p, q, r, and | so on. |     |     |
| ---- | --------------- | ------ | --- | --- |


Logicalconnectives
Logical connectives
(a) Conjunction
When two propositions are joined with the connective â€and,â€ the
proposition formed is a logical conjunction. â€œandâ€ is denoted by
â€âˆ§â€. So, the logical conjunction of two propositions, p and q, is
written as:
pâˆ§q read as â€œ p and q,â€ or â€œ p conjunction q â€. p and q are
called the components of the conjunction. pâˆ§q is true if and only
if p is true and q is true.
p q pâˆ§q
T T T
T F F
F T F
F F F


Logicalconnectives
Example
| Consider | the following propositions: |         |                    |         |
| -------- | --------------------------- | ------- | ------------------ | ------- |
| p: 3 is  | an odd number.              | (True)  |                    |         |
| q: 27 is | a prime number.             | (False) |                    |         |
| r: Addis | Ababa is the capital        | city of | Ethiopia. (True)   |         |
| pâˆ§q:     | 3 is an odd number          | and 27  | is a prime number. | (False) |
pâˆ§r: 3 is an odd number and Addis Ababa is the capital city of
| Ethiopia. | (True) |     |     |     |
| --------- | ------ | --- | --- | --- |


Logicalconnectives
Disjunction
(b) disjunction
When two propositions are joined with the connective â€or,â€ the
proposition formed is a logical conjunction. â€œandâ€ is denoted by
â€âˆ¨â€. So, the logical disnjunction of two propositions, p and q, is
written as:
pâˆ¨q read as â€œ p or q,â€ or â€œ p disjunction q â€. p and q are called
the components of the conjunction. pâˆ¨q is false if and only if p is
false and q is false.
p q pâˆ¨q
T T T
T F T
F T T
F F F


Logicalconnectives
Example
| Consider | the following propositions: |                   |                |
| -------- | --------------------------- | ----------------- | -------------- |
| p: 3 is  | an odd number.              | (True)            |                |
| q: 27 is | a prime number.             | (False)           |                |
| r: Addis | Ababa is the capital        | city of Ethiopia. | (True)         |
| pâˆ¨q:     | 3 is an odd number          | or 27 is a prime  | number. (True) |
pâˆ¨r: 3 is an odd number or Addis Ababa is the capital city of
| Ethiopia. | (True) |     |     |
| --------- | ------ | --- | --- |


Logicalconnectives
Implication
(c) Implication
When two propositions are joined with the connective â€implies,â€
the proposition formed is a logical implication. â€œimpliesâ€ is
| denoted       | by â€ =â‡’ â€. | So, the logical | implication | of two |
| ------------- | ---------- | --------------- | ----------- | ------ |
| propositions, | p and q,   | is written      | as:         |        |
p =â‡’ q read as â€œ p implies q,â€ p =â‡’ q is false if and only if p
| is true and | q is false. |     |        |     |
| ----------- | ----------- | --- | ------ | --- |
|             |             | p q | p =â‡’ q |     |
T T T
T F F
F T T
F F T


Logicalconnectives
Example
| Consider   | the following propositions: |                   |                    |
| ---------- | --------------------------- | ----------------- | ------------------ |
| p: 3 is an | odd number. (True)          |                   |                    |
| q: 27 is   | a prime number.             | (False)           |                    |
| r: Addis   | Ababa is the capital        | city of Ethiopia. | (True)             |
| p =â‡’ q:    | 3 is an odd number          | implies 27        | is a prime number. |
(False)
p =â‡’ r: 3 is an odd number implies Addis Ababa is the capital
| city of Ethiopia. | (True)                |             |     |
| ----------------- | --------------------- | ----------- | --- |
| p =â‡’ q            | can also be expressed | as follows. |     |
| If p,             | then q.               |             |     |
| q if              | p.                    |             |     |
| p only            | if q.                 |             |     |
| p is              | sufficient for q.     |             |     |
| q is              | necessary for p       |             |     |


Logicalconnectives
Bi-implication
(d) Bi-implication
When two propositions are joined with the connective â€bi-implies,â€
the proposition formed is a logical bi-implication. â€œBi-implicationâ€
| is denoted    | by â€â‡”â€.  | So, the logical | bi-implication | of two |
| ------------- | -------- | --------------- | -------------- | ------ |
| propositions, | p and q, | is written      | as:            |        |
p â‡” q read as â€œ p bi-implies q,â€ p â‡” is false if and only if p and q
| have different | truth | values. |       |     |
| -------------- | ----- | ------- | ----- | --- |
|                |       | p q     | p â‡” q |     |
|                |       | T T     | T     |     |
|                |       | T F     | F     |     |
|                |       | F T     | F     |     |
|                |       | F F     | T     |     |


Logicalconnectives
Example
| Consider | the following | propositions:  |                   |                       |
| -------- | ------------- | -------------- | ----------------- | --------------------- |
| p: 3 is  | an odd        | number.        | (True)            |                       |
| q: 27 is | a prime       | number.        | (False)           |                       |
| r: Addis | Ababa         | is the capital | city of Ethiopia. | (True)                |
| p â‡” q:   | 3 is an       | odd number     | bi-implies        | 27 is a prime number. |
(False)
p â‡” r: 3 is an odd number bi-implies Addis Ababa is the capital
| city of   | Ethiopia.  | (True)          |             |     |
| --------- | ---------- | --------------- | ----------- | --- |
| p â‡” q     | can also   | be expressed    | as follows. |     |
| p if      | and only   | if q.           |             |     |
| p is      | sufficient | and necessary   | for q.      |     |
| q is      | sufficient | and necessary   | for p.      |     |
| p implies |            | q and q implies | p           |     |
| p is      | equivalent | to q.           |             |     |


Logicalconnectives
Negation
Given any proposition p, we can form the proposition Â¬p called the
negation of p. The truth value of Â¬p is F if p is T and T if p is F.
Example
| Let p:    | Addis Ababa | is the capital  | city of Ethiopia. | (True)  |
| --------- | ----------- | --------------- | ----------------- | ------- |
| Â¬p: Addis | Ababa is    | not the capital | city of Ethiopia. | (False) |


Compound(orcomplex)propositions
| Compound | (or | complex) | propositions |     |     |     |     |
| -------- | --- | -------- | ------------ | --- | --- | --- | --- |
Definition
| The           | proposition | formed    | by         | joining | two or more | proposition | by  |
| ------------- | ----------- | --------- | ---------- | ------- | ----------- | ----------- | --- |
| connective(s) |             | is called | a compound |         | statement.  |             |     |
Example
| pâˆ§(q | =â‡’  | r), pâˆ§(Â¬qâˆ¨r) |     |     |     |     |     |
| ---- | --- | ------------ | --- | --- | --- | --- | --- |
The possible truth values of a proposition are often listed in a
table, called a truth table. If the compound proposition contains
| two | component | propositions, |     | then | there are | four possible |     |
| --- | --------- | ------------- | --- | ---- | --------- | ------------- | --- |
combinations of truth values for the components. If the compound
proposition contains three component propositions, then there are
eight possible combinations of truth values for the components. In
| general, | if  | the compound |     | proposition | contains | n component |     |
| -------- | --- | ------------ | --- | ----------- | -------- | ----------- | --- |
propositions, then there are 2n possible combinations of truth
| values | for | the components. |     |     |     |     |     |
| ------ | --- | --------------- | --- | --- | --- | --- | --- |


Compound(orcomplex)propositions
Definition
Two compound propositions P and Q are said to be equivalent if
they have the same truth value for all possible combinations of
truth values for the component propositions occurring in both P
| and Q. | In this case | we write P â‰¡ | Q.  |     |
| ------ | ------------ | ------------ | --- | --- |
Example
| Show that | P : p =â‡’ | q and Q : | Â¬q =â‡’ Â¬p | are equivalent. |
| --------- | -------- | --------- | -------- | --------------- |


Compound(orcomplex)propositions
| Given the | conditional p =â‡’        | q.   |            |
| --------- | ----------------------- | ---- | ---------- |
| q =â‡’      | p is the converse       | of p | =â‡’ q.      |
| Â¬p =â‡’     | Â¬q is the inverse       | of   | p =â‡’ q,    |
| Â¬q =â‡’     | Â¬p is the contapositive |      | of p =â‡’ q. |
Example
| If Kidist lives | in Addis Ababa, | then | she lives in Ethiopia. |
| --------------- | --------------- | ---- | ---------------------- |
Converse: If Kidist lives in Ethiopia, then she lives in Addis Ababa.
Contrapositive: If Kidist does not live in Ethiopia, then she does
| not live in | Addis Ababa. |     |     |
| ----------- | ------------ | --- | --- |
Inverse: If Kidist does not live in Addis Ababa, then she does not
live in Ethiopia.


Tautologyandcontradiction
| Tautology and | contradiction |     |     |     |
| ------------- | ------------- | --- | --- | --- |
Definition
| A compound | proposition | is a tautology | if it is always | true |
| ---------- | ----------- | -------------- | --------------- | ---- |
regardless of the truth values of its component propositions. If, on
the other hand, a compound proposition is always false regardless
of its component propositions, we say that such a proposition is a
contradiction.
A proposition that is neither a tautology nor a contradiction is
called a contingency.
Example
| pâˆ¨Â¬p is | tautology, pâˆ§Â¬p | is contradiction, | p =â‡’ | q is |
| ------- | --------------- | ----------------- | ---- | ---- |
contingency


Tautologyandcontradiction
Logical equivalence, which satisfy various laws or identities are
presented below.
1. Idempotent Laws:
(a) p â‰¡ pâˆ§p,
(b) p â‰¡ pâˆ¨p
2. Commutative Laws:
(a) pâˆ§q â‰¡ qâˆ§p,
(b) pâˆ¨q â‰¡ qâˆ¨p
3. Associative Laws;
(a) pâˆ§(qâˆ§r) â‰¡ (pâˆ§q)âˆ§r,
(b) pâˆ¨(qâˆ¨r) â‰¡ (pâˆ¨q)âˆ¨r
4. Distributive Laws:
(a) pâˆ¨(qâˆ§r) â‰¡ (pâˆ¨q)âˆ§(pâˆ¨r),
(b) pâˆ§(qâˆ¨r) â‰¡ (pâˆ§q)âˆ¨(pâˆ§r)


Tautologyandcontradiction
5. De Morganâ€™s Laws:
(a) Â¬(pâˆ¨q) â‰¡ Â¬pâˆ§Â¬q,
(b) Â¬(pâˆ§q) â‰¡ Â¬pâˆ¨Â¬q
6. Law of Contrapositive:
p =â‡’ q â‰¡ Â¬q =â‡’ Â¬p.
7. Complement Law:
Â¬(Â¬p) â‰¡ p.


| Open propositions | and quantifiers |     |     |
| ----------------- | --------------- | --- | --- |
Definition
An open statement (also called a predicate) is a sentence that
contains one or more variables and whose truth value depends on
| the values | assigned for | the variables. |     |
| ---------- | ------------ | -------------- | --- |
We represent an open statement by a capital letter followed by the
| variable(s) | in parenthesis, | example P(x), | Q(x,y) etc. |
| ----------- | --------------- | ------------- | ----------- |
Example
| Here are     | some open propositions: |     |     |
| ------------ | ----------------------- | --- | --- |
| x is the     | day before Sunday.      |     |     |
| x is a city  | in Africa.              |     |     |
| x is greater | than y                  |     |     |
The collection of all allowable values for the variable in an open
sentence is called the universal set (the universe of discourse)
| and denoted | by U |     |     |
| ----------- | ---- | --- | --- |


Definition
Two open propositions P(x) and Q(x) are said to be equivalent if
and only if P(a) â‰¡ Q(a) for all individual a in the specified (
| universal) | set U.. |     |     |     |
| ---------- | ------- | --- | --- | --- |
Example
x2âˆ’1
| Let P(x)   | : = 0          |               |                |     |
| ---------- | -------------- | ------------- | -------------- | --- |
| Q(x) : |x| | â‰¥ 1. Show that | P(x) and Q(x) | are equivalent | in  |
U = {âˆ’1,âˆ’1,0,,1}.
2


Quantifiers
Quantifiers
1 Universal quantifier:
| The phrases | â€for every | xâ€, â€for each | xâ€ and â€for | all xâ€ are |
| ----------- | ---------- | ------------- | ----------- | ---------- |
universal quantifiers.
| denoted | by âˆ€x, |     |     |     |
| ------- | ------ | --- | --- | --- |
If P(x) is an open proposition with universe U, then âˆ€x(P(x),
is a quantified proposition and is read as â€for every x âˆˆU with
property P(x).
2 Existential quantifier:
| The phrases | â€for some          | xâ€, â€for at least | one xâ€ and | â€there |
| ----------- | ------------------ | ----------------- | ---------- | ------ |
| exists an   | xâ€ are existential | quantifiers.      |            |        |
| denoted     | by âˆƒx,             |                   |            |        |
If P(x) is an open proposition with universe U, then âˆƒx(P(x),
is a quantified proposition and is read as â€for there existsâ€
| x âˆˆU with | property P(x). |     |     |     |
| --------- | -------------- | --- | --- | --- |


Quantifiers
To show that âˆ€x(P(x)) is F, it is sufficient to find at least one
| a âˆˆ U | such that | P(a) is False. |     |     |     |
| ----- | --------- | -------------- | --- | --- | --- |
To show that âˆƒx(P(x)) is T, it is sufficient to find at least one
| a âˆˆ U | such that | P(a) is True. |     |     |     |
| ----- | --------- | ------------- | --- | --- | --- |
Example
Write the following statements using quantifiers, and determine
| the truth | value.        |                    |            |                 |        |
| --------- | ------------- | ------------------ | ---------- | --------------- | ------ |
|           | (a) For       | each real number   | x > 0 such | that x2+1       | > 0    |
|           | (b) There     | is a real number   | x such     | that x2+5x      | +6 = 0 |
|           | (c) he        | square of any real | number     | is nonnegative. |        |
| Negation  | of quantified | proposition        |            |                 |        |
|           | Â¬(âˆ€x)P(x)     | â‰¡ (âˆƒx)Â¬P(x)        |            |                 |        |
|           | Â¬(âˆƒx)P(x)     | â‰¡ (âˆ€x)Â¬P(x)        |            |                 |        |


Quantifiers
Example
Let U = R.
|           | x2)      | x2) |     |     |
| --------- | -------- | --- | --- | --- |
| Â¬(âˆƒx)(x < | â‰¡ (âˆ€x)(x | â‰¥   |     |     |
Example
| Symbolize | (translate) | the following | statements | involving |
| --------- | ----------- | ------------- | ---------- | --------- |
1
quantifiers.
|            | (a) All rationals | are reals.     |            |                 |
| ---------- | ----------------- | -------------- | ---------- | --------------- |
|            | (b) No rationals  | are reals.     |            |                 |
|            | (c) Some          | rationals are  | reals.     |                 |
|            | (d) Some          | rationals are  | not reals. |                 |
| Let U =The | set of            | integers, P(x) | : x is     | a prime number, |
2
| Q(x):     | x is an even | number, R(x) | : x is    | an odd number. |
| --------- | ------------ | ------------ | --------- | -------------- |
| Determine | the truth    | value of the | following | propositions.  |
|           | (a) âˆƒx(P(x)  | =â‡’ Q(x))     |           |                |
|           | (b) âˆ€x(P(x)  | =â‡’ Q(x))     |           |                |
(c) âˆ€x(R(x)âˆ§Q(x))


Quantifiers
| Quantifiers | Occurring            | in     | Combinations          |                    |                |
| ----------- | -------------------- | ------ | --------------------- | ------------------ | -------------- |
|             | (i) (âˆ€x)(âˆ€y)P(x,y)   |        | means                 | â€for all x and     | for all y      |
|             | property             | P(x,y) | holds.                |                    |                |
|             | (ii) (âˆƒx)(âˆƒy)P(x,y)  |        | means                 | â€there exists      | an x and there |
|             | exists               | a y    | for which P(x,y)      | holds.             |                |
|             | (iii) (âˆ€x)(âˆƒy)P(x,y) |        | means                 | â€for every x there | exists a y     |
|             | such                 | that   | P(x,y) holds.         |                    |                |
|             | (iv) (âˆƒx)(âˆ€y)P(x,y)  |        | means                 | â€there exists      | an x which     |
|             | stands               | to     | every y with relation | P(x,y).            |                |
Example
| Determine | the truth        | value        | of the following. |     |     |
| --------- | ---------------- | ------------ | ----------------- | --- | --- |
| Let U     | = The set        | of integers. |                   |     |     |
|           | (i) (âˆ€x)(âˆ€y)(x   |              | +y = 7)           |     |     |
|           | (ii) (âˆƒx)(âˆƒy)(x  |              | +y = 7)           |     |     |
|           | (iii) (âˆ€x)(âˆƒy)(x |              | +y = 7)           |     |     |
|           | (iv) (âˆƒx)(âˆ€y)(x  |              | +y = 7)           |     |     |


ArgumentandValidity
Argument and Validity
Definition
An argument (logical deduction) is an assertion that a given set of
statements p ,p ,Â·Â·Â· ,p , called hypotheses or premises, yield
1 2 n
another statement q, called the conclusion.
Such a logical deduction is denoted by: p ,p ,Â·Â·Â· ,p (cid:96) q or
1 2 n
p
1
p
2
.
.
.
p
n
q


ArgumentandValidity
Example
If Addis Ababa is in Ethiopia, then it is in Africa. Addis Ababa is
not in Africa. Therefore, Addis Ababa is not in Ethiopia. Write the
| argument | form. |     |     |
| -------- | ----- | --- | --- |
Definition
An argument form p 1 ,p 2 ,Â·Â·Â· ,p n (cid:96) q is said to be valid if q is true
whenever all the premises p ,p ,Â·Â·Â· ,p are true; otherwise it is
|     |     | 1   | 2 n |
| --- | --- | --- | --- |
invalid.
Example
| Investigate | the validity | of the        | following argument: |
| ----------- | ------------ | ------------- | ------------------- |
|             | (a) p =â‡’     | q,Â¬q (cid:96) | Â¬p                  |
|             | (b) p =â‡’     | q,Â¬q =â‡’       | r (cid:96) p        |


ArgumentandValidity
Rules of inferences
1. Modes Ponens:
p
p =â‡’ q
q
2. Modes Tollens:
Â¬q
p =â‡’ q
Â¬p
3. Principle of Syllogism
p =â‡’ q
q =â‡’ r
p =â‡’ r


ArgumentandValidity
4. Principle of Adjunction:
p
p
(a) q (b)
pâˆ¨q
pâˆ§q
5. Principle of Detachment:
pâˆ§q
p,q
6. Modes Tollendo Ponens:
Â¬p
pâˆ¨q
q
7. Modes Ponendo Tollens:
Â¬(pâˆ§q)
p
Â¬q


ArgumentandValidity
8. Constructive Dilemma:
(p =â‡’ q)âˆ§(r =â‡’ s)
pâˆ¨r
qâˆ¨s
9. Principle of Equivalence:
p â‡” q
p
q
10. Principle of Conditionalization:
p
q =â‡’ p


| The concept of | a set |     |     |     |     |     |
| -------------- | ----- | --- | --- | --- | --- | --- |
The term set refers to a well-defined collection of objects that
| share a        | certain property | or        | certain | properties. |         |     |
| -------------- | ---------------- | --------- | ------- | ----------- | ------- | --- |
| we use capital | letters          | to denote |         | the names   | of sets | and |
| lowercase      | letters for      | elements  | of      | a set.      |         |     |
If A is a set, then the objects of the collection are called the
| elements    | or members | of the | set   | A. If x     | is an element | of the |
| ----------- | ---------- | ------ | ----- | ----------- | ------------- | ------ |
| set A, then | we write   | as x   | âˆˆ A.  | If x is not | an element    | of the |
| set A, then | we write   | as x   | âˆˆ/ A. |             |               |        |


| Descriptions | of set |     |     |     |     |     |
| ------------ | ------ | --- | --- | --- | --- | --- |
Sets are described or characterized by one of the following four
| different | ways.     |         |       |             |         |            |
| --------- | --------- | ------- | ----- | ----------- | ------- | ---------- |
|           | 1. Verbal | Method: | using | an ordinary | English | statement. |
Example
| The set | of vowels in       | English | alphabet. |         |         |              |
| ------- | ------------------ | ------- | --------- | ------- | ------- | ------------ |
| The set | of all countries   | in      | Africa.   |         |         |              |
|         | 2. Roster/Complete |         | Listing   | Method: | listing | all elements |
of a set.
Example
A = {a,e,i,o,u}
|     | 3. Partial   | Listing | Method:when  | the           | number | of elements      |
| --- | ------------ | ------- | ------------ | ------------- | ------ | ---------------- |
|     | of a set     | may     | be too large | to list       | them   | all, we list out |
|     | few elements |         | followed     | (or preceded) | by     | three dotes      |


Example
N = {1,2,3,Â·Â·Â·}
|             | 4. Set-builder | Method:        | using an open | proposition. |
| ----------- | -------------- | -------------- | ------------- | ------------ |
| The general | form is        | A = {x : P(x)} | or A = {x     | | P(x)}      |
Example
| A = {x | : x is a vowel | in English | alphabet} |     |
| ------ | -------------- | ---------- | --------- | --- |
| B = {n | : n is aneven  | integer}   |           |     |
Definition
The set which has no element is called the empty (or null) set and
| is denoted | by âˆ… or {}. |     |     |     |
| ---------- | ----------- | --- | --- | --- |
Example
| A = {x | âˆˆ R : x2+1 | = 0} is an | empty set. |     |
| ------ | ---------- | ---------- | ---------- | --- |


Definition
A set is finite if it has limited number of elements and it is called
| infinite      | if it has unlimited | number   | of elements. |     |
| ------------- | ------------------- | -------- | ------------ | --- |
| Relationships | between             | two sets |              |     |
Definition
A set B is said to be a subset of set A (or is contained in A),
denoted by B âŠ† A, if every elements of B is is an element of A.
That is,
|         |            | (âˆ€x)(x âˆˆ  | B =â‡’ x âˆˆ A) |     |
| ------- | ---------- | --------- | ----------- | --- |
| For any | set A, âˆ… âŠ† | A and A âŠ† | A.          |     |
Example
| Let A = | {1,2,3,4,5}, | B = {2,4}, | D = {x âˆˆ | N | 1 â‰¤ x â‰¤ 5}. |
| ------- | ------------ | ---------- | -------- | --------------- |
| Thus, B | âŠ† A, A âŠ†     | D.         |          |                 |


Definition
Sets A and B are said to be equal if they contain exactly the same
| elements. | That is, |       |        |     |
| --------- | -------- | ----- | ------ | --- |
|           | (âˆ€x)(x   | âˆˆ B â‡” | x âˆˆ A) |     |
Example
| The sets        | {1,2,3}, {2,1,3} | and {3,2,1} | are equal.     |        |
| --------------- | ---------------- | ----------- | -------------- | ------ |
| A = {1,2,3,4,5} | and D            | = {x âˆˆ N |  | 1 â‰¤ x â‰¤ 5} are | equal. |
Definition
Set B is said to be a proper subset of set A if every element of B
is also an element of A, but B has at least one element that is not
| in A. In | this case, we write | B âŠ‚ A. That | is,       |              |
| -------- | ------------------- | ----------- | --------- | ------------ |
|          | B âŠ‚ A â‡” (âˆ€x)(x      | âˆˆ B =â‡’      | x âˆˆ A)âˆ§(A | (cid:54)= B) |


Definition
| Let A be | a set. The power | set of A, P(A) | is the set whose |
| -------- | ---------------- | -------------- | ---------------- |
| elements | are all A. That  | is             |                  |
|          |                  | P(A) = {B | B  | âŠ† A}             |
Example
| Find the | power set of |     |     |
| -------- | ------------ | --- | --- |
(a) A = {âˆ…,0}.
(b) B = {a,b,c}
If a set A is finite with elements n, then the number of subsets of
A is 2n.


| Set Operations | and Venn | diagrams |     |
| -------------- | -------- | -------- | --- |
Definition
The union of two sets A and B, denoted by AâˆªB,is the set of all
elements that are either in A or in B (or in both sets). That is,
|     | AâˆªB | = {x | (x | âˆˆ A)âˆ¨(x âˆˆ B)} |
| --- | --- | --------- | ------------- |
Definition
The intersection of two sets A and B, denoted by Aâˆ©B,is the set
| of all elements | that | are in A and | B . That is,  |
| --------------- | ---- | ------------ | ------------- |
|                 | Aâˆ©B  | = {x | (x    | âˆˆ A)âˆ§(x âˆˆ B)} |
Definition
The difference of two sets A and B, denoted by Aâˆ’B,is the set of
| all elements | in A but | not in B . That | is,            |
| ------------ | -------- | --------------- | -------------- |
|              | Aâˆ’B      | = {x | (x       | âˆˆ A)âˆ§(x âˆˆ/ B)} |


Definition
The difference of two sets A and B, denoted by Aâˆ’B, is the set
of all elements in A but not in B. This set is also called the
| relative | complement | of B    | with respect | to A. Mathematically, |
| -------- | ---------- | ------- | ------------ | --------------------- |
|          |            | Aâˆ’B =   | {x | (x âˆˆ    | A)âˆ§(x âˆˆ/ B)}          |
| Aâˆ’B also | denoted    | by A\B. |              |                       |


Definition
: Let A be a subset of a universal set U. The absolute complement
(or simply complement) of A, denoted by A(cid:48) (or Ac or AÂ¯), is
defined to be the set of all elements of U that are not in A. That is
A(cid:48) = {x : x âˆˆ U âˆ§x âˆˆ/ A}
Definition
The symmetric difference of two sets A and B, denoted by A(cid:52)B is
the set
A(cid:52)B = (Aâˆ’B)âˆª(B âˆ’A)
Example


Theorem
| For any | two sets A | and B, the following | holds. |
| ------- | ---------- | -------------------- | ------ |
(A(cid:48))(cid:48)
|     | (a) | = A |     |
| --- | --- | --- | --- |
(b) A(cid:48) = U âˆ’A
|     | (c) Aâˆ’B       | = Aâˆ©B(cid:48)                      |                     |
| --- | ------------- | ---------------------------------- | ------------------- |
|     | (AâˆªB)(cid:48) | A(cid:48)âˆ©B(cid:48), (Aâˆ©B)(cid:48) | A(cid:48)âˆªB(cid:48) |
|     | (d)           | =                                  | =                   |
|     | (DeMorganâ€™s   | law)                               |                     |
|     |               | B(cid:48) A(cid:48)                |                     |
|     | (e) A âŠ† B     | =â‡’ âŠ†                               |                     |


Theorem
| For any | three sets  | A, B | and  | C , the following  | holds.             |
| ------- | ----------- | ---- | ---- | ------------------ | ------------------ |
|         | (a) AâˆªB     | =    | B âˆªA | (âˆª is commutative) |                    |
|         | (b) Aâˆ©B     | =    | B âˆ©A | (âˆ© is commutative) |                    |
|         | (c) (AâˆªB)âˆªC |      | =    | Aâˆª(B âˆªC)           | (âˆª is associative) |
|         | (d) (Aâˆ©B)âˆ©C |      | =    | Aâˆ©(B âˆ©C)           | (âˆ© is associative) |
|         | (e) Aâˆª(B    | âˆ©C)  | =    | (AâˆªB)âˆ©(AâˆªC)        | (âˆª is distributive |
|         | over        | âˆ©)   |      |                    |                    |
|         | (f) Aâˆ©(B    | âˆªC)  | =    | (Aâˆ©B)âˆª(Aâˆ©C)        | (âˆ© is distributive |
|         | over        | âˆª)   |      |                    |                    |


Venn diagrams
A Venn diagram is a schematic or pictorial representative of the
sets involved in the discussion.
Usually sets are represented as interlocking circles, each of which is
enclosed in a rectangle, which represents the universal set U.


Example
Consider the venn diagram below, and find Aâˆ©B, A\B, A(cid:52)B.

Function






Function
Out lines
Function
1
| The real    | number system       | and arithmetic   | operations |
| ----------- | ------------------- | ---------------- | ---------- |
| Equations   | and Inequalities:   | Linear and       | Quadratic  |
| Review of   | relations and       | functions        |            |
| Real Valued | functions and       | their properties |            |
| Polynomial  | function            |                  |            |
| Rational    | Functions and their | Graphs           |            |
| Exponential | function            |                  |            |
| Logarithm   | function            |                  |            |

Function
Therealnumbersystemandarithmeticoperations
| The real number | system |     |     |     |
| --------------- | ------ | --- | --- | --- |
Definition
| The set | of natural numbers | is given | by N = {1,2,3,Â·Â·Â·}. |     |
| ------- | ------------------ | -------- | ------------------- | --- |
| The set | of integers is     | given by |                     |     |
Z
| = {Â·Â·Â·     | ,âˆ’3,âˆ’2,âˆ’1,0,1,2,3,Â·Â·Â·}. |                   |     |     |
| ---------- | ----------------------- | ----------------- | --- | --- |
| The set    | of rational numbers     | is given          | by  |     |
| (cid:110)a |                         | (cid:111)         |     |     |
| Q =        | : a âˆˆ Zâˆ§0               | (cid:54)= b âˆˆ Z . |     |     |
b
A number which can not be written as the quotient of two integers
| is an irrational | number. |     |     |     |
| ---------------- | ------- | --- | --- | --- |
Definition
R
| The set | of real numbers | denoted by | can be described | as the |
| ------- | --------------- | ---------- | ---------------- | ------ |
union of the set of rational and irrational numbers. That is,
| R   | = {x : x is a | rational number | or an irrational | number} |
| --- | ------------- | --------------- | ---------------- | ------- |

Function
Therealnumbersystemandarithmeticoperations
Each point on the number line corresponds a unique real number
and vice-versa.
| The four arithmetic    | operations     |               |
| ---------------------- | -------------- | ------------- |
| Addition, subtraction, | multiplication | and Division. |
Properties
| The commutative | Properties:         |         |
| --------------- | ------------------- | ------- |
| 1.              | For addition: a+b   | = b+a   |
| 2.              | For multiplication: | ab = ba |

Function
Therealnumbersystemandarithmeticoperations
The associative properties:
| 3. For addition:       | a+(b+c) |       | =   | (a+b)+c |     |     |
| ---------------------- | ------- | ----- | --- | ------- | --- | --- |
| 4. For multiplication: |         | a(bc) | =   | (ab)c   |     |     |
The distributive properties:
| 5. a(b+c) | = ab+ac | or  | (b+c)a | =   | ba+ca |     |
| --------- | ------- | --- | ------ | --- | ----- | --- |
Identities:
| 6. For addition:       | There              | is          | a unique  | number      | called   |         |
| ---------------------- | ------------------ | ----------- | --------- | ----------- | -------- | ------- |
| the additive           | identity,          | represented |           | by          | 0, which | has     |
| the property           | that               | a+0         | = 0+a     | for         | all real |         |
| numbers                | a.                 |             |           |             |          |         |
| 7. For Multiplication: |                    | There       | is        | a unique    | number   |         |
| called                 | the multiplicative |             | identity, | represented |          | by      |
| 1, which               | has the            | property    | that      | a.1         | = 1.a    | for all |
| real numbers           | a.                 |             |           |             |          |         |

Function
Therealnumbersystemandarithmeticoperations
Inverses:
8. For addition: Each real number a has a unique
additive inverse, represented by âˆ’a which has
the property that a+âˆ’a = âˆ’a+a = 0
9. For Multiplication: Each real number a, except
0 has a unique additive inverse, represented by
1
which has the property that
a
(cid:18) (cid:19) (cid:18) (cid:19)
1 1
a. = .a = 1
a a
Closure properties:
10. For addition: The sum of two real numbers is a
real number.
11. For Multiplication: The product of two real
numbers is a real number.
Subtraction and division are defined by:
(cid:18) (cid:19)
1
x âˆ’y = x +(âˆ’y) and x Ã·y = x. , where y (cid:54)= 0.
y

Function
Therealnumbersystemandarithmeticoperations
The order relations
The order relation on the set of real numbers: We compare two
| real numbers    | using    | < or     | > or â‰¤ or      | â‰¥.     |         |            |
| --------------- | -------- | -------- | -------------- | ------ | ------- | ---------- |
| The order       | property |          |                |        |         |            |
| 1 Trichotomy:   |          | If x and | y are numbers, |        | exactly | one of the |
| following       | holds:   | x <      | y or y <       | x or x | = y     |            |
| 2 Transitivity: |          | x < y,y  | < z =â‡’         | x <    | z       |            |
| Addition:x      | <        | y â‡” x    | +z < y         | +z     |         |            |
3
4 Multiplication:
| When | z is positive, |     | x < y â‡” | x.z < | y.z, |     |
| ---- | -------------- | --- | ------- | ----- | ---- | --- |
| When | z is negative, |     | x < y â‡” | x.z > | y.z. |     |

Function
Therealnumbersystemandarithmeticoperations
| Let a     | and b be two | real numbers | such that a <        | b, then the   |       |
| --------- | ------------ | ------------ | -------------------- | ------------- | ----- |
| intervals | which are    | subsets of R | with end points      | a and b are   |       |
| denoted   | and defined  | as below:    |                      |               |       |
|           | (i) (a,b)    | = {x : a <   | x < b} open interval | from a to     | b,    |
|           | (ii) [a,b]   | = {x : a â‰¤ x | â‰¤ b} closed interval | from a        | to b, |
|           | (iii) (a,b]  | = {x : a <   | x â‰¤ b} open-closed   | interval from | a     |
to b,
|     | (iv) [a,b) | = {x : a â‰¤ | x < b} closed-open | from interval |     |
| --- | ---------- | ---------- | ------------------ | ------------- | --- |
|     | from       | a to b,    |                    |               |     |

Function
EquationsandInequalities:LinearandQuadratic
| Linear Equations | and Inequalities: |     |     |     |
| ---------------- | ----------------- | --- | --- | --- |
| Definition       | (Linear equation) |     |     |     |
A linear equation in one variable is an equation that can be put in
the form ax +b = 0, where a and b are constants, and a (cid:54)= 0.
Example
1
| 3x âˆ’5 = | 0, x = 5, x | +8 = 0 are | linear equations. |     |
| ------- | ----------- | ---------- | ----------------- | --- |
2
The solution of a linear equation ax +b = 0, where a (cid:54)= 0 is
b
| given | by x = âˆ’ . |     |     |     |
| ----- | ---------- | --- | --- | --- |
a
| If a | (cid:54)= a , the solution | of an equation | of the form |     |
| ---- | -------------------------- | -------------- | ----------- | --- |
|      | 1 2                        |                |             |     |
b âˆ’b
|       |                 |               | 2 1   |     |
| ----- | --------------- | ------------- | ----- | --- |
| a 1 x | +b 1 = a 2 x +b | 2 is given by | x = , |     |
a âˆ’a
|     |     |     | 1 2 |     |
| --- | --- | --- | --- | --- |
If a 1 = a 2 the solution of a 1 x +b 1 = a 2 x +b 2 does not exist
| when | b (cid:54)= b , and | has many solutions | when b | = b . |
| ---- | ------------------- | ------------------ | ------ | ----- |
|      | 1 2                 |                    |        | 1 2   |

Function
EquationsandInequalities:LinearandQuadratic
To find the solution of a linear equations the following properties
are important:
1 The addition property: If a = b then a+c = b+c
2 The multiplication property: If a = b then ac = bc
Example
Solve for x
(a) 820x = 10x +30(50âˆ’x)
(b) 3(2x +1) = 2(1âˆ’5x)+6x +11
Example
Find the solution set of
(cid:18) (cid:19)
8x +3 5
(a) âˆ’5(x +2) = âˆ’3 x +
2 6
(b) 5x âˆ’2(x âˆ’1)+4 = 3(x +2)
(c) 6+3(1âˆ’x) = 2(1âˆ’5x)+7x

Function
EquationsandInequalities:LinearandQuadratic
Example
| A computer | discount store | held an | end of summer | sale on two |
| ---------- | -------------- | ------- | ------------- | ----------- |
types of computers. They collected Birr 41,800 on the sale of 58
computers. If one type sold for Birr 600 and the other type sold for
| Birr 850,  | how many of           | each type were | sold? |     |
| ---------- | --------------------- | -------------- | ----- | --- |
| Definition | (Linear inequalities) |                |       |     |
A linear inequality is an inequality that can be put in the form
ax +b < 0, where a, and b are constants with a (cid:54)= 0. (< can be
| replaced | with >, â‰¤, or | â‰¥.) |     |     |
| -------- | ------------- | --- | --- | --- |
Example
2x âˆ’7 > 9, 5x â‰¤ 4, 1âˆ’x â‰¥ 10 are examples of linear inequalities.
To solve the inequalities, we use properties of order relation.

Function
EquationsandInequalities:LinearandQuadratic
Example
| Find the solution | set of       |     |
| ----------------- | ------------ | --- |
| (a) 5x +8(20âˆ’x)   | â‰¥ 2(x        | âˆ’5) |
| (b) 3x âˆ’5(x       | +2) â‰¥ 0      |     |
| (c) x âˆ’4(x        | +1) â‰¥ âˆ’13âˆ’(x | âˆ’2) |

Function
EquationsandInequalities:LinearandQuadratic
Quadratic equations and inequalities
Definition (Quadratic equation)
A quadratic equation is an equation that can be put in the form
ax2+bx +c = 0, where a, b and c are constants with a (cid:54)= 0.
Example
3x2+x +1 = 0, x2 = 4, x2+6x = 0 are quadratic equations.
To solve a quadratic equation, the following properties can be
used.
The zero-Product Rule If a.b = 0 then a = 0 or b = 0.
âˆš
The square Root Theorem If x2 = d, then x = Â± d

Function
EquationsandInequalities:LinearandQuadratic
Example
| Solve the        | following  | equations |             |          |             |         |
| ---------------- | ---------- | --------- | ----------- | -------- | ----------- | ------- |
| (a) 4x2+10x      | =          | 6         | (b) 5x2âˆ’6   | = 8      | (c) (x âˆ’2)2 | = 6     |
| The quadratic    |            | formula:  |             |          |             |         |
| The solutions    | (roots)    | of        | a quadratic | equation | ax2+bx      | +c = 0, |
| with a (cid:54)= | 0 is given | by        |             |          |             |         |
âˆš
b2âˆ’4ac
âˆ’bÂ±
x =
2a
Example
| Solve the | following | equations | using     | quadratic | formula |     |
| --------- | --------- | --------- | --------- | --------- | ------- | --- |
| (a) x2âˆ’8x | = âˆ’6      |           | (b) x2âˆ’5x | +6        | = 0     |     |
| (c) x2+4x | +4        | = 0       | (d)       | x2+x +1   | = 0     |     |

Function
EquationsandInequalities:LinearandQuadratic
| Definition | (Quadratic | inequalities) |     |     |
| ---------- | ---------- | ------------- | --- | --- |
A quadratic inequality is an inequality that can be put in the form
ax2+bx +c < 0, where a, b and c are constants with a (cid:54)= 0. (<
| can be | replaced with | >, â‰¤, or â‰¥.) |     |     |
| ------ | ------------- | ------------ | --- | --- |
Example
| 2x2+5x | âˆ’3 > 0, x2+5x | +6 â‰¤ 0, | x âˆ’x2 â‰¥ 2 | are examples of |
| ------ | ------------- | ------- | --------- | --------------- |
linear inequalities.
Note that a.b > 0 if and only if both a and b are either positive or
negative. We determine solution of the quadratic inequalities by
examining the sign of the factors. After putting the inequality in
the standard form, we will examine the sign of each factor of the
expression for the various values of x. This process is called sign
analysis.

Function
EquationsandInequalities:LinearandQuadratic
Example
| Solve the | following quadratic | inequalities |        |
| --------- | ------------------- | ------------ | ------ |
| 2x2+5x    |                     | x2âˆ’5x        |        |
| (a)       | âˆ’3 > 0              | (b)          | +6 â‰¤ 0 |
| (c) x2âˆ’2x | âˆ’2 < 0              |              |        |

Function
Reviewofrelationsandfunctions
Cartesian Product
Definition
| Suppose | A and B are | sets. The  | Cartesian      | product | of A and B,  |
| ------- | ----------- | ---------- | -------------- | ------- | ------------ |
| denoted | by AÃ—B,     | is the set | which contains | every   | ordered pair |
whose first coordinate is an element of A and second coordinate is
| an element | of B. That | is,      |       |            |     |
| ---------- | ---------- | -------- | ----- | ---------- | --- |
|            | AÃ—B        | = {(a,b) | : a âˆˆ | Aandb âˆˆ B} |     |
Example
| Let A =    | {1,2} and | B = {x,y}. | Find AÃ—B | and | B Ã—A. |
| ---------- | --------- | ---------- | -------- | --- | ----- |
| Definition | (Equality | of ordered | pairs)   |     |       |
Two ordered pairs (a,b) and (c,d) are equal if and only if a = c
| and b = | d.  |     |     |     |     |
| ------- | --- | --- | --- | --- | --- |

Function
Reviewofrelationsandfunctions
| Definition | (Relation) |     |     |     |
| ---------- | ---------- | --- | --- | --- |
Let A and B are sets, any subset of AÃ—B is called a relation from
A into B.
Example
| Let A | = {1,3,5,7} | and B = {6,8}. | Let R be | the relation |
| ----- | ----------- | -------------- | -------- | ------------ |
1
| â€less | thanâ€ from | A into B. Then |     |     |
| ----- | ---------- | -------------- | --- | --- |
R = {(1,6),(1,8),(3,6),(3,8),(5,6),(5,8)}
| 2 Let A   | = {1,2,3,4,5} | and B = {a,b,c}. | The | following are |
| --------- | ------------- | ---------------- | --- | ------------- |
| relations | from A        | into B.          |     |               |
|           | (i) R         | = {(1,a)}        |     |               |
1
|     | (ii) R | = {(2,b),(3,b),(4,c),(5,a)} |     |     |
| --- | ------ | --------------------------- | --- | --- |
2

Function
Reviewofrelationsandfunctions
Definition
| Let R | be a relation  | from A into   | B. Then            |            |         |
| ----- | -------------- | ------------- | ------------------ | ---------- | ------- |
|       | (a) the domain | of R,         | denoted by Dom(R), | is the     | set of  |
|       | the first      | coordinates   | of the elements    | of R. That | is      |
|       |                | Dom(R)        | = {a : (a,b)       | âˆˆ R}       |         |
|       | (ii) the range | of R, denoted | by Range(R),       | is the     | set of  |
|       | the second     | coordinates   | of the elements    | of R.      | That is |
|       |                | Range(R)      | = {b : (a,b)       | âˆˆ R}       |         |
If R is the relation from A into B, then the set B is called the
| codomain | of R. |     |     |     |     |
| -------- | ----- | --- | --- | --- | --- |

Function
Reviewofrelationsandfunctions
Example
| If R = | {(1,6),(3,6),(3,8),(5,6),(5,8)} |     |     |     | is the | relation from |
| ------ | ------------------------------- | --- | --- | --- | ------ | ------------- |
1
| A =       | {1,3,5,7} | into         | B = {6,8,10}.      | Then | find     | Dom(R),    |
| --------- | --------- | ------------ | ------------------ | ---- | -------- | ---------- |
| Range(R)  | and       | Codomain(R). |                    |      |          |            |
| 2 Let R   | be a      | relation     | on A = {1,2,3,4,5} |      | defined  | by         |
| R =       | {(a,b)    | : a,b        | âˆˆ A,a is a factor  | of   | b}. Find | the domain |
| and range | of        | R.           |                    |      |          |            |
Remark
| 1 A relation | R   | on a        | set A is called   |      |            |      |
| ------------ | --- | ----------- | ----------------- | ---- | ---------- | ---- |
|              | (a) | a universal | relation          | if R | = AÃ—A.     |      |
|              | (b) | identity    | relation if       | R =  | {(a,a) : a | âˆˆ A} |
|              | (c) | void        | or empty relation | if   | R = âˆ….     |      |
If R is a relation from A to B then the inverse of R, denoted
2
| by Râˆ’1, | is a | relation | from B to    | A and   | defined as: |     |
| ------- | ---- | -------- | ------------ | ------- | ----------- | --- |
|         |      |          | Râˆ’1 = {(b,a) | : (a,b) | âˆˆ R}        |     |

Function
Reviewofrelationsandfunctions
Example
| Let R = | {(a,b) : | a,b âˆˆ N,a+2b | = 11} | be a relation | on N. Find |
| ------- | -------- | ------------ | ----- | ------------- | ---------- |
Râˆ’1
| (a) Dom(R)  |               | (b) Range(R) |              | (c)    |        |
| ----------- | ------------- | ------------ | ------------ | ------ | ------ |
| Definition  | (Functions)   |              |              |        |        |
| A function  | is a relation | in which     | each element | of the | domain |
| corresponds | to exactly    | one element  | of the       | range. |        |
Example
Determine whether the following relations are functions or not.
(a) R = {(5,âˆ’2),(3,5),(3,7),(5,6),(5,8)}
(b) R = {(2,4),(3,4),(6,8)}
(c) R = {(âˆ’1,1),(1,1),(âˆ’2,4),(2,4),(âˆ’3,9),(3,9)}
Map or mapping, transformation and correspondence are synonyms
for the word function. If f is a function and (x,y) âˆˆ f then we say
| x is mapped | to y. |     |     |     |     |
| ----------- | ----- | --- | --- | --- | --- |

Function
Reviewofrelationsandfunctions
Definition
A relation f from A into B is called a function from A into B,
denoted by
|     |     | f   | : A â†’ B | or  |     |     |     |
| --- | --- | --- | ------- | --- | --- | --- | --- |
if and only if
|      | (i) Dom(f) | = A   |             |       |         |       |      |
| ---- | ---------- | ----- | ----------- | ----- | ------- | ----- | ---- |
| (ii) | No element | of    | A is mapped | by f  | to more | than  | one  |
|      | element    | in B. | That is, if | (x,y) | âˆˆ f and | (x,z) | âˆˆ f, |
|      | then y     | = z.  |             |       |         |       |      |
Remark
| If (x,y) | âˆˆ f, then | we write | as f(x) | = y. | In this | case, | y is |
| -------- | --------- | -------- | ------- | ---- | ------- | ----- | ---- |
called the image of x, and x is called the pre-image of y under
f.
| The | symbol f(x) | is read | as â€f of | xâ€. |     |     |     |
| --- | ----------- | ------- | -------- | --- | --- | --- | --- |

Function
Reviewofrelationsandfunctions
Example
| Let A | = {1,2,3,4} | and | B = {1,6,8,11,15}. | which | of the |
| ----- | ----------- | --- | ------------------ | ----- | ------ |
1
| following | are functions | from       | A to B.       |            |       |
| --------- | ------------- | ---------- | ------------- | ---------- | ----- |
|           | (a) f         | defined by |               |            |       |
|           | f(1)          | = 1,f(2)   | = 6,f(3)      | = 8,f(4) = | 8.    |
|           | (b) f         | defined by | f(1) = 1,f(2) | = 6,f(3)   | = 15. |
|           | (c) f         | defined by |               |            |       |
|           | f(1)          | = 6,f(2)   | = 6,f(3)      | = 6,f(4) = | 6.    |
|           | (d) f         | defined by | f(1) = 1,f(2) | = 6,f(2)   | =     |
|           | 8,f(3)        | = 8,f(4)   | = 11.         |            |       |
|           | x2            |            | R R?          |            |       |
| 2 Is f(x) | = a function  |            | from to       |            |       |

Function
Reviewofrelationsandfunctions
| Domain, | Codomain |     | and | range of | a function |     |     |
| ------- | -------- | --- | --- | -------- | ---------- | --- | --- |
Definition
| Let f | : A â†’ | B be | a function. |               |           |       |             |
| ----- | ----- | ---- | ----------- | ------------- | --------- | ----- | ----------- |
|       | (a)   | The  | set A       | is called the | domain    | of f. |             |
|       | (b)   | The  | set B       | is called the | codomain  | of    | f.          |
|       | (c)   | The  | set {f(x)   | : x âˆˆ A}      | is called | the   | range of f. |
Example
| Let A    | = {1,2,3} |              | and B | = {1,2,3,Â·Â·Â·10}. |     | Determine | Dom(f),       |
| -------- | --------- | ------------ | ----- | ---------------- | --- | --------- | ------------- |
| Range(f) | and       | Codomain(f), |       | given that       | f   | : A â†’ B   | is a function |
x2.
| defined | by  | f(x) = |     |     |     |     |     |
| ------- | --- | ------ | --- | --- | --- | --- | --- |
Example
| Determine |     | the domain | for | the following | functions. |     |     |
| --------- | --- | ---------- | --- | ------------- | ---------- | --- | --- |
2x
| (a) f(x) | =   | âˆ’3x +5 |     | (b) f(x) | =   |     |     |
| -------- | --- | ------ | --- | -------- | --- | --- | --- |
3x âˆ’5
âˆš
âˆ’x2
| (c) f(x) | =   | 3x  |     |     |     |     |     |
| -------- | --- | --- | --- | --- | --- | --- | --- |

Function
RealValuedfunctionsandtheirproperties
Real Valued functions and their properties
The function f : A â†’ B is called a real valued function if
B âŠ† R, and in particular if A is also a subset of R, then f is
called real function.
Example
f : R â†’ R defined by f(x) = x2 is real function.
Operations of functions
Let f(x) and g(x) be functions.
1 (f +g)(x) = f(x)+g(x) The sum of two functions.
2 (f âˆ’g)(x) = f(x)âˆ’g(x) The difference of two functions.
3 (f.g)(x) = f(x).g(x) The product of two functions.
(cid:18) (cid:19)
f f(x)
4 (x) = The quotient of two functions(provided
g g(x)
g(x) (cid:54)= 0).

Function
RealValuedfunctionsandtheirproperties
| The domain | of f +g, | f âˆ’g and | f.g is Dom(f)âˆ©Dom(g), |     |     |
| ---------- | -------- | -------- | --------------------- | --- | --- |
f
| whereas | the domain | of is Dom(f)âˆ©Dom(g)âˆ’{x |     |     | : g(x) = 0}. |
| ------- | ---------- | ---------------------- | --- | --- | ------------ |
g
Example
| Let | f(x) = 3x2+2 | and g(x) | = 5x âˆ’4. | Find | each of the |
| --- | ------------ | -------- | -------- | ---- | ----------- |
1
| following, | and its   | domain.       |     |     |     |
| ---------- | --------- | ------------- | --- | --- | --- |
| (a)        | (f +g)(x) | (b) (f âˆ’g)(x) |     |     |     |
(cid:18) f (cid:19)
| (c) | (f.g)(x) | (d) (x) |     |     |     |
| --- | -------- | ------- | --- | --- | --- |
g
|            | âˆš                   |              | âˆš     |      |             |
| ---------- | ------------------- | ------------ | ----- | ---- | ----------- |
|            | 4                   |              | 9âˆ’x2. |      |             |
| 2 Let      | f(x) = x            | +1 and g(x)  | =     | Find | each of the |
| following, | and their           | domains.     |       |      |             |
| (a)        | (f +g)(x)           | (b) (f.g)(x) |       |      |             |
|            | (cid:18) f (cid:19) |              |       |      |             |
| (c)        | (x)                 | (d) f3       |       |      |             |
g

Function
RealValuedfunctionsandtheirproperties
| Definition | (Composition |     | of  | functions) |     |     |     |     |
| ---------- | ------------ | --- | --- | ---------- | --- | --- | --- | --- |
The composition of two functions f(x) and g(x), is denoted by
| fog and | is defined | by  |          |     |         |     |     |     |
| ------- | ---------- | --- | -------- | --- | ------- | --- | --- | --- |
|         |            |     | (fog)(x) | =   | f(g(x)) |     |     |     |
x(cid:48)s
| The   | domain | of fog | consists | of         | those | in the | domain | of g |
| ----- | ------ | ------ | -------- | ---------- | ----- | ------ | ------ | ---- |
| whose | range  | values | are in   | the domain |       | of f.  |        |      |
Example
| Let | f = | {(2,z),(3,q)} | and | g = | {(a,2),(b,3),(c,5)}. |     |     | Find |
| --- | --- | ------------- | --- | --- | -------------------- | --- | --- | ---- |
1
| fog   | and  | its domain. |     |     |      |         |      |          |
| ----- | ---- | ----------- | --- | --- | ---- | ------- | ---- | -------- |
| Given | f(x) | = 5x2âˆ’3x    | +2  | and | g(x) | = 4x +3 | find | fog(âˆ’2), |
2
| gof(âˆ’2), |     | fog(x) and | gof(x) |     |        |         |        |     |
| -------- | --- | ---------- | ------ | --- | ------ | ------- | ------ | --- |
|          |     | x          |        |     | 2      |         |        |     |
| If f(x)  | =   | and        | g(x)   | =   | , find | fog(x), | gof(x) | and |
3
|       |          | x +1 |     | x   | âˆ’1  |     |     |     |
| ----- | -------- | ---- | --- | --- | --- | --- | --- | --- |
| their | domains. |      |     |     |     |     |     |     |

Function
RealValuedfunctionsandtheirproperties
| Definition | (Equality | of functions) |     |     |
| ---------- | --------- | ------------- | --- | --- |
Two functions are said to be equal if and only if the following two
| conditions | hold:                 |               |               |            |
| ---------- | --------------------- | ------------- | ------------- | ---------- |
|            | (i) The functions     | have the same | domain;       |            |
|            | (ii) Their functional | values are    | equal at each | element of |
the domain.
Example
x2âˆ’25
| The functions | f(x) = | and g(x) | = x +5 are | not equal. |
| ------------- | ------ | -------- | ---------- | ---------- |
x âˆ’5
Why?

Function
RealValuedfunctionsandtheirproperties
Types of functions
Definition
A function f : A â†’ B is called one to one, often written 1âˆ’1, if
and only if for all x ,x âˆˆ A, f(x ) = f(x ) implies x = x . In
|     | 1 2 | 1   | 2   | 1 2 |
| --- | --- | --- | --- | --- |
other words, no two elements of A are mapped to one element of
B.
Example
| Let A = {1,2,3,4} | and           | B = {1,4,7,8}. |            |               |
| ----------------- | ------------- | -------------- | ---------- | ------------- |
|                   | (i) f : A â†’ B | defined as     |            |               |
|                   | f(1) = 1,f(2) | = 4,f(3)       | = 4,f(4) = | 8 is not 1âˆ’1. |
| (ii)              | f : A â†’ B     | defined as     |            |               |
|                   | f(1) = 4,f(2) | = 7,f(3)       | = 1,f(4) = | 8 is 1âˆ’1.     |
Definition
A function f : A â†’ B is called an onto function(or f maps onto B )
| if every element | of B is | image of some | element | in A, i.e, |
| ---------------- | ------- | ------------- | ------- | ---------- |
| Range(f)         | = B.    |               |         |            |

Function
RealValuedfunctionsandtheirproperties
Definition
A function f : A â†’ B is called an onto function(or f maps onto B )
| if every | element of | B is image | of some element | in A, i.e, |
| -------- | ---------- | ---------- | --------------- | ---------- |
| Range(f) | = B.       |            |                 |            |
Example
| f : R â†’ | R defined | by f(x) = 3x | +5 is onto.  |     |
| ------- | --------- | ------------ | ------------ | --- |
| f : R â†’ | R defined | by f(x) = x2 | is not onto. |     |
Definition
A function f : A â†’ B is called said to be a 1âˆ’1 correspondence if
| f is both | 1âˆ’1 and | on to. |     |     |
| --------- | ------- | ------ | --- | --- |

Function
RealValuedfunctionsandtheirproperties
Inverse of a function
Definition
Let f is a function whose domain is A, and whose codomain is B.
Then f is invertible if there exists a function g with domain B and
codomain A, with property:
f(x) = y â‡” g(y) = x
The inverse of a function f is denoted by fâˆ’1 .
Not all functions have inverse. A function f is invertible(has
inverse) if it is one to one correspondence.
To find the inverse we will follow the steps below:
1 Interchange x and y in the equation y = f(x)
2 Solving the resulting equation for y , we will obtaining the
inverse function. That is y = fâˆ’1(x).

Function
RealValuedfunctionsandtheirproperties
Example
| Find the | inverse of the | following functions, | if exist. |
| -------- | -------------- | -------------------- | --------- |
x
x3
| (a) f(x) = |     | f(x) = |     |
| ---------- | --- | ------ | --- |
x +2
Properties
| If f : A â†’ | B and g : B | â†’ C are invertible,then |     |
| ---------- | ----------- | ----------------------- | --- |
(fâˆ’1)âˆ’1
|     | (a)            | = f     |     |
| --- | -------------- | ------- | --- |
|     | (b) (fâˆ’1of)(x) | = x     |     |
|     | (c) (fofâˆ’1)(y) | = y     |     |
|     | (fog)âˆ’1        | gâˆ’1ofâˆ’1 |     |
(d) =

Function
RealValuedfunctionsandtheirproperties
Polynomial function
Definition
| A polynomial | function |     | is a function |            | of the | form |     |
| ------------ | -------- | --- | ------------- | ---------- | ------ | ---- | --- |
|              |          |     | xn            | xnâˆ’1+Â·Â·Â·+a |        |      |     |
|              | p(x)     | = a | +a            |            |        | x +a | ,   |
|              |          |     | n             | nâˆ’1        |        | 1 0  |     |
where n is non negative integer, a n ,a nâˆ’1 ,Â·Â·Â· ,a 0 are constants.
| In a polynomial |              | function | p(x) | = a | xn+a | xnâˆ’1+Â·Â·Â·+a | x +a , |
| --------------- | ------------ | -------- | ---- | --- | ---- | ---------- | ------ |
|                 |              |          |      | n   |      | nâˆ’1        | 1 0    |
| where a         | (cid:54)= 0, |          |      |     |      |            |        |
n
| the        | constants  | a           | ,a ,Â·Â·Â· | ,a          | are called | coefficients | of the |
| ---------- | ---------- | ----------- | ------- | ----------- | ---------- | ------------ | ------ |
|            |            | n           | nâˆ’1     | 0           |            |              |        |
| polynomial |            | p,          |         |             |            |              |        |
| n is       | the degree | of          | p,      |             |            |              |        |
| a n is     | called     | the leading |         | coefficient | of         | p,           |        |
a xn is the leading term, and a is called the constant term of
| n   |     |     |     |     | 0   |     |     |
| --- | --- | --- | --- | --- | --- | --- | --- |
p.

Function
RealValuedfunctionsandtheirproperties
Example
2x5âˆ’3x4âˆ’x3+5x
| f(x) =                |     | +7 is a polynomial | function        | of degree |
| --------------------- | --- | ------------------ | --------------- | --------- |
| 5. g(x) = 2xâˆ’3âˆ’4x2+10 |     | is not polynomial  | function (Why?) |           |
Remark:
| A polynomial | of degree | 1 is called a         | linear function. |     |
| ------------ | --------- | --------------------- | ---------------- | --- |
| A polynomial | of degree | 2 is called quadratic | function.        |     |
| A polynomial | of degree | 3 is called a         | cubic function.  |     |
Properties
| The domain | of the | polynomial function | is the set of | real |
| ---------- | ------ | ------------------- | ------------- | ---- |
number.
| The graph   | of a polynomial    | is a smooth    | unbroken curve. | The       |
| ----------- | ------------------ | -------------- | --------------- | --------- |
| word smooth | means              | that the graph | does not have   | any sharp |
| corners     | as turning points. |                |                 |           |

Function
RealValuedfunctionsandtheirproperties
Definition
The zero (sometimes called roots) of a function f is (are) the
| value(s) | of x such that | f(x) = 0. |     |     |
| -------- | -------------- | --------- | --- | --- |
Example
| x = âˆ’2 | and x = âˆ’3 | are the zeros | of a quadratic | function |
| ------ | ---------- | ------------- | -------------- | -------- |
| f(x) = | x2+5x +6.  |               |                |          |
If p is a polynomial of degree n , then it has at most n zeros.

Function
RealValuedfunctionsandtheirproperties
| Theorem | (Division |     | Algorithm) |     |
| ------- | --------- | --- | ---------- | --- |
Let p(x) and d(x) be polynomials with d(x) (cid:54)= 0, and with the
degree of d(x) less than or equal to the degree of p(x). Then
| there are | polynomials |     | q(x) and r(x) | such that |
| --------- | ----------- | --- | ------------- | --------- |
p(x) = d(x) q(x) + r(x) , where either r(x) = 0 or the degree
|         | (cid:124)(cid:123)(cid:122)(cid:125) | (cid:124)(cid:123)(cid:122)(cid:125) | (cid:124)(cid:123)(cid:122)(cid:125) |     |
| ------- | ------------------------------------ | ------------------------------------ | ------------------------------------ | --- |
|         | divisorquotient                      |                                      | remainder                            |     |
| of r(x) | is less                              | than degree                          | d(x).                                |     |
Example
| Determine | the  | quotient,  | and remainder | obtained when |
| --------- | ---- | ---------- | ------------- | ------------- |
| p(x) =    | x4âˆ’1 | is divided | by d(x)       | = x2+2x       |
| Theorem   | (The | Remainder  | Theorem)      |               |
When a polynomial p(x) of degree at least 1 is divided by x âˆ’r ,
| then the | remainder | is  | p(r). |     |
| -------- | --------- | --- | ----- | --- |

Function
RealValuedfunctionsandtheirproperties
Example
| Find the remainder | found          | in dividing | P(x)    | = x3âˆ’x2+3x |     | âˆ’1 by |
| ------------------ | -------------- | ----------- | ------- | ---------- | --- | ----- |
| (a) x âˆ’2           |                | (b) x âˆ’3.   |         |            |     |       |
| Theorem            | (The Factor    | Theorem)    |         |            |     |       |
| x âˆ’r is a          | factor of p(x) | if and only | if p(r) | = 0.       |     |       |
Example
x3+3x2âˆ’4x
| Which of | the following | is a factor | of f(x) | =   |     | âˆ’12? |
| -------- | ------------- | ----------- | ------- | --- | --- | ---- |
| (a) x âˆ’2 |               | (b) x +3    |         |     | (b) | x +2 |
| Theorem  | (Location     | theorem)    |         |     |     |      |
Let f be a polynomial function and a and b be real numbers such
that a < b. If f(a)f(b) < 0, then there is at least one zero of f
| between a | and b. |     |     |     |     |     |
| --------- | ------ | --- | --- | --- | --- | --- |

Function
RealValuedfunctionsandtheirproperties
Example
| Show that | 2x4âˆ’3x2+5x |     | âˆ’7  | =   | 0 has atleast | one real root on |
| --------- | ---------- | --- | --- | --- | ------------- | ---------------- |
[1,2].
| Theorem | (Fundamental |     | Theorem |     | of Algebra) |     |
| ------- | ------------ | --- | ------- | --- | ----------- | --- |
If f be a polynomial function of degree n > 0 whose coefficients
| are complex |           | numbers, | then          | p(x) has | at least | one zero in the |
| ----------- | --------- | -------- | ------------- | -------- | -------- | --------------- |
| complex     | number    | system.  |               |          |          |                 |
| Theorem     | (The      | linear   | Factorization |          | Theorem) |                 |
| If p(x)     | = a xn    | +a       | xnâˆ’1+Â·Â·Â·+a    |          | x +a     | is a polynomial |
|             | n         | nâˆ’1      |               |          | 1 0      |                 |
| function    | of degree | n        | â‰¥ 1, then     |          |          |                 |
p(x) = a n (x âˆ’r 1 )(x âˆ’r 2 )Â·Â·Â·(x âˆ’r n ) where the r i are complex
| numbers | (possible | real | and | not necessarily | distinct). |     |
| ------- | --------- | ---- | --- | --------------- | ---------- | --- |

Function
RealValuedfunctionsandtheirproperties
Example
| Factorize | the following | polynomial | functions. |     |
| --------- | ------------- | ---------- | ---------- | --- |
|           | (a) f(x) =    | x3âˆ’x62âˆ’16x |            |     |
3x2âˆ’10x
|         | (b) f(x) =    |              | +8         |           |
| ------- | ------------- | ------------ | ---------- | --------- |
|         | (c) f(x) =    | 2x4+8x3+10x2 |            |           |
| Theorem | (The Rational | Root         | Theorem)   |           |
| Suppose | that f(x) =   | a xn +a      | xnâˆ’1+Â·Â·Â·+a | x +a is a |
|         |               | n nâˆ’1        |            | 1 0       |
polynomial function of degree n â‰¥ 1 with with integer coefficients.
p
If is a rational root of f where p and q have no common factor
q
other than Â±1 then p is a factor of a and q is a factor of a .
0 n
Example
| Find all | the zeros of | the function | p(x) = 2x3+3x2âˆ’23x | âˆ’12. |
| -------- | ------------ | ------------ | ------------------ | ---- |

Function
RealValuedfunctionsandtheirproperties
| Rational | Functions | and | their | Graphs |     |     |
| -------- | --------- | --- | ----- | ------ | --- | --- |
Definition
n(x)
| A rational |     | function | is a function | of the form | f(x) = | , where |
| ---------- | --- | -------- | ------------- | ----------- | ------ | ------- |
d(x)
| n(x) | and d(x) | are | polynomial | functions and | d(x) (cid:54)= 0. |     |
| ---- | -------- | --- | ---------- | ------------- | ----------------- | --- |
Example
x5+2x3âˆ’x
|      | 1   |        | 2+x   |          |       | +1  |
| ---- | --- | ------ | ----- | -------- | ----- | --- |
| f(x) | = , | g(x) = |       | and h(x) | =     | are |
|      | x   |        | x2+5x | +6       | x2+3x |     |
rational functions.
n(x)
| Note | that | the domain | of  | the rational function | f(x) = | is  |
| ---- | ---- | ---------- | --- | --------------------- | ------ | --- |
d(x)
| {x : | d(x) (cid:54)= | 0}. |     |     |     |     |
| ---- | -------------- | --- | --- | --- | --- | --- |

Function
RealValuedfunctionsandtheirproperties
Example
| Find the domain | and the zero(s) | of the | following | functions. |
| --------------- | --------------- | ------ | --------- | ---------- |
|                 | 3x âˆ’5           |        |           | 1          |
| (a) f(x) =      |                 |        | (b) f(x)  | =          |
| x2âˆ’x            |                 |        |           | x3âˆ’4x      |
âˆ’12
| Informal definition | of limits    |     |              |          |
| ------------------- | ------------ | --- | ------------ | -------- |
|                     | a+,          | aâˆ’, |              |          |
| x approaches        | x approaches | of  | x approaches | to +âˆž, x |
| approaches to       | âˆ’âˆž.          |     |              |          |
| Graph of rational   | functions    |     |              |          |
To sketch the graph of a function, the following information are
important.
| Domain, intercepts, | asymptotes, | Maximum | and | minimum values, |
| ------------------- | ----------- | ------- | --- | --------------- |
| etc. Define the     | following!  |         |     |                 |
Domain?
| Intercept:  | the x intercept,       | y intercept. |            |               |
| ----------- | ---------------------- | ------------ | ---------- | ------------- |
| Asymptotes: | Vertical asymptote(s), |              | Horizontal | asymptote(s), |
| Oblique     | asymptote(s).          |              |            |               |

Function
RealValuedfunctionsandtheirproperties
Example
| Sketch the graphs | of         |         |                        |
| ----------------- | ---------- | ------- | ---------------------- |
| 1                 |            | 1       |                        |
| (a) f(x) =        | (b) g(x) = | , where | n is positive integer. |
âˆ’a)n
| x   |     | (x  |     |
| --- | --- | --- | --- |
Example
| Find the Horizontal | asymptote(s) | of  |     |
| ------------------- | ------------ | --- | --- |
|                     | 3x3âˆ’x2+5x    | âˆ’9  |     |
(a) f(x) =
|     | 2x4âˆ’5x3+x2+x | âˆ’6  |     |
| --- | ------------ | --- | --- |
3x3âˆ’x2+5x
âˆ’9
(b) g(x) =
|     | 2x3+4x2+5x | +2  |     |
| --- | ---------- | --- | --- |
âˆ’x2+4x
+3
(c) h(x) =
|     | 5x3âˆ’3x2+7x | +8  |     |
| --- | ---------- | --- | --- |

Function
RealValuedfunctionsandtheirproperties
Example (Exercise)
Sketch the graphs of
x +2 x2+3x +2
(a) f(x) = (b) g(x) =
x âˆ’1 x2âˆ’1

Function
RealValuedfunctionsandtheirproperties
| Exponential | function |     |     |     |
| ----------- | -------- | --- | --- | --- |
Definition
For a natural number n and a real number x, the nth power of x is
defined as
xn
|     |     | =   | x.x.Â·Â·Â· .x                             |     |
| --- | --- | --- | -------------------------------------- | --- |
|     |     |     | (cid:124) (cid:123)(cid:122) (cid:125) |     |
ntimes
In the expression xn, x is called the base and n is called the
exponent.
Example
| 25 = 2Ã—2Ã—2Ã—2Ã—2       |         | = 32 |          |     |
| -------------------- | ------- | ---- | -------- | --- |
| (cid:18) 1 (cid:19)3 | 1 1     | 1    | âˆ’1Ã—âˆ’1Ã—âˆ’1 | 1   |
| âˆ’ =                  | âˆ’ Ã—âˆ’ Ã—âˆ’ | =    |          | = âˆ’ |
| 3                    | 3 3     | 3    | 3Ã—3Ã—3    | 27  |

Function
RealValuedfunctionsandtheirproperties
| Definition | (Zero and | Negative exponent) |
| ---------- | --------- | ------------------ |
1
|                 | x0         | xâˆ’n |
| --------------- | ---------- | --- |
| For x (cid:54)= | 0, = 0 and | = . |
xn
| Note that | 00 is undefined. |     |
| --------- | ---------------- | --- |

Function
RealValuedfunctionsandtheirproperties
| Definition | (Exponential | function) |     |     |
| ---------- | ------------ | --------- | --- | --- |
bx,
A function of the form f(x) = where b > 0 and b (cid:54)= 1, is called
| an exponential | function | with base b. |     |     |
| -------------- | -------- | ------------ | --- | --- |
Example
|        | (cid:18) 1 | (cid:19)x  |                 |            |
| ------ | ---------- | ---------- | --------------- | ---------- |
|        | 2x,        |            | ex              |            |
| f(x) = | g(x) =     | and h(x) = | are exponential | functions. |
3

Function
RealValuedfunctionsandtheirproperties
| Properties | of           | exponential | function    |            |          |                 |
| ---------- | ------------ | ----------- | ----------- | ---------- | -------- | --------------- |
| the        | domain       | of the      | exponential | function   | is       | the set of real |
| number,    | and          | the         | range is    | the set of | positive | real numbers.   |
| The        | y -intercept |             | is 1.       |            |          |                 |
| y =        | 0 is a       | horizontal  | asymptote.  |            |          |                 |
| The        | exponential  |             | function    | is 1 -1.   |          |                 |
Example
(cid:18) (cid:19)x
1
3x
| Sketch | the graphs | of  | (a) f(x) | =   | (b) | g(x) = |
| ------ | ---------- | --- | -------- | --- | --- | ------ |
3
| Example  | (Exercise) |     |          |      |     |            |
| -------- | ---------- | --- | -------- | ---- | --- | ---------- |
|          |            |     |          | 3x   |     | 3x+1       |
| Sketch   | the graphs | of  | (a) f(x) | = +1 |     | (b) g(x) = |
| (c) g(x) | = âˆ’9âˆ’x     | +3  |          |      |     |            |

Function
RealValuedfunctionsandtheirproperties
Logarithm function
Because the exponential function is 1-1, it has an inverse function.
bx,
| Question: | What is | the inverse of | f(x) = | where b > 0 and |
| --------- | ------- | -------------- | ------ | --------------- |
b (cid:54)= 1?
Example
3x.
| Determine | the inverse   | of f(x) =          |              |     |
| --------- | ------------- | ------------------ | ------------ | --- |
| Note:     | For b > 0 and | b (cid:54)= 1, x = | by â‡” y = log | x.  |
b

Function
RealValuedfunctionsandtheirproperties
| Definition | (Logarithm | function) |     |
| ---------- | ---------- | --------- | --- |
A function of the form f(x) = log x, where b > 0 and b (cid:54)= 1, is
b
| called a | logarithm function | with base b. |     |
| -------- | ------------------ | ------------ | --- |
Example
| f(x) = | log x, g(x) = | log x are logarithm | functions. |
| ------ | ------------- | ------------------- | ---------- |
|        | 2             | 1                   |            |
2

Function
RealValuedfunctionsandtheirproperties
| Properties | of  | logarithm | function |     |     |     |     |     |
| ---------- | --- | --------- | -------- | --- | --- | --- | --- | --- |
the domain of the logarithm function is the set of positive real
| number, | and          | the      | range      | is the | set of real | numbers. |     |     |
| ------- | ------------ | -------- | ---------- | ------ | ----------- | -------- | --- | --- |
| The     | x -intercept |          | is 1.      |        |             |          |     |     |
| x =     | 0 is a       | vertical | asymptote. |        |             |          |     |     |
| The     | logarithm    | function |            | is 1   | -1.         |          |     |     |
Example
| Sketch | the graphs | of  | (a) f(x) | =   | log x | (b) | g(x) = log | x   |
| ------ | ---------- | --- | -------- | --- | ----- | --- | ---------- | --- |
|        |            |     |          |     | 2     |     |            | 1   |
2
Definition
| Common | Logarithm: |     | f(x) | = log | x is | called common | logarithm |     |
| ------ | ---------- | --- | ---- | ----- | ---- | ------------- | --------- | --- |
10
|         | function.  |     | We   | simply | write as    | f(x) =  | logx.     |     |
| ------- | ---------- | --- | ---- | ------ | ----------- | ------- | --------- | --- |
| Natural | Logarithm: |     | f(x) | = log  | x is called | natural | logarithm |     |
e
|     | function. |     | We  | simply | write as | f(x) = | lnx. |     |
| --- | --------- | --- | --- | ------ | -------- | ------ | ---- | --- |

Matrix,DeterminantandSystemsoflinearequations






Matrix,DeterminantandSystemsoflinearequations
Out lines
| Matrix, Determinant | and Systems | of linear equations |
| ------------------- | ----------- | ------------------- |
1
| Definitions and Examples |     |     |
| ------------------------ | --- | --- |
Matrix algebra(operations)
| Determinant and   | itâ€™s Properties |     |
| ----------------- | --------------- | --- |
| Systems of Linear | Equations       |     |

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
Matrix
Definition
| A matrix with | m rows and | n columns | is a rectangular | array of |
| ------------- | ---------- | --------- | ---------------- | -------- |
| numbers of    | the form   |           |                  |          |
|               | ï£«          |           | ï£¶                |          |
|               | a 11       | a 12 .    | . . a 1n         |          |
|               | a          | a .       | . . a            |          |
|               | ï£¬ 21       | 22        | 2n ï£·             |          |
|               | ï£¬          |           | ï£·                |          |
|               | ï£¬ .        | .         | . ï£·              |          |
ï£·,
ï£¬
|          | ï£¬ .          | .           | . ï£·             |              |
| -------- | ------------ | ----------- | --------------- | ------------ |
|          | ï£¬            |             | ï£·               |              |
|          | .            | .           | .               |              |
|          | ï£­            |             | ï£¸               |              |
|          | a            | a .         | . . a           |              |
|          | m1           | m2          | mn              |              |
| The size | or order of  | a matrix    | with m rows and | n columns is |
| mÃ—n      | (and read it | as m by n). |                 |              |

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
| We often | use | capital | letters | to denote | matrices, | and | lower |
| -------- | --- | ------- | ------- | --------- | --------- | --- | ----- |
case letters to denote its elements. In short, we write a matrix
| as A = | (a     | ) or A    | = (a )       | or  | simply A | = (a ) | if the |
| ------ | ------ | --------- | ------------ | --- | -------- | ------ | ------ |
|        | ij     | mÃ—n       | ij           | m,n |          | ij     |        |
| order  | is not | important | to emphasis. |     |          |        |        |
ith
The element a ij of a matrix A = (a ij ) mÃ—n is located in the
| row and | the | jth column. |     |     |     |     |     |
| ------- | --- | ----------- | --- | --- | --- | --- | --- |
Example
|     |     |     |     | ï£«   | ï£¶   |     |     |
| --- | --- | --- | --- | --- | --- | --- | --- |
1 âˆ’2
| (cid:18) |     | 1 (cid:19) |     |     |     |           |       |
| -------- | --- | ---------- | --- | --- | --- | --------- | ----- |
| âˆ’4       | 3   |            |     |     |     |           |       |
| A =      |     | 3 and      | B = | ï£­ 2 | 4 ï£¸ | are a 2Ã—3 | and a |
1 âˆ’1 6
|              |              |     |     | âˆ’4  | 16  |     |     |
| ------------ | ------------ | --- | --- | --- | --- | --- | --- |
| 3Ã—2 matrices | respectively |     |     |     |     |     |     |
Definition
| A matrix with | a   | single column | (or | an nÃ—1 | matrix) | is called | a   |
| ------------- | --- | ------------- | --- | ------ | ------- | --------- | --- |
column vector(or column matrix). A matrix with single row( or
| 1Ã—n matrix) | is  | called a | row vector( | or  | row matrix). |     |     |
| ----------- | --- | -------- | ----------- | --- | ------------ | --- | --- |

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
Example
| ï£«   | ï£¶   |     |     |     |
| --- | --- | --- | --- | --- |
5
(cid:0) (cid:1)
| A = ï£­ | 6 ï£¸ is a column | vector(matrix) | and B = | 1 2 3 is |
| ----- | --------------- | -------------- | ------- | -------- |
11
a row vector(matrix).
Definition
An mÃ—n matrix A = (a ) is said to be the zero (null) matrix if
ij
| a ij = 0 | for all i,j. |     |     |     |
| -------- | ------------ | --- | --- | --- |
Example
|          |              |              | ï£«       | ï£¶            |
| -------- | ------------ | ------------ | ------- | ------------ |
|          |              |              | 0       | 0            |
| (cid:18) | 0 0 (cid:19) |              |         |              |
| O =      | is a 2Ã—2     | zero matrix, | O = ï£­ 0 | 0 ï£¸ is a 3Ã—2 |
|          | 0 0          |              |         |              |
|          |              |              | 0       | 0            |
zero matrix.

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
Definition
A matrix A = (a ) is called a square matrix of order n( or an n
ij mÃ—n
| square matrix) |     | if m=n. |     |     |
| -------------- | --- | ------- | --- | --- |
Example
| ï£«   | 5 4  | 1   | ï£¶                    |     |
| --- | ---- | --- | -------------------- | --- |
| A = | âˆ’1 0 | 2   | ï£¸is a square matrix. |     |
ï£­
0 3 âˆ’6
Definition
| The entries | a        | ,a ,a | ,...a are called | the main (principal) |
| ----------- | -------- | ----- | ---------------- | -------------------- |
|             | 11       | 22    | 33 nn            |                      |
| diagonal    | elements | of    | a square matix   | (a ) .               |
ij n

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
| Types of square | matrices |                  |          |         |             |           |
| --------------- | -------- | ---------------- | -------- | ------- | ----------- | --------- |
| (a) Triangular  | matrix:  |                  |          |         |             |           |
| A square        | matrix   | A =              | (a )     | is said | to be       | a         |
|                 |          |                  | ij       | n       |             |           |
|                 | (i)      | lower triangular |          | if all  | the entries | above     |
|                 |          | the main         | diagonal | are     | 0, or       | a = 0 for |
ij
|     |      | all i <  | j          |        |             |          |
| --- | ---- | -------- | ---------- | ------ | ----------- | -------- |
|     | (ii) | upper    | triangular | if all | the entries | below    |
|     |      | the main | diagonal   | are    | all 0,      | or a = 0 |
ij
|              |          | for all    | i > j.       |              |             |          |
| ------------ | -------- | ---------- | ------------ | ------------ | ----------- | -------- |
|              | (iii)    | triangular | if           | it is either | upper       | or lower |
|              |          | triangular | matrix.      |              |             |          |
| (b) Diagonal | matrix:  | A          | square       | matrix       | D is called | a        |
| diagonal     | matrix   | if all     | the elements |              | except      | the      |
| principal    | diagonal | elements   |              | are zero.    |             |          |
Example
content...

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
|           | (c) Scalar   | matrix:           |                     |               |
| --------- | ------------ | ----------------- | ------------------- | ------------- |
|           | A diagonal   | matrix D          | is called a scalar  | matrix if the |
|           | main         | diagonal elements | are equal.          |               |
|           | (d) Identity | matrix:           |                     |               |
|           | A scalar     | matrix whose      | all of its diagonal | elements are  |
|           | 1, is an     | identity matrix.  |                     |               |
| Notation: | An identity  | matrix of         | order n is denoted  | by I          |
n

Matrix,DeterminantandSystemsoflinearequations
DefinitionsandExamples
| Equality | of Matrices |     |     |     |     |     |
| -------- | ----------- | --- | --- | --- | --- | --- |
Definition
Two matrices A and B are said to be equal( written as A = B) if
| they have | the same | size and | their | corresponding | elements are |     |
| --------- | -------- | -------- | ----- | ------------- | ------------ | --- |
equal.
Example
| ï£«       |          | ï£¶      |       | ï£«    | ï£¶           |        |
| ------- | -------- | ------ | ----- | ---- | ----------- | ------ |
|         | 1 2      | âˆ’1     |       | 1 2  | w           |        |
| A =     | 2 âˆ’3     | 4 and  | B =   | 2 x  | 4 are equal | if and |
| ï£­       |          | ï£¸      |       | ï£­    | ï£¸           |        |
|         | 0 âˆ’4     | 5      |       | y âˆ’4 | z           |        |
| only if | w = âˆ’1,x | = âˆ’3,y | = 0,z | = 5. |             |        |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
Matrix algebra
| Definition | (Scalar Multiplication) |       |                         |     |
| ---------- | ----------------------- | ----- | ----------------------- | --- |
| Let A =    | (a ) be a matrix,       | and Î± | is a scalar (a number). | The |
ij mÃ—n
product of the scalar Î± with matrix A, denoted by Î±A, is defined by
Î±A = (Î±a ij ) mÃ—n
Example
|     | (cid:18) (cid:19) |     |     |     |
| --- | ----------------- | --- | --- | --- |
3 âˆ’2 1
| Let A = | . Find | 3A. |     |     |
| ------- | ------ | --- | --- | --- |
0 4 2

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
| Addition | of Matrices |     |     |     |     |
| -------- | ----------- | --- | --- | --- | --- |
Definition
| Let A | = (a )     | and    | B = (b )   | be two matrices. | The sum of |
| ----- | ---------- | ------ | ---------- | ---------------- | ---------- |
|       | ij mÃ—n     |        | ij mÃ—n     |                  |            |
| A and | B, denoted | by A+B | is defined | by               |            |
|       |            |        | A+B = (a   | ij +b ij ) mÃ—n   |            |
Example
|       | (cid:18) |     | (cid:19) | (cid:18) | (cid:19) |
| ----- | -------- | --- | -------- | -------- | -------- |
|       | âˆ’3       | 5   | âˆ’1       | 4 âˆ’4     | âˆ’6       |
| Let A | =        |     | and      | B =      | . Find   |
|       | 1        | âˆ’1  | 7        | 5 11     | 4        |
| A+B   | and B +A |     |          |          |          |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
Properties
Theorem
| Let A, | B, C be mÃ—n   | matrices       | and Î±, Î²         | be constants. | Then      |
| ------ | ------------- | -------------- | ---------------- | ------------- | --------- |
|        | (a) + is      | commutative    |                  |               |           |
|        | (b) + is      | associative    |                  |               |           |
|        | (c) Existence | of additive    | identity         |               |           |
|        | (d) Existence | of an          | additive inverse | matrix        |           |
|        | (e) scalar    | multiplication | is distributive  | over          | addition. |
|        | (f) (Î±+Î²)A    | = Î±A+Î²A        |                  |               |           |
|        | (g) (Î±Î²)A     | = Î±(Î²A)        | = Î²(Î±A)          |               |           |
|        | (h) 0A        | = O, Î±O =      | O                |               |           |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
| Multiplication | of Matrices |     |     |     |     |     |     |
| -------------- | ----------- | --- | --- | --- | --- | --- | --- |
Definition
Let A = (a ij ) mÃ—n and B = (b ij ) nÃ—p matrices. Then the matrix
| product | AB is defined | as C | = (c ) | with | entries |     |     |
| ------- | ------------- | ---- | ------ | ---- | ------- | --- | --- |
ij mÃ—p
|     | c = | a b +a | b +a  | b     | +...+a | b     |     |
| --- | --- | ------ | ----- | ----- | ------ | ----- | --- |
|     | ij  | i1 1j  | i2 2j | i3 3j |        | in nj |     |
n
(cid:88)
|     | =   | a b | , for 1 | â‰¤ i â‰¤ | m,1 | â‰¤ j â‰¤ p |     |
| --- | --- | --- | ------- | ----- | --- | ------- | --- |
ik kj
k=1
Example
|     | ï£«   | ï£¶   |          |     |          |     |     |
| --- | --- | --- | -------- | --- | -------- | --- | --- |
|     | âˆ’1  | 3   | (cid:18) |     | (cid:19) |     |     |
0 âˆ’5
| Let A = | ï£­ 1 âˆ’2 | ï£¸, B | =   |     | ,   |     |     |
| ------- | ------ | ---- | --- | --- | --- | --- | --- |
|         |        |      | âˆ’3  | 2   |     |     |     |
0 4
| (cid:18) |     | (cid:19) |     |     |     |     |     |
| -------- | --- | -------- | --- | --- | --- | --- | --- |
1 âˆ’3 4
| C = |      | . Then | find | AB, BA, | AC  | and CA | (if exist). |
| --- | ---- | ------ | ---- | ------- | --- | ------ | ----------- |
|     | âˆ’2 0 | 1      |      |         |     |        |             |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
| Matrix | multiplication | is not commutative. |     |     |     |
| ------ | -------------- | ------------------- | --- | --- | --- |
Theorem
| Let A,B | and C be | matrices for | which | all products | below make |
| ------- | -------- | ------------ | ----- | ------------ | ---------- |
sense. Then
|     | (a) Matrix | multiplication | is      | associative. |          |
| --- | ---------- | -------------- | ------- | ------------ | -------- |
|     | That       | is, A(BC)      | = (AB)C |              |          |
|     | (b) A(B    | +C) = AB       | +AC,    | (A+B)C)      | = AC +BC |
|     | (c) AI     | = A and IA =   | A       |              |          |
|     | (d) Î±(AB)  | = (Î±A)B        | = A(Î±B) |              |          |
|     | (e) AO     | = O            |         |              |          |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
| Transpose | of matrix |     |     |
| --------- | --------- | --- | --- |
Definition
| The transpose | of a matrix | A = (a ) | , is the matrix |
| ------------- | ----------- | -------- | --------------- |
ij mÃ—n
| At = (a | ) .    |     |     |
| ------- | ------ | --- | --- |
|         | ji nÃ—m |     |     |
Example
|     | ï£«   |     | ï£¶   |
| --- | --- | --- | --- |
5 âˆ’4 7
At
| Find       | for A = ï£­      | 0 6 âˆ’10            | ï£¸.             |
| ---------- | -------------- | ------------------ | -------------- |
|            |                | âˆ’4 3 1             |                |
| Theorem    | (Laws of       | transpose)         |                |
| If A and   | B are matrices | of the appropriate | sizes, Î±,Î² are |
| constants, | then           |                    |                |
|            | (a) (At)t =    | A                  |                |
|            | (Î±A+Î²B)t       | Î±At +Î²Bt           |                |
|            | (b)            | =                  |                |
|            | (AB)t          | BtAt               |                |
|            | (c)            | =                  |                |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
| Symmetric | and Skew | Symmetric | Matrices |
| --------- | -------- | --------- | -------- |
Definition
| A square | matrix A is   | called  |     |
| -------- | ------------- | ------- | --- |
|          | (a) symmetric | if At = | A.  |
At
|     | (b) skew symmetric | if  | = âˆ’A. |
| --- | ------------------ | --- | ----- |
Example
content...

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
Elementary row operations
The following operations performed on a matrix A are called
elementray operations.
(i) Interchanging two rows ( R â†” R )
i j
(ii) Multiplying a row by a non zero constant (kR â†’ R )
i i
(iii) Adding a multiple of entries of a row to the
corresponding entries of one another row (
R +kR â†’ R ).
j i j
Example
Using elementary row operations, transform the matrix
ï£« ï£¶
3 12 6
A = ï£­ 1 1 âˆ’1 ï£¸ to an upper triangular matrix.
1 2 3

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
Remark:Two matrices are said to be row equivalent if one can be
obtained from the other by a sequence of elementary row
operations.
Definition (Row Echelon and Row Reduced Echelon Forms)
A matrix A = (a ) is a row echelon form of a matrix if
ij mÃ—n
(a) all the zero rows (if any) are at the bottom of rows
of A
(b) the first non zero entry in each non zero row is 1.
We call this leading 1.
(c) each leading 1 is to the right of the leading 1 element
in the proceeding rows.
Example
content...

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
Definition
A matrix A = (a ) is a row reduced echelon form of a matrix if
ij mÃ—n
(a) A is a row echelon form
(b) If a column contains a leading 1, then all other
entries in that column are zero
Example
content...
Remark: We apply a sequence of elementary operations on a
matrix A to obtain its row echelon or row reduced echelon form.
Example
Find a row echelon and row reduced forms of the matrix
ï£« ï£¶
âˆ’1 2 âˆ’5
A = ï£­ 2 âˆ’1 6 ï£¸
1 1 3

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
Rank of a matrix
Definition
The rank of a matrix A, denoted by rank(A), is the number of non
zero rows(columns) of its equivalent row echelon form of a matrix.
Example
| Find the | rank of the | following matrices. |       |        |     |
| -------- | ----------- | ------------------- | ----- | ------ | --- |
|          | ï£«           | ï£¶                   |       | ï£«      | ï£¶   |
|          | 1 1         | 2                   |       | 1      | 2   |
| (a) A    | = ï£­ 2 2     | 5 ï£¸                 | (b) B | = ï£­ âˆ’2 | 4 ï£¸ |
|          | 3 3         | 2                   |       | 0      | âˆ’3  |

Matrix,DeterminantandSystemsoflinearequations
Matrixalgebra(operations)
| Inverse of | a Matrix |     |     |     |
| ---------- | -------- | --- | --- | --- |
Definition
Let A be a square matrix of size n. The matrix A is said to be
invertible (non-singular) if there exist a square matrix B of size n
| such that | AB = BA = | I . |     |     |
| --------- | --------- | --- | --- | --- |
n
Example
|             | (cid:18) | (cid:19)        | (cid:18) | (cid:19) |
| ----------- | -------- | --------------- | -------- | -------- |
|             | 2        | âˆ’3              | 1        | 3        |
| Verify that | B = 5    | 5 is an inverse | of A =   | .        |
|             | 1        | 1               |          |          |
âˆ’1 2
|     | 5   | 5   |     |     |
| --- | --- | --- | --- | --- |
Notation: The inverse of an invertible matrix A is denoted as Aâˆ’1.

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
| Determinant | and itâ€™s | Properties |     |     |     |
| ----------- | -------- | ---------- | --- | --- | --- |
The determinant is a function which assigns to each square matrix
| A = (a | ) a number,    | and it is | denoted by det(A) | or |A|   | or âˆ†. |
| ------ | -------------- | --------- | ----------------- | -------- | ----- |
|        | ij n           |           |                   |          |       |
|        | A. Determinant | of        | square matrix     | of order | 1 and |
|        | order          | 2         |                   |          |       |
|        |                | (i) For   | a 1Ã—1 matrix      | A = (a   | ),    |
11
|     |     | det(A) | = a . |     |     |
| --- | --- | ------ | ----- | --- | --- |
11
|     |     |          |              | (cid:18) | (cid:19) |
| --- | --- | -------- | ------------ | -------- | -------- |
|     |     |          |              | a        | b        |
|     |     | (ii) For | a 2Ã—2 matrix | A =      | ,        |
|     |     |          |              | c        | d        |
|     |     |          | (cid:12)     | (cid:12) |          |
a b
|     |     |        | (cid:12)     | (cid:12)           |     |
| --- | --- | ------ | ------------ | ------------------ | --- |
|     |     | det(A) | = (cid:12)   | (cid:12) = ad âˆ’bc. |     |
|     |     |        | (cid:12) c d | (cid:12)           |     |
Example
| Find the | determinants    | of the following | matrices.         |         |                   |
| -------- | --------------- | ---------------- | ----------------- | ------- | ----------------- |
|          |                 |                  | (cid:18) (cid:19) |         | (cid:18) (cid:19) |
|          | (cid:0) (cid:1) |                  | âˆ’3 5              |         | 1 2               |
| (a) A    | = âˆ’2            | (b) A =          |                   | (c) A = |                   |
|          |                 |                  | 1 4               |         | 3 6               |

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
| Minors and | Cofactors |     |     |     |     |     |     |     |
| ---------- | --------- | --- | --- | --- | --- | --- | --- | --- |
Definition
| Let A | = (a ) be | a matrix | and | A be | an nâˆ’1Ã—nâˆ’1 |     | sub matrix |     |
| ----- | --------- | -------- | --- | ---- | ---------- | --- | ---------- | --- |
|       | ij n      |          |     | ij   |            |     |            |     |
of A obtained from A by deleting the ith row and the jth column.
|     | (a) The | minor | of a | ,denoted | by M | is the | determinant | of  |
| --- | ------- | ----- | ---- | -------- | ---- | ------ | ----------- | --- |
|     |         |       | ij   |          |      | ij     |             |     |
A .
ij
|     | (b) the    | cofactor   | of a      | , denoted | by      | C is defined | as      |     |
| --- | ---------- | ---------- | --------- | --------- | ------- | ------------ | ------- | --- |
|     |            |            |           | ij        |         | ij           |         |     |
|     | C          | = (âˆ’1)i+jM |           |           |         |              |         |     |
|     | ij         |            | ij        |           |         |              |         |     |
|     | (c) Matrix | of         | Cofactors | of a      | matrix, | denoted      | by C is | a   |
|     | matrix     | C          | = (C )    | .         |         |              |         |     |
|     |            |            | ij        | n         |         |              |         |     |
Example
|       | ï£«        |      | ï£¶       |      |          |          |     |     |
| ----- | -------- | ---- | ------- | ---- | -------- | -------- | --- | --- |
|       | 1        | âˆ’1 2 |         |      |          |          |     |     |
| Let A | = ï£­ âˆ’2   | 0 5  | ï£¸. Find | M 11 | ,M 12 ,M | 22 ,M 32 | ,   |     |
|       | âˆ’3       | 4 3  |         |      |          |          |     |     |
| C ,C  | ,C ,C    |      |         |      |          |          |     |     |
| 11    | 12 22 32 |      |         |      |          |          |     |     |

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
|     | B. Determinant |     | of square matrix | of order | n, n â‰¥ 3. |
| --- | -------------- | --- | ---------------- | -------- | --------- |
Definition
| For a | square matrix | A = | (a ij ) n , |     |     |
| ----- | ------------- | --- | ----------- | --- | --- |
(cid:80)n
|     | (a) det(A) | = a       | C +a C +....+a | C =      | a C ,     |
| --- | ---------- | --------- | -------------- | -------- | --------- |
|     |            | i1        | i1 i2 i2       | in in    | k=1 ik ik |
|     | where,     | 1 â‰¤       | i â‰¤ n.         |          |           |
|     | (Cofactor  | expansion | along the      | ith row) |           |
(cid:80)n
(b) det(A) = a 1j C 1j +a 2j C 2j +....+a nj C nj = a kj C kj ,
k=1
|     | where,    | 1 â‰¤       | j â‰¤ n.    |              |     |
| --- | --------- | --------- | --------- | ------------ | --- |
|     | (Cofactor | expansion | along the | jth column). |     |
Example
content...

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
Properties of determinant
| (a) If a      | matrix      | B results   | from      | a square       | matrix | A by          |      |
| ------------- | ----------- | ----------- | --------- | -------------- | ------ | ------------- | ---- |
| interchanging |             | two         | different | rows(columns)  |        | of A,         | then |
|               |             |             | det(B)    | = âˆ’det(A)      |        |               |      |
| (b) If B      | is obtained | from        | A         | by multiplying |        | a row(column) |      |
| of            | A by a      | real number | k,        | then           |        |               |      |
|               |             |             | det(B)    | = kdet(A)      |        |               |      |
.
| (c) If A | is a square     | matrix   | of           | order      | n,            |               |     |
| -------- | --------------- | -------- | ------------ | ---------- | ------------- | ------------- | --- |
| det(kA)  | =               | kndet(A) | for          | some       | constant      | k.            |     |
| (d) If B | is obtained     | from     | A            | by adding  | to each       | element       | of  |
| the      | rth row(column) |          | of           | A, k times | the           | corresponding |     |
| element  | of              | the sth  | row(column), |            | r (cid:54)= s | of A, then    |     |
|          |                 |          | det(A)       | = det(B)   |               |               |     |

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
| (e) If A     | is a square    | matrix, | det(A)          | = det(At)       |             |
| ------------ | -------------- | ------- | --------------- | --------------- | ----------- |
| (f) If two   | rows(columns)  |         | of a            | square matrix A | are         |
| proportional |                | then    |                 |                 |             |
|              |                |         | det(A)          | = 0             |             |
| (g) If a     | row(column)    |         | of A consists   | entirely of     | zeros, then |
| det(A)       | = 0.           |         |                 |                 |             |
| (h) The      | determinant    |         | of a triangular | matrix is       | the product |
| of           | the elements   | on      | the main        | diagonal.       |             |
| (i) If A     | and B are      | square  | matrices        | of the same     | order,      |
|              |                | det(AB) | =               | det(A)det(B)    |             |
|              |                |         | det(Aâˆ’1)        | 1               |             |
| (j) If A     | is invertible, | then    |                 | = .             |             |
det(A)

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
| Adjoint of a matrix |     |     |     |     |
| ------------------- | --- | --- | --- | --- |
Definition
| Let A be nÃ—n | matrix and | C denote | the | cofactor of a . The |
| ------------ | ---------- | -------- | --- | ------------------- |
ij ij
adjoint of A, denoted by adj(A), is the transpose of the matrix of
| cofactors, C | = (C ) . |     |     |     |
| ------------ | -------- | --- | --- | --- |
ij n
| Theorem (Determinant |     | test for | invertiblity) |     |
| -------------------- | --- | -------- | ------------- | --- |
A square matrix A is invertible if and only if detA (cid:54)= 0.
Corollary
| If A is an invertible | matrix, | then Aâˆ’1 | =   | 1 adj(A) |
| --------------------- | ------- | -------- | --- | -------- |
det(A)

Matrix,DeterminantandSystemsoflinearequations
Determinantanditâ€™sProperties
Example
Find the inverse of each of the following matrices(if exist).
|         | ï£«    | ï£¶   |         | ï£« ï£¶   |
| ------- | ---- | --- | ------- | ----- |
|         | 2 3  | âˆ’4  |         | 1 2 2 |
| (a) A = | 0 âˆ’4 | 2   | (b) B = | 1 3 1 |
|         | ï£­    | ï£¸   |         | ï£­ ï£¸   |
|         | 1 âˆ’1 | 5   |         | 1 1 3 |

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
Systems of Linear Equations
Definition
A system of m linear equations in n unknowns x ,x ,...x is a set
1 2 n
| of linear equations given | by            |              |
| ------------------------- | ------------- | ------------ |
| a 11 x 1 +a               | 12 x 2 +...+a | 1n x n = b 1 |
| a x +a                    | x +...+a      | x = b        |
| 21 1                      | 22 2          | 2n n 2       |
| .                         |               | .            |
| .                         |               | .            |
| .                         |               | .            |
| a x +a                    | x +...+a      | x = b ,      |
| m1 1                      | m2 2          | mn n m       |

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
| Using a | matrix | relation | we    | write    | as    |     |          |     |
| ------- | ------ | -------- | ----- | -------- | ----- | --- | -------- | --- |
|         |        |          |       | AX       | = B   |     |          | (1) |
|         |        |          |       | (cid:0)  |       |     | (cid:1)t |     |
| where A | = (a   | )        | , X = | x        | x . . | . x | , and    |     |
|         |        | ij mÃ—n   |       | 1        | 2     | n   |          |     |
| (cid:0) |        |          |       | (cid:1)t |       |     |          |     |
| B =     | b b    | . .      | . b   |          |       |     |          |     |
|         | 1      | 2        |       | m        |       |     |          |     |
A matrix A of the system (1) is called the coefficient matrix,
| and  | the     | matrix          | obtained | by          | adjoining          | the column | vector     | B at |
| ---- | ------- | --------------- | -------- | ----------- | ------------------ | ---------- | ---------- | ---- |
| the  | end     | to the          | matrix   | A is called | the                | augmented  | matrix     | of   |
| the  | system, | denoted         |          | by M =      | (A|B) or           | M =        | (A : B).   |      |
| If B | = O,    | the system      |          | (1) is      | called homogenous; |            | otherwise, | it   |
| is   | called  | non homogenous. |          |             |                    |            |            |      |

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
Example
Given a system
|     | 2x +x     | âˆ’5x = 5 |     |
| --- | --------- | ------- | --- |
|     | 1 2       | 3       |     |
|     | x +x âˆ’4x  | = 0     |     |
|     | 2 2       | 3       |     |
|     | âˆ’5x 1 âˆ’6x | 3 = 12  |     |
Determine
| (a) The coefficient | and     | the augmented | matrices. |
| ------------------- | ------- | ------------- | --------- |
| (b) The matrix      | form of | the system.   |           |

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
For the system of linear equations in (1), precisely one of the
| statements below | is true: |     |     |     |
| ---------------- | -------- | --- | --- | --- |
It admits a unique Solution: There is one and only one vector
1
| x = (x ,x ,...x | ) that | satisfies all the | m-equations |     |
| --------------- | ------ | ----------------- | ----------- | --- |
1 2 n
| simultaneously    | (the system | is consistent).  |                |      |
| ----------------- | ----------- | ---------------- | -------------- | ---- |
| It has infinitely | Many        | Solutions: There | are infinitely | many |
2
| different values          | of x that            | satisfy all the | m-equations      |      |
| ------------------------- | -------------------- | --------------- | ---------------- | ---- |
| simultaneously            | (the system          | is said to      | be consistent).  |      |
| 3 Has no Solution:        | There                | is no vector    | x that satisfies | all  |
| equations simultaneously, |                      | or the solution | set is empty     | (the |
| system is said            | to be inconsistent). |                 |                  |      |

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
| Solving systems |          | of systems   | of linear    | equations    |                 |         |
| --------------- | -------- | ------------ | ------------ | ------------ | --------------- | ------- |
| Matrix          | Methods: | Gaussian     | Elimination, | Gauss-Jordan |                 | method, |
| Cramerâ€™s        | rule,    | Inverse      | Method.      |              |                 |         |
|                 | 1.       | Gaussian     | Elimination: |              |                 |         |
| The method      | of       | solving      | a system     | AX = B       | by transforming | the     |
| augmented       | matrix   | M            | = (A|B) to   | its row      | echelon form    | using   |
| elementary      | row      | operations   | is called    | Gaussian     | Elimination.    |         |
|                 | 2.       | Gauss-Jordan | Method:      |              |                 |         |
| The method      | of       | solving      | a system     | AX = B       | by transforming | the     |
augmented matrix M to row reduced echelon form of a matrix of
the system using elementary row operations is called Gauss-Jordan
reduction.

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
Example
Using Gaussian elimination method, solve the system of linear
equations.
x +2y +z = 3
2x +3y âˆ’z = âˆ’6
3x âˆ’2y âˆ’4z = âˆ’2

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
Theorem
| Let A be | an invertible | nÃ—n | matrix, | A denote | the matrix |     |
| -------- | ------------- | --- | ------- | -------- | ---------- | --- |
xi
ith
| obtained | from A by | replacing | the | column | of A with | the n- |
| -------- | --------- | --------- | --- | ------ | --------- | ------ |
vector B. If |A| =(cid:54) 0, the linear system AX = B, where
| (cid:0) |         |               | (cid:1)t |        |                |     |
| ------- | ------- | ------------- | -------- | ------ | -------------- | --- |
| X =     | x x . . | . x           | has a    | unique | solution given | by  |
|         | 1 2     | n             |          |        |                |     |
| det(Axi | )       |               |          |        |                |     |
| x =     | where i | = 1,2,3,...,n |          |        |                |     |
i det(A)
| 3.Cramerâ€™s | rule: |     |     |     |     |     |
| ---------- | ----- | --- | --- | --- | --- | --- |
Example
| Using Cramerâ€™s | rule, | solve | the system | of linear | equations. |     |
| -------------- | ----- | ----- | ---------- | --------- | ---------- | --- |
|                |       | 2x    | 1 âˆ’x 2 =   | 0         |            |     |
|                |       | âˆ’x    | +2x âˆ’x     | = 0       |            |     |
|                |       |       | 1 2        | 3         |            |     |
|                |       | âˆ’x    | +x = 1     |           |            |     |
2 3

Matrix,DeterminantandSystemsoflinearequations
SystemsofLinearEquations
4. Inverse method:
Theorem
If A is an invertible matrix, the linear system AX = B has a unique
solution, which is given by
X = Aâˆ’1B

---

© Tamhero. All rights reserved.