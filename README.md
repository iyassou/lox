# lox
Reading through "Crafting Interpreters" by Robert Nystrom.

**Now**: finished chapter 9, "Control Flow".

## `jlox` implementation

**Additional features**:
- [x] C-style `/* ... */` block comments (chapter 4)
- [x] Reverse Polish notation syntax tree printer (chapter 5)
- [x] Comma expressions (chapter 6)
- [x] C-style ternary operator `?:` (chapter 6)
- [x] Error production for binary operators missing their LHOperand (chapter 6)
- [x] Type coercion to string for + operator if either operand is a string (chapter 7)
- [x] Division by zero produces a runtime error (chapter 7)
- [ ] REPL expression support (chapter 8)
- [x] Accessing uninitialised variables is a runtime error (chapter 8) (_hmmm_...)
- [ ] `break` statement support (chapter 9)
