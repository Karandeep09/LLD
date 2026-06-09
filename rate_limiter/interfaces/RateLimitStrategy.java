package interfaces;
import entity.Rule;
public interface RateLimitStrategy {
    boolean allowRequest(String key, Rule rule);
}