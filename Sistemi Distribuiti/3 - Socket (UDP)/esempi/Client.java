import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        // Il SO sceglie una porta temporanea
        try (var socket = new DatagramSocket()) {
            var message = "Hello World!";
            var payload = message.getBytes();

            // Trasforma una stringa in una rappresentazione di un IP
            var serverAddress = InetAddress.getByName("127.0.0.1");
            var port = 8080;

            // Costruttore per datagramma da inviare: passo anche IP e Porta di destinazione
            var datagram = new DatagramPacket(payload, payload.length, serverAddress, port);
            
            // Invia un datagramma
            socket.send(datagram);

            System.out.println("Datagramma inviato!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
