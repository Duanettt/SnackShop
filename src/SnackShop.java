
import java.util.*;

public class SnackShop {
    /*
    So when creating the method for the addCustomers pass in the parameters to
    create a customer object. We have to have a function using if statements
     that determines whether a customer is a staff or student.
     */

    /* My justified use in hashmaps:
    The reason I'm using HashMaps is due to the idea of our ID and customer Info being like a door
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
        if(validAccount(accountID, "customer"))
        {
            Customer customer = new Customer(balance, name, accountID);
            customerAccounts.put(accountID, customer);
        }
    }


    public void addStudentCustomers(int balance, String name, String accountID)
    {
        if(validAccount(accountID, "customer"))
        {
            Customer studentCustomer = new StudentCustomer(balance, name, accountID);
            customerAccounts.put(accountID, studentCustomer);
        }
    }

    public void addStudentCustomers(String name, String accountID)
    {
        if(validAccount(accountID, "customer"))
        {
            Customer studentCustomer = new StudentCustomer(name, accountID);
            customerAccounts.put(accountID, studentCustomer);
        }
    }

    public void addStaffCustomers(int balance, String name, String accountID, String staffDepartment)
    {
        if(validAccount(accountID, "customer"))
        {
            Customer staffCustomer = new StaffCustomer(balance, name, accountID, staffDepartment);
            customerAccounts.put(accountID, staffCustomer);
        }
    }

    public void addStaffCustomers(String name, String accountID, String staffDepartment)
    {
        if(validAccount(accountID, "customer"))
        {
            Customer staffCustomer = new StaffCustomer(name, accountID, staffDepartment);
            customerAccounts.put(accountID, staffCustomer);
        }
    }

    public void addFood(String snackID, String name, int basePrice, String isHot)
    {
        if(validAccount(snackID, "snack"))
        {
            Food food = new Food(snackID, name, basePrice, isHot);
            food.setNewPrice(food.calculatePrice());
            snackCollection.put(snackID, food);
        }
    }

    public void addDrink(String snackID, String name, int basePrice, String sugarContent)
    {
        if(validAccount(snackID, "snack"))
        {
            Drink drink = new Drink(snackID, name, basePrice, sugarContent);
            drink.setNewPrice(drink.calculatePrice());
            snackCollection.put(snackID, drink);
        }
    }
    public void addDrink(String snackID, String name, int basePrice)
    {
        if(validAccount(snackID, "snack"))
        {
            Drink drink = new Drink(snackID, name, basePrice);
            drink.setNewPrice(drink.calculatePrice());
            snackCollection.put(snackID, drink);
        }
    }
    // our processPurchase function:

    public boolean processPurchase(String customerID, String snackID) throws TransactionException
    {
        boolean customerAccountsAndSnackCollectionContainsIDS =
                customerAccounts.containsKey(customerID) ||
                        snackCollection.containsKey(snackID);

        if (customerAccountsAndSnackCollectionContainsIDS)
        {
            /*
            UPDATE: Revisited slides and watched videos on exceptions relearnt multiple things
            which has enabled me to figure this out.
             */
            try
            {
                Customer c = getCustomer(customerID);
                Snack s = getSnack(snackID);
                // We will then charge the customer and charge account will return the value for turnover
                double discountedPrice = c.chargeAccount(s.getNewPrice());
                int newTurnover = this.turnover += (int) discountedPrice;
                setTurnover(newTurnover);
                return true;
            }
            catch ( InvalidCustomerException | InvalidSnackException | InvalidBalanceException e)
            {
                throw new TransactionException("Transaction could not be processed: " + e.getMessage());
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
            /*
            Test to actually calculate the median from this which is 400.
            Also clarifcation on values being added.
             */
//            System.out.println(customerBalances);
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

                    /* We use math.ceil to round to the nearest integer, when
                    using math.round this now rounds to the nearest float.
                     */

                    int median = (int) Math.ceil(((middle1 + middle2) / 2.0));
                    return median;
                }
                else
                {
                    /* We use math.ceil to round to the nearest integer, when
                    using math.round this now rounds to the nearest float.
                     */
                    int medianPosition = (int) Math.ceil(((customerBalances.size()) / 2.0));
                    return customerBalances.get(medianPosition);
                }
            }
            return 0;
        }

        /* Reason for using this method is from the feedback it states that we
        should not really be printing out information within our methods
        and instead return and then print them out in our main method.
         */
    public String displayAllAccounts()
    {
        StringBuilder allCustomersAccountsAsStrings = new StringBuilder();
        for (Customer customer : customerAccounts.values())
        {
                /* Firstly we check for each customers values within our customer
                accounts hashmap
                 */
            String customerAccountAsString = customer.toString();
            allCustomersAccountsAsStrings.append(customerAccountAsString).append("\n");
        }
        // Return all the customer accounts within the stringbuilder as a
        // string since we are in string builder format.
        return allCustomersAccountsAsStrings.toString();
    }

    public boolean validAccount(String ID, String type)
    {
        if(type.equalsIgnoreCase("customer"))
        {
            boolean isPresent = customerAccounts.containsKey(ID);
            if(isPresent)
            {
                throw new InvalidCustomerException("This accountID: " + ID + ", is already within our customer accounts database.");
            }
        }
        else if(type.equalsIgnoreCase("snack"))
        {
            boolean isPresent = snackCollection.containsKey(ID);
            if(isPresent)
            {
                throw new InvalidSnackException("This snackID: " + ID + ", is already within our customer accounts database.");
            }
        }
        return true;
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

    public int getTurnover()
    {
        return turnover;
    }

    public void setTurnover(int turnover)
    {
        this.turnover = turnover;
    }

    public Customer getCustomer (String customerID) throws InvalidCustomerException
        {
            if(customerAccounts.get(customerID) == null)
            {
                throw new InvalidCustomerException("Invalid customerID: " + customerID + " is not part of our account database.");
            }
            return customerAccounts.get(customerID);
        }

        public Snack getSnack (String snackID) throws InvalidSnackException
        {
            if(snackCollection.get(snackID) == null)
            {
                throw new InvalidSnackException("Invalid snackID: " + snackID + " is not part of our snack collection.");
            }
            return snackCollection.get(snackID);
        }

        public static void main (String[]args)
        {
            SnackShop Tesco = new SnackShop("Tesco");

        }


}

