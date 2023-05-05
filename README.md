# lox
Reading through "Crafting Interpreters" by Robert Nystrom.

**Now**: finished chapter 15, "A Virtual Machine".

## `clox` implementation

**Additional features**:
- [x] Run-length encoding of chunk line information (chapter 14)
- [ ] `OP_CONSTANT_LONG` opcode with 24-bit operand (chapter 14)
- [ ] Dynamically-grown VM stack (chapter 15)
- [x] Optimise `OP_NEGATE` (chapter 15)

## `jlox` implementation

**Additional features**:
- [x] C-style `/* ... */` block comments (chapter 4)
- [x] Reverse Polish notation syntax tree printer (chapter 5)
- [ ] Comma expressions (chapter 6)
- [x] C-style ternary operator `?:` (chapter 6)
- [x] Error production for binary operators missing their LHOperand (chapter 6)
- [x] Type coercion to string for + operator if either operand is a string (chapter 7)
- [x] Division by zero produces a runtime error (chapter 7)
- [ ] REPL expression support (chapter 8)
- [x] Accessing uninitialised variables is a runtime error (chapter 8) (_hmmm_...)
- [x] `break` statement (chapter 9)
- [ ] Anonymous function syntax support (chapter 10)
- [x] Extend the resolver to report an unused local variable as an error (chapter 11)
- [ ] Environment array representation (chapter 11)
- [ ] Class methods (chapter 12)
- [ ] Getter methods (chapter 12)
