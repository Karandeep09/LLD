public class RateLimiter {
    private RateLimitStrategy strategy;
    private Rule rule;
    private LimitingFactor limitingFactor;

    public RateLimiter(RateLimitStrategy strategy, Rule rule, LimitingFactor limitingFactor) {
        this.strategy = strategy;
        this.rule = rule;
        this.limitingFactor = limitingFactor;
    }

    public boolean allowRequest(Request request) {
        String key = limitingFactor.getKey(request);
        return strategy.allowRequest(key, rule);
    }
}