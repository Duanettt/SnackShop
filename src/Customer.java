public class Customer
{
    // Fields for Customer
    protected int balance;
    protected String name;
    protected String accountID;
    // Constructors for customer
    public Customer(int balance, String name, String accountID) throws InvalidCustomerException
    {
        validateCustomerInfo(balance, accountID);
        this.balance = balance;
        this.name = name;
        this.accountID = accountID;
    }
    public Customer(String name, String customerID)
    {
        this.name = name;
        this.accountID = customerID;
        balance = 0;
    }
    // Methods for Customer.

    private void validateCustomerInfo(int balance, String accountID) throws InvalidCustomerException
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

    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        if(balance < snackPrice)
        {
            throw new InvalidBalanceException("Insufficient balance, your balance: " +
                    balance);
        }
        /* Updated: Removed the else since, no need to add an else statement
         */
        return balance -= snackPrice;
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

    public String getName()
    {
        return name;
    }

    public String getAccountID()
    {
        return accountID;
    }

    public static void main(String[] args) throws InvalidBalanceException {
        Customer test = new Customer(250,"Duaine","A64127");
        test.chargeAccount(150);
        System.out.println(test.getBalance());

    }
}
