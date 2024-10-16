
public class ProfileTest {

    @Test
    public void testCompareTo() {
        Profile profile1 = new Profile("John", "Doe", new Date("01/01/1990"));
        Profile profile2 = new Profile("Jane", "Smith", new Date("02/01/1990"));
        Profile profile3 = new Profile("John", "Doe", new Date("01/01/1990")); // Same as profile1

        // Profile1 should be less than Profile2
        assertEquals(-1, profile1.compareTo(profile2));

        // Profile2 should be greater than Profile1
        assertEquals(1, profile2.compareTo(profile1));

        // Profile1 should be equal to Profile3
        assertEquals(0, profile1.compareTo(profile3));
    }
}
