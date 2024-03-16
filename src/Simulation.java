import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        int balance = 0;
        try
        {
            balance = Integer.parseInt(customerLine[2]);
        }
        catch(NumberFormatException e)
        {
            balance = Integer.parseInt(customerLine[4]);
        }

        if (customerLine.length > 3) {
            switch (customerLine[3].toUpperCase())
            {
                case "STUDENT":
                    try
                    {
                        snackShop.addStudentCustomers(balance, name, accountID);
                    } catch (NumberFormatException e) {
                        snackShop.addStudentCustomers(name, accountID);
                    }
                    break;
                case "STAFF":
                    /*
                    So with this code I had a lot of trouble creating this due to
                    the constructor setting a balance, for example Katya Vickers who did
                    not have a department but did have a balance, of zero.
                    What I did to correct this was again utilize try catches
                    we set a default department of other and if our customer's is greater than
                    5 we can try to assign the 4th index pos value of the line to our department
                    however if this throws an arrayindexoutofboundsexception we can catch it
                    and set jus
                     */
                    String staffDepartment = "OTHER";
                    if (customerLine.length >= 5)
                    {
                        try
                        {
                            staffDepartment = customerLine[4];
                        } catch (ArrayIndexOutOfBoundsException ex)
                        {
                            snackShop.addStaffCustomers(name, accountID, staffDepartment);
                        }
                    }
                    snackShop.addStaffCustomers(balance, name, accountID, staffDepartment);
                    break;
                default:
                    System.out.println("Something went wrong..");
            }
        } else
        {
            snackShop.addCustomer(balance, name, accountID);
        }
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
                snackShop.getSnack(snackID);
                snackShop.getCustomer(customerID);
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
                snackShop.getCustomer(customerID).addFunds(depositValue);
            }
            catch(InvalidCustomerException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else if(instruction.contains("NEW_CUSTOMER"))
        {
            ArrayList<String> transactionLinesAsList = new ArrayList<>(Arrays.asList(transactionLines));
            transactionLinesAsList.remove(0);
            String[] transactionLinesToCustomerLines = transactionLinesAsList.toArray(new String[0]);
            try
            {
                processAndAddCustomers(transactionLinesToCustomerLines, snackShop);
            }
            catch(InvalidCustomerException e)
            {
                System.out.println(e.getMessage());
            }
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
