package by.bsuir.daniil.hockey_schedule.counter;

import org.springframework.stereotype.Component;

@Component
public class RequestCounterService {
    private final RequestCounter counter = new RequestCounter();

    public synchronized void incrementCounter() {
        counter.increment();
    }

    public synchronized int getCounter() {
        return counter.getCount();
    }

    public synchronized void resetCounter() {
        counter.reset();
    }
}

