import java.util.Scanner;

public class HelloWorldThreadState {
    public static void main(String[] args) throws Exception {
        try (var sc = new Scanner(System.in)) {
            System.out.println("Press enter to continue: ");
            sc.nextLine();
        }


        final Object lock = new Object();

        // Thread in NEW state (created but not started)
        var newThread = new Thread(new Runnable() {
            public void run() {
                // do nothing
            }
        });
        System.out.println("newThread: " + newThread.getState()); // NEW

        // Thread that will be RUNNABLE
        var runnableThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    // Busy wait to stay RUNNABLE
                }
            }
        });
        runnableThread.start();
        Thread.sleep(50);
        System.out.println("runnableThread: " + runnableThread.getState()); // RUNNABLE

        // Thread that will be BLOCKED
        var blockedThread = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    // Will enter if lock is released
                }
            }
        });

        // Thread to hold the lock so blockedThread gets BLOCKED
        var lockingThread = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(500); // Hold lock
                    } catch (InterruptedException ignored) {}
                }
            }
        });
        lockingThread.start();
        Thread.sleep(100); // Ensure lockingThread acquires lock
        blockedThread.start();
        Thread.sleep(100); // Give blockedThread time to block
        System.out.println("blockedThread: " + blockedThread.getState()); // BLOCKED

        // Thread that will be WAITING
        var waitingThread = new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {}
                }
            }
        });
        waitingThread.start();
        Thread.sleep(100);
        System.out.println("waitingThread: " + waitingThread.getState()); // WAITING

        // Thread that will be TIMED_WAITING using sleep
        var timedWaitingThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        });
        timedWaitingThread.start();
        Thread.sleep(100);
        System.out.println("timedWaitingThread: " + timedWaitingThread.getState()); // TIMED_WAITING

        // Thread that will be TERMINATED
        var terminatedThread = new Thread(new Runnable() {
            public void run() {
                // do nothing
            }
        });
        terminatedThread.start();
        terminatedThread.join();
        System.out.println("terminatedThread: " + terminatedThread.getState()); // TERMINATED

        // Cleanup
        runnableThread.interrupt();
        lockingThread.join();
        blockedThread.interrupt();
        timedWaitingThread.join();
    }
}