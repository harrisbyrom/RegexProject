import java.util.*;

/**
 *Contains useful methods for regex conversion.
 */

public class RegexUtils {

  /**
   * Converts input regex into a list of Tokens and adds explicit concatenation.
   *
   * @param regex input regex to be tokenized
   * @return returns tokenized regex
   */
  public static List<Token> toTokens(String regex) {
    String expandedRegex = expandCharacterClasses(regex); // Expands all character classes that are found

    List<Token> tokenizedList = new ArrayList<>();
    int concatCounter = 0; /* Used to track when explicit concatenation needs to be added,
                              it is added when an open bracket or literal follows any
                              character that is not an open bracket '(' or an alternation '|' */
    // Evaluates any escape characters \ found and how to treat them
    for (int i = 0; i < expandedRegex.length(); i++) {
      char c = expandedRegex.charAt(i);

      if (c == '\\') {
        i++;

        if (i >= expandedRegex.length()) {
          throw new IllegalArgumentException("Trailing backslash in regex pattern: " + expandedRegex);
        }
        char literalChar = resolveControlChar(expandedRegex.charAt(i));

        if (concatCounter == 1) {
          tokenizedList.add(new Token(TokenType.CONCAT, '·'));
        } else {
          concatCounter = 1;
        }

        tokenizedList.add(new Token(TokenType.LITERAL, literalChar));

      } else {

        switch (c) {
          case '|' -> {
            concatCounter = 0;
            tokenizedList.add(new Token(TokenType.OR, c));
          }
          case '*' -> {
            concatCounter = 1;
            tokenizedList.add(new Token(TokenType.STAR, c));
          }
          case '+' -> {
            concatCounter = 1;
            tokenizedList.add(new Token(TokenType.PLUS, c));
          }
          case '?' -> {
            concatCounter = 1;
            tokenizedList.add(new Token(TokenType.QUESTION, c));
          }
          case '(' -> {
            if (concatCounter == 1) {
              tokenizedList.add(new Token(TokenType.CONCAT, '·'));
            }
            concatCounter = 0;
            tokenizedList.add(new Token(TokenType.LBRACKET, c));
          }
          case ')' -> {
            concatCounter = 1;
            tokenizedList.add(new Token(TokenType.RBRACKET, c));
          }
          default -> {
            if (concatCounter == 1) {
              tokenizedList.add(new Token(TokenType.CONCAT, '·'));
            } else {
              concatCounter = 1;
            }
            tokenizedList.add(new Token(TokenType.LITERAL, c));
          }
        }
      }
    }
    return tokenizedList;
  }

  // resolves special control characters by replacing the letter with the actual control character
  private static char resolveControlChar(char c) {
    return switch (c) {
      case 'n' -> '\n';
      case 't' -> '\t';
      case 'r' -> '\r';
      default -> c; // Returns '+', '*', '(', ')', '\', etc. directly
    };
  }

  /**
   * Converts input into list of literal tokens.
   *
   * @param input input string to be converted to literal tokens
   * @return returns tokenized output
   */
  public static List<Token> toLiteralTokens(String input) {
    List<Token> tokenizedInput = new ArrayList<>();
    for (char character : input.toCharArray()) {
      tokenizedInput.add(new Token(TokenType.LITERAL, character));
    }
    return tokenizedInput;
  }

  /**
   *  Extracts the alphabet of a given NdfaFragment.
   *
   * @param fragment input fragment to find alphabet of
   * @return returns alphabet of fragment
   */
  public static Set<Character> extractAlphabet(NdfaFragment fragment) {
    Set<Character> alphabet = new HashSet<>();
    Set<State> visited = new HashSet<>();
    Queue<State> queue = new LinkedList<>();

    queue.add(fragment.getStart());
    visited.add(fragment.getStart());

    while (!queue.isEmpty()) {
      State current = queue.poll();

      for (Transition transition : current.getTransitions()) {
        Token token = transition.getSymbol();

        if (token.getType() == TokenType.LITERAL) {
          alphabet.add(token.getCharacter());
        }
        State target = transition.getTarget();
        if (visited.add(target)) {
          queue.add(target);
        }
      }
    }
    return alphabet;
  }

  /**
   *  Finds and evaluates any character classes,
   *  checks for correct structure and escape characters
   *
   * @param regex input regex to be evaluated
   * @return returns regex with any valid character classes inserted as valid bracketed alternation
   */
  public static String expandCharacterClasses(String regex) {
    StringBuilder expanded = new StringBuilder();
    int i = 0;

    while (i < regex.length()) {
      char c = regex.charAt(i);

      // Check if character is an unescaped '['
      if (c == '[' && (i == 0 || regex.charAt(i - 1) != '\\')) {
        int closeBracket = findClosingBracket(regex, i);
        if (closeBracket == -1) {
          throw new IllegalArgumentException("Unclosed character class '[' at index " + i);
        }

        String classContent = regex.substring(i + 1, closeBracket);
        String expandedClass = processClassContent(classContent);

        expanded.append('(').append(expandedClass).append(')');
        i = closeBracket; // Move index to ']'
      } else {
        expanded.append(c);
      }
      i++;
    }

    return expanded.toString();
  }

  /**
   * Replaces valid character classes with equivalent alternations
   *
   * @param content input content of character class
   * @return returns the character class converted into regex of alternations
   */
  private static String processClassContent(String content) {
    Set<Character> chars = new LinkedHashSet<>(); // Preserve insertion order, prevent duplicates
    int j = 0;

    while (j < content.length()) {
      // Check for a range: e.g., "a-z" or "0-9"
      if (j + 2 < content.length() && content.charAt(j + 1) == '-' && content.charAt(j) != '\\') {
        char start = content.charAt(j);
        char end = content.charAt(j + 2);

        if (start > end) {
          throw new IllegalArgumentException("Invalid range in character class: " + start + "-" + end);
        }

        for (char ch = start; ch <= end; ch++) {
          chars.add(ch);
        }
        j += 3; // Skip start, '-', and end
      } else {
        // Handle escaped characters inside brackets, e.g., \- or \]
        if (content.charAt(j) == '\\' && j + 1 < content.length()) {
          j++;
        }
        chars.add(content.charAt(j));
        j++;
      }
    }

    // Join all collected characters with '|'
    StringBuilder sb = new StringBuilder();
    for (char ch : chars) {
      if (!sb.isEmpty()) {
        sb.append('|');
      }
      // If the character is a special operator in general regex, escape it inside the alternation
      if ("|*+?()\\".indexOf(ch) != -1) {
        sb.append('\\');
      }
      sb.append(ch);
    }

    return sb.toString();
  }

  /**
   * Finds closing bracket in a given regex after a given position
   *
   * @param regex input regex to be searched
   * @param startPos position to start search from
   * @return index of found closing bracket
   */
  private static int findClosingBracket(String regex, int startPos) {
    for (int k = startPos + 1; k < regex.length(); k++) {
      if (regex.charAt(k) == ']' && regex.charAt(k - 1) != '\\') {
        return k;
      }
    }
    return -1;
  }
}
