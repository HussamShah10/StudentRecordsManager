/**
 * Custom exception class for handling invalid grade values.
 * This demonstrates how to create a custom exception in Java.
 *
 * Custom exceptions allow you to create application-specific error types
 * that provide more meaningful error handling than generic exceptions.
 */
public class InvalidGradeException extends Exception {

    /**
     * Constructs a new InvalidGradeException with the specified detail message.
     *
     * @param message The detailed message explaining why the exception occurred
     */
    public InvalidGradeException(String message) {
        super(message);
    }
}
