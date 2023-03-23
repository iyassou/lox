package com.craftinginterpreters.lox;

class BreakError extends RuntimeException {
    final Token token;

    BreakError(Token token, String message) {
        super(message);
        this.token = token;
    }
}
