import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contains the dictionaries that define the precedence and associativity of tokens.
 */
public class TokenPrecedence {

  private static final Map<TokenType, Integer> precedence = new HashMap<>();
  private static final Set<TokenType> rightAssociative = new HashSet<>();

  static {
    precedence.put(TokenType.STAR, 3);
    precedence.put(TokenType.PLUS, 3);
    precedence.put(TokenType.QUESTION, 3);
    precedence.put(TokenType.CONCAT, 2);
    precedence.put(TokenType.OR, 1);
    rightAssociative.add(TokenType.STAR);
    rightAssociative.add(TokenType.PLUS);
    rightAssociative.add(TokenType.QUESTION);
  }

  public static int getPrecedence(TokenType type) {

    return precedence.get(type);
  }

  public static boolean isRightAssociative(TokenType type) {

    return rightAssociative.contains(type);
  }
}
