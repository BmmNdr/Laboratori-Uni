import java.net.Socket;
import java.util.Scanner;

public class ClientText {
    public static void main(String[] args) {
        final var serverAddress = "127.0.0.1";
        final var serverPort = 8080;
        
        try (var socket = new Socket(serverAddress, serverPort); 
            var scannerStd = new Scanner(System.in);
            var scanner = new Scanner(socket.getInputStream());
            var output = socket.getOutputStream()) {
            
            var targetFound = false;

            while (!targetFound) { // Ciclo infinito finché l'utente non invia CTRl+C.
                System.out.print("Inserisci il numero ipotizzato: ");
                var guess = scannerStd.nextInt();

                // Invio della guess al Server
                var msg = (Integer.toString(guess) + "\n").getBytes();
                output.write(msg);
                output.flush();

                // Riceve il feedback dal server.
                var response = scanner.nextLine();
                System.out.println("Feedback Server: " + response);

                // Se l'utente ha indovinato si interrompe il gioco.
                if (response.toLowerCase().contains("indovinato")) {
                    targetFound = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}