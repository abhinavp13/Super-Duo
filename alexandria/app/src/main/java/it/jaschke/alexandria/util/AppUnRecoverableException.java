package it.jaschke.alexandria.util;

/**
 * This is exception class for all those exceptions which when caught,
 * cannot let app continue what it was doing.
 * Need to stop app for further continuing.
 *
 * Created by pabhinav.
 */
public class AppUnRecoverableException extends Exception {

    /**
     * Constructs a new {@code AppUnRecoverableException} that includes the current stack trace.
     */
    public AppUnRecoverableException() {
    }

    /**
     * Constructs a new {@code AppUnRecoverableException} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage
     *            the detail message for this exception.
     */
    public AppUnRecoverableException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code AppUnRecoverableException} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage
     *            the detail message for this exception.
     * @param throwable
     *            the cause of this exception.
     */
    public AppUnRecoverableException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code AppUnRecoverableException} with the current stack trace and the
     * specified cause.
     *
     * @param throwable
     *            the cause of this exception.
     */
    public AppUnRecoverableException(Throwable throwable) {
        super(throwable);
    }
}
