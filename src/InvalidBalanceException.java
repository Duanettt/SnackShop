public class InvalidBalanceException extends Exception
{
    // When this is initialized we can send a message to our user of what
    // has occured. We'll manually pass a message and handle errors within
    // the functions.
    public InvalidBalanceException(String message)
    {
        super(message);
    }
}
