package edu.uoc.pac3;

import java.time.LocalDate;

public class Passenger {

    // constants
    public static final String INVALID_NAME = "Name cannot be null, empty or exceed the maximum number of characters.";
    public static final String INVALID_BIRTHDAY = "Invalid birthday.";
    public static final String INVALID_ADDRESS = "Address cannot be null or empty.";
    public static final String INVALID_PHONE_NUMBER_FORMAT = "Invalid phone number format.";
    public static final String INVALID_HEIGHT = "Height must be between 50 and 250 cm.";
    public static final String INVALID_OCCUPATION = "Occupation cannot be null or empty.";
    public static final String INVALID_NATIONALITY = "Nationality cannot be null or empty.";
    private static final int NAME_MAX_LENGTH = 50;

    // variables
    private String name;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
    private String nationality;
    private double height;
    private boolean specialNeeds;
    private String occupation;
    private Passport passport;

    // constructors
    public Passenger(String name, LocalDate birthday, String address, String phoneNumber,
                     double height, boolean specialNeeds, String occupation, String nationality) {
        setName(name);
        setBirthday(birthday);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setHeight(height);
        setNationality(nationality);
        setSpecialNeeds(specialNeeds);
        setOccupation(occupation);
        this.passport = null;
    }

    public Passenger(String name, LocalDate birthday, String address, String phoneNumber,
                     double height, boolean specialNeeds, String occupation, String nationality,
                     String passportNumber, LocalDate issueDate, LocalDate expirationDate, int visaType) {
        this(name, birthday, address, phoneNumber, height, specialNeeds, occupation, nationality);
        try {
            this.passport = new Passport(passportNumber, issueDate, expirationDate, visaType);
        } catch (Exception e) {
            this.passport = null;
        }
    }

    //setters & getters
    public Passport getPassport() {
        return passport;
    }

    public void setPassport(String passportNumber, LocalDate issueDate, LocalDate expirationDate, int visaType) throws IllegalArgumentException {
        try {
            this.passport = new Passport(passportNumber, issueDate, expirationDate, visaType);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty() || name.length() > NAME_MAX_LENGTH || name.startsWith(" ")) {
            throw new IllegalArgumentException(INVALID_NAME);
        }

        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) throws IllegalArgumentException {
        if (birthday == null) {
            throw new IllegalArgumentException(INVALID_BIRTHDAY);
        }

        final int MAX_YEARS = 110;
        LocalDate now = LocalDate.now();
        int age = now.getYear() - birthday.getYear();

        if (birthday.isAfter(now) || age > MAX_YEARS) {
            throw new IllegalArgumentException(INVALID_BIRTHDAY);
        }

        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IllegalArgumentException {
        if (address == null || address.isEmpty() || address.startsWith(" ")) {
            throw new IllegalArgumentException(INVALID_ADDRESS);
        }

        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        // regex code
        String phoneRegex = "^\\+\\d{1,3}-\\d{1,12}$";

        if (phoneNumber == null || !phoneNumber.matches(phoneRegex)) {
            throw new IllegalArgumentException(INVALID_PHONE_NUMBER_FORMAT);
        }

        this.phoneNumber = phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) throws IllegalArgumentException {
        if (nationality == null || nationality.isEmpty() || nationality.startsWith(" ")) {
            throw new IllegalArgumentException(INVALID_NATIONALITY);
        }

        this.nationality = nationality;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) throws IllegalArgumentException {
        if (height < 50 || height > 250) {
            throw new IllegalArgumentException(INVALID_HEIGHT);
        }

        this.height = height;
    }

    public boolean hasSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(boolean specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) throws IllegalArgumentException {
        if (occupation == null || occupation.isEmpty() || occupation.startsWith(" ")) {
            throw new IllegalArgumentException(INVALID_OCCUPATION);
        }

        this.occupation = occupation;
    }

}
