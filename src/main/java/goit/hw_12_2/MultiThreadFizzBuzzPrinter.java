package goit.hw_12_2;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiThreadFizzBuzzPrinter {
    private final int maxNumber;
    private final CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();

    public MultiThreadFizzBuzzPrinter(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void print() {
        ArrayList<ProcessorThread> threads = createThreads();

        threads.forEach(Thread::start);

        for (int i = 1; i <= maxNumber; i++) {
            for (ProcessorThread thread : threads) {
                thread.doProcess(i);
            }

            while (true) {
                int processedCount = 0;
                for (ProcessorThread thread : threads) {
                    if (thread.isProcessed()) {
                        processedCount++;
                    }
                }
                if (processedCount == threads.size()) {
                    break;
                }
            }
        }

        threads.forEach(Thread::interrupt);

        System.out.println(String.join(", ", result));
    }

    private ArrayList<ProcessorThread> createThreads() {
        ArrayList<ProcessorThread> threads = new ArrayList<>();

        threads.add(new ProcessorThread(this::fizz));
        threads.add(new ProcessorThread(this::buzz));
        threads.add(new ProcessorThread(this::fizzBuzz));
        threads.add(new ProcessorThread(this::number));

        return threads;
    }

    public void fizz(int n) {
        if (n % 3 == 0 && n % 5 != 0) {
            result.add("fizz");
        }
    }

    public void buzz(int n) {
        if (n % 5 == 0 && n % 3 != 0) {
            result.add("buzz");
        }
    }

    public void fizzBuzz(int n) {
        if (n % 15 == 0) {
            result.add("fizzBuzz");
        }
    }

    public void number(int n) {
        if ((n % 3 != 0 && n % 5 != 0)) {
            result.add(String.valueOf(n));
        }
    }
}
