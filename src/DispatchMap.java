import java.util.Stack;

/**
 * Interface for lambda functions in dispatch map.
 */
public interface DispatchMap {
  void apply(Stack<NdfaFragment> stack, Token token);
}