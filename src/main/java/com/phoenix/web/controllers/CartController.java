package com.phoenix.web.controllers;

import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.dto.CartResponseDto;
import com.phoenix.data.dto.CartUpdateDto;
import com.phoenix.service.cart.CartService;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")

public class CartController {

    @Autowired
    CartService cartServiceImpl;


    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartRequestDto cartRequestDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {
        CartResponseDto cartResponseDto = null;
        try {
            cartResponseDto = cartServiceImpl.addItemToCart(cartRequestDto);
        } catch (ProductDoesNotExistException | UserNotFoundException | BusinessLogicException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok().body(cartResponseDto);
    }

    @PatchMapping()
    public ResponseEntity<?> updateCartItem(@RequestBody CartUpdateDto updateDto){

        try{
          CartResponseDto responseDto =  cartServiceImpl.updateCartItem(updateDto);
          return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        }
        catch(UserNotFoundException | BusinessLogicException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/")
    public ResponseEntity<?> viewCartById(@PathVariable("userId") Long id){
        try{
            CartResponseDto responseDto = cartServiceImpl.viewCart(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        }
        catch(UserNotFoundException | BusinessLogicException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
