public class InvalidCustomerException extends RuntimeException
{
    // When this is initialized we can send a message to our user of what
    // has occured. We'll manually pass a message and handle errors within
    // the functions.
    public InvalidCustomerException(String message)
    {
        super(message);
    }
}
