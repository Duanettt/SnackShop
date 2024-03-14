public class Food extends Snack {
    // Actual fields
    public enum hotOrCold {HOT, COLD}
    private hotOrCold isHot;
    public final double surcharge = 1.1;

    Food(String snackID, String name, int basePrice, String isHot) throws InvalidSnackException
    {
        // Call our original instructor it will then do the normal Snack Checks to check if its id is valid
        super(snackID, name, basePrice);
        // Then we come out of and check within this constructor:

        // Conditional Variables to check for F (Easier for me to understand)
        boolean firstLetterIsEqualToF = (getSnackIDCharAt(0) == 'F');
        /* If our first letter is equal to f we finally initalize our last constructor parameter
        variable else we catch the exception and display the message. (In this case we need F!)
         */
        if (firstLetterIsEqualToF)
        {
            this.isHot = determineFoodHotOrCold(isHot);
        } else {
            throw new InvalidSnackException("Your snackID: " + snackID + " is not valid we need F(Food) or" +
                    "D(Drink)..Please try again!");
        }


    }

    public hotOrCold determineFoodHotOrCold(String hotOrCold)
    {
        hotOrCold.toLowerCase();

        switch(hotOrCold)
        {
            case "hot":
                return Food.hotOrCold.HOT;
            case "cold":
                return Food.hotOrCold.COLD;
            default:
                throw new InvalidSnackException("Exception thrown, you have not" +
                        "selected the correct options from HOT or COLD.");
        }
    }

    @Override
    protected char getSnackIDCharAt(int position)
    {
        return snackID.charAt(position);
    }

    @Override
    public int calculatePrice()
    {
        // This casts to an int then we round the result to the nearest penny.
        return (int) Math.round(basePrice * surcharge);
    }

    public hotOrCold isHot()
    {
        return isHot;
    }

    // Test Harness
    public static void main(String[] args) throws InvalidSnackException
    {
        Food test = new Food("G/8547328", "Popcorn", 200, "Hot");

    }
}

