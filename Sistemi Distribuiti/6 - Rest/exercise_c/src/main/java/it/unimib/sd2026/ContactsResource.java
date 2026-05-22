package it.unimib.sd2026;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/*
 * Espone la rubrica telefonica in "http://localhost:8080/CAMBIAMI".
 */
@Path("CAMBIAMI")
public class ContactsResource {

    // Mappa che salva i contatti nella rubrica (per ID).
    static private Map<Integer, Contact> contacts = new ConcurrentHashMap<Integer, Contact>();
    
    // Primo ID disponibile. Non ci sono mai due contatti con lo stesso ID.
    static private AtomicInteger lastId = new AtomicInteger(0);

    static {
        // Aggiungo qualche contatto di esempio iniziale.
        var contact = new Contact();
        contact.setId(lastId.getAndIncrement());
        contact.setFirstName("Maria Luisa");
        contact.setLastName("Assunta");
        contact.setPhoneNumbers(new ConcurrentHashMap<String, String>());
        contact.getPhoneNumbers().put("3232423223", "Casa");
        // contact.setEmail("null"); // No email.
        contact.setBirthday(LocalDate.of(1997, 12, 15));
        contacts.put(contact.getId(), contact);

        contact = new Contact();
        contact.setId(lastId.getAndIncrement());
        contact.setFirstName("Alessandro");
        contact.setLastName("Giroglio");
        contact.setPhoneNumbers(new ConcurrentHashMap<String, String>());
        contact.getPhoneNumbers().put("3221344121", "");
        contact.getPhoneNumbers().put("254221", "Lavoro");
        contact.setEmail("a.giro@gmail.com"); // No email.
        // contact.setBirthday(LocalDate.of(1997, 12, 15)); // No compleanno.
        contacts.put(contact.getId(), contact);
    }
}
