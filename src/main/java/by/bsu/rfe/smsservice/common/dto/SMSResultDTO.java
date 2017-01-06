package by.bsu.rfe.smsservice.common.dto;

/**
 * Created by pluhin on 6/1/16.
 */
public class SMSResultDTO {
    private int count;
    private int errorCount;

    private String lastError;

    private boolean inQueue;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public void incrementTotalCount() {
        this.count++;
    }

    public void incrementErrorCount() {
        this.errorCount++;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }
}
