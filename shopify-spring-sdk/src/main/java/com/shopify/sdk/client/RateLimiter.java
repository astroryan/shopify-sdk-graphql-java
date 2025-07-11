package com.shopify.sdk.client;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RateLimiter {
    private final Semaphore semaphore;
    private final int maxCallsPerSecond;
    private final AtomicInteger availableTokens;
    private long lastRefillTime;
    
    public RateLimiter(int maxCallsPerSecond, int bucketSize) {
        this.maxCallsPerSecond = maxCallsPerSecond;
        this.semaphore = new Semaphore(bucketSize);
        this.availableTokens = new AtomicInteger(bucketSize);
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    public void acquire() throws InterruptedException {
        refillTokens();
        semaphore.acquire();
        availableTokens.decrementAndGet();
        
        log.debug("Rate limit token acquired. Available tokens: {}", availableTokens.get());
    }
    
    public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
        refillTokens();
        boolean acquired = semaphore.tryAcquire(timeout, unit);
        if (acquired) {
            availableTokens.decrementAndGet();
            log.debug("Rate limit token acquired. Available tokens: {}", availableTokens.get());
        }
        return acquired;
    }
    
    private synchronized void refillTokens() {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastRefillTime;
        
        if (timePassed >= 1000) {
            int tokensToAdd = (int) (timePassed / 1000 * maxCallsPerSecond);
            int currentTokens = availableTokens.get();
            int maxTokens = semaphore.availablePermits() + currentTokens;
            
            if (tokensToAdd > 0 && currentTokens < maxTokens) {
                int actualTokensToAdd = Math.min(tokensToAdd, maxTokens - currentTokens);
                semaphore.release(actualTokensToAdd);
                availableTokens.addAndGet(actualTokensToAdd);
                lastRefillTime = currentTime;
                
                log.debug("Refilled {} tokens. Total available: {}", actualTokensToAdd, availableTokens.get());
            }
        }
    }
    
    public int getAvailableTokens() {
        return availableTokens.get();
    }
}