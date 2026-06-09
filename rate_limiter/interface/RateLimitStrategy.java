package interface;
import entity.Request;
public interface RateLimitStrategy {
    boolean allowRequest(String key, Rule rule);
}