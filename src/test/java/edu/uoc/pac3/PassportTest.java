package edu.uoc.pac3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PassportTest {


    @Test
    void testPassportCreation() {
        Passport passport = new Passport("GHI789", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertEquals("GHI789", passport.getPassportNumber());
        assertEquals(LocalDate.of(2022, 1, 15), passport.getIssueDate());
        assertEquals(LocalDate.of(2032, 1, 15), passport.getExpirationDate());
        assertEquals(1, passport.getVisaType());
    }


    @Test
    void testSetPassportNumberValid() {
        Passport passport = new Passport("12342", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);
        assertEquals("12342", passport.getPassportNumber());

        passport = new Passport("12FR4234", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);
        assertEquals("12FR4234", passport.getPassportNumber());

        passport = new Passport("12F-R4|234", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);
        assertEquals("12F-R4|234", passport.getPassportNumber());
    }

    @Test
    void testSetPassportNumberInvalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passport("", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1));

        assertEquals(Passport.PASSPORT_NUMBER_ERROR, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1));

        assertEquals(Passport.PASSPORT_NUMBER_ERROR, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("     ", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1));

        assertEquals(Passport.PASSPORT_NUMBER_ERROR, ex.getMessage());
    }

    @Test
    void testIssueDateValid() {
        LocalDate currentDate = LocalDate.now();
        LocalDate validIssueDate = currentDate.minusDays(91);
        Passport passport = new Passport("JKL012", validIssueDate, LocalDate.of(2032, 1, 1), 1);

        assertEquals(validIssueDate, passport.getIssueDate());

        //Today
        passport = new Passport("JKL012", currentDate, LocalDate.of(2032, 1, 1), 1);
        assertEquals(currentDate, passport.getIssueDate());
    }

    @Test
    void testSetIssueDateInvalid() {
        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passport("ABC123", null, LocalDate.of(2032, 1, 15), 1));
        assertEquals(Passport.ISSUE_DATE_ERROR, ex.getMessage());

        //Previous to today - 10 years
        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("ABC123", LocalDate.of(2013, 1, 1), LocalDate.of(2032, 1, 15), 1));
        assertEquals(Passport.ISSUE_DATE_ERROR, ex.getMessage());

        //Previous to today - 10 years
        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("MNO345", LocalDate.now().minusDays(36600), LocalDate.of(2032, 1, 1), 1));
        assertEquals(Passport.ISSUE_DATE_ERROR, ex.getMessage());

        //Further than today
        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("ABC123", LocalDate.now().plusDays(100), LocalDate.of(2032, 1, 15), 1));
        assertEquals(Passport.ISSUE_DATE_ERROR, ex.getMessage());
    }

    @Test
    void testExpirationDateValid() {
        LocalDate validIssueDate = LocalDate.now();

        Passport passport = new Passport("PQR678", validIssueDate, LocalDate.of(2032, 12, 31), 1);
        assertEquals(LocalDate.of(2032, 12, 31), passport.getExpirationDate());

        passport = new Passport("PQR678", validIssueDate, LocalDate.of(2028, 11, 8), 1);
        assertEquals(LocalDate.of(2028, 11, 8), passport.getExpirationDate());

        passport = new Passport("PQR678", validIssueDate, LocalDate.of(2028, 11, 8), 1);
        assertEquals(LocalDate.of(2028, 11, 8), passport.getExpirationDate());

        passport = new Passport("PQR678", validIssueDate, LocalDate.of(2028, 11, 8), 1);
        assertEquals(LocalDate.of(2028, 11, 8), passport.getExpirationDate());

    }

    @Test
    void testExpirationDateInvalid() {
        LocalDate validIssueDate = LocalDate.of(2023, 1, 1);

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passport("STU901", validIssueDate, null, 1));
        assertEquals(Passport.EXPIRATION_DATE_ERROR, ex.getMessage());

        //Over 10 years ago
        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("STU901", validIssueDate, LocalDate.of(2000, 1, 15), 1));
        assertEquals(Passport.EXPIRATION_DATE_ERROR, ex.getMessage());

        //Previous to issueDate
        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2021, 12, 31), 1));
        assertEquals(Passport.EXPIRATION_DATE_ERROR, ex.getMessage());

        //10 years after issueDate
        ex = assertThrows(IllegalArgumentException.class, () -> new Passport("ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2042, 1, 15), 1));
        assertEquals(Passport.EXPIRATION_DATE_ERROR, ex.getMessage());
    }


    @Test
    void testVisaTypeValid() {
        Passport passport = new Passport("BCD901", LocalDate.of(2023, 1, 15), LocalDate.of(2032, 1, 14), 5);
        assertEquals(5, passport.getVisaType());

        //0
        passport = new Passport("BCD901", LocalDate.of(2023, 1, 15), LocalDate.of(2032, 1, 14), 0);
        assertEquals(0, passport.getVisaType());
    }

    @Test
    void testSetVisaTypeInvalid() {
        //Negative
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Passport("ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), -1));
        assertEquals(Passport.VISA_TYPE_ERROR, ex.getMessage());
    }

    @Test
    @DisplayName("Sanity - Fields definition")
    void checkFieldsSanity() {
        //check attribute fields
        assertEquals(8, Passport.class.getDeclaredFields().length);
        try {
            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredField("passportNumber").getModifiers()));
            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredField("issueDate").getModifiers()));
            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredField("expirationDate").getModifiers()));
            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredField("visaType").getModifiers()));
            assertTrue(Modifier.isPublic(Passport.class.getDeclaredField("PASSPORT_NUMBER_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(Passport.class.getDeclaredField("PASSPORT_NUMBER_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(Passport.class.getDeclaredField("PASSPORT_NUMBER_ERROR").getModifiers()));
            assertTrue(Modifier.isPublic(Passport.class.getDeclaredField("ISSUE_DATE_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(Passport.class.getDeclaredField("ISSUE_DATE_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(Passport.class.getDeclaredField("ISSUE_DATE_ERROR").getModifiers()));
            assertTrue(Modifier.isPublic(Passport.class.getDeclaredField("EXPIRATION_DATE_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(Passport.class.getDeclaredField("EXPIRATION_DATE_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(Passport.class.getDeclaredField("EXPIRATION_DATE_ERROR").getModifiers()));
            assertTrue(Modifier.isPublic(Passport.class.getDeclaredField("VISA_TYPE_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(Passport.class.getDeclaredField("VISA_TYPE_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(Passport.class.getDeclaredField("VISA_TYPE_ERROR").getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Sanity - Methods definition")
    void checkMethodsSanity() {
        //check constructors
        assertEquals(1, Passport.class.getDeclaredConstructors().length);

        try {
            assertTrue(Modifier.isPublic(Passport.class.getDeclaredConstructor(String.class, LocalDate.class, LocalDate.class, int.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("There is some problem with the definition of constructors: " + e.getMessage());
            e.printStackTrace();
        }

        //check methods, parameters and return types
        assertEquals(4,
                Arrays.stream(Passport.class.getDeclaredMethods()).filter(m -> Modifier.isPublic(m.getModifiers())).toList().size());

        try {
            assertTrue(Modifier.isPublic(Passport.class.getDeclaredMethod("getPassportNumber").getModifiers()));
            assertEquals(String.class, Passport.class.getDeclaredMethod("getPassportNumber").getReturnType());

            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredMethod("setPassportNumber", String.class).getModifiers()));
            assertEquals(void.class, Passport.class.getDeclaredMethod("setPassportNumber", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Passport.class.getDeclaredMethod("getIssueDate").getModifiers()));
            assertEquals(LocalDate.class, Passport.class.getDeclaredMethod("getIssueDate").getReturnType());

            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredMethod("setIssueDate", LocalDate.class).getModifiers()));
            assertEquals(void.class, Passport.class.getDeclaredMethod("setIssueDate", LocalDate.class).getReturnType());

            assertTrue(Modifier.isPublic(Passport.class.getDeclaredMethod("getExpirationDate").getModifiers()));
            assertEquals(LocalDate.class, Passport.class.getDeclaredMethod("getExpirationDate").getReturnType());

            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredMethod("setExpirationDate", LocalDate.class).getModifiers())); assertEquals(LocalDate.class, Passport.class.getDeclaredMethod("getExpirationDate").getReturnType());
            assertEquals(void.class, Passport.class.getDeclaredMethod("setExpirationDate", LocalDate.class).getReturnType());

            assertTrue(Modifier.isPublic(Passport.class.getDeclaredMethod("getVisaType").getModifiers()));
            assertEquals(int.class, Passport.class.getDeclaredMethod("getVisaType").getReturnType());

            assertTrue(Modifier.isPrivate(Passport.class.getDeclaredMethod("setVisaType", int.class).getModifiers()));
            assertEquals(void.class, Passport.class.getDeclaredMethod("setVisaType", int.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
