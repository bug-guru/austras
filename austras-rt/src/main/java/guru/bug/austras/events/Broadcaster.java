package guru.bug.austras.events;

// TODO second type param for response type
public interface Broadcaster<M> {
    // TODO response consumer as the second param (result of each called method must be passed to this consumer)
    void send(M message);

    /**
     * convenience method, the same as send(null). Useful when @Message annotation is placed on method, not on parameter
     */
    default void send() {
        send(null);
    }
}
