
import java.io.File;

public class Main {

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        File snackFile = sim.makeSnackFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\snacks.txt");
        File customerFile = sim.makeCustomerFileObject("C:\\Users\\dnett\\Documents\\COMPSCI---Coursework\\src\\customers.txt");
        sim.initialiseShop("Tesco", snackFile, customerFile);
    }
}

