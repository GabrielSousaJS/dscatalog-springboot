package com.gabrielsousa.dscatalog.tests;

import com.gabrielsousa.dscatalog.dto.CategoryDTO;
import com.gabrielsousa.dscatalog.dto.ProductDTO;
import com.gabrielsousa.dscatalog.entities.Category;
import com.gabrielsousa.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Iphone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2022-11-08T03:00:00Z"));
        product.getCategories().add(new Category(1L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDto() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1L, "Cloth");
    }

    public static CategoryDTO createCategoryDTO() {
        return new CategoryDTO(createCategory());
    }
}
