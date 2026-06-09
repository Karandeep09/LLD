package entity;

public class Rule {
    private int count;
    private long timeWindow;
    public Rule(int count, long timeWindow) {
        this.count = count;
        this.timeWindow = timeWindow;
        
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return this.count;
    }
    public void setTimeWindow(long timeWindow) {
        this.timeWindow = timeWindow;
    }
    public long getTimeWindow() {
        return this.timeWindow;
    }
}