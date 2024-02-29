package com.whizzarc.orderservice.controller;

import com.whizzarc.orderservice.dto.OrderRequest;
import com.whizzarc.orderservice.dto.OrderResponse;
import com.whizzarc.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
       return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException exception  ){
        return CompletableFuture.supplyAsync( () ->"Oops! something went wrong, please order after sometime.");
    }
}
