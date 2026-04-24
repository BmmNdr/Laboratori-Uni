import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        final var port = 8080;
        try (var serverSocket = new ServerSocket(port)) {
            System.out.printf("Server in ascolto alla porta %d\n", port);

            while (true) {
                // Blocca finché non riceve una nuova connessione.
                var socket = serverSocket.accept();
                System.out.printf("Nuovo client (%s) connesso!\n", socket.getRemoteSocketAddress());

                processClient(socket);
            }
        } catch (Exception ex) {
            System.err.println("Errore del server");
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static void processClient(Socket socket) {
        try (socket; // Chiude automaticamente il socket alla fine.
             var scanner = new Scanner(socket.getInputStream())) {
            var output = socket.getOutputStream();

            while (true) {
                // Continuare da qui.
            }
        } catch (Exception ex) {
            System.err.printf("Errore con il client \"%s\"\n", socket.getRemoteSocketAddress());
            ex.printStackTrace();
        }
    }
}