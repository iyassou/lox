# lox

Reading through "Crafting Interpreters" by Robert Nystrom.

**Now**: finished chapter 26, "Garbage Collection".

## `clox` implementation

**Additional features**:

- [X] Run-length encoding of chunk line information (chapter 14)
- [ ] `OP_CONSTANT_LONG` opcode with 24-bit operand (chapter 14)
- [ ] Dynamically-grown VM stack (chapter 15)
- [X] Optimise `OP_NEGATE` (chapter 15)
- [ ] `ObjString` single allocation using flexible array member (chapter 19)
- [ ] `ObjString` payload ownership distinction to reduce memory usage (chapter 19)
- [ ] Literal (`true, false, nil`) and numerical hash table keys (chapter 20)
- [ ] Hash table benchmarking (chapter 20)
- [X] Optimise global variable constant table usage (chapter 21)
- [ ] Single-assignment variables using the `const` keyword (chapter 22)
- [ ] Support >256 local variables in the same scope (chapter 22)
- [ ] `switch` statement (with neither fallthrough nor `break`) (chapter 23)
- [ ] `continue` statement (chapter 23)
- [ ] Native function arity checking (chapter 24)
- [ ] Native functions report runtime errors (chapter 24)
- [ ] Only wrap functions that need upvalues in `ObjClosure` (chapter 25)
- [ ] Create a new variable for each loop iteration (chapter 25)

## `jlox` implementation

**Additional features**:

- [X] C-style `/* ... */` block comments (chapter 4)
- [X] Reverse Polish notation syntax tree printer (chapter 5)
- [ ] Comma expressions (chapter 6)
- [X] C-style ternary operator `?:` (chapter 6)
- [X] Error production for binary operators missing their LHOperand (chapter 6)
- [X] Type coercion to string for + operator if either operand is a string (chapter 7)
- [X] Division by zero produces a runtime error (chapter 7)
- [ ] REPL expression support (chapter 8)
- [X] Accessing uninitialised variables is a runtime error (chapter 8) (_hmmm_...)
- [X] `break` statement (chapter 9)
- [ ] Anonymous function syntax support (chapter 10)
- [X] Extend the resolver to report an unused local variable as an error (chapter 11)
- [ ] Environment array representation (chapter 11)
- [ ] Class methods (chapter 12)
- [ ] Getter methods (chapter 12)
