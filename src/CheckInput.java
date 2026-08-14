import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains checkInput method and currently used for testing.
 */
public class CheckInput {

  /**
   * Checks if input string is accepted by given Ndfa.
   *
   * @param ndfa input ndfa
   * @param tokenizedInput input string to be checked
   * @return true if input string is accepted, otherwise false
   */
  public static boolean checkInputNdfa(NdfaFragment ndfa, List<Token> tokenisedInput) {
    Set<State> startStates = new HashSet<>(Set.of(ndfa.getStart()));
    Set<State> currentStates = LambdaClosure.lambdaClosure(startStates);
    for (Token token : tokenisedInput) {
      startStates.clear();
      for (State state : currentStates) {
        for (Transition transition : state.getTransitions()) {
          if (transition.getSymbol().equals(token)) {
            startStates.add(transition.getTarget());
          }
        }
      }
      currentStates = LambdaClosure.lambdaClosure(startStates);
    }
    for (State state : currentStates) {
      if (state.getAcceptance()) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkInputDfa(DfaState dfaStart, List<Token> tokenisedInput) {
    DfaState currentState = dfaStart;

    for (Token token : tokenisedInput) {
      currentState = currentState.getTransitions().get(token.getCharacter());

      if (currentState == null) {
        return false; //checks if string has failed to match
      }
    }
    return currentState.isAccept();
  }
}
