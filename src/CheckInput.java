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
  public static boolean checkInput(NdfaFragment ndfa, List<Token> tokenizedInput) {
    Set<State> startStates = new HashSet<>(Set.of(ndfa.getStart()));
    Set<State> currentStates = LambdaClosure.lambdaClosure(startStates);
    for (Token token : tokenizedInput) {
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
      if (state.getAcceptance() == true) {
        return true;
      }
    }
    return false;
  }
}
