import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulation {

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
        addNewCustomer(customerLine, snackShop, accountID, name, balance, 3);
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
        if(instruction.contains("PURCHASE"))
        {
            try
            {
                customerID = transactionLines[1];
                String snackID = transactionLines[2];
                System.out.println(snackShop.getCustomer(customerID).getName());
                System.out.println(snackShop.getCustomer(customerID).getBalance());
                snackShop.processPurchase(customerID, snackID);
                System.out.println(snackShop.getCustomer(customerID).getBalance());
            }
            catch(InvalidCustomerException | InvalidSnackException e)
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
                System.out.println(snackShop.getCustomer(customerID).getBalance());
                snackShop.getCustomer(customerID).addFunds(depositValue);
                System.out.println(snackShop.getCustomer(customerID).getBalance());
                System.out.println(snackShop.getCustomer(customerID).getName());
            }
            catch(InvalidCustomerException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else if(instruction.contains("NEW_CUSTOMER"))
        {
            String[] customerInfo = Arrays.copyOfRange(transactionLines, 1, transactionLines.length);
            String accountID = customerInfo[0];
            String name = customerInfo[1];
            int balance = Integer.parseInt(transactionLines[customerInfo.length]);
            addNewCustomer(customerInfo, snackShop, accountID, name, balance, 2);
            System.out.println(snackShop.getCustomer(accountID).getBalance());
            System.out.println(snackShop.getCustomer(accountID).getName());
        }

    }

    private void addNewCustomer(String[] customerInfo, SnackShop snackShop, String accountID, String name, int balance, int determinant)
    {
        if (customerInfo.length > 3)
        {
            if (customerInfo[determinant].equalsIgnoreCase("STUDENT"))
            {
                try
                {
                    snackShop.addStudentCustomers(balance, name, accountID);
                } catch (NumberFormatException e)
                {
                    snackShop.addStudentCustomers(name, accountID);
                }
            }
            else if(customerInfo[determinant].equalsIgnoreCase("STAFF"))
            {
                String staffDepartment = "OTHER";
                if (customerInfo.length >= 5)
                {
                    try
                    {
                        staffDepartment = customerInfo[4];
                    } catch (ArrayIndexOutOfBoundsException ex)
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
