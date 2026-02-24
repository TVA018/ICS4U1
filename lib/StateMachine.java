import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class StateMachine<State extends Enum<State>> {
    /** A code representing the result of a request */
    public static enum RequestCode {
        /** State change request succeeded */
        SUCCESS,
        /**
         * This state doesn't have requirements, meaning it can't be requested. This
         * likely means it is
         * a state used internally, rather a state that is meant to be requested
         * externally.
         */
        CANNOT_REQUEST,
        /** The requirements to switch to the state aren't met */
        REQUIREMENTS_FAILED,
        /** The current state is already set to this */
        ALREADY_SET
    }

    /** The requirements to move to this state */
    public final Map<State, BooleanSupplier> stateRequirements = new HashMap<>();

    /** Default state requirement meaning the state can always be switched to */
    public static final BooleanSupplier STATE_ALWAYS_AVAILABLE = () -> true;

    private State currentState;

    /**
     * Creates a new State Machine
     * 
     * @param initialState The initial state of the state machine
     */
    public StateMachine(State initialState) {
        this.currentState = initialState;
    }

    /**
     * @return The current state of the state machine
     */
    public State getState() {
        return currentState;
    }

    /**
     * Forces a certain state
     *
     * @param newState The new state the state machine should be in
     */
    public void forceState(State newState) {
        this.currentState = newState;
    }

    /**
     * @return Whether the state machine is currently in said state
     */
    public boolean isInState(State stateToCompareTo) {
        return this.currentState.equals(stateToCompareTo);
    }

    /**
     * Request to switch to a new state and returns a RequestCode representing
     * whether it was
     * successful
     *
     * @param targetState The requested state
     * @return A {@link RequestCode} representing the request result
     */
    public RequestCode requestState(State targetState) {
        // If the state is already set
        if (currentState.equals(targetState))
            return RequestCode.ALREADY_SET;
        // If no state requirement exists
        if (!stateRequirements.containsKey(targetState))
            return RequestCode.CANNOT_REQUEST;
        // If the state requirement isn't met
        if (!stateRequirements.get(targetState).getAsBoolean())
            return RequestCode.REQUIREMENTS_FAILED;

        forceState(targetState);
        return RequestCode.SUCCESS;
    }
}
