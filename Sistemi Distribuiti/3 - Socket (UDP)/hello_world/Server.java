import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        var port = 8080;

        try (var socket = new DatagramSocket(port)) {
            var buf = new byte[1024];

            System.out.println("Server in ascolto...");
            while (true) {

                var datagram = new DatagramPacket(buf, buf.length);
                socket.receive(datagram);
                
                var message = new String(datagram.getData(),0,  datagram.getLength());
                System.out.printf("Datagramma ricevuto (porta %d) con messaggio: \"%s\"\n",
                                  datagram.getPort(),
                                  message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}