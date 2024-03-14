
import java.io.File;

public class Main {

    public static void main(String[] args)
    {
        Simulation sim = new Simulation();
        File snackFile = sim.makeSnackFileObject("C:\\Users\\duain\\IdeaProjects\\Coursework 2 - SimpleSnack\\src\\snacks.txt");
        File customerFile = sim.makeCustomerFileObject("C:\\Users\\duain\\IdeaProjects\\Coursework 2 - SimpleSnack\\src\\customers.txt");
        sim.initialiseShop("Tesco", snackFile, customerFile);
    }
}

