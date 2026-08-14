import java.util.*;

/*
 * Aggregate class for grouping NDFA states to act as a single DFA state
 */
public class DfaState {
    private final Set<State> NdfaStates;
    private final Map<Character, DfaState> transitions = new HashMap<>();
    private final boolean isAccept;
    private final MiniCTokenType matchedTokenType;


    public DfaState(Set<State> NdfaStates) {
        this.NdfaStates = Collections.unmodifiableSet(NdfaStates);
        boolean accept = false;
        MiniCTokenType winningType = null;
        for (State s : this.NdfaStates) {
            if (s.getAcceptance()){
                accept = true;
                MiniCTokenType candidate = s.getAcceptedTokenType();
                if (winningType == null || candidate.getPriority() < winningType.getPriority()) {
                    winningType = candidate;
                }
            }
        }
        this.isAccept = accept;
        this.matchedTokenType = winningType;

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

    public MiniCTokenType getMatchedTokenType() {
        return matchedTokenType;
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
