
import java.util.Set;

/**
 * Class containing useful methods for Ndfa Fragment operations.
 */
public class NdfaBuilderUtils {

  public static NdfaFragment buildLiteral(Token token) {
    return new NdfaFragment(token);
  }

  /**
   * Builds Ndfa Fragment of concatenation between two fragments.
   *
   * @param frag1 first input fragment
   * @param frag2 second input fragment
   * @return concatenated fragment
   */
  public static NdfaFragment buildConcat(NdfaFragment frag2, NdfaFragment frag1) {
    for (State state : frag1.getAcceptStates()) {
      state.addTransition(new Transition(new Token(), frag2.getStart()));
      state.isNotAccept();
    }
    return new NdfaFragment(frag1.getStart(), frag2.getAcceptStates());
  }

  /**
   * Builds Ndfa Fragment of alternation between two fragments.
   *
   * @param frag1 first input fragment
   * @param frag2 second input fragment
   * @return alternated fragment
   */
  public static NdfaFragment buildOr(NdfaFragment frag2, NdfaFragment frag1) {
    State start = new State(false);
    State accept = new State(true);
    start.addTransition(new Transition(new Token(), frag1.getStart()));
    start.addTransition(new Transition(new Token(), frag2.getStart()));
    for (State state : frag1.getAcceptStates()) {
      state.addTransition(new Transition(new Token(), accept));
      state.isNotAccept();
    }
    for (State state : frag2.getAcceptStates()) {
      state.addTransition(new Transition(new Token(), accept));
      state.isNotAccept();
    }
    return new NdfaFragment(start, Set.of(accept));
  }

  /**
   * Builds Ndfa Fragment of the Kleene star operator applied to another fragment.
   *
   * @param frag input fragment
   * @return fragment with Kleene star applied
   */
  public static NdfaFragment buildStar(NdfaFragment frag) {
    State start = new State(false);
    State accept = new State(true);
    start.addTransition(new Transition(new Token(), frag.getStart()));
    start.addTransition(new Transition(new Token(), accept));
    for (State state : frag.getAcceptStates()) {
      state.addTransition(new Transition(new Token(), frag.getStart()));
      state.addTransition(new Transition(new Token(), accept));
      state.isNotAccept();
    }
    return new NdfaFragment(start, Set.of(accept));
  }

  /**
   * Builds Ndfa Fragment of the One-or-more operator applied to another fragment.
   *
   * @param frag input fragment
   * @return fragment with One-or-more applied
   */
  public static NdfaFragment buildPlus(NdfaFragment frag) {
    State start = new State(false);
    State accept = new State(true);
    start.addTransition(new Transition(new Token(), frag.getStart()));
    for (State state : frag.getAcceptStates()) {
      state.addTransition(new Transition(new Token(), frag.getStart()));
      state.addTransition(new Transition(new Token(), accept));
      state.isNotAccept();
    }
    return new NdfaFragment(start, Set.of(accept));
  }

  /**
   * Builds Ndfa Fragment of the zero-or-one operator applied to another fragment.
   *
   * @param frag input fragment
   * @return fragment with zero-or-one applied
   */
  public static NdfaFragment buildQuestion(NdfaFragment frag) {
    State start = new State(false);
    State accept = new State(true);
    start.addTransition(new Transition(new Token(), frag.getStart()));
    start.addTransition(new Transition(new Token(), accept));
    for (State state : frag.getAcceptStates()) {
      state.addTransition(new Transition(new Token(), accept));
      state.isNotAccept();
    }
    return new NdfaFragment(start, Set.of(accept));
  }
}
