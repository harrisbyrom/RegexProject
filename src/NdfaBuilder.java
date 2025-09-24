
import java.util.List;
import java.util.Stack;
/**
 * Base class for the NdfaBuilder method.
 */

public class NdfaBuilder {

  /**
   * Converts postfix regex into a complete NDFA using Thompson's construction method.
   *
   * @param postfixRegex postfix regex to be converted.
   * @return returns complete NDFA of the regex.
   */

  public static NdfaFragment thompsonConstruction(List<Token> postfixRegex) {
    final NdfaBuildDispatcher dispatcher = new NdfaBuildDispatcher();
    Stack<NdfaFragment> fragmentStack = new Stack<>();
    for (Token token : postfixRegex) {
      dispatcher.apply(token, fragmentStack);
    }
    if (fragmentStack.size() != 1) {
      throw new IllegalStateException("Malformed regex: leftover fragments");
    }
    return fragmentStack.pop();
  }
}
