import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server {
    public static void main(String[] args) {
        final var port = 8080;
        final var random = new Random();
        final Map<Integer, Integer> targetNumbers = new HashMap<>();

        System.out.println("Server in ascolto...");
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            var buff = new byte[1024];

            while (true) {

                var datagram = new DatagramPacket(buff, buff.length);
                serverSocket.receive(datagram);

                var clientPort = datagram.getPort();
                var clientIP = datagram.getAddress();
                var message = new String(datagram.getData(), 0, datagram.getLength());
                var clientGuess = Integer.parseInt(message);

                var toGuess = targetNumbers.getOrDefault(clientPort, 0);
                if (toGuess == 0){
                    toGuess = random.nextInt(101);
                    targetNumbers.put(clientPort, toGuess);
                }
            
                System.out.printf("Server: %d\tClient (%d): %d\n", toGuess, clientPort, clientGuess);

                if (clientGuess > toGuess)
                    message = "Troppo Alto";
                else if (clientGuess < toGuess)
                    message = "Troppo Basso";
                else {
                    message = "Indovinato";
                    targetNumbers.remove(clientPort);
                }

                var payload = message.getBytes();
                datagram = new DatagramPacket(payload, payload.length, clientIP, clientPort);
                serverSocket.send(datagram);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}