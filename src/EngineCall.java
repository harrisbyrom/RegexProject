import java.util.List;

/**
 * Contains engineCall method to initiate regex engine.
 */
public class EngineCall {

  /**
   * Initializes regex engine by formatting inputs and runs the regex through thompsonConstruction()
   * to build NDFA, then uses checkInput() to run the input through the created NDFA
   * to check if it is accepting.
   *
   * @param regex input regex
   * @param input input string to be checked against regex
   * @return true if input string is accepted, otherwise false
   */
  public static boolean engineCall(String regex, String input) {

    List<Token> tokenizedList = RegexUtils.toTokens(regex);
    List<Token> postfixList = ToPostfix.toPostfix(tokenizedList);
    NdfaFragment finalFragment = NdfaBuilder.thompsonConstruction(postfixList);
    List<Token> tokenizedInput = RegexUtils.toLiteralTokens(input);
    return CheckInput.checkInput(finalFragment, tokenizedInput);

  }
}
