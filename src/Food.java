public class Food extends Snack {

    // Actual fields
    public enum hotOrCold {HOT, COLD}
    private hotOrCold isHotOrCold;

    private int newPrice;
    public final double surcharge = 1.1;

    Food(String snackID, String name, int basePrice, String hotOrCold) throws InvalidSnackException
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
            this.isHotOrCold = determineFoodHotOrCold(hotOrCold);
        } else {
            throw new InvalidSnackException("Your snackID: " + snackID + " is not valid we need F(Food) or" +
                    "D(Drink)..Please try again!");
        }


    }

    public hotOrCold determineFoodHotOrCold(String hotOrCold)
    {
        hotOrCold = hotOrCold.toLowerCase();

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
        // Test to see what the new price is after i apply this.
        /*
        So, basically last minute change - I didn't realise that in the coursework the 10% surcharge was meant only for
        food's and I kept wondering why I was getting such weird values for turnover and the median. I had to create
        an if statement or just a switch statement to identify whether a food object is hot or cold, if it was hot then
        I apply the 10% surcharge if not I just use the normal basePrice and that seemed to fix all my issues.
         */
        switch(this.isHotOrCold)
        {
            case HOT:
                newPrice = (int) Math.round(basePrice * surcharge);
                return newPrice;
            case COLD:
                return basePrice;
        }

        return 0;
    }

    public hotOrCold isHot()
    {
        return isHotOrCold;
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

    @Override
    public String toString() {
        return "Food is " + name + " and is " + isHotOrCold + ",our original price is " + basePrice +
                " and our new price is " + newPrice + ".The snackID is " + snackID;
    }

    // Test Harness
    public static void main(String[] args) throws InvalidSnackException
    {
        Food test = new Food("G/8547328", "Popcorn", 200, "Hot");
        Food test2 = new Food("F/1234567", "Plantain Chips", 200, "NCP");
        Food test3 = new Food("F/12347", "Chicken Soup", 500, "COLD");
        Food test4 = new Food("F21234567", "Jollof Rice", 200, "HOT");
        Food test5 = new Food("F/1234567", "Jollof Rice", 200, "HOT");
    }
}

