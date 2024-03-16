
import java.io.File;

public class Main {

    public static void main(String[] args)
    {
        SnackShop Tesco = getSnackShop();
//        System.out.println(Tesco);
        System.out.println(Tesco.displayAllAccounts());
        System.out.println(Tesco.calculateMedianCustomerBalance());
        System.out.println(Tesco.findLargestBasePrice());
//        Tesco.processPurchase("594401", "D/1122128");
//        int adriannaBalance = Tesco.getCustomer("594401").getBalance();
//        System.out.println(adriannaBalance);
//        System.out.println(Tesco.countNegativeAccount());
    }

    private static SnackShop getSnackShop() {
        Simulation sim = new Simulation();
        File snackFile = sim.makeFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\snacks.txt");
        File customerFile = sim.makeFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\customers.txt");
        File transactionFile = sim.makeFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\transactions.txt");
        SnackShop Tesco = sim.initialiseShop("Tesco", snackFile, customerFile);
        sim.simulateShopping(Tesco, transactionFile);
        return Tesco;
    }
}

