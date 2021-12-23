package com.example;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MathParser {

    private static DecimalFormat decimalFormat;

    static {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);
    }

    public static String evaluate(String expression) {
        return evaluate(expression, null);
    }

    public static String evaluate(String expression, DecimalFormat format) {
        if (expression == null) {
            throw new NullPointerException("expression must not be null");
        }

        if (format != null) {
            decimalFormat = format;
        }

        return calculate(shuntingYard(tokenizeString(expression)));
    }

    private static List<Token> tokenizeString(String input) {
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> i = readNumber(input, i, tokens);
                case '(' -> tokens.add(new Token(TokenType.LEFT_BRACKET, "("));
                case ')' -> tokens.add(new Token(TokenType.RIGHT_BRACKET, ")"));
                case '^' -> tokens.add(new Token(TokenType.POW, "^"));
                case 's' -> {
                    String str = input.substring(i, i + 4);
                    if (str.equals("sin(")) {
                        tokens.add(new Token(TokenType.SIN, "sin"));
                    }
                    else {
                        throw new InvalidExpressionSyntaxException("Unexpected char sequence: " + str);
                    }
                    i = i + 2;
                }
                case 'c' -> {
                    String str = input.substring(i, i + 4);
                    if (str.equals("cos(")) {
                        tokens.add(new Token(TokenType.COS, "cos"));
                    }
                    else {
                        throw new InvalidExpressionSyntaxException("Unexpected char sequence: " + str);
                    }
                    i = i + 2;
                }
                case 't' -> {
                    String str = input.substring(i, i + 4);
                    if (str.equals("tan(")) {
                        tokens.add(new Token(TokenType.TAN, "tan"));
                    }
                    else {
                        throw new InvalidExpressionSyntaxException("Unexpected char sequence: " + str);
                    }
                    i = i + 2;
                }
                case '*' -> tokens.add(new Token(TokenType.MUL, "*"));
                case '/' -> tokens.add(new Token(TokenType.DIV, "/"));
                case '%' -> tokens.add(new Token(TokenType.MOD, "%"));
                case '+' -> tokens.add(new Token(TokenType.PLUS, "+"));
                case '-' -> {
                    if (tokens.isEmpty() || tokens.get(tokens.size() - 1).getType() != TokenType.NUMBER) {
                        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).getType() == TokenType.RIGHT_BRACKET) {
                            tokens.add(new Token(TokenType.MINUS, "-"));
                        }
                        else {
                            c = input.charAt(i + 1);
                            if (c == '(' || c == 's' || c == 'c' || c == 't') {
                                tokens.add(new Token(TokenType.NUMBER, "-1"));
                                tokens.add(new Token(TokenType.MUL, "*"));
                            } else {
                                i = readNumber(input, i, tokens);
                            }
                        }
                    }
                    else {
                        tokens.add(new Token(TokenType.MINUS, "-"));
                    }
                }
                case ' ' -> {}
                default -> throw new UnsupportedTokenException("Unexpected char '" + c + "' at " + i + " position");
            }
        }

        return tokens;
    }

    private static List<Token> shuntingYard(List<Token> tokens) {
        List<Token> result = new ArrayList<>();
        Stack<Token> stack = new Stack<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case NUMBER -> result.add(token);
                case LEFT_BRACKET -> stack.push(token);
                case RIGHT_BRACKET -> {
                    while (stack.peek().getType() != TokenType.LEFT_BRACKET) {
                        result.add(stack.pop());
                    }
                    stack.pop();
                }
                case POW, SIN, COS, TAN -> {
                    if (stack.empty()) {
                        stack.push(token);
                    }
                    else {
                        switch (stack.peek().getType()) {
                            case POW, SIN, COS, TAN -> {
                                result.add(stack.pop());
                                stack.push(token);
                            }
                            case LEFT_BRACKET, MUL, DIV, MOD, PLUS, MINUS -> stack.push(token);
                        }
                    }
                }
                case MUL, DIV, MOD -> {
                    if (stack.empty()) {
                        stack.push(token);
                    }
                    else {
                        switch (stack.peek().getType()) {
                            case POW, SIN, COS, TAN, MUL, DIV, MOD -> {
                                result.add(stack.pop());
                                stack.push(token);
                            }
                            case LEFT_BRACKET, PLUS, MINUS -> stack.push(token);
                        }
                    }
                }
                case PLUS, MINUS -> {
                    if (stack.empty()) {
                        stack.push(token);
                    }
                    else {
                        switch (stack.peek().getType()) {
                            case POW, SIN, COS, TAN, MUL, DIV, MOD, PLUS, MINUS -> {
                                result.add(stack.pop());
                                stack.push(token);
                            }
                            case LEFT_BRACKET -> stack.push(token);
                        }
                    }
                }
            }
        }

        while (!stack.empty()) {
            result.add(stack.pop());
        }

        return result;
    }

    private static String calculate(List<Token> tokens) {
        Stack<Token> stack = new Stack<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case NUMBER -> stack.push(token);
                case POW -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    double pow = Math.pow(first, second);
                    if (first < 0) {
                        pow = -1 * pow;
                    }
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(pow)));
                }
                case SIN -> {
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(Math.sin(Math.toRadians(first)))));
                }
                case COS -> {
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(Math.cos(Math.toRadians(first)))));
                }
                case TAN -> {
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(Math.tan(Math.toRadians(first)))));
                }
                case MUL -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first * second)));
                }
                case DIV -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    if (second == 0) {
                        throw new ArithmeticException("Division by zero - " + first + " / " + second);
                    }
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first / second)));
                }
                case MOD -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    if (second == 0) {
                        throw new ArithmeticException("Division by zero - " + first + " % " + second);
                    }
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first % second)));
                }
                case PLUS -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first + second)));
                }
                case MINUS -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first - second)));
                }
            }
        }

        return decimalFormat.format(Double.parseDouble(stack.pop().getValue()));
    }

    private static int readNumber(String input, int currentPosition, List<Token> tokens) {
        int x = currentPosition + 1;
        boolean isDotPresent = false;
        while (x < input.length()) {
            char c = input.charAt(x);
            if ((c >= '0' && c <= '9') || c == '.') {
                if (c == '.') {
                    if (!isDotPresent) {
                        isDotPresent = true;
                    }
                    else {
                        throw new InvalidExpressionSyntaxException(input.substring(currentPosition, ++x) + " is not a valid number");
                    }
                }
                x++;
            }
            else {
                break;
            }
        }
        tokens.add(new Token(TokenType.NUMBER, input.substring(currentPosition, x)));
        currentPosition = x - 1;
        return currentPosition;
    }
}