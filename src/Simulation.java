
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Simulation {
    public Simulation() {
    }

    public SnackShop initialiseShop(String shopName, File snackFile, File customerFile) {
        SnackShop snackShop = new SnackShop(shopName);

        try {
            BufferedReader snackReader = new BufferedReader(new FileReader(snackFile));
            BufferedReader customerReader = new BufferedReader(new FileReader(customerFile));

            String snackLine;
            while((snackLine = snackReader.readLine()) != null) {
                String[] snackLineValues = snackLine.split("@");
                this.processAndAddSnacks(snackLineValues, snackShop);
            }

            String customerLine;
            while((customerLine = customerReader.readLine()) != null) {
                String[] customerLineValues = customerLine.split("#");
                this.processAndAddCustomers(customerLineValues, snackShop);
            }
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return snackShop;
    }

    public File makeSnackFileObject(String snackPath) {
        File snackFile = null;

        try {
            snackFile = new File(snackPath);
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }

        return snackFile;
    }

    public File makeCustomerFileObject(String customerPath) {
        File customerFile = null;

        try {
            customerFile = new File(customerPath);
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }

        return customerFile;
    }

    public void processAndAddCustomers(String[] customerLine, SnackShop snackShop) {
        String accountID = customerLine[0];
        String name = customerLine[1];
        int balance = Integer.parseInt(customerLine[2]);
        if (customerLine.length > 3) {
            switch (customerLine[3].toUpperCase()) {
                case "STUDENT":
                    if (customerLine.length > 4) {
                        try {
                            snackShop.addStudentCustomers(balance, name, accountID);
                        } catch (NumberFormatException var12) {
                            snackShop.addStudentCustomers(name, accountID);
                        }
                    }
                    break;
                case "STAFF":
                    if (customerLine.length >= 5) {
                        String staffDepartment = null;

                        try {
                            staffDepartment = customerLine[4];
                            snackShop.addStaffCustomers(balance, name, accountID, staffDepartment);
                        } catch (ArrayIndexOutOfBoundsException var11) {
                            snackShop.addStaffCustomers(name, accountID, staffDepartment);
                        }
                    }
                    break;
                default:
                    System.out.println("Logic went wrong..");
            }
        } else {
            snackShop.addCustomer(balance, name, accountID);
        }

    }

    public void processAndAddSnacks(String[] snackLine, SnackShop snackShop) {
        String snackID = snackLine[0];
        char[] snackIDCharArray = snackID.toCharArray();
        char snackIDFirstLetter = snackIDCharArray[0];
        String name;
        int basePrice;
        String sugarContentLevel;
        if (snackIDFirstLetter == 'F') {
            name = snackLine[1];
            basePrice = Integer.parseInt(snackLine[3]);
            sugarContentLevel = snackLine[2];
            snackShop.addFood(snackID, name, basePrice, sugarContentLevel);
        } else if (snackIDFirstLetter == 'D') {
            name = snackLine[1];
            basePrice = Integer.parseInt(snackLine[3]);
            sugarContentLevel = snackLine[2].toLowerCase();
            if (sugarContentLevel.contains("none")) {
                snackShop.addDrink(snackID, name, basePrice);
            }

            snackShop.addDrink(snackID, name, basePrice, sugarContentLevel);
        }

    }

    public void simulateShopping(SnackShop snackShop, File transactionFile) {
    }
}
