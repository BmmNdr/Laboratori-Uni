import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientText {
    public static void main(String[] args) {
        final var serverPort = 8080;
        
        try (var socket = new DatagramSocket(); var scanner = new Scanner(System.in)) {
            var targetFound = false;
            final var serverAddress = InetAddress.getByName("127.0.0.1");

            while (!targetFound) { // Ciclo infinito finché l'utente non invia CTRl+C.
                System.out.print("Inserisci il numero ipotizzato: ");
                var guess = scanner.nextInt();

                // Converte in byte il numero e lo invia al server.
                //  Viene usata la codifica di default che è UTF-8.
                var sendBuf = Integer.toString(guess).getBytes();
                var sendDatagram = new DatagramPacket(sendBuf, sendBuf.length, serverAddress, serverPort);
                socket.send(sendDatagram);

                // Riceve il feedback dal server.
                var replyBuf = new byte[100];
                var replyDatagram = new DatagramPacket(replyBuf, replyBuf.length);
                socket.receive(replyDatagram);

                // Converte il buffer ricevuto in stringa. Lo stampa subito su schermo.
                var feedback = new String(replyDatagram.getData(), 0, replyDatagram.getLength());
                System.out.println(feedback);

                // Se l'utente ha indovinato si interrompe il gioco.
                if (feedback.toLowerCase().contains("indovinato")) {
                    targetFound = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}