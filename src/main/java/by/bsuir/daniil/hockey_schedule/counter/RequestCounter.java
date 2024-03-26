package by.bsuir.daniil.hockey_schedule.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public int getCount() {
        return count.get();
    }

    public void increment() {
        count.incrementAndGet();
    }

    public void reset() {
        count.set(0);
    }
}
