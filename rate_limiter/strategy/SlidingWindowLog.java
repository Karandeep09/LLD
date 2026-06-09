package strategy;
import interfaces.RateLimitStrategy;
import entity.Rule;
import java.util.*;
public class SlidingWindowLog implements RateLimitStrategy {
    Map<String, Queue<Long>> requestLogs;
    public SlidingWindowLog() {
        requestLogs = new HashMap<>();
    }
    @Override
    public boolean allowRequest(String key, Rule rule) {
       long currentTime = System.currentTimeMillis() / 1000;
        requestLogs.putIfAbsent(key, new LinkedList<>());
        Queue<Long> logs = requestLogs.get(key);
        logs.offer(currentTime);
        while(!logs.isEmpty() && logs.peek() <= currentTime - rule.getTimeWindow()){
            logs.poll();
        }
        return logs.size() <= rule.getCount();
    }

}