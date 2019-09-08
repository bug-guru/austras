package guru.bug.austras.events;

public interface ApplicationEvents {
    /**
     * Framework is about to execute all @Service methods. Called by the framework when all dependencies are resolved and all providers were created.
     */
    default void preStart() {

    }

    /**
     * This method called after all @Service methods.
     */
    default void postStart() {

    }
}
