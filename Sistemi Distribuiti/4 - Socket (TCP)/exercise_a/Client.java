import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final var serverAddress = "127.0.0.1";
        final var serverPort = 8080;

        // Due scanner: uno per lo standard input dall'utente e uno per il socket.
        try (var socket = new Socket(serverAddress, serverPort);
             var scannerStdin = new Scanner(System.in);
             var scannerSocket = new Scanner(socket.getInputStream())) {
            var output = socket.getOutputStream();

            while (true) {
                System.out.print("Inserisci un fuso orario (\"Esci\" per uscire): ");
                var timezone = scannerStdin.nextLine();

                var msg = "";

                if (timezone.equals("Esci")) 
                    // Inviare messaggio di chiusura
                    msg = "QUIT\n";
                else 
                    // Inviare richiesta timezone
                    msg = "GET_TIME " + timezone + "\n";

                // Invio dei messaggi al Server
                output.write(msg.getBytes());
                output.flush();

                // In caso di messaggio di chiusura non attende risposta
                if (msg.contains("QUIT"))
                    return;

                // Attesa di risposta dal Server
                var response = scannerSocket.nextLine();
                System.out.printf("Stringa ricevuta dal server: \"%s\"\n", response);
            }
        } catch (Exception ex) {
            System.err.println("Errore del server");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}