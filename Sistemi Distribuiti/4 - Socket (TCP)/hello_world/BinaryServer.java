import java.io.IOException;
import java.net.ServerSocket;

public class BinaryServer {
    public static void main(String[] args) {
        final var port = 8080;
        try (var serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto...");

            while (true) {
                try (var socket = serverSocket.accept()) {
                    System.out.printf("Nuovo client (%s) connesso!\n", socket.getRemoteSocketAddress());

                    var input = socket.getInputStream();
                    var buf = input.readNBytes(4);

                    // Il server può ricevere un buffer lungo meno di quello richiesto
                    // se il client chiude il flusso prima del previsto.
                    if (buf.length != 4) {
                        throw new IOException("4 byte non tutti ricevuti!");
                    }

                    var value = byteArrayToInt(buf, 0);
                    System.out.printf("Valore ricevuto: %d\n", value);
                } catch (Exception ex) {
                    System.err.println("Errore con il client");
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.err.println("Errore del server");
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static int byteArrayToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24) | ((bytes[offset + 1] & 0xFF) << 16) | ((bytes[offset + 2] & 0xFF) << 8) | (bytes[offset + 3] & 0xFF);
    }
}