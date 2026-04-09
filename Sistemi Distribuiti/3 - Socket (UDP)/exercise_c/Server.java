import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server {
    public static void main(String[] args) {
        final var port = 8080;
        final var random = new Random();

        System.out.println("Server in ascolto...");
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            var targetNumber = random.nextInt(101); // 0-100.
            while (true) {
 
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}