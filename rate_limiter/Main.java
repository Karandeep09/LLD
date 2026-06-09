import entity.Request;
import entity.Rule;
import interfaces.LimitingFactor;
import interfaces.RateLimitStrategy;
import strategy.ClientIdBased;
import strategy.FixedWindow;
import strategy.IPBasedLimit;
import strategy.SlidingWindowLog;
import strategy.TokenBucket;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Build strategy array manually because TokenBucket has a different constructor
        RateLimitStrategy[] strategies = new RateLimitStrategy[3];
        strategies[0] = new FixedWindow();
        strategies[1] = new SlidingWindowLog();
        // Use Rule(5,1) so that count/timeWindow > 0 (avoids 0 refill rate from integer division)
        strategies[2] = new TokenBucket(5, new Rule(5, 1));

        Rule rule = new Rule(5, 60);
        LimitingFactor limitingFactor = new IPBasedLimit();

        System.out.println("==== Strategy Array Tests ====\n");

        // Test each strategy
        for (RateLimitStrategy strategy : strategies) {
            System.out.println("Testing Strategy: " + strategy.getClass().getSimpleName());
            System.out.println("  Rule: " + rule.getCount() + " requests per " + rule.getTimeWindow() + " seconds");
            System.out.println("  Limiting Factor: " + limitingFactor.getClass().getSimpleName());
            testBasicScenario(strategy, rule, limitingFactor);
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

        // Dedicated TokenBucket test: refill + burst behavior
        System.out.println("==== TokenBucket Specific Test ====\n");
        testTokenBucketRefill();
    }

    private static void testBasicScenario(RateLimitStrategy strategy, Rule rule, LimitingFactor limitingFactor) {
        RateLimiter rateLimiter = new RateLimiter(strategy, rule, limitingFactor);

        Request request1 = new Request("127.0.0.1", "client1", "/api/data");
        Request request2 = new Request("192.168.0.1", "client2", "/api/data");
        try {
            Thread.sleep(600); // Small delay to ensure different timestamps for strategies that rely on time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Scenario: requests within limit should succeed
        System.out.println("  allow request1 (1st):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (2nd):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (3rd):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (4th):  " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (5th):  " + rateLimiter.allowRequest(request1));

        // Scenario: exceeding limit should be denied
        System.out.println("  allow request1 (6th - exceeds): " + rateLimiter.allowRequest(request1));
        System.out.println("  allow request1 (7th - exceeds): " + rateLimiter.allowRequest(request1));

        // Scenario: different client / key should be allowed with isolation
        System.out.println("  allow request2 (diff): " + rateLimiter.allowRequest(request2));
        System.out.println("  allow request2 (diff): " + rateLimiter.allowRequest(request2));
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

    /**
     * Tests the specific refill (token replenishment) and burst behavior.
     * Only makes sense for TokenBucket.
     */
    private static void testTokenBucketRefill() throws InterruptedException {
        LimitingFactor limitingFactor = new IPBasedLimit();
        // Capacity 3, refill rate 3 tokens per second (Rule(3,1))
        RateLimitStrategy tokenBucketStrategy = new TokenBucket(3, new Rule(3, 1));
        RateLimiter rateLimiter = new RateLimiter(tokenBucketStrategy, new Rule(3, 1), limitingFactor);

        Request request = new Request("127.0.0.1", "client1", "/api/heavy");

        // Drain all tokens (capacity = 3, but first request is often denied because bucket starts empty)
        System.out.println("  After init, allow (1st):       " + rateLimiter.allowRequest(request));
        System.out.println("  After init, allow (2nd):       " + rateLimiter.allowRequest(request));
        System.out.println("  After init, allow (3rd):       " + rateLimiter.allowRequest(request));
        System.out.println("  After init, allow (4th):       " + rateLimiter.allowRequest(request));
        System.out.println("  After init, allow (5th):       " + rateLimiter.allowRequest(request));

        // Wait for refill thread to replenish tokens
        System.out.println("  Waiting 1.5 seconds for refill...");
        Thread.sleep(1500);

        // After refill, some tokens should be available again
        System.out.println("  After refill, allow (1st):     " + rateLimiter.allowRequest(request));
        System.out.println("  After refill, allow (2nd):     " + rateLimiter.allowRequest(request));
        System.out.println("  After refill, allow (3rd):     " + rateLimiter.allowRequest(request));
    }
}
