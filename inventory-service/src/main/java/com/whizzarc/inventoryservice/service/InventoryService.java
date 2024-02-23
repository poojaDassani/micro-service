package com.whizzarc.inventoryservice.service;

import com.whizzarc.inventoryservice.dto.InventoryResponse;
import com.whizzarc.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public  boolean skuIsInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> skuIsInStock(List<String > skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
            InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .quantity(inventory.getQuantity())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
        ).toList();
    }
}
