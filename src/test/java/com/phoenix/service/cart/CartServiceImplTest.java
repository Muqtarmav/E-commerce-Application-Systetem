package com.phoenix.service.cart;

import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.dto.CartResponseDto;
import com.phoenix.data.dto.CartUpdateDto;
import com.phoenix.data.dto.QuantityOperation;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Cart;
import com.phoenix.data.models.Item;
import com.phoenix.data.models.Product;
import com.phoenix.data.repository.AppRepository;
import com.phoenix.data.repository.CartRepository;
import com.phoenix.data.repository.ProductRepository;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@Sql("/db/insert.sql")
class CartServiceImplTest {



    @Autowired
    AppRepository appRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    private CartUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        updateDto = CartUpdateDto.builder().itemId(510L)
                .userId(5010L)
                .quantityOperation(QuantityOperation.INCREASE).build();

    }

    @Test
    void addItemToCart() throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {

        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setUserId(5010L);
        cartRequestDto.setQuantity(1);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
        assertThat(cartResponseDto.getCartItems()).isNotNull();
        assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);

    }


    @Test
    void viewCart() {
    }


    @Test
    void priceHasBeenUpdatedTest() throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setUserId(5010L);
        cartRequestDto.setQuantity(1);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
        assertThat(cartResponseDto.getCartItems()).isNotNull();
        assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);
        assertThat(cartResponseDto.getTotalPrice()).isEqualTo(5449);


    }

    @Test
    @DisplayName("Increase cart item quantity test")
    void updateCartItemTest() throws UserNotFoundException, BusinessLogicException {

        CartUpdateDto updateDto = CartUpdateDto.builder()
                .itemId(511L)
                .quantityOperation(QuantityOperation.INCREASE)
                .userId(5010L)
                .build();

        AppUser appUser = appRepository.findById(updateDto.getUserId()).orElse(null);
        assertThat(appUser).isNotNull();
        Cart userCart = appUser.getMyCart();
        assertThat(userCart.getItemList().size()).isEqualTo(1);
        Item item = userCart.getItemList().get(0);
        log.info("item -> {}", item);
        assertThat(item).isNotNull();
        assertThat(item.getQuantityAdded()).isEqualTo(1);
        log.info("cart update obj ->{} ", userCart);

        CartResponseDto responseDto = cartService.updateCartItem(updateDto);
            assertThat(responseDto.getCartItems()).isNotNull();
            assertThat(responseDto.getCartItems().get(0)
                    .getQuantityAdded()).isEqualTo(2);


        }
    }
