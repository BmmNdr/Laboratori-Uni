package it.unimib.sd2026;

/**
 * Keyboard rappresenta una tastiera per computer.
 */
public class Keyboard {
    // Identificativo univoco della tastiera.
    private int id;

    // Nome commerciale della tastiera.
    private String name;

    // Produttore della tastiera.
    private String manufacturer;

    // Anno di produzione della tastiera.
    private int year;

    // Se la tastiera è ergonomica.
    private boolean ergonomic;

    // Se la tastiera è retroilluminata.
    private boolean backlight;

    /**
     * Layout rappresenta i possibli schemi delle tastiere.
     */
    public enum Layout {
        QWERTY, AZERTY, DVORAK
    }

    private Layout layout;

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isErgonomic() {
        return ergonomic;
    }

    public void setErgonomic(boolean ergonomic) {
        this.ergonomic = ergonomic;
    }

    public boolean isBacklight() {
        return backlight;
    }

    public void setBacklight(boolean backlight) {
        this.backlight = backlight;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
