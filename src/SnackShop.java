
import java.util.HashMap;

public class SnackShop {
    /*
    So when creating the method for the addCustomers pass in the parameters to
    create a customer object. We have to have a function using if statements
     that determines whether a customer is a staff or student.
     */

    /* My justified use in hashmaps:
    The reason I'm using HashMaps is since I visualized the idea of our ID and customer Info like a door
     and a key. I was also told in the labs about them from a TA for a query I had on a project
     and luckily enough it applies to the CW. It saves a lot more lines of code so simplicity, It's an efficient
     lookup system. From C++ i learnt that HashMaps provide performance related advantages as well.
    for our customer. It was so much simpler than using an ArrayList which you would need to use a for loop etc.
    instead all I need to do is input my key(CustomerID) and it will directly link to my customer as seen in the
    HashMap declaration below.
     */
    // Fields
    HashMap<String, Customer> customerAccounts = new HashMap<>();
    HashMap<String, Snack> snackCollection = new HashMap<>();
    private String shopName;
    private int turnover;
    public SnackShop(String shopName)
    {
        this.shopName = shopName;
    }

    /* UPDATE: Methods to add individual customers and snacks need multiple methods to access,
    multiple constructors cannot find another way to do this other than possibly changing
    the whole structure of this code which is not ideal....
     */

    public void addStudentCustomers(int balance, String name, String accountID)
    {
        try
        {
            Customer studentCustomer = new StudentCustomer(balance, name, accountID);
            customerAccounts.put(accountID, studentCustomer);
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void addStudentCustomers(String name, String accountID)
    {
        try
        {
            Customer studentCustomer = new StudentCustomer(name, accountID);
            customerAccounts.put(accountID, studentCustomer);
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void addStaffCustomers(int balance, String name, String accountID, String staffDepartment)
    {
        try
        {
            Customer staffCustomer = new StaffCustomer(balance, name, accountID, staffDepartment);
            customerAccounts.put(accountID, staffCustomer);
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addStaffCustomers(String name, String accountID, String staffDepartment)
    {
        try
        {
            Customer staffCustomer = new StaffCustomer(name, accountID, staffDepartment);
            customerAccounts.put(accountID, staffCustomer);
        }
        catch (InvalidSnackException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addFood(String snackID, String name, int basePrice, boolean isHot)
    {
        try
        {
            Food food = new Food(snackID, name, basePrice, isHot);
            snackCollection.put(snackID, food);
        }
        catch (InvalidSnackException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addDrink(String snackID, String name, int basePrice, String sugarContent)
    {
        try
        {
            Drink drink = new Drink(snackID, name, basePrice, sugarContent);
            snackCollection.put(snackID, drink);
        }
        catch (InvalidSnackException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void addDrink(String snackID, String name, int basePrice)
    {
        try
        {
            Drink drink = new Drink(snackID, name, basePrice);
            snackCollection.put(snackID, drink);
        }
        catch (InvalidSnackException e)
        {
            System.out.println(e.getMessage());
        }

    }
    // our processPurchase function:

    public boolean processPurchase(String customerID, String snackID)
    {
        boolean customerAccountsAndSnackCollectionContainsIDS =
                customerAccounts.containsKey(customerID) ||
                        snackCollection.containsKey(snackID);

        if(customerAccountsAndSnackCollectionContainsIDS)
        {
            try
            {
                Customer c = customerAccounts.get(customerID);
                Snack s = snackCollection.get(snackID);
                c.chargeAccount(s.getBasePrice());
                return true;
            }
            catch (InvalidBalanceException e)
            {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
// Getters and Setters for our Shop name.
    public String getShopName()
    {
        return shopName;
    }

    public void setShopName(String shopName)
    {
        this.shopName = shopName;
    }

    public Customer getCustomer(String customerID)
    {
        return customerAccounts.get(customerID);
    }

    public static void main(String[] args)
    {
        SnackShop Tesco = new SnackShop("Tesco");

    }

}
