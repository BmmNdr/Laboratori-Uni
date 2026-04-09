import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final var serverPort = 8080;
        
        try (var socket = new DatagramSocket(); var scanner = new Scanner(System.in)) {
            final var serverAddress = InetAddress.getByName("127.0.0.1");

            while (true) {
                System.out.print("Inserisci un numero da indovinare: ");
                var clientGuess = Integer.toString(scanner.nextInt());

                var payload = clientGuess.getBytes();
                var datagram = new DatagramPacket(payload, payload.length, serverAddress, serverPort);
                socket.send(datagram);

                var buff = new byte[1024];
                datagram = new DatagramPacket(buff, buff.length);
                socket.receive(datagram);

                var message = new String(datagram.getData(), 0, datagram.getLength());
                System.out.printf("Esito: %s\n", message);

                if (message.equals("Indovinato"))
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}