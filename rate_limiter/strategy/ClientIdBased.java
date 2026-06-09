package strategy;
import interfaces.LimitingFactor;
import entity.Request;
public class ClientIdBased implements LimitingFactor {
    @Override
    public String getKey(Request request) {
        return request.getClientId();
    }
}