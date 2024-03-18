public class Customer
{
    // Fields for Customer
    protected int balance;
    protected String name;
    protected String accountID;
    // Constructors for customer
    public Customer(int balance, String name, String accountID) throws InvalidCustomerException
    {
        try
        {
            validateCustomerInfo(balance,accountID);
            this.name = name;
            this.accountID = accountID;
            this.balance = balance;
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public Customer(String name, String accountID) throws InvalidCustomerException
    {
        balance = 0;
        try
        {
            validateCustomerInfo(balance,accountID);
            this.name = name;
            this.accountID = accountID;
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }

    }
    // Methods for Customer.

    protected void validateCustomerInfo(int balance, String accountID) throws InvalidCustomerException
    {
        if (balance < 0)
        {
            throw new InvalidCustomerException("You cannot submit a negative value: " +
                    balance + " , please input a positive balance.");
        }

        if (accountID.length() < 6)
        {
            throw new InvalidCustomerException("Your accountID: " + accountID +
                    " is the wrong length must be 6 alphanumeric values.");
        }

        /* We can use the character library to identify whether our string has
        an alphanumeric value it contains the .isAlphaOrDigit. So for every
        character within our accountID's charArray it will iterate and determine
        if the charArray contains an alpha or numeric value if not it will throw
        our exception.
         */

        for (char c : accountID.toCharArray())
        {
            boolean doesNotContainAlphanumerics = (!Character.isLetterOrDigit(c));

            if (doesNotContainAlphanumerics)
            {
                throw new InvalidCustomerException("Your accountID: " + accountID +
                        " must contain alphanumeric values.");
            }
        }

    }

    public void chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        if(balance < snackPrice)
        {
            throw new InvalidBalanceException("Insufficient balance " + getName()
                    + " balance: " +
                    balance);
        }
        /* Updated: Removed the else since, no need to add an else statement
         */
        balance -= snackPrice;

        this.setBalance(balance);
    }

    public double calculateDiscountedPrice(int snackPrice, double studentDiscountPercentage)
    {
        /* We do our studentDiscountPercentage e.g 5/100 which gives us 0.05 we then do 1 - 0.05 to get 0.95
        we then times this by our price to get our discounted price for our snack.
         */
        return snackPrice * ( 1 - (studentDiscountPercentage / 100.0));
    }


    @Override
    public String toString()
    {
        return "Customer with accountID of: " + accountID + " is a normal customer" +
             ", their name is: " + name + " they have a balance of: " + balance + "\n";

    }

    public int addFunds(int amount)
    {
        return balance += amount;
    }

    // Getters for our fields.
    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public String getName()
    {
        return name;
    }

    public String getAccountID()
    {
        return accountID;
    }

    public static void main(String[] args) throws InvalidBalanceException {
        Customer test = new Customer(-250,"Duaine","A64127");
        test.chargeAccount(150);
        System.out.println(test.getBalance());

    }
}
