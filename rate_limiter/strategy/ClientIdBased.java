package strategy;
import interface.LimitingFactor;
import entity.Request;

public class ClientIdBasedLimit implements LimitingFactor {
    @Override
    public String getKey(Request request) {
        return request.getClientId();
    }
}