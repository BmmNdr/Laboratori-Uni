public class WaitNotify {
    public static void main(String[] args) {
        var lock = new Object();

        var waiting = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println("WAIT: chiamata wait()...");
                        lock.wait();
                        System.out.println("WAIT: chiamata wait() conclusa!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        var notifying = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    System.out.println("NOTIFY: invio notifica con notify()...");
                    lock.notify();
                    System.out.println("NOTIFY: notifica inviata!");
                }
            }
        });

        waiting.start();
        notifying.start();
    }
}