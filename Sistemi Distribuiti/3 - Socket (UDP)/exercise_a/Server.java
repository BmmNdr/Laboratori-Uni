import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) {
        // Scrivere qui i server. Per ogni datagramma all'interno di un ciclo infinito:
        //      1. Riceve un datagramma ed estrae la stringa,
        //      2. Modifica in maiuscolo la stringa,
        //      3. Invia la risposta.
        
        var port = 8000;
        try (var socket = new DatagramSocket(port)) {
           
            var buf = new byte[1024];
            System.out.println("Server in ascolto...");

            while (true) {

                var datagram = new DatagramPacket(buf, buf.length);
                socket.receive(datagram);

                var message = new String(datagram.getData(), 0, datagram.getLength());
                
                int clientPort = datagram.getPort();
                InetAddress clientIP = datagram.getAddress();
                
                System.out.printf("Datagramma Ricevuto (%s:%d) con messaggio: %s\n",
                                    clientIP.toString(), 
                                    clientPort,
                                    message);

                var payload = message.toUpperCase().getBytes();
                var datagramResponse = new DatagramPacket(payload, payload.length, clientIP, clientPort);
                socket.send(datagramResponse);
            }

        } catch (Exception e) {
           e.printStackTrace();
           System.exit(-1);
        }
    }
}