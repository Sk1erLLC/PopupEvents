package club.sk1er.popupevents.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mitchell Katz on 5/8/2017.
 */
class Multithreading {

    private static AtomicInteger counter = new AtomicInteger(0);

    private static ExecutorService POOL = Executors.newFixedThreadPool(8, r -> new Thread(r, String.format("Thread %s", counter.incrementAndGet())));
    private static ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(2, r -> new Thread(r, "Thread " + counter.incrementAndGet()));

    static void runAsync(Runnable runnable) {
        POOL.execute(runnable);
    }

    static void schedule(Runnable r) {
        RUNNABLE_POOL.scheduleAtFixedRate(r, 0, 5, TimeUnit.MINUTES);
    }
}