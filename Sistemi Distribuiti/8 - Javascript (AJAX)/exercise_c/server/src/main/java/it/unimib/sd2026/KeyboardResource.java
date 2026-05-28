package it.unimib.sd2026;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.JsonException;
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

/**
 * Rappresenta la risorsa "keyboards" in "http://localhost:8080/keyboards".
 */
@Path("keyboards")
public class KeyboardResource {

    // Mappa che associa l'id della tastiera alla tastiera stessa.
    static private Map<Integer, Keyboard> keyboards = new HashMap<Integer, Keyboard>();

    // Mappa che salva l'ultimo ID inserito per i commenti in una tastiera (id).
    static private Map<Integer, Integer> commentsLastId = new HashMap<Integer, Integer>();

    static private int lastId;

    // Inizializzazione della mappa delle tastiere.
    static {
        var kb = new Keyboard();
        kb.setId(lastId++);
        kb.setName("K55 RGB PRO");
        kb.setManufacturer("Corsair");
        kb.setYear(2023);
        kb.setErgonomic(true);
        kb.setBacklight(true);
        kb.setLayout(Keyboard.Layout.QWERTY);
        kb.setComments(new HashMap<Integer, Comment>());
        commentsLastId.put(kb.getId(), 0);
        keyboards.put(kb.getId(), kb);

        kb = new Keyboard();
        kb.setId(lastId++);
        kb.setName("K30");
        kb.setManufacturer("Corsair");
        kb.setYear(2020);
        kb.setErgonomic(false);
        kb.setBacklight(false);
        kb.setLayout(Keyboard.Layout.QWERTY);
        kb.setComments(new HashMap<Integer, Comment>());
        commentsLastId.put(kb.getId(), 0);
        keyboards.put(kb.getId(), kb);

        kb = new Keyboard();
        kb.setId(lastId++);
        kb.setName("G213");
        kb.setManufacturer("Logitech");
        kb.setYear(2023);
        kb.setErgonomic(false);
        kb.setBacklight(true);
        kb.setLayout(Keyboard.Layout.Dvorak);
        kb.setComments(new HashMap<Integer, Comment>());
        commentsLastId.put(kb.getId(), 0);
        keyboards.put(kb.getId(), kb);
    }

    /**
     * Implementazione di GET "/keyboards".
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, Keyboard> getKeyboards() {
        return keyboards;
    }

    /**
     * Implementazione di POST "/keyboards".
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addKeyboard(Keyboard kb) {
        // L'ID viene generato dal server.
        kb.setId(lastId++);
        // Bisogna inizializzare una mappa di commenti vuota.
        kb.setComments(new HashMap<Integer, Comment>());
        commentsLastId.put(kb.getId(), 0);
        keyboards.put(kb.getId(), kb);

        try {
            var uri = new URI("/keyboards/" + kb.getId());

            return Response.created(uri).build();
        } catch (URISyntaxException e) {
            System.out.println(e);
            return Response.serverError().build();
        }
    }

    /**
     * Implementazione di GET "/keyboards/{id}".
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKeyboard(@PathParam("id") int id) {
        var kb = keyboards.get(id);
        if (kb == null)
            return Response.status(Status.NOT_FOUND).build();

        return Response.ok(kb).build();
    }

    /**
     * Implementazione di DELETE "/keyboards/{id}".
     */
    @Path("/{id}")
    @DELETE
    public Response deleteKeyboard(@PathParam("id") int id) {
        // La risorsa può non essere esistente, non è un problema.
        keyboards.remove(id);
        return Response.noContent().build();
    }

    /**
     * Implementazione di POST "/keyboards/{id}/comments".
     */
    @Path("/{id}/comments")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(@PathParam("id") int id, String rawContent) {
        // Cerco la tastiera, se non la trovo ho 404 Not Found.
        var keyboard = keyboards.get(id);
        if (keyboard == null)
            return Response.status(Status.NOT_FOUND).build();

        // Nuovo commento da aggiungere alla tastiera
        var comment = new Comment();

        /*
         * C'è un problema: il parametro rawContent è di tipo String, Jersey
         * però interpreta la stringa non come singola stringa JSON (es. "ciao")
         * ma come intero contenuto del body come stringa.
         * 
         * Soluzione: va fatto il parsing a mano della stringa.
         */
        try {
            var jsonb = JsonbBuilder.create(); // Parser JSON.
            var content = jsonb.fromJson(rawContent, String.class);
            comment.setContent(content);
        } catch (JsonbException e) {
            // Se il JSON è malformato restituisco 400.
            System.out.println(e);
            return Response.status(Status.BAD_REQUEST).build();
        }

        // Genero l'ID lato server.
        var commentId = commentsLastId.get(id);
        comment.setId(commentId);
        commentsLastId.put(id, ++commentId);
        
        keyboard.getComments().put(comment.getId(), comment);

        /*
         * Mi trovo in una POST: in caso di successo restituisco 201 Created con
         * l'header "Location" che ha l'URL al commento appena creato.
         */
        try {
            var uri = new URI(String.format("/keyboards/%d/comments/%d",
                        keyboard.getId(), comment.getId()));

            return Response.created(uri).build();
        } catch (URISyntaxException e) {
            System.out.println(e);
            return Response.serverError().build();
        }
    }

