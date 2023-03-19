package com.craftinginterpreters.lox;

class AstPrinterRPN implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(expr.left.accept(this));
        builder.append(" ").append(expr.right.accept(this));
        builder.append(" ").append(expr.operator.lexeme);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return "(group " + expr.expression.accept(this) + ")";
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return "(" + expr.right.accept(this) + " " + expr.operator.lexeme + ")";
    }

    @Override
    public String visitConditionalExpr(Expr.Conditional expr) {
        StringBuilder builder = new StringBuilder();

        builder.append("(cond ").append(expr.predicate.accept(this)).append("? ");
        builder.append(expr.iftrue.accept(this)).append(" : ");
        builder.append(expr.iffalse.accept(this)).append(")");
        
        return builder.toString();
    }

    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67))
        );
        
        System.out.println(new AstPrinterRPN().print(expression));
    }
}
