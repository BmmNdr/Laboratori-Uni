# API per la rubrica telefonica

La API per la rubrica telefonica gestisce un elenco di contatti, in cui ognuno è
così definito:

* `id`: un identificativo univoco (intero);
* `firstName`: nome (intero);
* `lastName`: cognome (intero);
* `phoneNumbers`: una mappa (stringa -> stringa) che contiene numeri di telefono
  con associato un'etichetta opzionale.
* `email`: email (stringa, opzionale);
* `birthday`: data di nascita (stringa, opzionale).

Un contatto deve avere almeno un numero di telefono.

Tutti gli endpoint accettano e restituiscono dati in formato `application/json`.

**Attenzione**: specifico solo i codici di stato HTTP restituiti che io devo
implementare. Quelli fatti in automatico dal framework non li specifico.

## POST /collezioneEsempio

Aggiunge una nuova risorsa alla collezione. Nella richiesta ci deve essere
l'header `Content-Type: application/json`. Accetta un body in formato JSON con
una risorsa. Alcuni campi possono essere omessi (tipo `id` che viene generato
dal server), ma ci deve essere almeno `XXX` e un campo YYY.

Restituisce i seguenti codici di stato HTTP:

* `201 Created`: la risorsa è stata creata e l'URL è nell'header `Location`.
* `400 Bad Request`: c'è un errore del client (JSON, campo mancante o altro).
* `415 Unsupported Media Type`: se l'header previsto è assente o non configurato
  come JSON.

## GET /collezioneEsempio/{id}

Restituisce il contatto con l'id `id`.

Restituisce i seguenti codici di stato HTTP:

* `200 OK`: la risorsa esiste ed è stata restituita.
* `400 Not Found`: la risorsa non esiste.

## DELETE /collezioneEsempio/{id}

Rimuove il contatto con l'identificativo `id`.

Restituisce i seguenti codici di stato HTTP:

* `204 No Content`: il contatto è stato eliminato.
* `400 Not Found`: l'identificativo `id` non esiste.
