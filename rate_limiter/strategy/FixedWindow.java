package strategy;
import interfaces.RateLimitStrategy;
import entity.Rule;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class FixedWindow implements RateLimitStrategy {
    private Map<String, Map<Long, Integer>> buckets;
    public FixedWindow() {
       buckets = new ConcurrentHashMap<>();
    }
    @Override
    public boolean allowRequest(String key, Rule rule) {
        long currentTime = System.currentTimeMillis() / 1000;
        long bucketnum = currentTime / rule.getTimeWindow();
        buckets.putIfAbsent(key, new ConcurrentHashMap<>());
        Map<Long, Integer> keyBuckets = buckets.get(key);
        keyBuckets.entrySet().removeIf(entry -> entry.getKey() < bucketnum);
        keyBuckets.put(bucketnum, keyBuckets.getOrDefault(bucketnum, 0) + 1);
        return keyBuckets.get(bucketnum) <= rule.getCount();
    }
}