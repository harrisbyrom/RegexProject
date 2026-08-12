import java.util.*;

public class DfaState {
    private final Set<State> NdfaStates;
    private final Map<Character, DfaState> transitions = new HashMap<>();
    private final boolean isAccept;


    public DfaState(Set<State> NdfaStates) {
        this.NdfaStates = Collections.unmodifiableSet(NdfaStates);
        boolean accept = false;
        for (State s : this.NdfaStates) {
            if (s.getAcceptance()){
                accept = true;
                break;
            }
        }
        this.isAccept = accept;
    }

    public Set<State> getStates(){
        return NdfaStates;
    }

    public Map<Character, DfaState> getTransitions(){
        return transitions;
    }

    public boolean isAccept(){
        return isAccept;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DfaState other = (DfaState) obj;
        return Objects.equals(NdfaStates, other.getStates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(NdfaStates);
    }

}
