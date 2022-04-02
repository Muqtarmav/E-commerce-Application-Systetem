package com.phoenix.service.cart;

import com.github.fge.jsonpatch.JsonPatch;
import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.dto.CartResponseDto;
import com.phoenix.data.dto.CartUpdateDto;
import com.phoenix.data.models.Cart;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import org.springframework.data.web.JsonPath;

public interface CartService {


    CartResponseDto addItemToCart(CartRequestDto cart) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException;
    CartResponseDto viewCart(Long cartId) throws BusinessLogicException, UserNotFoundException;
    CartResponseDto updateCartItem(CartUpdateDto updateDto) throws UserNotFoundException, BusinessLogicException;
}
