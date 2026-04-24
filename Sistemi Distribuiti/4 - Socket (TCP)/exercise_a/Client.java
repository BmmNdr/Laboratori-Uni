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

                // Continuare da qui.
            }
        } catch (Exception ex) {
            System.err.println("Errore del server");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}