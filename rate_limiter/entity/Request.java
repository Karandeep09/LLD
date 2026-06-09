package entity;

public class Request {
    private String ip;
    private String clientId;
    private String endpoint;
    public Request(String ip, String clientId, String endpoint) {
        this.ip = ip;
        this.clientId = clientId;
        this.endpoint = endpoint;
    }

    public String getIp() {
        return ip;
    }

    public String getClientId() {
        return clientId;
    }

    public String getEndpoint() {
        return endpoint;
    }
}