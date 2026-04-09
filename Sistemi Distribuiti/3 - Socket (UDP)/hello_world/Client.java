import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try (var socket = new DatagramSocket()) {
            var message = "Hello World!";
            var payload = message.getBytes();

            var serverAddress = InetAddress.getByName("127.0.0.1");
            var port = 8080;

            var datagram = new DatagramPacket(payload, payload.length, serverAddress, port);
            socket.send(datagram);

            System.out.println("Datagramma inviato!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