    /**
     * Implementazione di GET "/keyboards/{id}/comments/{comment_id}".
     */
    @Path("/{id}/comments/{comment_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@PathParam("id") int keyboardId, @PathParam("comment_id") int commentId) {
        // Cerco la tastiera, se non la trovo ho 404 Not Found. 
        var keyboard = keyboards.get(keyboardId);
        if (keyboard == null)
            return Response.status(Status.NOT_FOUND).build();

        // Cerco il commento e lo restituisco.
        var comment = keyboard.getComments().get(commentId);
        if (comment != null)
            return Response.ok(comment).build();

        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * Implementazione di DELETE "/keyboards/{id}/comments/{comment_id}".
     */
    @Path("/{id}/comments/{comment_id}")
    @DELETE
    public Response deleteComment(@PathParam("id") int keyboardId, @PathParam("comment_id") int commentId) {
        // Cerco la tastiera, se non esiste restituisco 404 Not Found.
        var keyboard = keyboards.get(keyboardId);
        if (keyboard == null)
            return Response.status(Status.NOT_FOUND).build();

        // Rimuovo il commento. Se non esiste non è un problema.
        keyboard.getComments().remove(commentId);
        return Response.noContent().build();
    }

    /**
     * Implementazione di GET "/keyboards/{id}/{property}".
     */
    @Path("/{id}/{property}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProperty(@PathParam("id") int id, @PathParam("property") String property) {
        // Cerco la tastiera, se non esiste restituisco 404 Not Found.
        var keyboard = keyboards.get(id);
        if (keyboard == null)
            return Response.status(Status.NOT_FOUND).build();

        var jsonb = JsonbBuilder.create();

        /*
         * In base alla proprietà desiderata restituisco il valore in formato
         * JSON (la conversion e in JSON è fatta in automatico da Jersey).
         */
        switch (property) {
            case "id":
                return Response.ok(id).build();
            case "name":
                //return Response.ok(keyboard.getName()).build();
                return Response.ok(jsonb.toJson(keyboard.getName())).build();
            case "manufacturer":
                return Response.ok(jsonb.toJson(keyboard.getManufacturer())).build();
            case "year":
                return Response.ok(keyboard.getYear()).build();
            case "ergonomic":
                return Response.ok(keyboard.isErgonomic()).build();
            case "backlight":
                return Response.ok(keyboard.isBacklight()).build();
            case "layout":
                return Response.ok(keyboard.getLayout()).build();
            case "comments":
                return Response.ok(keyboard.getComments()).build();
            default: // Proprietà non trovata.
                return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * Implementazione di GET "/keyboards/{id}/{property}".
     */
    @Path("/{id}/{property}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putProperty(@PathParam("id") int id, @PathParam("property") String property, String body) {
        var keyboard = keyboards.get(id);
        if (keyboard == null)
            return Response.status(Status.NOT_FOUND).build();

        try {
            var jsonb = JsonbBuilder.create(); // Parser JSON.
            switch (property) {
                case "comments":
                case "id":
                    // Per comments e id non permetto la sovrascrittura.
                    return Response.status(Status.METHOD_NOT_ALLOWED).build();
                case "name":
                    var name = jsonb.fromJson(body, String.class);
                    keyboard.setName(name);
                    return Response.noContent().build();
                case "manufacturer":
                    var manufacturer = jsonb.fromJson(body, String.class);
                    keyboard.setManufacturer(manufacturer);
                    return Response.noContent().build();
                case "year":
                    var year = jsonb.fromJson(body, Integer.class);
                    keyboard.setYear(year);
                    return Response.noContent().build();
                case "ergonomic":
                    var ergonomic = jsonb.fromJson(body, Boolean.class);
                    keyboard.setErgonomic(ergonomic);
                    return Response.noContent().build();
                case "backlight":
                    var backlight = jsonb.fromJson(body, Boolean.class);
                    keyboard.setErgonomic(backlight);
                    return Response.noContent().build();
                case "layout":
                    var layout = jsonb.fromJson(body, Keyboard.Layout.class);
                    keyboard.setLayout(layout);
                    return Response.noContent().build();
                default: // Proprietà non trovata.
                    return Response.status(Status.NOT_FOUND).build();
            }
        } catch (JsonException e) {
            // Se il JSON è malformato restituisco 400.
            System.out.println(e);
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}