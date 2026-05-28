package it.unimib.sd2026;

import java.time.LocalDate;

/**
 * Rappresenta un utente.
 */
public class User {
    // ID pubblico identificativo dell'utente.
    private int id;

    // Nome dell'utente.
    private String name;

    // Data di iscrizione dell'utente.
    private LocalDate joined;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
        this.joined = joined;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        var user = (User)obj;

        return id == user.id && name.equals(user.name) && joined.equals(user.joined);
    }
}
