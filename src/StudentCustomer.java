public class StudentCustomer extends Customer
{
    private int maxNegativeBalance = -500;
    private int studentDiscountNum = 5;
    public StudentCustomer(int balance, String name, String accountID)
    {
        super(balance, name, accountID);
    }

    public StudentCustomer(String name, String customerID)
    {
        super(name, customerID);
    }

    /*@Override
     public void validateCustomerInfo(int balance, String accountID) throws InvalidCustomerException
    {
        super.validateCustomerInfo(balance,accountID);
        if (balance < maxNegativeBalance)
        {
            throw new InvalidCustomerException("Your initial balance is too low: " +
                    balance + " , please input a positive balance.");
        }
    } */

    @Override
    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        /* If my balance + the negative balance is less than our snack price when buying a snack
        we essentially just throw our invalidBalanceException. This also takes into account after our
        calculation of discounted price using our function which takes two parameters of our snackPrice
        and the studentDiscountNum which can be set by utilizing out setter function.
         */
        // Conditional variable for my understanding:
        boolean balanceLessThanDiscountedPrice = (balance + maxNegativeBalance) <
                calculateDiscountedPrice(snackPrice, studentDiscountNum);

        if(balanceLessThanDiscountedPrice)
        {
            throw new InvalidBalanceException("Insufficient balance, your balance: " +
                    balance);
        }
        /* Updated: Removed the else since, no need to add an else statement
         */
        return balance -= snackPrice;
    }

    public static double calculateDiscountedPrice(int snackPrice, double studentDiscountPercentage)
    {
        /* We do our studentDiscountPercentage e.g 5/100 which gives us 0.05 we then do 1 - 0.05 to get 0.95
        we then times this by our price to get our discounted price for our snack.
         */
        return snackPrice * ( 1 - (studentDiscountPercentage / 100.0));
    }

    public static void main(String[] args)
    {
        StudentCustomer test = new StudentCustomer(-250, "Duaine", "A12345");
    }


    // Getters and Setters (Setters needed, last feedback did not want such specific requirements so we should
    // be able to customize our studentdiscountnumbers and our maximumbalance.
    public void setStudentDiscountNum(int studentDiscountNumber)
    {
        this.studentDiscountNum = studentDiscountNumber;
    }

    public int getMaxNegativeBalance()
    {
        return maxNegativeBalance;
    }

    public int getStudentDiscountNum()
    {
        return studentDiscountNum;
    }

    public void setMaxNegativeBalance(int maxNegativeBalance)
    {
        this.maxNegativeBalance = maxNegativeBalance;
    }
}
