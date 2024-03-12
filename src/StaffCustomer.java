public class StaffCustomer extends Customer
{
    public enum schoolDepartment {CMP, BIO, MTH, OTHER};

    private schoolDepartment staffDepartment;

    public StaffCustomer(int balance, String name, String accountID, String staffDepartment) throws InvalidCustomerException
    {
        super(balance, name, accountID);
        this.staffDepartment = determineStaffDepartment(staffDepartment);
    }

    public StaffCustomer(String name, String accountID, String staffDepartment)
    {
        super(name, accountID);
        this.staffDepartment = determineStaffDepartment(staffDepartment);
    }

    @Override
    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        boolean balanceIsLessThanStaffDiscountedPrice = (balance <
                StudentCustomer.calculateDiscountedPrice(snackPrice, calculateDiscountFromDept()));

        if(balanceIsLessThanStaffDiscountedPrice)
        {
            throw new InvalidBalanceException("Insufficient balance, your balance: " +
                    balance);
        }

        double staffDiscountedSnackPrice = (StudentCustomer.calculateDiscountedPrice(snackPrice, calculateDiscountFromDept()));
        /* Updated: Removed the else since, no need to add an else statement
         */
        return Math.round(balance -= (int) staffDiscountedSnackPrice);
    }

    private double calculateDiscountFromDept()
    {
        schoolDepartment temp = this.staffDepartment;

        double discount = 0.0;

        switch(temp)
        {
            case CMP:
                discount = 10.0;
                System.out.println("Computer Science selected!");
                break;
            // I do understand that jdk 14+ can support both MTH,BIO but I'm not risking PASS so ill separate
            // MTH and BIO for now.
            /* UPDATE: Learned that you could just put case MTH:/case BIO on top of eachother and will take same
            value.
             */
            case MTH:
            case BIO:
                discount = 2.0;
                System.out.println("Maths or Biology selected!");
                break;
            case OTHER:
                discount = 0.0;
                System.out.println("Other selected!");
                break;
        }
        return discount;
    }
    private schoolDepartment determineStaffDepartment(String department)
    {
        String departmentUpperCase = department.toUpperCase();

        if (departmentUpperCase.contains("CMP"))
        {
            return schoolDepartment.CMP;
        }
        else if (departmentUpperCase.contains("BIO"))
        {
            return schoolDepartment.BIO;
        }
        else if (departmentUpperCase.contains("MTH"))
        {
            return schoolDepartment.MTH;
        }
        else if (departmentUpperCase.contains("OTHER"))
        {
            return schoolDepartment.OTHER;
        }
        else
        {
            throw new InvalidCustomerException("You haven't selected a correct department within the school, please" +
                    "select from: CMP, BIO, MTH, OTHER");
        }
    }

    public schoolDepartment getStaffDepartment()
    {
        return staffDepartment;
    }

    public void setStaffDepartment(schoolDepartment staffDepartment)
    {
        this.staffDepartment = staffDepartment;
    }
    public static void main(String[] args) throws InvalidBalanceException {
        StaffCustomer test = new StaffCustomer(500, "Duaine", "A123456", "bio");
        System.out.println(test.chargeAccount(150));
    }
}
