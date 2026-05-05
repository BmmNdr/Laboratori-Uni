package it.unimib.sd2026;

import java.util.ArrayList;

/**
 * Keyboard rappresenta una tastiera per computer.
 * 
 * Nota: è una semplice classe con attributi privati e metodi getter/setter per ogni attributo.
 */
public class Keyboard {
    private String name;

    private String manufacturer;

    private int year;

    private boolean ergonomic;

    private boolean backlight;

    private int id;

    private ArrayList<String> comments = new ArrayList<String>();

    /**
     * Layout rappresenta i possibli schemi delle tastiere.
     */
    public enum Layout {
        QWERTY, AZERTY, Dvorak
    }

    public Layout layout;

    /**
     * Nome commerciale della tastiera.
     */
    public String getName() {
        return name;
    }

    /**
     * Nome commerciale della tastiera.
     *
     * @param name Nuovo nome da impostare.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Produttore della tastiera.
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Produttore della tastiera.
     *
     * @param manufacturer nuovo produttore da impostare.
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Anno di rilascio della tastiera.
     */
    public int getYear() {
        return year;
    }

    /**
     * Anno di rilascio della tastiera.
     *
     * @param year nuovo anno da impostare.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Indica se la tastiera è ergonomica.
     */
    public boolean isErgonomic() {
        return ergonomic;
    }

    /**
     * Indica se la tastiera è ergonomica.
     *
     * @param ergonomic nuovo valore da impostare.
     */
    public void setErgonomic(boolean ergonomic) {
        this.ergonomic = ergonomic;
    }

    /**
     * Indica se la tastiera è retroilluminata.
     */
    public boolean isBacklight() {
        return backlight;
    }

    /**
     * Indica se la tastiera è retroilluminata.
     *
     * @param backlight nuovo valore da impostare.
     */
    public void setBacklight(boolean backlight) {
        this.backlight = backlight;
    }

    /**
     * Tipo di schema della tastiera.
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Tipo di schema della tastiera.
     *
     * @param layout nuovo schema da impostare.
     */
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    /**
     * Id della tastiera.
     *
     * @param id nuovo id da impostare.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Id della tastiera.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Commenti della tastiera.
     *
     * @param comment nuovo commento da aggiungere.
     */
    public void addComment(String comment) {
        this.comments.add(comment);
    }

    /**
     * Commenti della tastiera.
     */
    public ArrayList<String> getComments() {
        return this.comments;
    }
}