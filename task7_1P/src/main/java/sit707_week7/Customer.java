package sit707_week7;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    
    public String getFullName() {
    	return this.firstName+" "+this.lastName;
    }
}
