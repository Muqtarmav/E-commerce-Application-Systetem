package com.phoenix.data.repository;

import com.phoenix.data.models.Cart;
import com.phoenix.data.models.Item;
import com.phoenix.data.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = "/db/insert.sql")
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("WE CAN ADD PRODUCT TO CART")
    void addNewProductToCart(){
        Product product = productRepository.findById(12l).orElse(null);
        assertThat(product).isNotNull();
              Item item = new Item(product, 2);
               Cart cart = new Cart();
              cart.addItem(item);

              cartRepository.save(cart);
              assertThat(cart.getId()).isNotNull();
              assertThat(cart.getItemList().isEmpty()).isFalse();
              assertThat(cart.getItemList().get(0).getProduct()).isNotNull();


              //assertThat(cart.getId()).isNotNull();
              log.info("cart object saved -> {} ", cart);

    }

    @Test
    @DisplayName("view all item in a cart")
    @Transactional
    void viewItemsInCartTest(){

        Cart savedCart = cartRepository.findById(345L).orElse(null);
        assertThat(savedCart).isNotNull();
        assertThat(savedCart.getItemList().size()).isEqualTo(3);

        log.info("cart retrieved from DB :: {}", savedCart);

    }


}