package it.unimib.sd2026;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/*")
public class RestApplication extends Application {
    // Non è necessario aggiungere altro perché Jersey all'avvio
    // scansiona in automatico tutte le classi alla ricerca di quelle
    // con l'annotazione @Path e le aggiunge all'applicazione Web.
}