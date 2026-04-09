import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        // Scrivere qui i server. Per ogni datagramma all'interno di un ciclo infinito:
        //      1. Riceve un datagramma ed estrae la stringa,
        //      2. Modifica in maiuscolo la stringa,
        //      3. Invia la risposta.

        // Stabilire una porta e usare il seguente costrutto:
        // try (var sockert = new DatagramSocket(...)) {
        //    ...
        // } catch (Exception e) {
        //    e.printStackTrace();
        //    System.exit(-1);
        // }
    }
}