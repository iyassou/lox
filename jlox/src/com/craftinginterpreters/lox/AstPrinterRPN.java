package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

class AstPrinterRPN implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return "(" + expr.name.lexeme + " " + expr.value.accept(this) + " <-)";
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
    public String visitCallExpr(Expr.Call expr) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < expr.arguments.size(); i++) {
            builder.append(expr.arguments.get(i).accept(this));
            if (i != expr.arguments.size() - 1) {
                builder.append(", ");
            }
        }
        if (!expr.arguments.isEmpty()) {
            builder.append(" : ");
        }
        builder.append(expr.callee.accept(this)).append(" CALL)");

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
        return "(" + expr.object.accept(this) + " " + expr.name.lexeme + " " + "GET)";
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
    public String visitLogicalExpr(Expr.Logical expr) {
        return "(" + expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme + ")";
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(expr.object.accept(this));
        builder.append(" ").append(expr.name.lexeme);
        builder.append(" ").append(expr.value.accept(this));
        builder.append(" SET)");
        return builder.toString();
    }

    @Override
    public String visitSuperExpr(Expr.Super expr) {
        return "(super)";
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return "(" + expr.keyword.lexeme + ")";
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return "(var " + expr.name.lexeme + ")";
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return "(" + expr.right.accept(this) + " " + expr.operator.lexeme + ")";
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

        AstPrinterRPN astPrinter = new AstPrinterRPN();
        
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
