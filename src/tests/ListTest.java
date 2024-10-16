import static org.junit.Assert.*;
import org.junit.Test;

public class ListTest {

    @Test
    public void testAdd() {
        List<Provider> providerList = new List<>();

        Doctor doctor = new Doctor(new Profile("Alice", "Johnson", new Date("05/05/1980")), "Cardiology", "12345");
        Technician technician = new Technician(new Profile("Bob", "Williams", new Date("03/03/1975")), "X-ray", 100);

        providerList.add(doctor);
        providerList.add(technician);

        // Check if the items were added
        assertTrue(providerList.contains(doctor));
        assertTrue(providerList.contains(technician));
    }

    @Test
    public void testRemove() {
        List<Provider> providerList = new List<>();

        Doctor doctor = new Doctor(new Profile("Alice", "Johnson", new Date("05/05/1980")), "Cardiology", "12345");
        Technician technician = new Technician(new Profile("Bob", "Williams", new Date("03/03/1975")), "X-ray", 100);

        providerList.add(doctor);
        providerList.add(technician);

        // Remove the doctor and check
        providerList.remove(doctor);
        assertFalse(providerList.contains(doctor));

        // Remove the technician and check
        providerList.remove(technician);
        assertFalse(providerList.contains(technician));
    }
}
