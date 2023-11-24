package edu.uoc.pac3;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {
    // const
    public static final String ERROR_ORIGIN = "Origin cannot be null or empty.";
    public static final String ERROR_DESTINATION = "Destination cannot be null or empty.";
    public static final String ERROR_DATES = "Destination date must be previous to arrival date.";
    public static final String ERROR_NULL = "Element is null.";
    public static final String ERROR_PASSENGER_ALREADY_IN_FLIGHT = "The passenger is already in the flight.";
    public static final String ERROR_NO_PASSPORT = "The passport is null.";
    private static int nextId = 1;
    public final int NUM_MAX_PASSENGERS;
    // variables
    private int id;
    private String origin;
    private String destination;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    //TODO: podria ser final
    private Passenger[] passengers;

    public Flight(String origin, String destination, LocalDateTime departureDate, LocalDateTime arrivalDate, int numPassengers) throws IllegalArgumentException {
        if (departureDate == null || arrivalDate == null || departureDate.isAfter(arrivalDate)) {
            throw new IllegalArgumentException(ERROR_DATES);
        }

        this.passengers = new Passenger[numPassengers];
        setOrigin(origin);
        setDestination(destination);
        setDepartureDate(departureDate);
        setArrivalDate(arrivalDate);
        this.NUM_MAX_PASSENGERS = numPassengers;

        setId();
    }

    public static int getNextId() {
        return nextId;
    }

    private static void incNextId() {
        nextId++;
    }

    public int getId() {
        return id;
    }

    private void setId() {
        this.id = getNextId();
        incNextId();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) throws IllegalArgumentException {
        if (origin == null || origin.isEmpty() || origin.startsWith(" "))
            throw new IllegalArgumentException(ERROR_ORIGIN);
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) throws IllegalArgumentException {
        if (destination == null || destination.isEmpty() || destination.startsWith(" "))
            throw new IllegalArgumentException(ERROR_DESTINATION);
        this.destination = destination;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) throws IllegalArgumentException {
        if (departureDate != null && this.arrivalDate != null && !departureDate.isBefore(this.arrivalDate)) {
            throw new IllegalArgumentException(ERROR_DATES);
        }
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) throws IllegalArgumentException {
        if (arrivalDate != null && this.departureDate != null && !arrivalDate.isAfter(this.departureDate)) {
            throw new IllegalArgumentException(ERROR_DATES);
        }
        this.arrivalDate = arrivalDate;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public double getDuration() {
        Duration duration = Duration.between(departureDate, arrivalDate);
        return Math.abs(duration.toHours() + duration.toMinutesPart() / 60.0);
    }

    public boolean addPassenger(Passenger p) throws IllegalArgumentException, NullPointerException, IllegalStateException {
        if (p == null || p.getPassport() == null) throw new NullPointerException(ERROR_NO_PASSPORT);
        if (containsPassenger(p)) throw new IllegalStateException(ERROR_PASSENGER_ALREADY_IN_FLIGHT);

        for (int pos = 0; pos < passengers.length; pos++) {
            if (passengers[pos] == null) {
                passengers[pos] = p;
                return true;
            }
        }
        return false;
    }

    public boolean removePassenger(Passenger p) {
        if (p == null) throw new NullPointerException(ERROR_NO_PASSPORT);
        if (p.getPassport() == null) throw new NullPointerException(ERROR_NO_PASSPORT);

        int index = findPassenger(p);
        if (index != -1) {
            passengers[index] = null;
            return true;
        } else {
            return false;
        }
    }

    //TODO: dubte -> el findPassenger() ja fa la comprovació de ERROR_NO_PASSPORT, realment està duplicada al mètode.
    public boolean containsPassenger(Passenger p) throws NullPointerException {
        if (p == null) throw new NullPointerException(ERROR_NULL);
        if (p.getPassport() == null) throw new NullPointerException(ERROR_NO_PASSPORT);
        return (findPassenger(p) != -1);

    }

    private int findPassenger(Passenger passenger) throws NullPointerException {
        // returns position
        if (passenger == null || passenger.getPassport() == null) {
            return -1;
        }
        for (int pos = 0; pos < passengers.length; pos++) {
            if (passengers[pos] != null && passengers[pos].getPassport().equals(passenger.getPassport())) {
                return pos;
            }
        }
        return -1;
    }

    public int getNumPassengers() {
        int count = 0;
        for (Passenger passenger : passengers) {
            if (passenger != null) {
                count++;
            }
        }
        return count;
    }
}
