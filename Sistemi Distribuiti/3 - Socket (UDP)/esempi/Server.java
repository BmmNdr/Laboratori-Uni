// Singolo datagramma ricevuto o inviato
import java.net.DatagramPacket;

// Socket per invio e ricezione di datagramma
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        var port = 8080;

        // Scelgo la porta da assegnare alla socket.
        try (var socket = new DatagramSocket(port)) {
            var buf = new byte[1024];

            System.out.println("Server in ascolto...");
            while (true) {

                // Costruttore per datagramma in ricezione: alloca solamente un buffer di dimensione fissa
                var datagram = new DatagramPacket(buf, buf.length);

                // Blocca il processo finchè non si riceve un datagramma
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