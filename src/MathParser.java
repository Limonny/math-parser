import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MathParser {

    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

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
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    int x = i + 1;
                    boolean isDotPresent = false;
                    while (x < input.length()) {
                        c = input.charAt(x);
                        if ((c >= '0' && c <= '9') || c == '.') {
                            if (c == '.') {
                                if (!isDotPresent) {
                                    isDotPresent = true;
                                }
                                else {
                                    throw new InvalidExpressionSyntaxException(input.substring(i, ++x) + " is not a valid number");
                                }
                            }
                            x++;
                        }
                        else {
                            break;
                        }
                    }
                    tokens.add(new Token(TokenType.NUMBER, input.substring(i, x)));
                    i = x - 1;
                }
                case '(' -> tokens.add(new Token(TokenType.LEFT_BRACKET, "("));
                case ')' -> tokens.add(new Token(TokenType.RIGHT_BRACKET, ")"));
                case '*' -> tokens.add(new Token(TokenType.MUL, "*"));
                case '/' -> tokens.add(new Token(TokenType.DIV, "/"));
                case '+' -> tokens.add(new Token(TokenType.PLUS, "+"));
                case '-' -> {
                    if (tokens.isEmpty() || tokens.get(tokens.size() - 1).getType() != TokenType.NUMBER) {
                        tokens.add(new Token(TokenType.NUMBER, "0"));
                    }
                    tokens.add(new Token(TokenType.MINUS, "-"));
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
                case MUL, DIV -> {
                    if (stack.empty()) {
                        stack.push(token);
                    }
                    else {
                        switch (stack.peek().getType()) {
                            case MUL, DIV -> {
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
                            case MUL, DIV, PLUS, MINUS -> {
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
                case MUL -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first * second)));
                }
                case DIV -> {
                    double second = Double.parseDouble(stack.pop().getValue());
                    if (second == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    double first = Double.parseDouble(stack.pop().getValue());
                    stack.push(new Token(TokenType.NUMBER, String.valueOf(first / second)));
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
}