package com.whizzarc.inventoryservice.controller;

import com.whizzarc.inventoryservice.dto.InventoryResponse;
import com.whizzarc.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean skuIsInStock(@PathVariable("sku-code") String skuCode){
        return inventoryService.skuIsInStock(skuCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> skuIsInStock(@RequestParam List<String> skuCode){
        return inventoryService.skuIsInStock(skuCode);
    }
}
