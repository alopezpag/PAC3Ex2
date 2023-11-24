package edu.uoc.pac3;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    Flight flight;

    @BeforeEach
    public void setUp(){
        flight = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 160);
    }

    @Test
    public void testAddPassengerOK(){
        Passenger passenger = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertTrue(flight.addPassenger(passenger));
        assertEquals(passenger, flight.getPassengers()[0]);

        passenger = new Passenger("Àngels Fitó", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "UOC2330", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertTrue(flight.addPassenger(passenger));
        assertEquals(passenger, flight.getPassengers()[1]);

        passenger = new Passenger("Leo Messi", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "BCA123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertTrue(flight.addPassenger(passenger));
        assertEquals(passenger, flight.getPassengers()[2]);

        assertEquals(3,flight.getNumPassengers());
    }

    @Test
    public void testAddPassengerNullOrNoPassport(){
        Passenger passenger = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE");

        //Passenger without passport
        NullPointerException ex = assertThrows(NullPointerException.class, () -> flight.addPassenger(passenger));
        assertEquals(Flight.ERROR_NO_PASSPORT, ex.getMessage());
        assertNull(flight.getPassengers()[0]);
        assertEquals(0,flight.getNumPassengers());

        //Null passenger
        NullPointerException exNull = assertThrows(NullPointerException.class, () -> flight.addPassenger(null));
        assertEquals(Flight.ERROR_NULL, exNull.getMessage());
        assertNull(flight.getPassengers()[0]);
        assertEquals(0,flight.getNumPassengers());
    }

    @Test
    public void testAddPassengerAlreadyInFlight() {
        Passenger passenger = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertTrue(flight.addPassenger(passenger));
        assertEquals(passenger, flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> flight.addPassenger(passenger));
        assertEquals(Flight.ERROR_PASSENGER_ALREADY_IN_FLIGHT, ex.getMessage());
        assertEquals(passenger, flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());
    }

    @Test
    public void testAddPassengerFullFlight(){
        Flight flightFull = new Flight("Paris", "Girona",
                LocalDateTime.of(2023, 12, 1, 23, 39, 10),
                LocalDateTime.of(2023, 12, 2, 1, 0, 1), 1);

        Passenger passenger1 = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertTrue(flightFull.addPassenger(passenger1));
        assertEquals(passenger1, flightFull.getPassengers()[0]);
        assertEquals(1,flightFull.getNumPassengers());

        Passenger passenger2 = new Passenger("Mickey Mouse", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ZXY321", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        assertFalse(flightFull.addPassenger(passenger2));
        assertEquals(passenger1, flightFull.getPassengers()[0]);
        assertEquals(1,flightFull.getNumPassengers());
    }

    @Test
    public void testRemovePassengerOK(){
        Passenger passengerOK = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        Passenger passengerNO = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "RAD123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        flight.addPassenger(passengerOK);
        assertEquals(passengerOK,flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());

        assertFalse(flight.removePassenger(passengerNO));
        assertEquals(passengerOK,flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());

        assertTrue(flight.removePassenger(passengerOK));
        assertNull(flight.getPassengers()[0]);
        assertEquals(0,flight.getNumPassengers());
    }

    @Test
    public void testRemovePassengerNullOrNoPassport(){
        Passenger passengerOK = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        flight.addPassenger(passengerOK);

        Passenger passenger = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE");

        //Passenger without passport
        NullPointerException ex = assertThrows(NullPointerException.class, () -> flight.removePassenger(passenger));
        assertEquals(Flight.ERROR_NO_PASSPORT, ex.getMessage());
        assertEquals(passengerOK,flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());

        //Null passenger
        NullPointerException exNull = assertThrows(NullPointerException.class, () -> flight.removePassenger(null));
        assertEquals(Flight.ERROR_NULL, exNull.getMessage());
        assertEquals(passengerOK,flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());
    }

    @Test
    public void testContainsPassenger(){
        Passenger passengerOK = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        Passenger passengerNO = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "RAD123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        flight.addPassenger(passengerOK);
        assertEquals(passengerOK,flight.getPassengers()[0]);
        assertEquals(1,flight.getNumPassengers());

        assertTrue(flight.containsPassenger(passengerOK));
        assertFalse(flight.containsPassenger(passengerNO));
    }

    @Test
    public void testCombo(){
        Passenger passenger1 = new Passenger("Robert White", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "ABC123", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        Passenger passenger2 = new Passenger("Mickey Mouse", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "DISENY20", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        Passenger passenger3 = new Passenger("Goffy", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "GOD13", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        Passenger passenger4 = new Passenger("Great", LocalDate.of(1998, 9, 18), "123 Oak St",
                "+99-98887776", 180.0, true, "Engineer", "DE",
                "Fantastic10", LocalDate.of(2022, 1, 15), LocalDate.of(2032, 1, 15), 1);

        Passenger[] passengers = flight.getPassengers();

        for (Passenger passenger : passengers) {
            assertNull(passenger);
        }
        assertTrue(flight.addPassenger(passenger1));
        assertTrue(flight.addPassenger(passenger2));
        assertTrue(flight.addPassenger(passenger3));

        assertEquals(passenger1, passengers[0]);
        assertEquals(passenger2, passengers[1]);
        assertEquals(passenger3, passengers[2]);
        assertEquals(3,flight.getNumPassengers());
        for(int i = 3; i < passengers.length; i++){
            assertNull(passengers[i]);
        }

        assertTrue(flight.removePassenger(passenger2));
        assertEquals(passenger1, passengers[0]);
        assertNull(passengers[1]);
        assertEquals(passenger3, passengers[2]);
        assertEquals(2,flight.getNumPassengers());
        for(int i = 3; i < passengers.length; i++){
            assertNull(passengers[i]);
        }

        assertTrue(flight.addPassenger(passenger4));
        assertEquals(passenger1, passengers[0]);
        assertEquals(passenger4, passengers[1]);
        assertEquals(passenger3, passengers[2]);
        assertEquals(3,flight.getNumPassengers());

        for(int i = 3; i < passengers.length; i++){
            assertNull(passengers[i]);
        }
    }
}
