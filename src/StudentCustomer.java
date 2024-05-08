public class StudentCustomer extends Customer
{
    private int maxNegativeBalance = -500;
    private static int studentDiscountNum = 5;
    public StudentCustomer(int balance, String name, String accountID)
    {
        super(balance, name, accountID);
    }

    public StudentCustomer(String name, String accountID)
    {
        super(name, accountID);
    }

    @Override
    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        /* If my balance + the negative balance is less than our snack price when buying a snack
        we essentially just throw our invalidBalanceException. This also takes into account after our
        calculation of discounted price using our function which takes two parameters of our snackPrice
        and the studentDiscountNum which can be set by utilizing out setter function.
         */
        double newSnackPrice = Math.ceil(calculateDiscountedPrice(snackPrice, studentDiscountNum));
        // Conditional variable for my understanding:
        boolean balanceLessThanDiscountedPrice = (balance - newSnackPrice ) < maxNegativeBalance;

        int newBalance = 0;
        if(balanceLessThanDiscountedPrice)
        {
            throw new InvalidBalanceException("Insufficient balance, " + getName()
                    + " balance: " + balance + " the snack's price is: " + newSnackPrice);
        }
        newBalance = (int)(balance - newSnackPrice);
        /* Updated: Removed the else since, no need to add an else statement
         */
        /* Changes our current customers balance */
        this.setBalance(newBalance);

        return (int) Math.ceil(newSnackPrice);
    }

    @Override
    public String toString() {
        return "Customer with accountID of: " + accountID + " is a Student. "
                + "Their name is: " + name + " and they have a balance of: " + balance;
    }

    // UPDATE: Moved this function to the customer class since using it in both student
    // and staff and in my code for the staff it was getting confusing to read
    // since i had to reference studentcustomer to access the
    // calculateDiscountedPrice method.


    // Getters and Setters (Setters needed, last feedback did not want such specific requirements so we should
    // be able to customize our studentdiscountnumbers and our maximumbalance.
    public void setStudentDiscountNum(int studentDiscountNumber)
    {
        studentDiscountNum = studentDiscountNumber;
    }

    public int getMaxNegativeBalance()
    {
        return maxNegativeBalance;
    }

    public static int getDiscountAmount()
    {
        return studentDiscountNum;
    }

    public void setMaxNegativeBalance(int maxNegativeBalance)
    {
        this.maxNegativeBalance = maxNegativeBalance;
    }

    public static void main(String[] args)
    {
        StudentCustomer test = new StudentCustomer(-250, "Duaine", "A12345");
    }
}
