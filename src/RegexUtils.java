import java.util.ArrayList;
import java.util.List;

/**
 *Contains useful methods for regex conversion.
 */

public class RegexUtils {

  /**
   * Converts input regex into a list of Tokens and adds explicit concatenation.
   *
   * @param regex input regex to be tokenized
   * @return returns tokenized regex
   */
  public static List<Token> toTokens(String regex) {

    List<Token> tokenizedList = new ArrayList<>();
    int concatCounter = 0; /* Used to track when explicit concatenation needs to be added,
                              it is added when an open bracket or literal follows any
                              character that is not an open bracket '(' or an alternation '|' */

    for (char c : regex.toCharArray()) {

      switch (c) {
        case '|' -> {
          concatCounter = 0;
          tokenizedList.add(new Token(TokenType.OR, c));
        }
        case '*' -> {
          concatCounter = 1;
          tokenizedList.add(new Token(TokenType.STAR, c));
        }
        case '+' -> {
          concatCounter = 1;
          tokenizedList.add(new Token(TokenType.PLUS, c));
        }
        case '?' -> {
          concatCounter = 1;
          tokenizedList.add(new Token(TokenType.QUESTION, c));
        }
        case '(' -> {
          if (concatCounter == 1) {
            tokenizedList.add(new Token(TokenType.CONCAT, '·'));
          }
          concatCounter = 0;
          tokenizedList.add(new Token(TokenType.LBRACKET, c));
        }
        case ')' -> {
          concatCounter = 1;
          tokenizedList.add(new Token(TokenType.RBRACKET, c));
        }
        default -> {
          if (concatCounter == 1) {
            tokenizedList.add(new Token(TokenType.CONCAT, '·'));
          } else {
            concatCounter = 1;
          }
          tokenizedList.add(new Token(TokenType.LITERAL, c));
        }
      }
    }
    return tokenizedList;
  }

  /**
   * Converts input into list of literal tokens.
   *
   * @param input input string to be converted to literal tokens
   * @return returns tokenized output
   */
  public static List<Token> toLiteralTokens(String input) {
    List<Token> tokenizedInput = new ArrayList<>();
    for (char character : input.toCharArray()) {
      tokenizedInput.add(new Token(TokenType.LITERAL, character));
    }
    return tokenizedInput;
  }
}
