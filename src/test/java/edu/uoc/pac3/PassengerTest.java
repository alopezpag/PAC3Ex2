package edu.uoc.pac3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PassengerTest {


    @Test
    void testConstructorsInvalid() {
        //Wrong name
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger(" ", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        //Wrong birthday
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("David", LocalDate.of(1890, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES"));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());

        //Wrong address
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("David", LocalDate.of(1990, 5, 15), " ",
                "+12-34567890", 175.5, false, "Person", "ES"));
        assertEquals(Passenger.INVALID_ADDRESS, ex.getMessage());

        //Wrong phone
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("David", LocalDate.of(1990, 5, 15), "123 Main St",
                "+1243-34567890", 175.5, false, "Person", "ES"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Wrong height
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("David", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 265.5, false, "Person", "ES"));
        assertEquals(Passenger.INVALID_HEIGHT, ex.getMessage());

        //Wrong occupation
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("David", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 165.5, false, null, "ES"));
        assertEquals(Passenger.INVALID_OCCUPATION, ex.getMessage());

        //Wrong nationality
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("David", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 165.5, false, "Teacher", " "));
        assertEquals(Passenger.INVALID_NATIONALITY, ex.getMessage());


    }

    @Test
    void testSettersValid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        assertEquals("Froilan de Todos los Santos de Marichalar y Borbon", p.getName());
        p.setName("David");
        assertEquals("David", p.getName());

        assertEquals(LocalDate.of(1990, 5, 15), p.getBirthday());
        p.setBirthday(LocalDate.of(2003, 6, 12));
        assertEquals(LocalDate.of(2003, 6, 12), p.getBirthday());

        assertEquals("123 Main St", p.getAddress());
        p.setAddress("Coimbra 59");
        assertEquals("Coimbra 59", p.getAddress());

        assertEquals("+12-34567890", p.getPhoneNumber());
        p.setPhoneNumber("+34-931883626");
        assertEquals("+34-931883626", p.getPhoneNumber());

        assertEquals(175.5, p.getHeight());
        p.setHeight(159.3);
        assertEquals(159.3, p.getHeight());

        assertFalse(p.hasSpecialNeeds());
        p.setSpecialNeeds(false);
        assertFalse(p.hasSpecialNeeds());
        p.setSpecialNeeds(true);
        assertTrue(p.hasSpecialNeeds());

        assertEquals("Person", p.getOccupation());
        p.setOccupation("Cook");
        assertEquals("Cook", p.getOccupation());

        assertEquals("ES", p.getNationality());
        p.setNationality("FR");
        assertEquals("FR", p.getNationality());
    }

    @Test
    void testSetNameInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger(null, LocalDate.of(1990, 5, 15), "123 Main St",
                "+1234567890", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setName(null));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("", LocalDate.of(1990, 5, 15), "123 Main St",
                "+1234567890", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setName(""));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        //Empty, with only spaces
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("   ", LocalDate.of(1995, 1, 1), "123 Elm St",
                "+12-34567890", 170.0, true, "Student", "US"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setName("         "));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        //Too much long
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("Felipe Juan Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1995, 1, 1), "123 Elm St",
                "+12-34567890", 170.0, true, "Student", "US"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setName("Felipe Juan Froilan de Todos los Santos de Marichalar y Borbon Revilla"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        //Too much long
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("ThisIsAVeryLongNameThatExceedsTheLimitOfAllowedChars1", LocalDate.of(1995, 1, 1),
                "123 Elm St", "+12-345678901234", 170.0, true, "Student", "US"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setName("ThisIsAVeryLongNameThatExceedsTheLimitOfAllowedChars1"));
        assertEquals(Passenger.INVALID_NAME, ex.getMessage());
    }

    @Test
    void testSetDateOfBirthInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", null, "123 Main St",
                "+12-34567890", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setBirthday(null));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());

        //Day after today
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.now().plusDays(1), "123 Main St",
                "+12-34567890", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setBirthday(LocalDate.now().plusDays(2)));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());

        //Older than 110 years old
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("Elderly Person", LocalDate.of(1899, 1, 1), "123 Elm St",
                "+12-34567890", 165.0, true, "Student", "US"));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setBirthday(LocalDate.of(1898, 1, 1)));
        assertEquals(Passenger.INVALID_BIRTHDAY, ex.getMessage());
    }

    @Test
    void testSetAddressInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15),
                null, "+12-34567890", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_ADDRESS, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setAddress(null));
        assertEquals(Passenger.INVALID_ADDRESS, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15),
                "", "+12-34567890", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_ADDRESS, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setAddress("       "));
        assertEquals(Passenger.INVALID_ADDRESS, ex.getMessage());
    }

    @Test
    void testSetPhoneNumberInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                null, 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber(null));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "       ", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("      "));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //No country code
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "12345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("67890"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Country code with more than 3 digits
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+3434-12345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("+4524-12145"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Country code with 0 digits
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "-12345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("-67890"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Only country code
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+123", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("+67"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Only country code with more than 3 digits
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("+67890"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Phone with spaces
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "34 12345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("678 90"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Phone with spaces
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "34-12 345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("67-890 4233"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Phone with extra dashes
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "34-12-345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Phone with other special characters
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "34-12*345", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("67A89b0c"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        //Phone with 13 numbers after country code
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "34-1234532451234", 175.5, true, "Software Engineer", "US"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("32-1234532451234"));
        assertEquals(Passenger.INVALID_PHONE_NUMBER_FORMAT, ex.getMessage());
    }

    @Test
    void testSetNationality() {
        Passenger passenger = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE");
        assertEquals("DE", passenger.getNationality());

        passenger.setNationality("IT");
        assertEquals("IT", passenger.getNationality());
    }

    @Test
    void testSetNationalityInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+34-12342451234", 175.5, true, "Software Engineer", null));
        assertEquals(Passenger.INVALID_NATIONALITY, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setNationality(null));
        assertEquals(Passenger.INVALID_NATIONALITY, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+34-12342451234", 250, true, "Software Engineer", "   "));
        assertEquals(Passenger.INVALID_NATIONALITY, ex.getMessage());

        assertThrows(IllegalArgumentException.class, () -> p.setNationality("                     "));
        assertEquals(Passenger.INVALID_NATIONALITY, ex.getMessage());
    }


    @Test
    void testSetHeightValid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        Passenger passenger = new Passenger("Jane Smith", LocalDate.of(1985, 3, 10), "456 Oak Ave",
                "+32-9876543210", 50.0, false, "Doctor", "CA");
        assertEquals(50.0, passenger.getHeight());

        p.setHeight(50);
        assertEquals(50.0, passenger.getHeight());

        passenger = new Passenger("Jane Smith", LocalDate.of(1985, 3, 10), "456 Oak Ave",
                "+32-9876543210", 250.0, false, "Doctor", "CA");
        assertEquals(250.0, passenger.getHeight());

        p.setHeight(250);
        assertEquals(250.0, passenger.getHeight());
    }

    @Test
    void testSetHeightInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Greater than 250
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("Bob Johnson", LocalDate.of(2000, 8, 20), "789 Elm Rd",
                "+11-12223334", 300.0, true, "Student", "GB"));
        assertEquals(Passenger.INVALID_HEIGHT, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setHeight(350));
        assertEquals(Passenger.INVALID_HEIGHT, ex.getMessage());

        //Lower than 50
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("Bob Johnson", LocalDate.of(2000, 8, 20), "789 Elm Rd",
                "+11-12223334", 49, true, "Student", "GB"));
        assertEquals(Passenger.INVALID_HEIGHT, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setHeight(29));
        assertEquals(Passenger.INVALID_HEIGHT, ex.getMessage());
    }

    @Test
    void testHasSpecialNeeds() {
        Passenger passenger = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE");
        assertTrue(passenger.hasSpecialNeeds());

        passenger.setSpecialNeeds(false);
        assertFalse(passenger.hasSpecialNeeds());
    }

    @Test
    void testSetOccupationInvalid() {
        Passenger p = new Passenger("Froilan de Todos los Santos de Marichalar y Borbon", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 175.5, false, "Person", "ES");

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 50, true, "", "US"));
        assertEquals(Passenger.INVALID_OCCUPATION, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setOccupation(null));
        assertEquals(Passenger.INVALID_OCCUPATION, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> new Passenger("John Doe", LocalDate.of(1990, 5, 15), "123 Main St",
                "+12-34567890", 50, true, "", "US"));
        assertEquals(Passenger.INVALID_OCCUPATION, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> p.setOccupation("        "));
        assertEquals(Passenger.INVALID_OCCUPATION, ex.getMessage());
    }

    @Test
    void testPassportAssociation() {
        Passenger passenger = new Passenger("Michael Lee", LocalDate.of(1980, 4, 3), "789 Maple Ave",
                "+55-54443332", 175.0, false, "Manager", "SG",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertEquals("ABC123", passenger.getPassport().getPassportNumber());
        assertEquals(LocalDate.of(2022, 1, 15), passenger.getPassport().getIssueDate());
        assertEquals(LocalDate.of(2032, 1, 15), passenger.getPassport().getExpirationDate());
        assertEquals(1, passenger.getPassport().getVisaType());
    }

    @Test
    void testSetPassportNull() {
        Passenger passenger = new Passenger("John Smith", LocalDate.of(1985, 6, 10), "789 Pine Ln",
                "+44-45556667", 180.0, false, "Doctor", "GB");
        assertNull(passenger.getPassport());
    }

    @Test
    void testSetPassportValid() {
        Passenger passenger = new Passenger("Daniel Green", LocalDate.of(1990, 4, 3), "123 Maple Ave",
                "+55-54443332", 175.0, false, "Manager", "SG");

        Passport passport1 = new Passport("ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);
        passenger.setPassport("ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);
        assertEquals(passport1.getPassportNumber(), passenger.getPassport().getPassportNumber());
        assertEquals(passport1.getIssueDate(), passenger.getPassport().getIssueDate());
        assertEquals(passport1.getExpirationDate(), passenger.getPassport().getExpirationDate());
        assertEquals(passport1.getVisaType(), passenger.getPassport().getVisaType());

        Passport passport2 = new Passport("DEF456", LocalDate.of(2022, 2, 1), LocalDate.of(2032, 1, 15), 2);
        passenger.setPassport("DEF456", LocalDate.of(2022, 2, 1), LocalDate.of(2032, 1, 15), 2);
        assertEquals(passport2.getPassportNumber(), passenger.getPassport().getPassportNumber());
        assertEquals(passport2.getIssueDate(), passenger.getPassport().getIssueDate());
        assertEquals(passport2.getExpirationDate(), passenger.getPassport().getExpirationDate());
        assertEquals(passport2.getVisaType(), passenger.getPassport().getVisaType());
    }

    @Test
    void testPassportInvalid() {
        Passenger passenger = new Passenger("No Passport", LocalDate.of(1995, 6, 1), "123 Elm St",
                "+12-34567890", 165.0, true, "Student", "US",
                "", LocalDate.of(2022, 12, 1), LocalDate.of(2024, 12, 12), 1);
        assertNull(passenger.getPassport());

        passenger = new Passenger("No Passport", LocalDate.of(1995, 6, 1), "123 Elm St",
                "+12-34567890", 165.0, true, "Student", "US",
                "afasdf3", LocalDate.of(1989, 12, 1), LocalDate.of(1989, 12, 12), 1);
        assertNull(passenger.getPassport());

        final Passenger passenger2 = new Passenger("Michael Lee", LocalDate.of(1980, 4, 3), "789 Maple Ave",
                "+55-54443332", 175.0, false, "Manager", "SG",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                passenger2.setPassport("PASSPORT N1", LocalDate.of(1989, 12, 1),LocalDate.of(1989, 12, 12), 1));
        assertEquals(Passport.ISSUE_DATE_ERROR, ex.getMessage());
        assertEquals("ABC123", passenger2.getPassport().getPassportNumber());
    }

    @Test
    @DisplayName("Sanity - Fields definition")
    void checkFieldsSanity() {
        //check attribute fields
        assertEquals(17, Passenger.class.getDeclaredFields().length);
        try {
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("name").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("birthday").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("address").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("phoneNumber").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("nationality").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("height").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("specialNeeds").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("occupation").getModifiers()));
            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("passport").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_NAME").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_NAME").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_NAME").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_BIRTHDAY").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_BIRTHDAY").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_BIRTHDAY").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_ADDRESS").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_ADDRESS").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_ADDRESS").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_PHONE_NUMBER_FORMAT").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_PHONE_NUMBER_FORMAT").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_PHONE_NUMBER_FORMAT").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_HEIGHT").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_HEIGHT").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_HEIGHT").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_OCCUPATION").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_OCCUPATION").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_OCCUPATION").getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredField("INVALID_NATIONALITY").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("INVALID_NATIONALITY").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("INVALID_NATIONALITY").getModifiers()));

            assertTrue(Modifier.isPrivate(Passenger.class.getDeclaredField("NAME_MAX_LENGTH").getModifiers()));
            assertTrue(Modifier.isStatic(Passenger.class.getDeclaredField("NAME_MAX_LENGTH").getModifiers()));
            assertTrue(Modifier.isFinal(Passenger.class.getDeclaredField("NAME_MAX_LENGTH").getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Sanity - Methods definition")
    void checkMethodsSanity() {
        //check constructors
        assertEquals(2, Passenger.class.getDeclaredConstructors().length);

        try {
            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredConstructor(String.class, LocalDate.class, String.class
                    , String.class, double.class, boolean.class, String.class, String.class).getModifiers()));

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredConstructor(String.class, LocalDate.class, String.class
                    , String.class, double.class, boolean.class, String.class, String.class,
                    String.class, LocalDate.class, LocalDate.class, int.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("There is some problem with the definition of constructors: " + e.getMessage());
            e.printStackTrace();
        }

        //check methods, parameters and return types
        assertEquals(18,
                Arrays.stream(Passenger.class.getDeclaredMethods()).filter(m -> Modifier.isPublic(m.getModifiers())).toList().size());

        try {
            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getPassport").getModifiers()));
            assertEquals(Passport.class, Passenger.class.getDeclaredMethod("getPassport").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setPassport", String.class, LocalDate.class, LocalDate.class, int.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setPassport", String.class, LocalDate.class, LocalDate.class, int.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getName").getModifiers()));
            assertEquals(String.class, Passenger.class.getDeclaredMethod("getName").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setName", String.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setName", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getBirthday").getModifiers()));
            assertEquals(LocalDate.class, Passenger.class.getDeclaredMethod("getBirthday").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setBirthday", LocalDate.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setBirthday", LocalDate.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getAddress").getModifiers()));
            assertEquals(String.class, Passenger.class.getDeclaredMethod("getAddress").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setAddress", String.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setAddress", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getPhoneNumber").getModifiers()));
            assertEquals(String.class, Passenger.class.getDeclaredMethod("getPhoneNumber").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setPhoneNumber", String.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setPhoneNumber", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getNationality").getModifiers()));
            assertEquals(String.class, Passenger.class.getDeclaredMethod("getNationality").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setNationality", String.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setNationality", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getHeight").getModifiers()));
            assertEquals(double.class, Passenger.class.getDeclaredMethod("getHeight").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setHeight", double.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setHeight", double.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("hasSpecialNeeds").getModifiers()));
            assertEquals(boolean.class, Passenger.class.getDeclaredMethod("hasSpecialNeeds").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setSpecialNeeds", boolean.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setSpecialNeeds", boolean.class).getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("getOccupation").getModifiers()));
            assertEquals(String.class, Passenger.class.getDeclaredMethod("getOccupation").getReturnType());

            assertTrue(Modifier.isPublic(Passenger.class.getDeclaredMethod("setOccupation", String.class).getModifiers()));
            assertEquals(void.class, Passenger.class.getDeclaredMethod("setOccupation", String.class).getReturnType());


        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
