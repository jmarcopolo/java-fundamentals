package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.Arrays;

import org.zeroturnaround.jf2012.concurrency.counter.counter.Counter;
import org.zeroturnaround.jf2012.concurrency.counter.counter.CounterType;
import org.zeroturnaround.jf2012.concurrency.counter.executor.ConcurrentExecutor;
import org.zeroturnaround.jf2012.concurrency.counter.executor.ConcurrentExecutorType;

public class Main {

  private static final int DEFAULT_NO_THREADS = 10;

  private static final int DEFAULT_NO_TIMES = 100000;

  public static void main(String[] args) throws Exception {
    ConcurrentExecutorType executor = ConcurrentExecutorType.Countdown;
    CounterType counter = CounterType.SimplePrimitive;
    int threads = DEFAULT_NO_THREADS;
    int times = DEFAULT_NO_TIMES;

    try {
      int i = 0;
      if (args.length > i) {
        executor = ConcurrentExecutorType.valueOf(args[i]);
        i++;
      }
      if (args.length > i) {
        counter = CounterType.valueOf(args[i]);
        i++;
      }
      if (args.length > i) {
        threads = Integer.parseInt(args[i]);
        i++;
      }
      if (args.length > i) {
        times = Integer.parseInt(args[i]);
        i++;
      }
    }
    catch (IllegalArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
      System.out.println();
      System.out.println("Args usages: [<executor> [<counter> [<no of threads> [<no of times>]]]]");
      System.out.println();
      System.out.format("Available executors: %s%n", Arrays.toString(ConcurrentExecutorType.values()));
      System.out.format("Available counters: %s%n", Arrays.toString(CounterType.values()));
      System.out.println();
      return;
    }

    System.out.format("Using executor: %s%n", executor);
    System.out.format("Using counter: %s%n", counter);
    System.out.format("No of threads: %,d%n", threads);
    System.out.format("No of times: %,d%n", times);
    execute(executor.create(), counter.create(), threads, times);
  }

  private static void execute(ConcurrentExecutor executor, Counter counter, int threads, int times) throws Exception {
    long start = System.currentTimeMillis();
    executor.invoke(counter, threads, times);
    long time = System.currentTimeMillis() - start;
    System.out.format("%d x %,d => %,d%n", threads, times, counter.getCount());
    System.out.format("Time taken: %d ms%n", time);
  }

}
