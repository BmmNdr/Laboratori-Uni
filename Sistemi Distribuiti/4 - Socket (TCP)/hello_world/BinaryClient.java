import java.net.Socket;

public class BinaryClient {
    public static void main(String[] args) {
        final var serverAddress = "127.0.0.1";
        final var port = 8080;

        try (var socket = new Socket(serverAddress, port)) {
            System.out.println("Connesso al server");

            var output = socket.getOutputStream();

            System.out.printf("Invio del numero %d al server...\n", socket.getLocalPort());
            var buf = intToByteArray(socket.getLocalPort());
            output.write(buf); // 4 byte!
            output.flush();
            System.out.println("Inviato!");
        } catch (Exception ex) {
            System.err.println("Errore del client");
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    private static byte[] intToByteArray(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }
}