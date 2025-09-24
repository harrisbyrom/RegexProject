import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Class that holds the LambdaClosure method.
 */
public class LambdaClosure {

  /**
   * Computes the lambda closure of a given state,
   * ie finds all states reachable from startStates without consuming an input.
   *
   * @param startStates input states to apply closure to
   * @return completed closure
   */
  public static Set<State> lambdaClosure(Set<State> startStates) {
    Set<State> closure = new HashSet<>(startStates);
    Stack<State> stack = new Stack<>();
    stack.addAll(startStates);

    while (!stack.isEmpty()) {
      State current = stack.pop();
      for (Transition transition : current.getTransitions()) {
        if (transition.getSymbol().getType() == TokenType.LAMBDA) {
          if (closure.add(transition.getTarget())) {
            stack.push(transition.getTarget());
          }
        }
      }
    }
    return closure;
  }
}
