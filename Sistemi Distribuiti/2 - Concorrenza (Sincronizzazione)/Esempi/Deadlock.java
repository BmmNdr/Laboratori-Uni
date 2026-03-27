class Task implements Runnable {
    private final Object lock1, lock2;

    public Task(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        var name = Thread.currentThread().getName();

        System.out.println(name + ": acquisizione lock1...");
        synchronized (lock1) {
            System.out.println(name + ": lock1 acquisito.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": acquisizione lock2...");
            synchronized (lock2) {
                System.out.println(name + ": lock2 acquisito.");
                System.out.println(name + ": lock1 e lock2 acquisiti, lavoro in corso!");
            }
        }
    }
}


class Deadlock {
    public static void main(String[] args) {
        var lock1 = new Object();
        var lock2 = new Object();

        var taskA = new Task(lock1, lock2);
        var taskB = new Task(lock2, lock1);

        new Thread(taskA).start();
        new Thread(taskB).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}