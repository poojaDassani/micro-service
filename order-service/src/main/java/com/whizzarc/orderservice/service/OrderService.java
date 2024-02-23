package com.whizzarc.orderservice.service;

import com.whizzarc.orderservice.dto.*;
import com.whizzarc.orderservice.model.Order;
import com.whizzarc.orderservice.model.OrderLineItems;
import com.whizzarc.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsRequestList().stream().map(this::mapToOrderLineItem).toList());

        List<String> listOfSkuCode = orderRequest.getOrderLineItemsRequestList().stream().map(OrderLineItemsRequest::getSkuCode).toList();

        //call Inventory Service to check if item is in stock or not, and then place order
        InventoryResponse[] inventoryResponses = webClient.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",listOfSkuCode).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock)
            orderRepository.save(order);
        else
            throw  new IllegalArgumentException("Product is not in stock, please try later");
    }

    private OrderLineItems mapToOrderLineItem(OrderLineItemsRequest orderLineItemsRequest) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsRequest.getSkuCode());
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        return orderLineItems;
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToOrderDto).toList();
    }

    private OrderResponse mapToOrderDto(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderLineItemsRequestList(order.getOrderLineItemsList().stream().map(this::mapToOrderLineItemDto).toList());
        return orderResponse;
    }

    private OrderLineItemsResponse mapToOrderLineItemDto(OrderLineItems orderLineItems) {
        OrderLineItemsResponse orderLineItemsResponse = new OrderLineItemsResponse();
        orderLineItemsResponse.setId(orderLineItems.getId());
        orderLineItemsResponse.setSkuCode(orderLineItems.getSkuCode());
        orderLineItemsResponse.setPrice(orderLineItems.getPrice());
        orderLineItemsResponse.setQuantity(orderLineItems.getQuantity());
        return orderLineItemsResponse;
    }
}
