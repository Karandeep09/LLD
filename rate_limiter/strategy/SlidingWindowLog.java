package strategy;
import entity.Rule;
import interfaces.RateLimitStrategy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
public class SlidingWindowLog implements RateLimitStrategy {
    Map<String, Queue<Long>> requestLogs;
    public SlidingWindowLog() {
        requestLogs = new ConcurrentHashMap<>();
    }
    @Override
    public boolean allowRequest(String key, Rule rule) {
       long currentTime = System.currentTimeMillis() / 1000;
        requestLogs.putIfAbsent(key, new ConcurrentLinkedQueue<>());
        Queue<Long> logs = requestLogs.get(key);
        logs.offer(currentTime);
        while(!logs.isEmpty() && logs.peek() <= currentTime - rule.getTimeWindow()){
            logs.poll();
        }
        return logs.size() <= rule.getCount();
    }

}