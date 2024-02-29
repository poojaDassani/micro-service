package com.whizzarc.inventoryservice.service;

import com.whizzarc.inventoryservice.dto.InventoryResponse;
import com.whizzarc.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public  boolean skuIsInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    //sneaky throws shouldn't be added in production code, only for demo and development.
    //In production exception should be properly handled
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> skuIsInStock(List<String > skuCode) {
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
            InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .quantity(inventory.getQuantity())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
        ).toList();
    }
}
