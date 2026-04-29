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
                // Scompongo il messaggio dell'utente
                var arrayMsg = scanner.nextLine().split("\\s+");
                var reply = "";

                if (arrayMsg[0].equals("GET_TIME")) {
                    ZoneId timezone = null;
                    // Messaggio richiesta time
                    if (arrayMsg.length > 1){
                        // E' presente il parametro <timezone> 
                        try {
                            timezone = ZoneId.of(arrayMsg[1]);
                            reply = ZonedDateTime.now(timezone).toString();
                        } catch (Exception e) {
                            // Timezone invalida
                            reply = "Errore: fuso orario sconosciuto";
                        }
                    }
                    else {
                        // Non sono presenti parametri
                        timezone = ZoneId.systemDefault();
                        reply = ZonedDateTime.now(timezone).toString();
                    }
                }
                else if (arrayMsg[0].equals("QUIT")){
                    // Messaggio di chiusura
                    System.out.printf("Client (%s) disconnesso!\n", socket.getRemoteSocketAddress());
                    return;
                }
                else {
                    // Messaggio non riconosciuto
                    reply = "Errore: messaggio sconosciuto!";
                }

                // Invio dei messaggi al Client
                output.write((reply+"\n").getBytes());
                output.flush();
            }
        } catch (Exception ex) {
            System.err.printf("Errore con il client \"%s\"\n", socket.getRemoteSocketAddress());
            ex.printStackTrace();
        }
    }
}