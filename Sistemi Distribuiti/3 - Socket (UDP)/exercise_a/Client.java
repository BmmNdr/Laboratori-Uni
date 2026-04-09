import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (var socket = new DatagramSocket(); var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Inserisci un messaggio: ");
                var message = scanner.nextLine();

                // Scrivere qui l'invio del datagramma e la ricezione della risposta.
                // Stampare su schermo l'invio e la risposta!
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}