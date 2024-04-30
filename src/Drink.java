public class Drink extends Snack
{
    // Enumeration for sugarContent Levels/
    public enum sugarContent {HIGH, LOW, NONE}
    // Actual fields
    private int newPrice = 0;
    private final sugarContent sugarContentLevel;
    // Conditional Variables to check for F (Easier for me to understand)
    private final boolean firstLetterIsEqualToD = (getSnackIDCharAt(0) == 'D');

    Drink(String snackID, String name, int basePrice, String sugarContent) throws InvalidSnackException
    {
        // Call our original instructor it will then do the normal Snack Checks to check if its id is valid
        super(snackID, name, basePrice);
        // Then we come out of and check within this constructor:
        /* If our first letter is equal to f we finally initalize our last constructor parameter
        variable else we catch the exception and display the message. (In this case we need F!)
         */
        if(firstLetterIsEqualToD)
        {
            // We will take in our
            this.sugarContentLevel = sugarContentConversion(sugarContent);
        }
        else
        {
            throw new InvalidSnackException("Your snackID: " + snackID + " is not valid we need F(Food) or" +
                    "D(Drink)..Please try again!");
        }
    }

    Drink(String snackID, String name, int basePrice) throws InvalidSnackException
    {
        super(snackID, name, basePrice);
        if(firstLetterIsEqualToD)
        {
            this.sugarContentLevel = sugarContent.NONE;
        }
        else
        {
            throw new InvalidSnackException("Your snackID: " + snackID + " is not valid we need " +
                    "D(Drink)..Please try again!");
        }
    }

    @Override
    protected char getSnackIDCharAt(int position)
    {
        return snackID.charAt(position);
    }

    @Override
    public int calculatePrice() {
        int price = basePrice; // Initialize with the base price
        /* Checks the sugarContentLevel variable if it contains HIGH,LOW or NONE,
        applies the tax to our price.
         */

        switch (sugarContentLevel) {
            case HIGH:
                price += 24;
                break;
            case LOW:
                price += 18;
                break;
            case NONE:
                // No price adjustment for NONE
                break;
        }

        setNewPrice(price);

        return price;
    }
    /* UPDATE: When building the drink class and trying to create an object we had to
    input sugarContentLevel.HIGH for example would rather be able to just input high in
    string format and then have a content Conversion function in the constructor. This might
    come in handy later on within the coursework.
     */

    public sugarContent sugarContentConversion(String sugarContent)
    {
        // make each sugarcontent input lower case.

        // Do conditional checks for each sugarContent and return the value.
        if (sugarContent.equalsIgnoreCase("high"))
        {
            return Drink.sugarContent.HIGH;
        }
        else if (sugarContent.equalsIgnoreCase("low"))
        {
            return Drink.sugarContent.LOW;
        }
        else if (sugarContent.equalsIgnoreCase("none"))
        {
            return Drink.sugarContent.NONE;
        }
        else
        {
            throw new InvalidSnackException("You have chosen an invalid sugar content level. " +
                    "Must be from HIGH, LOW or NONE");
        }
    }

    public void setNewPrice(int newPrice)
    {
        this.newPrice = newPrice;
    }

    @Override
    public int getNewPrice()
    {
        return newPrice;
    }

    public sugarContent getSugarContentLevel()
    {
        return sugarContentLevel;
    }

    @Override
    public String toString() {
        return "Drink is " + name + " and has a sugar content value of " + sugarContentLevel + " , our original price is " + basePrice +
                " and our new price is " + newPrice + ".The snackID is " + snackID;
    }
    public static void main(String[] args) throws InvalidSnackException {
        /* Testing my conversion system for Drink class

         */
        Drink test = new Drink("D/8547328", "Popcorn", 200, "high");
        System.out.println(test.getSugarContentLevel());


    }
}