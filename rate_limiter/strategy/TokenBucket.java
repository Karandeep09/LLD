package strategy;

import entity.Request;
import interface.RateLimitStrategy;
import entity.Rule;

public class TokenBucket implements RateLimitStrategy {
    private final int capacity;
    private Map<String, Integer> tokens;
    private final Map<String, Thread> refillThread;
    private final int tokensRate;
    public TokenBucket(int capacity, Rule rule) {
        this.capacity = capacity;
        this.tokensRate = rule.getCount() / (int)rule.getTimeWindow();
        this.refillThread = new HashMap<>();
        this.tokens = new HashMap<>();
    }
    private void refill(String key){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            synchronized(this){
               this.tokens.put(key, Math.min(capacity, tokens.getOrDefault(key, 0) + tokensRate));
            }
        }
    }
    @Override
    public boolean allowRequest(String key, Rule rule) {
        refillThread.putIfAbsent(key, new Thread(() -> refill(key)));
        if(!refillThread.get(key).isAlive()){
            refillThread.get(key).start();
        }
        synchronized(this){
            int availableTokens = tokens.getOrDefault(key, 0);
            if(availableTokens > 0){
                tokens.put(key, availableTokens - 1);
                return true;
            } else {
                return false;   
            }
    }
}