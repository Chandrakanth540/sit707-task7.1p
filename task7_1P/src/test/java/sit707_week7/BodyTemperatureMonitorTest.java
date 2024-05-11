package sit707_week7;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class BodyTemperatureMonitorTest {

    @Test
    public void testStudentIdentity() {
        String studentId = "S223798216";
        Assert.assertNotNull("Student ID is ", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "chandrakanth";
        Assert.assertNotNull("Student name is ", studentName);
    }
    
    @Test
    public void testTemperatureReading() {
        Customer customer = new Customer("John","Doe","Johndoe@gmail.com");
        String readingTime = "12:30:45";
        double bodyTemperature = 36.5;

        TemperatureReading temperatureReading = new TemperatureReading(customer, readingTime, bodyTemperature);

        Assert.assertEquals("Customer name is John Doe", "John Doe", temperatureReading.getCustomer().getFullName());
        Assert.assertEquals("Reading time is 12:30:45", "12:30:45", temperatureReading.getReadingTime());
        Assert.assertEquals("Body temperature is 36.5", 36.5, temperatureReading.getBodyTemperature(), 0);
    }

    @Test
    public void testReadTemperatureNegative() {
        TemperatureSensor mockTemperatureSensor = Mockito.mock(TemperatureSensor.class);
        Mockito.when(mockTemperatureSensor.readTemperatureValue()).thenReturn(-1.0);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(mockTemperatureSensor, null, null);
        double temperature = monitor.readTemperature();

        Assert.assertEquals("Temperature is -1", -1, temperature, 0);
    }

    @Test
    public void testReadTemperatureZero() {
        TemperatureSensor mockTemperatureSensor = Mockito.mock(TemperatureSensor.class);
        Mockito.when(mockTemperatureSensor.readTemperatureValue()).thenReturn(0.0);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(mockTemperatureSensor, null, null);
        double temperature = monitor.readTemperature();

        Assert.assertEquals("Temperature is 0", 0, temperature, 0);
    }

    @Test
    public void testReadTemperatureNormal() {
        TemperatureSensor mockTemperatureSensor = Mockito.mock(TemperatureSensor.class);
        Mockito.when(mockTemperatureSensor.readTemperatureValue()).thenReturn(37.0);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(mockTemperatureSensor, null, null);
        double temperature = monitor.readTemperature();

        Assert.assertEquals("Temperature is 37", 37, temperature, 0);
    }

    @Test
    public void testReadTemperatureAbnormallyHigh() {
        TemperatureSensor mockTemperatureSensor = Mockito.mock(TemperatureSensor.class);
        Mockito.when(mockTemperatureSensor.readTemperatureValue()).thenReturn(50.0);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(mockTemperatureSensor, null, null);
        double temperature = monitor.readTemperature();

        Assert.assertEquals("Temperature is 50", 50, temperature, 0);
    }
    
    @Test
    public void testReportTemperatureReadingToCloud() {
        TemperatureReading temperatureReading = new TemperatureReading(null, null, 37.0);

        CloudService mockCloudService = Mockito.mock(CloudService.class);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(null, mockCloudService, null);
        monitor.reportTemperatureReadingToCloud(temperatureReading);

        Mockito.verify(mockCloudService).sendTemperatureToCloud(temperatureReading);
    }
    
    @Test
    public void testInquireBodyStatusNormal() {
        CloudService mockCloudService = Mockito.mock(CloudService.class);
        Mockito.when(mockCloudService.queryCustomerBodyStatus(Mockito.any())).thenReturn("NORMAL");

        NotificationSender mockNotificationSender = Mockito.mock(NotificationSender.class);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(null, mockCloudService, mockNotificationSender);
        monitor.inquireBodyStatus();

        Mockito.verify(mockNotificationSender).sendEmailNotification(Mockito.any(Customer.class), Mockito.eq("Thumbs Up!"));
    }
    
    @Test
    public void testInquireBodyStatusAbnormal() {
        CloudService mockCloudService = Mockito.mock(CloudService.class);
        Mockito.when(mockCloudService.queryCustomerBodyStatus(Mockito.any())).thenReturn("ABNORMAL");

        NotificationSender mockNotificationSender = Mockito.mock(NotificationSender.class);

        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(null, mockCloudService, mockNotificationSender);
        monitor.inquireBodyStatus();

        Mockito.verify(mockNotificationSender).sendEmailNotification(Mockito.any(FamilyDoctor.class), Mockito.eq("Emergency!"));
    }
}
