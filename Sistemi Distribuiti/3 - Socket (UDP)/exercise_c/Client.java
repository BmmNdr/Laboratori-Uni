import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final var serverPort = 8080;

        try (var socket = new DatagramSocket(); var scanner = new Scanner(System.in)) {
            var serverAddress = InetAddress.getByName("127.0.0.1");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}