package com.phoenix.data.repository;

import com.phoenix.data.models.Product;
import com.phoenix.web.exceptions.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Sql(scripts = {"/db/insert.sql"})
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Save a new Product to the database")
    void saveProductToDatabaseTest() {

        Product product = new Product();
        product.setName("Bamboo chair");
        product.setDescription("World class bamboo");
        product.setPrice(5540);
        product.setQuantity(9);

        assertThat(product.getId()).isNull();

        log.info("Product saved :: {}", product);
        productRepository.save(product);
        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo("Bamboo chair");
        assertThat(product.getPrice()).isEqualTo(5540);
        assertThat(product.getDateCreated()).isNotNull();

    }

    @Test
    @DisplayName("Find an existing product from database")
    void findExistingProductFromDatabaseTest() {


        Product product = productRepository.findById(12L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(12);
        assertThat(product.getName()).isEqualTo("Luxury Map");
        assertThat(product.getPrice()).isEqualTo(2340);
        assertThat(product.getQuantity()).isEqualTo(3);
        log.info("Product retrieved :: {}", product);

    }


    @Test
    @DisplayName("find all product in the database")

    void findAllProductsTest() {


        List<Product> productList = productRepository.findAll();
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(5);

    }


    @Test
    @DisplayName("Find Product By Name")
    void findProductByName(){

        Product product = productRepository.findByName("Milk").orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(16);
        assertThat(product.getName()).isEqualTo("Milk");
        assertThat(product.getPrice()).isEqualTo(2536);
        assertThat(product.getQuantity()).isEqualTo(8);

        log.info("Product retrieved :: {}", product);


    }

    @Test
    @DisplayName("update product")
    void updateProductTest(){



       Product savedProduct =  productRepository.findByName("Macbook Air").orElse(null);
       assertThat(savedProduct).isNotNull();

       //updateProduct
        assertThat(savedProduct.getName()).isEqualTo("Macbook Air");
        assertThat(savedProduct.getPrice()).isEqualTo(5449);
        savedProduct.setName("Macbook Air 13");
        savedProduct.setPrice(23420);

        //save product
        productRepository.save(savedProduct);
        assertThat(savedProduct.getName()).isEqualTo("Macbook Air 13");
        assertThat(savedProduct.getPrice()).isEqualTo(23420);




    }



}

