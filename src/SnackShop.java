
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
        try
        {
            Customer customer = new Customer(balance, name, accountID);
            customerAccounts.put(accountID, customer);
            // System.out.println(customer);
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
            // System.out.println(studentCustomer);
        }
        catch (InvalidCustomerException e)
        {
            // System.out.println(e.getMessage());
        }

    }

    public void addStudentCustomers(String name, String accountID)
    {
        try
        {
            Customer studentCustomer = new StudentCustomer(name, accountID);
            customerAccounts.put(accountID, studentCustomer);
            // System.out.println(studentCustomer);
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
            // System.out.println(staffCustomer);
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
            // System.out.println(staffCustomer);
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
            food.setNewPrice(food.calculatePrice());
            snackCollection.put(snackID, food);
            // System.out.println(food);
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
            drink.setNewPrice(drink.calculatePrice());
            snackCollection.put(snackID, drink);
            // System.out.println(drink);
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
            drink.setNewPrice(drink.calculatePrice());
            snackCollection.put(snackID, drink);
            // System.out.println(drink);
        }
        catch (InvalidSnackException e)
        {
            System.out.println(e.getMessage());
        }

    }
    // our processPurchase function:

    public boolean processPurchase(String customerID, String snackID) throws InvalidBalanceException {
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
                Customer c = getCustomer(customerID);
                Snack s = getSnack(snackID);
                c.chargeAccount(s.getBasePrice());
                return true;
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
            for(Customer customer : customerAccounts.values())
            {
                /* Firstly we check for each customers values within our customer
                accounts hashmap
                 */
                String customerAccountAsString = customer.toString();
                allCustomersAccountsAsStrings.append(customerAccountAsString);
            }
            // Return all the customer accounts within the stringbuilder as a
            // string since we are in string builder format.
            return allCustomersAccountsAsStrings.toString();
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
            if(customerAccounts.get(customerID) == null)
            {
                throw new InvalidSnackException("Invalid customerID: " + customerID + " is not part of our account database.");
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

