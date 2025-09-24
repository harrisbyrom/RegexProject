import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * Converts regex into postfix notation.
 */

public class ToPostfix {

  /**
   * Converts tokenized list that represents the regex into postfix form.
   *
   * @param tokenizedList the input tokenized regex.
   * @return the output postfix regex.
   */

  public static List<Token> toPostfix(List<Token> tokenizedList) {
    Stack<Token> stack = new Stack<>();
    List<Token> postfixRegex = new ArrayList<>();

    for (Token token : tokenizedList) {
      switch (token.getType()) {
        case LITERAL -> postfixRegex.add(token);
        case LBRACKET -> stack.push(token);
        case RBRACKET -> {
          while (!stack.isEmpty() && stack.peek().getType() != TokenType.LBRACKET) {
            postfixRegex.add(stack.pop());
          }
          stack.pop(); //removes the LBRACKET that is left on the stack
        }
        default -> {
          while (!stack.isEmpty()
                && stack.peek().getType() != TokenType.LBRACKET
                && (stack.peek().getPrecedence() > token.getPrecedence()
                || (stack.peek().getPrecedence() == token.getPrecedence()
                && !token.isRightAssociative()))) {
            postfixRegex.add(stack.pop());
          }
          stack.push(token);
        }
      }
    }
    while (!stack.isEmpty()) {
      postfixRegex.add(stack.pop());
    }
    return postfixRegex;
  }

}
