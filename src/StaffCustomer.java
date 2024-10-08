public class StaffCustomer extends Customer
{
    public enum schoolDepartment {CMP, BIO, MTH, OTHER};

    private schoolDepartment staffDepartment;

    public StaffCustomer(int balance, String name, String accountID, String staffDepartment) throws InvalidCustomerException
    {
        super(balance, name, accountID);
        try
        {
            this.staffDepartment = determineStaffDepartment(staffDepartment);
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public StaffCustomer(String name, String accountID, String staffDepartment)
    {
        super(name, accountID);
        try
        {
            this.staffDepartment = determineStaffDepartment(staffDepartment);
        }
        catch (InvalidCustomerException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int chargeAccount(int snackPrice) throws InvalidBalanceException
    {
        // Will calculate the new snack price after a staff discount has been applied.
        double staffDiscountedSnackPrice = Math.ceil(calculateDiscountedPrice(snackPrice, calculateDiscountFromDept()));

        // Boolean value for readability
        boolean balanceIsLessThanStaffDiscountedPrice = (balance <
               staffDiscountedSnackPrice);

        if(balanceIsLessThanStaffDiscountedPrice)
        {
            throw new InvalidBalanceException("Insufficient balance, " + getName()
                    + " balance: " + balance + " the snack's price is: " + staffDiscountedSnackPrice);
        }
        /* Updated: Removed the else since, no need to add an else statement
         */
        int newBalance = 0;
        newBalance = (balance -= (staffDiscountedSnackPrice));

        this.setBalance(newBalance);
        return (int) Math.ceil(staffDiscountedSnackPrice);
    }

    private double calculateDiscountFromDept()
    {
        schoolDepartment temp = this.staffDepartment;

        double discount = 0.0;

        switch(temp)
        {
            case CMP:
                discount = 10.0;
//                System.out.println("Computer Science selected!");
                break;
            // I do understand that jdk 14+ can support both MTH,BIO but I'm not risking PASS so ill separate
            // MTH and BIO for now.
            /* UPDATE: Learned that you could just put case MTH:/case BIO on top of eachother and will take same
            value.
             */
            case MTH:
            case BIO:
                discount = 2.0;
//                System.out.println("Maths or Biology selected!");
                break;
            default:
                discount = 0.0;
//                System.out.println("Other selected!");
                break;
        }
        return discount;
    }
    private schoolDepartment determineStaffDepartment(String department)
    {
        if (department.equalsIgnoreCase("CMP"))
        {
            return schoolDepartment.CMP;
        }
        else if (department.equalsIgnoreCase("BIO"))
        {
            return schoolDepartment.BIO;
        }
        else if (department.equalsIgnoreCase("MTH"))
        {
            return schoolDepartment.MTH;
        }
        else if (department.equalsIgnoreCase("OTHER"))
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

    @Override
    public String toString() {
        return "Customer with accountID of: " + accountID + " is a member of " +
                "staff, at this specific school department: " + staffDepartment
                + ", their name is: " + name + " they have a balance of: " + balance;
    }
    public static void main(String[] args) throws InvalidBalanceException {
        StaffCustomer test = new StaffCustomer(500, "Duaine", "A123456", "bio");
        test.chargeAccount(150);
        System.out.println(test.balance);
    }
}
