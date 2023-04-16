package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return parenthesize(expr.name.lexeme + " <-", expr.value);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        StringBuilder builder = new StringBuilder();
        builder.append("(CALL ").append(expr.callee.accept(this));
        if (!expr.arguments.isEmpty()) {
            builder.append(" : ");
        }
        for (int i = 0; i < expr.arguments.size(); i++) {
            builder.append(expr.arguments.get(i).accept(this));
            if (i != expr.arguments.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public String visitConditionalExpr(Expr.Conditional expr) {
        StringBuilder builder = new StringBuilder();

        builder.append("(cond ").append(expr.predicate.accept(this)).append("? ");
        builder.append(expr.iftrue.accept(this)).append(" : ");
        builder.append(expr.iffalse.accept(this)).append(")");
        
        return builder.toString();
    }

    @Override
    public String visitGetExpr(Expr.Get expr) {
        return parenthesize("GET " + expr.name.lexeme, expr.object);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return parenthesize("SET " + expr.name.lexeme, expr.object, expr.value);
    }

    @Override
    public String visitSuperExpr(Expr.Super expr) {
        return parenthesize("super");
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return parenthesize(expr.keyword.lexeme);
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return "(var " + expr.name.lexeme + ")";
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

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
        Expr assignment = new Expr.Assign(
            new Token(TokenType.IDENTIFIER, "a", null, 2),
            new Expr.Literal("global a")
        );
        Expr variable = new Expr.Variable(
            new Token(TokenType.IDENTIFIER, "b", null, 3)
        );
        Expr _and = new Expr.Logical(
            new Expr.Literal(5),
            new Token(TokenType.AND, "and", null, 4),
            new Expr.Literal(6)
        );
        Expr object = new Expr.Variable(
            new Token(TokenType.IDENTIFIER, "programmer", null, 5)
        );
        Expr get = new Expr.Get(
            object,
            new Token(TokenType.IDENTIFIER, "name", "Iyassou", 6)
        );
        Expr set = new Expr.Set(
            object,
            new Token(TokenType.IDENTIFIER, "age", null, 7),
            new Expr.Literal(21)
        );
        Expr tthis = new Expr.This(
            new Token(TokenType.THIS, "this", null, 8)
        );
        Expr cond = new Expr.Conditional(
            new Expr.Binary(
                new Expr.Literal(5),
                new Token(TokenType.GREATER, ">", null, 9),
                new Expr.Literal(6)),
            new Expr.Literal(5),
            new Expr.Literal(6)
        );
        List<Expr> arguments = new ArrayList<>();
        arguments.add(expression);
        arguments.add(new Expr.Literal(true));
        Expr callNoArgs = new Expr.Call(
            object,
            new Token(TokenType.LEFT_PAREN, "(", null, 10),
            new ArrayList<>()
        );
        Expr callArgs = new Expr.Call(
            object,
            new Token(TokenType.LEFT_PAREN, "(", null, 11),
            arguments
        );

        AstPrinter astPrinter = new AstPrinter();
        
        System.out.println(astPrinter.print(expression));
        System.out.println(astPrinter.print(assignment));
        System.out.println(astPrinter.print(variable));
        System.out.println(astPrinter.print(_and));
        System.out.println(astPrinter.print(get));
        System.out.println(astPrinter.print(set));
        System.out.println(astPrinter.print(tthis));
        System.out.println(astPrinter.print(cond));
        System.out.println(astPrinter.print(callNoArgs));
        System.out.println(astPrinter.print(callArgs));
    }
}
