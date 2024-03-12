public class SnackShop {
    /*
    So when creating the method for the addCustomers pass in the parameters to
    create a customer object. We have to have a function using if statements
     that determines whether a customer is a staff or student.
     */
    private String shopName;
    public SnackShop(String shopName)
    {
        this.shopName = shopName;
    }
    public Customer addCustomers(String customerType, int balance, String customerName, String customerAccountID, String staffDepartment)
    {
        String customerTypeLower = customerType.toLowerCase();
        Customer customerAdded = null;
        switch(customerTypeLower)
        {
            case "staff":
                customerAdded = new StaffCustomer(balance,customerName,
                        customerAccountID, staffDepartment);
                break;
            case "student":
                customerAdded = new StudentCustomer(balance,customerName,
                        customerAccountID);
                break;
        }

        return customerAdded;
    }
}
