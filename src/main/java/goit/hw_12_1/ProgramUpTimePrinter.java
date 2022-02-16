package goit.hw_12_1;

public class ProgramUpTimePrinter implements ProgramUpTimePrinterInterface {
    Thread printUpSecThread;
    Thread printEveryFiveSecThread;
    private int upTimeSeconds = 0;


    public ProgramUpTimePrinter() {
        this.printEveryFiveSecThread = new Thread(this::printMessageEveryFiveSeconds);
        this.printUpSecThread = new Thread(this::printUptimeSeconds);
    }

    @Override
    public void exec() {
        printUpSecThread.start();
        printEveryFiveSecThread.start();
    }

    private void printUptimeSeconds() {
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++upTimeSeconds;

            System.out.println("От начала сессии прошло: " + upTimeSeconds + " сек");

            if (upTimeSeconds % 5 == 0) {
                synchronized (printEveryFiveSecThread) {
                    printEveryFiveSecThread.notify();
                }
            }
        }
    }

    private void printMessageEveryFiveSeconds() {
        while (true) {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Прошло 5 секунд");
        }
    }
}
