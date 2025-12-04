package com.alsolakyle.lab7.controller;

import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductGraphQLController {

    private final ProductService productService;

    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // --- QUERIES ---

    // 'allProducts'
    @QueryMapping
    public List<Product> allProducts() {
        return productService.findAll();
    }

    // 'productById'
    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }

    // --- MUTATIONS ---

    // 'createProduct'
    @MutationMapping
    public Product createProduct(@Argument String name, @Argument Double price) {
        Product newProduct = new Product(null, name, price);
        return productService.save(newProduct);
    }

    // 'updateProduct'
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String name, @Argument Double price) {
        Product productDetails = new Product(null, name, price);
        Optional<Product> updated = productService.update(id, productDetails);
        return updated.orElse(null); // Return null if ID not found (GraphQL handles nulls gracefully)
    }

    // 'deleteProduct'
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.delete(id);
    }
}
