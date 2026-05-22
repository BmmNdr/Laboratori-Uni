package it.unimib.sd2026;

/**
 * Rappresenta un utente.
 */
public class User {
    private int id;

    private String name;

    private boolean admin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return String.format("[id = %d, name = %s, admin = %b]", id, name, admin);
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

        return id == user.id && name.equals(user.name) && admin == user.admin;
    }
}
