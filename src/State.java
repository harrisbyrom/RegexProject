import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing each state within the NDFAs.
 */
public class State {
  List<Transition> transitions = new ArrayList<>();
  boolean isAccept = false;

  public State(List<Transition> transitions, boolean isAccept) {
    this.transitions = transitions;
    this.isAccept = isAccept;
  }

  public State(boolean isAccept) {
    this.isAccept = isAccept;
  }

  public void isAccept() {
    this.isAccept = true;
  }

  public void isNotAccept() {
    this.isAccept = false;
  }

  public void addTransition(Transition transition) {
    transitions.add(transition);
  }

  public boolean getAcceptance() {
    return this.isAccept;
  }

  public List<Transition> getTransitions() {
    return this.transitions;
  }

  @Override
public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    State other = (State) obj;
    return isAccept == other.isAccept
          && Objects.equals(transitions, other.transitions);
  }

  @Override
public int hashCode() {
    return System.identityHashCode(this);
  }

  /**
   * Used for testing purposes only.
   */
  @Override
  public String toString() {
    if (this.getAcceptance()) {
      return "This state is accepting, and has a transition on the character "
        + transitions.get(0);
    } else {
      return "This state is not accepting and has a transition on the character "
        + transitions.get(0);
    }
  }
}
