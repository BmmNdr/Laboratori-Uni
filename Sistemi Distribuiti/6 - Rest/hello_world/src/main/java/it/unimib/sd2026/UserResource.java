package it.unimib.sd2026;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Rappresenta la risorsa "users" in "http://localhost:8080/users".
 *
 * A ogni richiesta ricevuta viene creata una nuova istanza, è il comportamento
 * predefinito di Jackarta JAX-RS. Posso evitare ciò con @Singleton.
 */
@Path("users")
public class UserResource {

    /*
     * Lista degli utenti. Viene salvata come attributo statico della classe
     * perché a ogni richiesta HTTP viene istanziata una nuova istanza della
     * classe associata.
     */
    static private List<User> users = new ArrayList<User>();

    // Inizializzazione della lista degli utenti.
    static {
        var user = new User();
        user.setId(1);
        user.setName("Luca");
        users.add(user);

        user = new User();
        user.setId(2);
        user.setName("Laura");
        user.setAdmin(true);
        users.add(user);
    }

    /**
     * Risponde a GET "/users" in formato "text/plain".
     * 
     * Restituisce la lista degli utenti.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers() {
        var buf = new StringBuffer();
        buf.append(String.format("[size = %d, elements = [", users.size()));

        // Ho necessità prima di convertire la lista di utenti in una lista di
        // stringhe, una per ogni utente.
	List<String> userFormatted = new ArrayList<>(users.size());
	synchronized (users) {
        for (var user : users) {
            userFormatted.add(user.toString());
		}
	}
	buf.append(String.join(", ", userFormatted));
	buf.append("]]");


        return buf.toString();
    }

    /**
     * Risponde a GET "/users" in formato "application/json".
     * 
     * Restituisce la lista degli utenti.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsersJson() {
        /*
         * Chi è che converte "users" in formato JSON? Jersey, l'implementazione
         * di Jakarta JAX-RS, usa in automatico il modulo per convertire da (in)
         * oggetto Java in (da) Json (e viceversa).
         */
        return users;
    }

    /**
     * Risponde a GET "/users/{id}" in formato "text/plain".
     * 
     * Restituisce le informazioni dell'utente con l'ID fornito. Se non
     * trova l'utente restituisce lo stato HTTP 404.
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUser(@PathParam("id") int id) {
        synchronized (users) {
            for (var user : users) {
                if (user.getId() == id) {
                    // Il metodo "ok" non fa altro che restituire l'oggetto con il
                    // codice HTTP 200 OK.
                    //
                    // Notare che alla fine è presente la chiamata a build() che
                    // costruisce e restituisce la risposta da inviare al client.
                    return Response.ok(user.toString()).build();
                }
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Risponde a GET "/users/{id}" in formato "application/json".
     * 
     * Restituisce le informazioni dell'utente con l'ID fornito. Se non
     * trova l'utente restituisce lo stato HTTP 404.
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserJson(@PathParam("id") int id) {
        synchronized (users) {
            for (var user : users) {
                if (user.getId() == id) {
                    return Response.ok(user).build();
                }
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /*
     * Risponde a POST "/users" in formato "application/json".
     * 
     * In caso di successo restituisce lo stato HTTP 201, 400 se il JSON è mal
     * formato, altrimenti 500.
     * 
     * Che succede se il JSON in partenza è mal formato? Jersey lancia un'eccezione
     * che viene catturata da JsonParsingException (vedere l'altro file).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        /*
         * Il client invia un JSON arbitrario, Jersey cerca comunque di
         * convertirlo nell'oggetto User come indicato. Attributi che non
         * fanno parte di User vengono scartati, attributi di User che non
         * compaiono in JSON hanno "null" o valori di default.
         */
        if (user.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // L'ID è impostato lato server.
        user.setId(users.size() + 1);
        synchronized (users) {
            users.add(user);
        }

        try {
            var uri = new URI("/users/" + user.getId());

            return Response.created(uri).build();
        } catch (URISyntaxException e) {
            System.out.println(e);
            return Response.serverError().build();
        }
    }

    /*
     * Risponde a PUT "/users/{id}" in formato "application/json".
     * 
     * In caso di successo restituisce lo stato HTTP 200 o 400 se il JSON è mal
     * formato.
     * 
     * La differenza con addUser(), oltre al metodo HTTP, è che in questo caso
     * si manipola il JSON direttamente come stringa invece di lasciar fare a
     * Jersey la deserializzazione.
     */
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setUser(@PathParam("id") int id, String rawUser) {
        User oldUser = null;

        // Cerco l'utente con l'ID fornito.
        synchronized (users) {
            for (var user : users) {
                if (user.getId() == id) {
                    oldUser = user;
                    break;
                }
            }
        }

        // Se non esiste l'utente, restituisco 404.
        if (oldUser == null)
            return Response.status(Status.NOT_FOUND).build();

        /*
         * Faccio ora il parsing del JSON, mi serve un oggetto che implementa la specifica
         * Jakarta JSON-B. Jersey usa Yasson che implementa JSON-B.
         */
        var jsonb = JsonbBuilder.create();

        try {
            var user = jsonb.fromJson(rawUser, User.class);

            // Sovrascrivo il vecchio utente con i nuovi dati.
            synchronized (oldUser) {
                oldUser.setName(user.getName());
                oldUser.setAdmin(user.isAdmin());
            }

            return Response.ok(oldUser).build();
        } catch (JsonbException e) {
            // Se il JSON è malformato restituisco 400.
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
