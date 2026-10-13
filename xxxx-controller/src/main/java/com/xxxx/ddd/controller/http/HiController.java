package com.xxxx.ddd.controller.http;

import com.xxxx.ddd.application.service.event.EventAppService;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/hello")
public class HiController {
    @Autowired
    private EventAppService eventAppService;

    @Autowired
    private RestTemplate restTemplate;

    @RateLimiter(name = "backendA", fallbackMethod = "fallbackHello")
    @GetMapping("/hi")
    public String hello() {
        return eventAppService.sayHi("Hi");
    }

    public String fallbackHello(Throwable throwable) {
        return "Too many request";
    }

    @RateLimiter(name = "backendB", fallbackMethod = "fallbackHello")
    @GetMapping("/hi/v1")
    public String sayHi() {
        return eventAppService.sayHi("Ho");
    }

    private static final SecureRandom secureRandom = new SecureRandom();

    @GetMapping("/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackcircuitBreaker")
    public String circuitBreaker() {
        int productId = secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackcircuitBreaker(Throwable throwable) {
        return "Service fakestoreapi Error !";
    }
}
