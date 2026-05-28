package it.unimib.sd2026;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

/**
 * Rappresenta la risorsa esposta in "http://localhost:8080/users".
 */
@Path("users")
public class UserResource {
    // Insieme degli utenti.
    //
    // L'uso di ConcurrentHashMap previene problemi di concorrenza che possono
    // accadere se si modifica la mappa in modo concorrente.
    private static ConcurrentMap<Integer, User> users = new ConcurrentHashMap<Integer, User>();

    // Ultimo ID generato per gli utenti.
    //
    // L'uso di AtomicInteger previene problemi di concorrenza in caso di
    // incremento concorrente.
    private static AtomicInteger latestId = new AtomicInteger();

    // Inizializza la mappa degli utenti con qualche elemento. L'uso del blocco
    // static permette l'inizializzazione una sola volta all'avvio del server.
    static {
        var user = new User();
        user.setId(latestId.incrementAndGet());
        user.setName("luc99");
        user.setJoined(LocalDate.of(2026, 5, 20));
        users.putIfAbsent(user.getId(), user);

        user = new User();
        user.setId(latestId.incrementAndGet());
        user.setName("xalma_t");
        user.setJoined(LocalDate.of(2026, 5, 20));
        users.putIfAbsent(user.getId(), user);

        user = new User();
        user.setId(latestId.incrementAndGet());
        user.setName("AutoMod");
        user.setJoined(LocalDate.of(2026, 5, 18));
        users.putIfAbsent(user.getId(), user);

        user = new User();
        user.setId(latestId.incrementAndGet());
        user.setName("__mist_43");
        user.setJoined(LocalDate.of(2026, 5, 21));
        users.putIfAbsent(user.getId(), user);
    }

    /**
     * Risponde alla chiamata GET "/users", restituendo in formato JSON la lista
     * degli utenti salvati.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        // L'uso dell'API Stream di Java permette di fare la conversione da una
        // mappa a una lista di utenti in modo compatto.
        return users.values().stream().collect(Collectors.toList());
    }

    /**
     * Risponde alla chiamata POST "/users".
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        if (user == null || user.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        user.setId(latestId.incrementAndGet());
        user.setJoined(LocalDate.now());
        users.put(user.getId(), user);

        try {
            var uri = new URI("/users/" + user.getId());

            return Response.created(uri).build();
        } catch (URISyntaxException e) {
            System.err.println(e);
            return Response.serverError().build();
        }
    }

    /**
     * Risponde alla chiamata GET "/users/{id}", restituendo in formato JSON le
     * informazioni di un singolo utente.
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        var user = users.get(id);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(user).build();
    }

    /**
     * Risponde alla chiamate PUT "/users/{id}/name", aggiornando il nome
     * dell'utente con l'ID fornito.
     */
    @Path("/{id}/name")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setUserName(@PathParam("id") int id, String body) {
        var user = users.get(id);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        try {
            var jsonb = JsonbBuilder.create();
            var name = jsonb.fromJson(body, String.class);
            user.setName(name);

            return Response.noContent().build();
        } catch (JsonbException e) {
            System.err.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Risponde alla chiamata DELETE "/users/{id}", eliminando l'utente con l'ID
     * fornito nel percorso.
     */
    @Path("/{id}")
    @DELETE
    public Response deleteUser(@PathParam("id") int id) {
        users.remove(id);

        return Response.noContent().build();
    }
}