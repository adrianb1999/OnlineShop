package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.Category;
import com.adrian99.onlineShop.model.Product;
import com.adrian99.onlineShop.service.ProductService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/product")
    public Product addProduct(@RequestBody Product product){
        return productService.save(product);
    }

    @PostMapping("/api/products")
    public List<Product> addProducts(@RequestBody List<Product> products){
        return (List<Product>) productService.saveAll(products);
    }

    @GetMapping("/api/products")
    public List<Product> getAllProducts(){
        return (List<Product>) productService.findAll();
    }

    @GetMapping("/api/product")
    public List<Product> getProducts(@RequestParam(name = "category", required = false) Category category){
        return productService.findAllByCategory(category, 20, 0).getContent();
    }
}
