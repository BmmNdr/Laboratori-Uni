import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final var serverPort = 8080;

        System.out.println("Server in avvio...");
        try (var serverSocket = new ServerSocket(serverPort)) {
            while (true) {
                var socket = serverSocket.accept();
                System.out.printf("Client (%d) connesso!", socket.getRemoteSocketAddress());
                
                processClient(socket);
            }
        } catch (Exception e) {
            System.err.println("Errore del server: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void processClient(Socket socket) {
        try (socket) {
            // ...
        } catch (Exception e) {
            System.err.println("Errore del server durante la gestione del client: " + e.getMessage());
            e.printStackTrace();
            // Si prosegue con il prossimo client.
        }
    }
}