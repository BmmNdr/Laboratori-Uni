import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ExampleServer {
    public static void main(String[] args) {
        final var port = 8080;
        try (var serverSocket = new ServerSocket(port)) {
            System.out.printf("Server in ascolto alla porta %d\n", port);

            while (true) {
                var socket = serverSocket.accept();
                System.out.printf("Nuovo client (%s) connesso!\n", socket.getRemoteSocketAddress());

                processClient(socket);
                
                try {
                    Thread.sleep(5000); // 5 secondi.
                } catch (InterruptedException e) {
                    System.err.println("Sleep interrotto: " + e.getMessage());
                }
                
            }
        } catch (Exception ex) {
            System.err.println("Errore del server");
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static void processClient(Socket socket) {
        try (socket;
             var scanner = new Scanner(socket.getInputStream())) {
            var output = socket.getOutputStream();

            System.out.println("Invio delle tre stringhe al client!");
            output.write("Hello\n".getBytes());
            output.write("World\n".getBytes());
            output.write("!\n".getBytes());
            output.flush();

            // Aspetto la risposta del client.
            System.out.println("In attesa di risposta dal client...");
            var reply = scanner.nextLine();
            System.out.printf("Il client ha risposto con \"%s\"\n", reply);

        } catch (Exception ex) {
            System.err.printf("Errore con il client \"%s\"\n", socket.getRemoteSocketAddress());
            ex.printStackTrace();
        }
    }
}