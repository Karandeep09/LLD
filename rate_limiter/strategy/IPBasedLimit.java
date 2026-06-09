package strategy;
import interfaces.LimitingFactor;
import entity.Request;

public class IPBasedLimit implements LimitingFactor {
    @Override
    public String getKey(Request request) {
        return request.getIp();
    }
}