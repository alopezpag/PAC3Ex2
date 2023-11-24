package edu.uoc.pac3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class FlightTest {

    @BeforeEach
    void init() {
        try {
            Field field = Flight.class.getDeclaredField("nextId");
            field.setAccessible(true);
            field.set(null, 1);
        } catch (Exception e) {
            fail("Parameterized constructor failed");
        }
    }

    @Test
    void testConstructorInvalid() {
        //Wrong origin
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new Flight(" ", "Paris",
                        LocalDateTime.of(2023, 1, 1, 10, 1, 10),
                        LocalDateTime.of(2023, 1, 1, 11, 1, 10), 100));

        assertEquals(Flight.ERROR_ORIGIN, ex.getMessage());

        //Wrong destination
        ex = assertThrows(IllegalArgumentException.class, () ->
                new Flight("Paris", null,
                        LocalDateTime.of(2023, 1, 1, 10, 1, 10),
                        LocalDateTime.of(2023, 1, 1, 11, 1, 10), 100));

        assertEquals(Flight.ERROR_DESTINATION, ex.getMessage());

        //Wrong dates
        ex = assertThrows(IllegalArgumentException.class, () ->
                new Flight("Paris", "Barcelona",
                        LocalDateTime.of(2023, 1, 2, 10, 1, 10),
                        LocalDateTime.of(2023, 1, 1, 11, 1, 10), 100));

        assertEquals(Flight.ERROR_DATES, ex.getMessage());
    }

    @Test
    public void testSettersValid() {
        Flight flight = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        assertEquals("Paris", flight.getOrigin());
        assertEquals("Girona", flight.getDestination());
        assertEquals(LocalDateTime.of(2023, 12, 1, 23, 39, 10), flight.getDepartureDate());
        assertEquals(LocalDateTime.of(2023, 12, 2, 1, 0, 1), flight.getArrivalDate());
        assertEquals(160, flight.NUM_MAX_PASSENGERS);
        assertEquals(0, flight.getNumPassengers());
        assertEquals(160, flight.getPassengers().length);
    }

    @Test
    public void testSetId() {
        assertEquals(1, Flight.getNextId());

        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        assertEquals(2, Flight.getNextId());

        Flight f2 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        assertEquals(3, Flight.getNextId());

        Flight f3 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        assertEquals(4, Flight.getNextId());

        assertEquals(1, f1.getId());
        assertEquals(2, f2.getId());
        assertEquals(3, f3.getId());
    }

    @Test
    public void testSetOriginValid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        assertEquals("Paris", f1.getOrigin());

        f1.setOrigin("Barcelona");
        assertEquals("Barcelona", f1.getOrigin());
    }

    @Test
    public void testSetOriginInvalid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> f1.setOrigin(null));
        assertEquals(Flight.ERROR_ORIGIN, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> f1.setOrigin(""));
        assertEquals(Flight.ERROR_ORIGIN, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> f1.setOrigin("             "));
        assertEquals(Flight.ERROR_ORIGIN, ex.getMessage());
    }

    @Test
    public void testSetDestinationValid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        assertEquals("Girona", f1.getDestination());

        f1.setDestination("Barcelona");
        assertEquals("Barcelona", f1.getDestination());
    }

    @Test
    public void testSetDestinationInvalid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        //Null
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> f1.setDestination(null));
        assertEquals(Flight.ERROR_DESTINATION, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> f1.setDestination(""));
        assertEquals(Flight.ERROR_DESTINATION, ex.getMessage());

        //Empty
        ex = assertThrows(IllegalArgumentException.class, () -> f1.setDestination("             "));
        assertEquals(Flight.ERROR_DESTINATION, ex.getMessage());
    }

    @Test
    public void testSetDepartureDateValid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        //Departure date is previous to arrival date
        f1.setDepartureDate(LocalDateTime.of(2023, 12, 2, 0, 0, 1));
        assertEquals(LocalDateTime.of(2023, 12, 2, 0, 0, 1), f1.getDepartureDate());

        //Departure date is null
        f1.setDepartureDate(null);
        assertNull(f1.getDepartureDate());

        //Arrival date is null, but departure date not
        f1.setArrivalDate(null);
        f1.setDepartureDate(LocalDateTime.of(2023, 12, 2, 0, 0, 1));
        assertEquals(LocalDateTime.of(2023, 12, 2, 0, 0, 1), f1.getDepartureDate());
    }

    @Test
    public void testSetDepartureDateInvalid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        //Same date
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> f1.setDepartureDate(LocalDateTime.of(2023, 12, 2, 1, 0, 1)));
        assertEquals(Flight.ERROR_DATES, ex.getMessage());

        //Posterior date
        ex = assertThrows(IllegalArgumentException.class, () -> f1.setDepartureDate(LocalDateTime.of(2023, 12, 2, 1, 0, 2)));
        assertEquals(Flight.ERROR_DATES, ex.getMessage());
    }

    @Test
    public void testSetArrivalDateValid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        //Arrival date is after departure date
        f1.setArrivalDate(LocalDateTime.of(2023, 12, 3, 0, 0, 1));
        assertEquals(LocalDateTime.of(2023, 12, 3, 0, 0, 1), f1.getArrivalDate());

        //Arrival date is null
        f1.setArrivalDate(null);
        assertNull(f1.getArrivalDate());

        //Departure date is null, but arrival date not
        f1.setDepartureDate(null);
        f1.setArrivalDate(LocalDateTime.of(2023, 12, 2, 0, 0, 1));
        assertEquals(LocalDateTime.of(2023, 12, 2, 0, 0, 1), f1.getArrivalDate());
    }

    @Test
    public void testSetArrivalDateInvalid() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);

        //Same date
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> f1.setArrivalDate(LocalDateTime.of(2023, 12, 1, 23, 39, 10)));
        assertEquals(Flight.ERROR_DATES, ex.getMessage());

        //Previous date
        ex = assertThrows(IllegalArgumentException.class, () -> f1.setDepartureDate(LocalDateTime.of(2024, 12, 1, 1, 20, 20)));
        assertEquals(Flight.ERROR_DATES, ex.getMessage());
    }

    @Test
    public void testGetDuration() {
        Flight f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 0, 0),
                LocalDateTime.of(2023, 12, 1, 23, 45, 0), 160);

        assertEquals(0.75, f1.getDuration());

        f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 31, 0),
                LocalDateTime.of(2023, 12, 2, 1, 1, 0), 180);

        assertEquals(1.5, f1.getDuration());

        f1 = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 20, 5, 0),
                LocalDateTime.of(2023, 12, 2, 1, 20, 0), 180);

        assertEquals(5.25, f1.getDuration());
    }

    @Test
    @DisplayName("Sanity - Fields definition")
    void checkFieldsSanity() {
        //check attribute fields
        assertEquals(14, Flight.class.getDeclaredFields().length);
        try {
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("id").getModifiers()));
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("nextId").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("nextId").getModifiers()));
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("origin").getModifiers()));
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("destination").getModifiers()));
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("departureDate").getModifiers()));
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("arrivalDate").getModifiers()));
            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredField("passengers").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("NUM_MAX_PASSENGERS").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("NUM_MAX_PASSENGERS").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("ERROR_ORIGIN").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("ERROR_ORIGIN").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("ERROR_ORIGIN").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("ERROR_DESTINATION").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("ERROR_DESTINATION").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("ERROR_DESTINATION").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("ERROR_DATES").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("ERROR_DATES").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("ERROR_DATES").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("ERROR_NULL").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("ERROR_NULL").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("ERROR_NULL").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("ERROR_PASSENGER_ALREADY_IN_FLIGHT").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("ERROR_PASSENGER_ALREADY_IN_FLIGHT").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("ERROR_PASSENGER_ALREADY_IN_FLIGHT").getModifiers()));
            assertTrue(Modifier.isFinal(Flight.class.getDeclaredField("ERROR_NO_PASSPORT").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredField("ERROR_NO_PASSPORT").getModifiers()));
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredField("ERROR_NO_PASSPORT").getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Sanity - Methods definition")
    void checkMethodsSanity() {
        //check constructors
        assertEquals(1, Flight.class.getDeclaredConstructors().length);

        try {
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredConstructor(String.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("There is some problem with the definition of constructors: " + e.getMessage());
            e.printStackTrace();
        }

        //check methods, parameters and return types
        assertEquals(16,
                Arrays.stream(Flight.class.getDeclaredMethods()).filter(m -> Modifier.isPublic(m.getModifiers())).toList().size());
        try {
            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getId").getModifiers()));
            assertEquals(int.class, Flight.class.getDeclaredMethod("getId").getReturnType());

            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredMethod("setId").getModifiers()));
            assertEquals(void.class, Flight.class.getDeclaredMethod("setId").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getNextId").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredMethod("getNextId").getModifiers()));
            assertEquals(int.class, Flight.class.getDeclaredMethod("getNextId").getReturnType());

            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredMethod("incNextId").getModifiers()));
            assertTrue(Modifier.isStatic(Flight.class.getDeclaredMethod("incNextId").getModifiers()));
            assertEquals(void.class, Flight.class.getDeclaredMethod("incNextId").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getOrigin").getModifiers()));
            assertEquals(String.class, Flight.class.getDeclaredMethod("getOrigin").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("setOrigin", String.class).getModifiers()));
            assertEquals(LocalDate.class, Passport.class.getDeclaredMethod("getExpirationDate").getReturnType());
            assertEquals(void.class, Flight.class.getDeclaredMethod("setOrigin", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getDestination").getModifiers()));
            assertEquals(String.class, Flight.class.getDeclaredMethod("getDestination").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("setDestination", String.class).getModifiers()));
            assertEquals(void.class, Flight.class.getDeclaredMethod("setDestination", String.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getDepartureDate").getModifiers()));
            assertEquals(LocalDateTime.class, Flight.class.getDeclaredMethod("getDepartureDate").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("setDepartureDate", LocalDateTime.class).getModifiers()));
            assertEquals(void.class, Flight.class.getDeclaredMethod("setDepartureDate", LocalDateTime.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getArrivalDate").getModifiers()));
            assertEquals(LocalDateTime.class, Flight.class.getDeclaredMethod("getArrivalDate").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("setArrivalDate", LocalDateTime.class).getModifiers()));
            assertEquals(void.class, Flight.class.getDeclaredMethod("setArrivalDate", LocalDateTime.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getPassengers").getModifiers()));
            assertEquals(Passenger[].class, Flight.class.getDeclaredMethod("getPassengers").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getDuration").getModifiers()));
            assertEquals(double.class, Flight.class.getDeclaredMethod("getDuration").getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("addPassenger", Passenger.class).getModifiers()));
            assertEquals(boolean.class, Flight.class.getDeclaredMethod("addPassenger", Passenger.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("removePassenger", Passenger.class).getModifiers()));
            assertEquals(boolean.class, Flight.class.getDeclaredMethod("removePassenger", Passenger.class).getReturnType());

            assertTrue(Modifier.isPrivate(Flight.class.getDeclaredMethod("findPassenger", Passenger.class).getModifiers()));
            assertEquals(int.class, Flight.class.getDeclaredMethod("findPassenger", Passenger.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("containsPassenger", Passenger.class).getModifiers()));
            assertEquals(boolean.class, Flight.class.getDeclaredMethod("containsPassenger", Passenger.class).getReturnType());

            assertTrue(Modifier.isPublic(Flight.class.getDeclaredMethod("getNumPassengers").getModifiers()));
            assertEquals(int.class, Flight.class.getDeclaredMethod("getNumPassengers").getReturnType());


        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of getter or setter methods: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
