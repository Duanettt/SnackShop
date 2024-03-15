
import java.io.File;

public class Main {

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        File snackFile = sim.makeFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\snacks.txt");
        File customerFile = sim.makeFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\customers.txt");
        SnackShop Tesco = sim.initialiseShop("Tesco", snackFile, customerFile);
        System.out.println(Tesco.calculateMedianCustomerBalance());
        System.out.println(Tesco.findLargestBasePrice());
        Tesco.processPurchase("48GFS3", "D/1122128");
        int adriannaBalance = Tesco.getCustomer("48GFS3").getBalance();
        System.out.println(adriannaBalance);
        System.out.println(Tesco.countNegativeAccount());
    }
}

