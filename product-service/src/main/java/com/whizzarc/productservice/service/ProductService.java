package com.whizzarc.productservice.service;

import com.whizzarc.productservice.dto.ProductRequest;
import com.whizzarc.productservice.dto.ProductResponse;
import com.whizzarc.productservice.model.Product;
import com.whizzarc.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .barcode(productRequest.getBarcode())
                .name(productRequest.getName())
                .mrp(productRequest.getMrp())
                .purchasePrice(productRequest.getPurchasePrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved",product.getBarcode());
    }

    public List<ProductResponse> getAllProducts() {
       List<Product> products = productRepository.findAll();
       return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .barcode(product.getBarcode())
                .name(product.getName())
                .mrp(product.getMrp())
                .purchasePrice(product.getPurchasePrice())
                .build();
    }
}
