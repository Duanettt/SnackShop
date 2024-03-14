import java.io.*;

public class Simulation {
    public SnackShop initialiseShop(String shopName, File snackFile, File customerFile) {
        SnackShop snackShop = new SnackShop(shopName);
        try {
            BufferedReader snackReader = new BufferedReader(new FileReader(snackFile));
            BufferedReader customerReader = new BufferedReader(new FileReader(customerFile));

            String snackLine;
            while ((snackLine = snackReader.readLine()) != null) {
                String[] snackLineValues = snackLine.split("#");

                for (int i = 0; i < snackLineValues.length; i++) {
                    snackLineValues[i] = snackLineValues[i].trim();
                    processAndAddSnacks(snackLineValues, snackShop);
                }
            }
            String customerLine;
            while ((customerLine = customerReader.readLine()) != null) {
                String[] customerLineValues = customerLine.split("#");

                for (int i = 0; i < customerLineValues.length; i++) {
                    customerLineValues[i] = customerLineValues[i].trim();
                    processAndAddCustomers(customerLineValues, snackShop);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return snackShop;
    }

    public void processAndAddCustomers(String[] customerLine, SnackShop snackShop) {
        String accountID = customerLine[0];
        String name = customerLine[1];
        int balance = Integer.parseInt(customerLine[2]);

        // Check if there are additional elements in the array to determine customer type
        if (customerLine.length > 3) {
            String typeOfCustomer = customerLine[3].toUpperCase();
            switch (typeOfCustomer) {
                case "STUDENT":
                    // Check if there are enough elements to include balance
                    if (customerLine.length > 4) {
                        try {
                            snackShop.addStudentCustomers(balance, name, accountID);
                        } catch (NumberFormatException e) {
                            snackShop.addStudentCustomers(name, accountID);
                        }
                    }
                    break;
                case "STAFF":
                    String staffDepartment = customerLine[4];
                    if (customerLine.length > 5) {
                        try {
                            snackShop.addStaffCustomers(balance, name, accountID, staffDepartment);
                        } catch (NumberFormatException e) {
                            snackShop.addStaffCustomers(name, accountID, staffDepartment);
                        }
                    }
                    break;
                default:
                    System.out.println("Logic went wrong..");
                    break;
            }
        } else {
            // If no type of customer was specified we treat as a regular customer
            snackShop.addCustomer(balance, accountID, name);
        }
    }


    public void processAndAddSnacks(String[] snackLine, SnackShop snackShop) {
        String snackID = snackLine[0];
        char[] snackIDCharArray = snackID.toCharArray();
        char snackIDFirstLetter = snackIDCharArray[0];

        if (snackIDFirstLetter == 'F') {
            String name = snackLine[1];
            int basePrice = Integer.parseInt(snackLine[2]);
            String isHot = snackLine[3];

            snackShop.addFood(snackID, name, basePrice, isHot);
        } else if (snackIDFirstLetter == 'D') {
            String name = snackLine[1];
            int basePrice = Integer.parseInt(snackLine[2]);
            String sugarContentLevel = snackLine[3].toLowerCase();
            if (sugarContentLevel.contains("none")) {
                snackShop.addDrink(snackID, name, basePrice);
            }
            snackShop.addDrink(snackID, name, basePrice, sugarContentLevel);
        }
    }

    /*
    Not a lot to do here since I have exceptions being thrown in previous
    classes. Might be updated if i find a way to use the exceptions as a way
    of determining a food or drink.
     */
    public void simulateShopping(SnackShop snackShop, File transactionFile)
    {
        
    }

    public static void main(String[] args)
    {

    }


};

