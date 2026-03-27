class HelloWorldStarvation {
    private final Object lock = new Object();

    public void run() {
        while (true) {
            synchronized (lock) {
                System.out.printf("%s in esecuzione\n", Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        var starvation = new HelloWorldStarvation();

        var high1 = new Thread(starvation::run, "High-Priority-1");
        var high2 = new Thread(starvation::run, "High-Priority-2");
        var low = new Thread(starvation::run, "Low-Priority");

        high1.setPriority(Thread.MAX_PRIORITY);
        high2.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);

        high1.start();
        high2.start();
        low.start();

        // I primi due thread saranno eseguiti più spesso rispetto al terzo.
        // Come risolvere? Usare sempre la stessa priorità e preferire notifyAll()
        // a notify(). Infine, usare un lock che gestisce l'ordine di arrivo (alto livello).
    }
}