public class Main {
    public static void main(String[] args) {
        RateLimitStrategy strategy = new FixedWindowStrategy();
        Rule rule = new Rule(5, 60); // 5 requests per 60 seconds
        LimitingFactor limitingFactor = new IPBasedLimit();
        RateLimiter rateLimiter = new RateLimiter();
        Request request1 = new Request("127.0.0.1", "client1", "/api/data");
        Request request2 = new Request("127.0.0.1", "client2", "/api/data");
        System.out.println(rateLimiter.allowRequest(request1)); // true
        System.out.println(rateLimiter.allowRequest(request1)); // true
        System.out.println(rateLimiter.allowRequest(request1)); // false (exceeds limit)
        System.out.println(rateLimiter.allowRequest(request2)); // true (different client)  
    }
}