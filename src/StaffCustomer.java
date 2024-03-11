public class StaffCustomer extends Customer
{
    public enum schoolDepartment {CMP, BIO, MTH, OTHER};

    private schoolDepartment staffDepartment;

    public StaffCustomer(int balance, String name, String accountID, String staffDepartment) throws InvalidCustomerException
    {
        super(balance, name, accountID);
        this.staffDepartment = determineStaffDepartment(staffDepartment);
    }

    public StaffCustomer(String name, String customerID, String staffDepartment)
    {
        super(name, customerID);
        this.staffDepartment = determineStaffDepartment(staffDepartment);
    }

    @Override
    public int chargeAccount(int snackPrice, String staffDepartment) throws InvalidBalanceException
    {
        /* This is wrong need to chill ill be back.. */
        return snackPrice * ( 1 - (calculateDiscountFromDept(staffDepartment)/ 100.0));
    }

    private double calculateDiscountFromDept(String department)
    {
        schoolDepartment temp = determineStaffDepartment(department);
        double discount = 0.0;

        switch(temp)
        {
            case CMP:
                discount = 10.0;
                break;
            // I do understand that jdk 14+ can support both MTH,BIO but I'm not risking PASS so ill separate
            // MTH and BIO for now.
            /* UPDATE: Learned that you could just put case MTH:/case BIO on top of eachother and will take same
            value.
             */
            case MTH:
            case BIO:
                discount = 2.0;
                break;
            case OTHER:
                discount = 0.0;
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
}
