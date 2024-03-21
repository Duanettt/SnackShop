import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulation
{

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        sim.runSimulation();
    }

    public void runSimulation()
    {
        File snackFile = makeFileObject("snacks.txt");
        File customerFile = makeFileObject("customers.txt");
        File transactionFile = makeFileObject("transactions.txt");
        SnackShop Tesco = initialiseShop("Tesco", snackFile, customerFile);
//        System.out.println(Tesco.displayAllAccounts());
//        System.out.println(Tesco.calculateMedianCustomerBalance());
//        System.out.println(Tesco.findLargestBasePrice());
        simulateShopping(Tesco, transactionFile);
    }

    public SnackShop initialiseShop(String shopName, File snackFile, File customerFile)
    {
        SnackShop snackShop = new SnackShop(shopName);

        try
        {
            BufferedReader snackReader = new BufferedReader(new FileReader(snackFile));
            BufferedReader customerReader = new BufferedReader(new FileReader(customerFile));

            String snackLine;
            while ((snackLine = snackReader.readLine()) != null)
            {
                String[] snackLineValues = snackLine.split("@");
                this.processAndAddSnacks(snackLineValues, snackShop);
            }

            String customerLine;
            while ((customerLine = customerReader.readLine()) != null)
            {
                String[] customerLineValues = customerLine.split("#");
                this.processAndAddCustomers(customerLineValues, snackShop);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return snackShop;
    }

    public File makeFileObject(String filePath)
    {
        File file = null;

        try
        {
            file = new File(filePath);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return file;
    }

    public void processAndAddCustomers(String[] customerLine, SnackShop snackShop) {
        String accountID = customerLine[0];
        String name = customerLine[1];
        int balance = Integer.parseInt(customerLine[2]);
        addNewCustomer(customerLine, snackShop, accountID, name, balance, 3, customerLine.length - 1);
    }

    public void processAndAddSnacks(String[] snackLine, SnackShop snackShop)
    {
        String snackID = snackLine[0];
        char[] snackIDCharArray = snackID.toCharArray();
        char snackIDFirstLetter = snackIDCharArray[0];
        String name;
        int basePrice;
        String sugarContentLevel;
        if (snackIDFirstLetter == 'F')
        {
            name = snackLine[1];
            basePrice = Integer.parseInt(snackLine[3]);
            sugarContentLevel = snackLine[2];
            snackShop.addFood(snackID, name, basePrice, sugarContentLevel);
        }
        else if (snackIDFirstLetter == 'D')
        {
            name = snackLine[1];
            basePrice = Integer.parseInt(snackLine[3]);
            sugarContentLevel = snackLine[2].toLowerCase();
            if (sugarContentLevel.contains("none"))
            {
                snackShop.addDrink(snackID, name, basePrice);
            }
            else
            {
                snackShop.addDrink(snackID, name, basePrice, sugarContentLevel);
            }
        }
    }

    public void processTransactions(String[] transactionLines, SnackShop snackShop)
    {
        String instruction = transactionLines[0].toUpperCase();
        // Just be wasted memory (I think..) if i kept creating customerID variables
        // in every try catch statement..
        String customerID;
        Customer customer;
        Snack snack;
        if(instruction.contains("PURCHASE"))
        {
            try
            {
                /* Here we gain our customerID and snackID and place them in our processPurchase we also need these
                in order to print out information. I have to catch InvalidSnackException and CustomerException since
                the methods i'm using to get them throw these exceptions aswell... So we are not only doing exception
                handling in the processPurchase part.
                 */
                customerID = transactionLines[1];
                String snackID = transactionLines[2];
                customer = snackShop.getCustomer(customerID);
                snack = snackShop.getSnack(snackID);
                System.out.println(customer);
                System.out.println("Original balance: " + customer.getBalance()
                + " Base snack price: " + snack.getBasePrice()
                        + ", new snack price: " + snack.getNewPrice());
                snackShop.processPurchase(customerID, snackID);
                System.out.println("Transaction successful: " + customer.getName()
                + " has bought " + snack.getName() + " and now has a balance of: "
                + customer.getBalance());
            }
            catch(InvalidSnackException | InvalidCustomerException | TransactionException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else if(instruction.contains("ADD_FUNDS"))
        {
            try
            {
                customerID = transactionLines[1];
                int depositValue = Integer.parseInt(transactionLines[2]);
                customer = snackShop.getCustomer(customerID);
                System.out.println("Original balance: " + customer.getBalance());
                customer.addFunds(depositValue);
                System.out.println("Transaction successful: " + customer.getName()
                        + " now has a balance of: "
                        + customer.getBalance());
            }
            catch(InvalidCustomerException e)
            {
                System.out.println("Transaction unsuccessful: " + e.getMessage() + "\n");

            }
        }
        else if(instruction.contains("NEW_CUSTOMER"))
        {
            try
            {
                // The copies of range method allows me to specify which parts of the line I need.
                String[] customerInfo = Arrays.copyOfRange(transactionLines, 1, transactionLines.length);
                String accountID = customerInfo[0];
                String name = customerInfo[1];
                int balance = Integer.parseInt(transactionLines[customerInfo.length]);
                addNewCustomer(customerInfo, snackShop, accountID, name, balance, 2, 3);
                System.out.println("Transaction successful, NEW CUSTOMER ADDED!: " + name + " with a balance of, " + balance);
            }
            catch(InvalidCustomerException e)
            {
                System.out.println("Transaction unsuccessful: " + e.getMessage() + "\n");
            }
        }

    }

    /*
    The addNewCustomer function takes in 7 parameters our usual line and then
    our index positions for determining where the customerType is within our string
    and if there is a staff type where it would be in this string.
     */

    private void addNewCustomer(String[] customerInfo, SnackShop snackShop, String accountID, String name, int balance,
                                int customerTypeIndexPos, int staffTypeIndexPos)
    {
        /* If the line within our transactions.txt file is greater than 3
        we do our check for if its a student or a staff member
         */
        if (customerInfo.length > 3)
        {
            if (customerInfo[customerTypeIndexPos].equalsIgnoreCase("STUDENT"))
            {
                try
                {
                    snackShop.addStudentCustomers(balance, name, accountID);
                } catch (NumberFormatException e)
                {
                    snackShop.addStudentCustomers(name, accountID);
                }
            }
            else if(customerInfo[customerTypeIndexPos].equalsIgnoreCase("STAFF"))
            {
                String staffDepartment = "OTHER";
                if (customerInfo.length >= 5)
                {
                    try
                    {
                        staffDepartment = customerInfo[staffTypeIndexPos];
                    } catch (ArrayIndexOutOfBoundsException e)
                    {
                        snackShop.addStaffCustomers(name, accountID, staffDepartment);
                    }
                }
                snackShop.addStaffCustomers(balance, name, accountID, staffDepartment);
            }
        }
        else
        {
            snackShop.addCustomer(balance, name, accountID);
        }
    }



    public void simulateShopping(SnackShop snackShop, File transactionFile)
    {
        try
        {
            BufferedReader transanctionReader = new BufferedReader(new FileReader(transactionFile));

            String transactionLine;
            while ((transactionLine = transanctionReader.readLine()) != null)
            {
                String[] transactionLineValues = transactionLine.split(",");
                processTransactions(transactionLineValues, snackShop);
            }
            System.out.println("The largest base price in our snack collection: " + snackShop.findLargestBasePrice());
            System.out.println("The median customer balance out of customers in the " +
                            "shop: " + snackShop.getShopName() + " , the median " +
                    "balance: " + snackShop.calculateMedianCustomerBalance());
            System.out.println("The number of negative accounts in the snack shop, " +
                    snackShop.getShopName() + " is: "
                    + snackShop.countNegativeAccount());
            System.out.println("The turnover for " + snackShop.getShopName() + " is: " + snackShop.getTurnover());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
