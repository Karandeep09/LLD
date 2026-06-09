package interface;
import entity.Request;

public interface LimitingFactor {
    String getKey(Request request);
}