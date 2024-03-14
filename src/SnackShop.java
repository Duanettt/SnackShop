
import java.util.*;

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

    /* UPDATE: Struggled on this since did not really know if the coursework wanted
    me to find a way to add snacks and then determine which snacks are which or
    just have these individual methods. Stuck with this since coursework said individual
    customers and snacks..
    /*

     /*
    Methods to add individual customers and snacks need multiple methods to access,3
    multiple constructors cannot find another way to do this other than possibly changing
    the whole structure of this code which is not ideal...
     */

    public void addCustomer(int balance, String name, String accountID)
    {
        try
        {
            Customer customer = new Customer(balance, name, accountID);
            customerAccounts.put(accountID, customer);
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }
    }

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

    public void addFood(String snackID, String name, int basePrice, String isHot)
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

    public boolean processPurchase(String customerID, String snackID) throws InvalidBalanceException
    {
        boolean customerAccountsAndSnackCollectionContainsIDS =
                customerAccounts.containsKey(customerID) ||
                        snackCollection.containsKey(snackID);

        if (customerAccountsAndSnackCollectionContainsIDS)
        {
                /*
                This code is what the code below does I did not really want to keep
                creating variables but for readability I chose the code below this comment.
                Both do the same thing just which one is better for
                readability.
                getCustomer(customerID).chargeAccount(getSnack(snackID).getBasePrice());
                */
            /*
            UPDATE: Revisited slides and watched videos on exceptions relearnt multiple things
            which has enabled me to figure this out.
             */
            try
            {
                Customer c = getCustomer(customerID);
                Snack s = getSnack(snackID);
                c.chargeAccount(s.getBasePrice());
                return true;
            }
            catch (InvalidCustomerException | InvalidBalanceException |
                     InvalidSnackException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

        public int findLargestBasePrice()
        {
            int largestSnackBasePrice = 0;
            for (Snack snack : snackCollection.values())
            {
                int snackBasePrice = snack.getBasePrice();
                if(snackBasePrice > largestSnackBasePrice)
                {
                    largestSnackBasePrice = snackBasePrice;
                }
            }
            return largestSnackBasePrice;
        }

        public int countNegativeAccount()
        {
            int numOfNegativeAccounts = 0;
            for(Customer customer : customerAccounts.values())
            {
                int currentCustomerBalance = customer.getBalance();
                if(currentCustomerBalance < 0)
                {
                    numOfNegativeAccounts++;
                }
            }
            return numOfNegativeAccounts;
        }

        public int calculateMedianCustomerBalance()
        {
            List<Integer> customerBalances = new ArrayList<>();
            /*
            Method of conversion from hashmap to list for sorting of our data.
             */
            for(Customer customer : customerAccounts.values())
            {
                int customerBalance = customer.getBalance();
                customerBalances.add(customerBalance);
            }
            /*
            Sorts our new List with customer balances into order
             */
            Collections.sort(customerBalances);
            int size = customerBalances.size();
            /*
            Finding the median.
             */
            for(int value : customerBalances)
            {
                boolean sizeOfBalanceListEven = size % 2 == 0;

                if(sizeOfBalanceListEven)
                {

                    int middle1 = customerBalances.get(size / 2 - 1);
                    int middle2 = customerBalances.get(size / 2);

                    return (int) ((middle1 + middle2) / 2.0);
                }
                else
                {
                    return (int) ((customerBalances.size()) / 2.0);
                }
            }
            return 0;
        }

// Getters and Setters for our Shop name.
        public String getShopName()
        {
            return shopName;
        }

        public void setShopName (String shopName)
        {
            this.shopName = shopName;
        }

        public Customer getCustomer (String customerID) throws InvalidCustomerException
        {
            return customerAccounts.get(customerID);
        }

        public Snack getSnack (String snackID) throws InvalidSnackException
        {
            return snackCollection.get(snackID);
        }

        public static void main (String[]args)
        {
            SnackShop Tesco = new SnackShop("Tesco");

        }


}

