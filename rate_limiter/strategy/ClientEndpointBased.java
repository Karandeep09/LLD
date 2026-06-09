package strategy;

import entity.Request;
import interfaces.LimitingFactor;

public class ClientEndpointBased implements LimitingFactor {
    @Override
    public String getKey(Request request) {
        return request.getClientId() + ":" + request.getEndpoint();
    }
}