import java.util.ArrayList;
import java.util.List;

public class MathParser {

    public static String evaluate(String expression) {
        return null;
    }

    private static List<Token> tokenizeString(String input) {
        if (input == null) {
            throw new NullPointerException("Input must not be null");
        }

        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    int x = i + 1;
                    while (x < input.length()) {
                        c = input.charAt(x);
                        if (c >= '0' && c <= '9') {
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
                case '-' -> tokens.add(new Token(TokenType.MINUS, "-"));
                case ' ' -> {}
                default -> throw new UnsupportedTokenException("Unexpected char '" + c + "' at " + i + " position");
            }
        }

        return tokens;
    }
}