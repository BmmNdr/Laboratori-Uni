package it.unimib.sd2026;

import java.time.LocalDate;
import java.util.Map;

/**
 * Rappresenta un contatto nella rubrica telefonica.
 */
public class Contact {
    // Identificativo.
    private int id;

    // Nome.
    private String firstName;

    // Cognome.
    private String lastName;

    // Numeri di telefono associati. La chiave è il numero,
    // il valore è un'etichetta opzionale.
    private Map<String, String> phoneNumbers;

    // Email, opzionale.
    private String email;

    // Data di nascita, opzionale.
    private LocalDate birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<String, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
