package strategy;

import entity.Rule;
import interfaces.RateLimitStrategy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
public class TokenBucket implements RateLimitStrategy {
    private final int capacity;
    private Map<String, AtomicInteger> tokens;
    private final Map<String, Thread> refillThread;
    private final double tokensRate;
    private final Map<String, AtomicLong> lastRefillTime;
    public TokenBucket(int capacity, Rule rule) {
        this.capacity = capacity;
        this.tokensRate = rule.getCount() / (double) rule.getTimeWindow();
        this.refillThread = new ConcurrentHashMap<>();
        this.tokens = new ConcurrentHashMap<>();
        this.lastRefillTime = new ConcurrentHashMap<>();
    }
    @Override
    public boolean allowRequest(String key, Rule rule) {
        AtomicLong currentTime = new AtomicLong(System.currentTimeMillis() / 1000);
        AtomicLong elaspedTime = new AtomicLong(currentTime.get() - lastRefillTime.getOrDefault(key, new AtomicLong(currentTime.get())).get());
        AtomicInteger newTokens = new AtomicInteger((int) (elaspedTime.get() * tokensRate));
        tokens.putIfAbsent(key, new AtomicInteger((int) tokensRate));
        tokens.put(key, new AtomicInteger(Math.min(capacity, newTokens.get() + tokens.get(key).get())));
        if(tokens.get(key).get() > 0){
            tokens.get(key).decrementAndGet();
            lastRefillTime.put(key, currentTime);
            return true;
        } else {
            return false;   
        }
    }
}