package goit.hw_12_1;

public class ProgramUpTimePrinter implements ProgramUpTimePrinterInterface {
    private int upTimeSeconds = 0;
    private volatile boolean isFiveSeconds = false;

    @Override
    public void exec() {
        new Thread(this::printUptimeSeconds).start();
        new Thread(this::printMessageEveryFiveSeconds).start();
    }

    private void printMessageEveryFiveSeconds() {
        while (true) {
            if (isFiveSeconds) {
                System.out.println("Прошло 5 секунд");

                isFiveSeconds = false;
            }
        }
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
                isFiveSeconds = true;
            }
        }
    }
}
