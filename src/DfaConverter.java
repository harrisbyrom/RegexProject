import java.util.*;

public class DfaConverter {

    public static DfaState convertToDfa(NdfaFragment fragment) {
        Set<Character> alphabet = RegexUtils.extractAlphabet(fragment);
        Set<State> initialNdfaSet = LambdaClosure.lambdaClosure(Set.of(fragment.getStart()));
        DfaState dfaStart = new DfaState(initialNdfaSet);

        Map<Set<State>, DfaState> dfaStateMap = new HashMap<>();
        dfaStateMap.put(initialNdfaSet, dfaStart);

        Queue<DfaState> worklist = new LinkedList<>();
        worklist.add(dfaStart);

        while (!worklist.isEmpty()) {
            DfaState currentState = worklist.poll();

            for (char symbol : alphabet) {

                Set<State> moveSet = new HashSet<>();
                for (State state : currentState.getStates()) {
                    for (Transition transition : state.getTransitions()) {
                        Token token = transition.getSymbol();

                        if (token.getType() == TokenType.LITERAL && token.getCharacter() == symbol) {
                            moveSet.add(transition.getTarget());
                        }
                    }
                }

                if (moveSet.isEmpty()){
                    continue; // No reachable states on this char
                }

                Set<State> nextNdfaSet = LambdaClosure.lambdaClosure(moveSet);

                DfaState nextDfaState = dfaStateMap.get(nextNdfaSet);
                if (nextDfaState == null) {
                    nextDfaState = new DfaState(nextNdfaSet);
                    dfaStateMap.put(nextNdfaSet, nextDfaState);
                    worklist.add(nextDfaState);
                }
                currentState.getTransitions().put(symbol, nextDfaState);
            }
        }

        return dfaStart;

    }

}
