
/* UPDATE: Was struggling on why i could not create a food object without having to throw
an exception on my test harness this caused even more problems since I got into a loop of
errors in the IDE. Then it states in the IDE that i was using checked Exceptions and any exception that is not
a subclass of RuntimeException will be checked. I don't want to force try catches since I need to
initialize my object and my constructor will handle exceptions at runtime.
 */
public class InvalidSnackException extends RuntimeException
{
    // When this is initialized we can send a message to our user of what
    // has occured. We'll manually pass a message and handle errors within
    // the functions.
    public InvalidSnackException(String message)
    {
        super(message);
    }
}
