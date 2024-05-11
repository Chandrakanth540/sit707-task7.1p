package sit707_week7;

public class TemperatureReading {
    
    // Customer object
    private Customer customer;
    
    // reading time string format hh:mm:ss
    private String readingTime;
    
    // Body temperature
    private double bodyTemperature;

    public TemperatureReading(Customer customer, String readingTime, double bodyTemperature) {
        this.customer = customer;
        this.readingTime = readingTime;
        this.bodyTemperature = bodyTemperature;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public double getBodyTemperature() {
        return bodyTemperature;
    }
}
