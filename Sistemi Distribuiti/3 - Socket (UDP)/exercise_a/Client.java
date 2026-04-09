import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (var socket = new DatagramSocket(); var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Inserisci un messaggio: ");
                var message = scanner.nextLine().strip();
                var payload = message.getBytes();

                var serverAddress = InetAddress.getByName("127.0.0.1");
                var port = 8000;

                // Scrivere qui l'invio del datagramma e la ricezione della risposta.
                // Stampare su schermo l'invio e la risposta!
                var datagram = new DatagramPacket(payload, payload.length, serverAddress, port);
                socket.send(datagram);
                System.out.println("Datagram inviato!");
                System.out.println("In attesa della risposta...");

                //var buf = new byte[1024];
                //var datagramResponse = new DatagramPacket(buf, buf.length);

                // Attendo di ricevere dal server
                socket.receive(datagram);
                var response = new String(datagram.getData(), 0, datagram.getLength());
                System.out.printf("Risposta Ricevuta: \"%s\"\n", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}