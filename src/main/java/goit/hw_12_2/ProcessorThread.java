package goit.hw_12_2;

import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessorThread extends Thread {
    private final AtomicBoolean processed = new AtomicBoolean(true);
    private final ProcessorInterface processor;
    private int currentNumber;

    public ProcessorThread(ProcessorInterface processor) {
        this.processor = processor;
    }

    public boolean isProcessed() {
        return this.processed.get();
    }

    public void doProcess(int n) {
        this.currentNumber = n;
        this.processed.set(false);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!isProcessed()) {
                processor.process(this.currentNumber);
                processed.set(true);
            }
        }
    }
}
