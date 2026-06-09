import entity.Request;
import entity.Rule;
import interfaces.LimitingFactor;
import interfaces.RateLimitStrategy;
import strategy.ClientIdBased;
import strategy.FixedWindow;
import strategy.IPBasedLimit;
import strategy.SlidingWindowLog;

public class Main {
    public static void main(String[] args) {
        // Array of rate limit strategies to test
        RateLimitStrategy[] strategies = {
            new FixedWindow(),
            new SlidingWindowLog(),
        };

        Rule rule = new Rule(5, 60);
        LimitingFactor limitingFactor = new ClientIdBased();

        System.out.println("==== Strategy Array Tests ====\n");

        // Test each strategy
        for (RateLimitStrategy strategy : strategies) {
            System.out.println("Testing Strategy: " + strategy.getClass().getSimpleName());
            testBasicScenario(strategy, new Rule(5, 60), limitingFactor);
            System.out.println();
        }

        // Test different limiting factors
        LimitingFactor[] factors = {
            new IPBasedLimit(),
            new ClientIdBased(),
        };

        System.out.println("==== Limiting Factor Isolation Tests ====\n");
        for (LimitingFactor factor : factors) {
            System.out.println("Testing Limiting Factor: " + factor.getClass().getSimpleName());
            testKeyIsolation(new FixedWindow(), new Rule(2, 60), factor);
            System.out.println();
        }
    }

    private static void testBasicScenario(RateLimitStrategy strategy, Rule rule, LimitingFactor limitingFactor) {
        RateLimiter rateLimiter = new RateLimiter(strategy, rule, limitingFactor);

        Request request1 = new Request("127.0.0.1", "client1", "/api/data");
        Request request2 = new Request("127.0.0.1", "client2", "/api/data");

        // Scenario: requests within limit should succeed
        System.out.println("  allow request1 (1st):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (2nd):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (3rd):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (4th):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (5th):  " + rateLimiter.allowRequest(request1));

        // Scenario: exceeding limit should be denied
        System.out.println("  allow request1 (6th - exceeds): " + rateLimiter.allowRequest(request1));

        // Scenario: different client / key should be allowed with isolation
        System.out.println("  allow request2 (diff): " + rateLimiter.allowRequest(request2));
    }

    private static void testKeyIsolation(RateLimitStrategy strategy, Rule rule, LimitingFactor limitingFactor) {
        RateLimiter rateLimiter = new RateLimiter(strategy, rule, limitingFactor);

        Request req1 = new Request("192.168.1.1", "userA", "/api/login");
        Request req2 = new Request("192.168.1.1", "userB", "/api/login");

        // Scenario: consume limit for req1
        System.out.println("  req1 (1st): " + rateLimiter.allowRequest(req1));
        System.out.println("  req1 (2nd): " + rateLimiter.allowRequest(req1));

        // Scenario: req2 may share the key depending on the limiting factor
        System.out.println("  req2 (1st): " + rateLimiter.allowRequest(req2));

        // Scenario: req1 exceeds its own limit
        System.out.println("  req1 (3rd - exceeds): " + rateLimiter.allowRequest(req1));
    }
}
