import java.net.Socket;
import java.util.Scanner;

public class ExampleClient {
    public static void main(String[] args) {
        final var serverAddress = "127.0.0.1";
        final var serverPort = 8080;
        final var messages = 3; // Il client si aspetta tre stringhe seguita da newline.

        // Viene creato il socket e subito anche lo scanner che servirà per
        // ricevere testo linea per linea.
        try (var socket = new Socket(serverAddress, serverPort);
             var scanner = new Scanner(socket.getInputStream())) {

            System.out.println("In attesa di ricezione dal server...");
            for (int index = 0; index < messages; index++) {
                // nextLine() blocca il processo perché internamente chiama "read" di
                // InputStream che è bloccante, aspetta dati dal server.
                var message = scanner.nextLine();

                System.out.printf("Stringa ricevuta dal server: \"%s\"\n", message);
            }

            // OutputStream viene chiuso in automatico quando si chiude il socket.
            var output = socket.getOutputStream();
            output.write("OK!\n".getBytes());
            output.flush();
        } catch (Exception ex) {
            System.err.println("Errore del client");
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}