public class Drink extends Snack
{
    // Enumeration for sugarContent Levels/
    public enum sugarContent {HIGH, LOW, NONE}
    // Actual fields
    private sugarContent sugarContentLevel;
    // Conditional Variables to check for F (Easier for me to understand)
    private final boolean firstLetterIsEqualToD = (getSnackIDCharAt(0) == 'D');

    Drink(String snackID, String name, int basePrice, sugarContent sugarContentLevel) throws InvalidSnackException
    {
        // Call our original instructor it will then do the normal Snack Checks to check if its id is valid
        super(snackID, name, basePrice);
        // Then we come out of and check within this constructor:
        /* If our first letter is equal to f we finally initalize our last constructor parameter
        variable else we catch the exception and display the message. (In this case we need F!)
         */
        if(firstLetterIsEqualToD)
        {
            this.sugarContentLevel = sugarContentLevel;
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
            throw new InvalidSnackException("Your snackID: " + snackID + " is not valid we need F(Food) or" +
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
        return price;
    }

    public sugarContent getSugarContentLevel()
    {
        return sugarContentLevel;
    }
}