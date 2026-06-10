package strategy;

import entity.Rule;
import interfaces.RateLimitStrategy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe Token Bucket with lazy refill.
 *
 * Uses per-key bucket objects and synchronized block on each bucket,
 * so different keys do not block each other.
 */
public class TokenBucket implements RateLimitStrategy {

    private final int capacity;
    private final double tokensPerSecond;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public TokenBucket(int capacity, Rule rule) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.tokensPerSecond = rule.getCount() / (double) rule.getTimeWindow();
    }

    @Override
    public boolean allowRequest(String key, Rule rule) {
        long now = System.currentTimeMillis() / 1000;
        Bucket bucket = buckets.computeIfAbsent(key, k -> new Bucket(capacity, now));

        synchronized (bucket) {
            // Lazy refill based on elapsed time
            long elapsed = now - bucket.lastRefillTime;
            if (elapsed > 0) {
                int tokensToAdd = (int) (elapsed * tokensPerSecond);
                if (tokensToAdd > 0) {
                    bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd);
                    bucket.lastRefillTime = now;
                }
            }
            // Try to consume a token
            if (bucket.tokens > 0) {
                bucket.tokens--;
                return true;
            }
            return false;
        }
    }

    /**
     * Mutable state for a single key's bucket.
     * Synchronization is performed externally on the Bucket instance.
     */
    private static class Bucket {
        int tokens;
        long lastRefillTime;

        Bucket(int tokens, long lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }
}
