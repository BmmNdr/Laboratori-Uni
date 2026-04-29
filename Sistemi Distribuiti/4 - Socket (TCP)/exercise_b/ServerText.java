    import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerText {
    public static void main(String[] args) {
        final var serverPort = 8080;
        final var poolSize = 5;

        try (var serverSocket = new ServerSocket(serverPort);
            ExecutorService pool = Executors.newFixedThreadPool(poolSize)) {
            System.out.println("Server in ascolto...");

            while (true) {
                pool.execute(new Game(serverSocket.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Game implements Runnable {
    private final Socket socket;
    private final int target;

    public Game(Socket socket) {
        this.socket = socket;
        this.target = new Random().nextInt(101);

        System.out.printf("Nuovo client (%s) connesso!\n", socket.getRemoteSocketAddress());
        System.out.printf("Numero da indovinare: %d\n", target);
    }

    @Override
    public void run() {
        try (this.socket;
            var scanner = new Scanner(socket.getInputStream());
            var output = socket.getOutputStream()) {

            while (true) {
                // Attende la guess del Client
                var guess = Integer.parseInt(scanner.nextLine());

                // Valuta e prepara la risposta
                var feedback = "";
                var isGuessed = false;
                if (guess == target) {
                    feedback = "Indovinato!\n";
                    isGuessed = true;
                } else if (guess < target) {
                    feedback = "Troppo basso\n";
                } else if (guess > target) {
                    feedback = "Troppo alto\n";
                }

                // Invio del feedback
                output.write(feedback.getBytes());
                output.flush();

                if (isGuessed) {
                    System.out.printf("Client (%s) terminato!\n", socket.getRemoteSocketAddress());
                    return;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}