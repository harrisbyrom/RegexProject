import java.util.Set;
/**
 * Class representing NDFA Fragments that are joined together to create a complete NDFA.
 */

public class NdfaFragment {
  State start;
  Set<State> acceptStates;

  /**
   * Constructs NdfaFragment directly from a single token.
   *
   * @param literalToken input token that the NdfaFragment is constructed based on.
   */
  public NdfaFragment(Token literalToken) {
    State accept = new State(true);
    this.start = new State(false);
    start.addTransition(new Transition(literalToken, accept));
    acceptStates = Set.of(accept);
  }

  /**
   * Constructs NdfaFragment given existing start and accept states.
   *
   * @param start input existing start state.
   * @param acceptStates input existing accept states.
   */
  public NdfaFragment(State start, Set<State> acceptStates) {
    this.start = start;
    this.acceptStates = acceptStates;
  }

  public State getStart() {
    return start;
  }

  public Set<State> getAcceptStates() {
    return acceptStates;
  }
}
