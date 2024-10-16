package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import util.Date;

public class DateTest {

    @Test
    public void testValidDates() {
        // Test valid dates
        Date date1 = new Date("10/15/2023");
        Date date2 = new Date("02/29/2020"); // Leap year

        assertTrue(date1.isValid());
        assertTrue(date2.isValid());
    }

    @Test
    public void testInvalidDates() {
        // Test invalid dates
        Date date1 = new Date("13/15/2023"); // Invalid month
        Date date2 = new Date("02/30/2023"); // Invalid day
        Date date3 = new Date("04/31/2023"); // Invalid day for April
        Date date4 = new Date("02/29/2021"); // Non-leap year

        assertFalse(date1.isValid());
        assertFalse(date2.isValid());
        assertFalse(date3.isValid());
        assertFalse(date4.isValid());
    }
}
