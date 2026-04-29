import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final var serverAddress = "127.0.0.1";
        final var serverPort = 8080;

        System.out.println("Calcolatore in avvio...");
        try (var socket = new Socket(serverAddress, serverPort);
             var scanner = new Scanner(System.in)) {
            System.out.println("Connesso al server.");

            while (true) {
                System.out.print("> ");
                var rawCommand = scanner.nextLine();
                
                // ...
            }
        } catch (Exception e) {
            System.err.println("Errore del client: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
