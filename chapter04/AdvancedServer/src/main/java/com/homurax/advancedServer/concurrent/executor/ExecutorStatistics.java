package com.homurax.advancedServer.concurrent.executor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorStatistics {

    private final AtomicLong executionTime = new AtomicLong(0);

    private final AtomicInteger numTasks = new AtomicInteger(0);

    public void addExecutionTime(long time) {
        executionTime.addAndGet(time);
    }

    public long getExecutionTime() {
        return executionTime.get();
    }

    public void addTask() {
        numTasks.incrementAndGet();
    }

    public int getNumTasks() {
        return numTasks.get();
    }


    @Override
    public String toString() {
        return "Executed Tasks: " + getNumTasks() + ". Execution Time: " + getExecutionTime();

    }
}
