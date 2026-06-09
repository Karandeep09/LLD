package strategy;
import interface.RateLimitStrategy;
import entity.Rule;
import java.util.*;
public class FixedWindow implements RateLimitStrategy {
    private Map<String, Map<Long, Integer>> buckets;
    public FixedWindow() {
       buckets = new HashMap<>();
    }
    @Override
    public boolean allowRequest(String key, Rule rule) {
        long currentTime = System.currentTimeMillis() / 1000;
        long bucketnum = currentTime / rule.getWindowSize();
        buckets.putIfAbsent(key, new HashMap<>());
        Map<Long, Integer> keyBuckets = buckets.get(key);
        keyBuckets.put(bucketnum, keyBuckets.getOrDefault(bucketnum, 0) + 1);
        return keyBuckets.get(bucketnum) <= rule.getLimit();
    }
}