package utils;

import com.github.javafaker.Faker;
import java.util.Locale;

/**
 * Utility class for generating fake test data using Java Faker.
 * Centralized, reusable, and extensible for future test cases.
 */
public class FakeDataUtil {

    private static final Faker faker = new Faker(new Locale("en-US"));

    // ------------------ BASIC INFO ------------------
    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getUsername() {
        return faker.name().username();
    }

    // ------------------ CONTACT INFO ------------------
    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public static String getSafeEmail() {
        return faker.internet().safeEmailAddress();
    }

    // ------------------ ADDRESS INFO ------------------
    public static String getStreetAddress() {
        return faker.address().streetAddress();
    }

    public static String getCity() {
        return faker.address().city();
    }

    public static String getState() {
        return faker.address().state();
    }

    public static String getCountry() {
        return faker.address().country();
    }

    public static String getZipCode() {
        return faker.address().zipCode();
    }

    // ------------------ COMPANY INFO ------------------
    public static String getCompanyName() {
        return faker.company().name();
    }

    public static String getJobTitle() {
        return faker.job().title();
    }

    // ------------------ FINANCE INFO ------------------
    public static String getCreditCardNumber() {
        return faker.finance().creditCard();
    }

    public static String getIban() {
        return faker.finance().iban();
    }

    // ------------------ RANDOM HELPERS ------------------
    public static String getRandomDigits(int length) {
        return faker.number().digits(length);
    }

    public static String getCustomPattern(String pattern) {
        // '?' → random letter, '#' → random digit
        return faker.bothify(pattern);
    }

    public static int getRandomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    // ------------------ INTERNET ------------------
    public static String getUrl() {
        return faker.internet().url();
    }

    public static String getPassword() {
        return faker.internet().password();
    }
}
