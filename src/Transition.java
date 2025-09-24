/**
 * Class representing each transition within the NDFAs.
 */
public class Transition {

  private final Token symbol;
  private final State target;

  public Transition(Token symbol, State target) {
    this.symbol = symbol;
    this.target = target;
  }

  public State getTarget() {
    return target;
  }

  public Token getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return symbol.getCharacter() + "";
  }
}
