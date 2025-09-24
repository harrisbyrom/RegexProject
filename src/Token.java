
/**
 * Represents a single token.
 */
public class Token {

  private final TokenType type;
  private final char character;
  private final int precedence;
  private final boolean rightAssociative;

  /**
   * Instantiates Token and gets precedence and associativity.
   *
   * @param type the type of token that the token is.
   * @param character character that the Token represents.
   */
  public Token(TokenType type, char character) {
    this.type = type;
    this.character = character;
    switch (type) {
      case LITERAL, LBRACKET, RBRACKET -> {
        this.precedence = 0;
        this.rightAssociative = true;
      }
      default -> {
        this.precedence = TokenPrecedence.getPrecedence(type);
        this.rightAssociative = TokenPrecedence.isRightAssociative(type);
      }
    }
  }

  /**
   * Token constructor specifically for building empty Lambda tokens.
   */
  public Token() {
    this.type = TokenType.LAMBDA;
    this.character = ' ';
    this.precedence = 0;
    this.rightAssociative = true;
  }

  public TokenType getType() {

    return type;
  }

  public int getPrecedence() {

    return precedence;
  }

  public boolean isRightAssociative() {

    return rightAssociative;
  }

  public char getCharacter() {

    return character;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Token other = (Token) obj;
    return this.getType() == other.getType()
          && this.getCharacter() == other.getCharacter()
          && this.getPrecedence() == other.getPrecedence()
          && this.isRightAssociative() == other.isRightAssociative();
  }

  @Override
public int hashCode() {
    return System.identityHashCode(this);
  }
}
