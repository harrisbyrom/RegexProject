import java.util.EnumMap;
import java.util.Map;
import java.util.Stack;

/**
 * Dispatch map that maps token types to the Ndfa building method of that specific type.
 */
public class NdfaBuildDispatcher {

  private final Map<TokenType, DispatchMap> dispatchMap;

  public NdfaBuildDispatcher() {
    dispatchMap = new EnumMap<>(TokenType.class);
    registerBuilders();
  }

  private void registerBuilders() {
    dispatchMap.put(TokenType.LITERAL, (stack, token) -> {
      stack.push(NdfaBuilderUtils.buildLiteral(token));
    });

    dispatchMap.put(TokenType.CONCAT, (stack, token) -> {
      NdfaFragment frag2 = stack.pop();
      NdfaFragment frag1 = stack.pop();
      stack.push(NdfaBuilderUtils.buildConcat(frag1, frag2));
    });

    dispatchMap.put(TokenType.OR, (stack, token) -> {
      NdfaFragment frag2 = stack.pop();
      NdfaFragment frag1 = stack.pop();
      stack.push(NdfaBuilderUtils.buildOr(frag1, frag2));
    });

    dispatchMap.put(TokenType.STAR, (stack, token) -> {
      stack.push(NdfaBuilderUtils.buildStar(stack.pop()));
    });

    dispatchMap.put(TokenType.PLUS, (stack, token) -> {
      stack.push(NdfaBuilderUtils.buildPlus(stack.pop()));
    });

    dispatchMap.put(TokenType.QUESTION, (stack, token) -> {
      stack.push(NdfaBuilderUtils.buildQuestion(stack.pop()));
    });
  }

  /**
   * Runs the correct method for the input token type via the output of the dispatch map.
   *
   * @param token input token for method
   * @param stack input stack for method
   */
  public void apply(Token token, Stack<NdfaFragment> stack) {
    DispatchMap builder = dispatchMap.get(token.getType());
    if (builder == null) {
      throw new UnsupportedOperationException("Unsupported token: " + token.getType());
    }
    builder.apply(stack, token);
  }
}
