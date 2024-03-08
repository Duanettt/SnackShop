public abstract class Snack
{
    /* Fields for the Snack abstract base class */
    protected String snackID;
    protected String name;
    protected int basePrice;

    Snack(String snackID, String name, int basePrice) throws InvalidSnackException
    {
        try
        {
            validateSnack(snackID, basePrice);
            this.snackID = snackID;
            this.basePrice = basePrice;
            this.name = name;
        }
        /* So if this occurs we essentially catch the error and get the message from our
        InvalidSnackException class then throwing e in order to terminate the current process
        of our program. Then recursively call.
         */
        catch(InvalidSnackException e)
        {
            System.out.println("Error creating snack object: " + e.getMessage());
            throw e;
        }

    }
    protected void validateSnack(String snackID, int price) throws InvalidSnackException
    {
        if(snackID == null || snackID.length() < 9)
        {
            throw new InvalidSnackException("Your snackID: " + snackID + " is the wrong length!");
        }
        /* UPDATED: We move this infront of this if statement to actually check whether we even have a snackID
        if we don't we could just assign a whitespace/empty char to our firstCharacter.
         */
        char firstCharacter = snackID.charAt(0);
        char secondCharacter = snackID.charAt(1);
        /* These variables make more sense to me for conditional checking */
        boolean firstCharIsNotALetter = !Character.isLetter(firstCharacter);
        boolean secondCharIsNotASlash = (secondCharacter != '/');

        if(firstCharIsNotALetter || secondCharIsNotASlash)
        {
            throw new InvalidSnackException("Your snackID: " + snackID + " has the wrong format, the format must be:" +
                    "([F/D]/1234567)");
        }
        if(price < 0)
        {
            throw new InvalidSnackException("Your price: " + price + "is an invalid price, way too low!");
        }
    }
    // Specifically made this so we can access individual char positions for our snackID for checks for
    // F or D. Protected since we will only be using this for our subclasses. (could be subject to change).
    protected abstract char getSnackIDCharAt(int position);

    public String getSnackID()
    {
        return snackID;
    }

    public String getName()
    {
        return name;
    }

    public int getBasePrice()
    {
        return basePrice;
    }

    public abstract int calculatePrice();

    // Test harness :)
    public static void main(String[] args)
    {

    }
}

