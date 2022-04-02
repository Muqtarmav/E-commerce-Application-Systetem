package com.phoenix.service.cart;

import com.github.fge.jsonpatch.JsonPatch;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AppRepository appRepository;

    @Override
    public CartResponseDto addItemToCart(CartRequestDto cartItemDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {


//        CartItemDto cartItemDto = new CartItemDto();
//        cartItemDto.setProductId(13L);
//        cartItemDto.setUserId(5010L);
//        cartItemDto.setQuantity(1);

        //CHECK IF USER EXIST

       Optional<AppUser> query = appRepository.findById(cartItemDto.getUserId());

        // assertThat(exisingUser).isNotNull();

        if (query.isEmpty()){
            throw new UserNotFoundException("user with id " + cartItemDto.getUserId() + "not found");

        }

        AppUser existingUser = query.get();
        //get user cart
        Cart myCart = existingUser.getMyCart();


        //check product exists

        Product product = productRepository.findById(cartItemDto.getProductId()).orElse(null);

        if (product == null){
            throw new ProductDoesNotExistException("product with Id " + cartItemDto.getProductId() + "not found");
        }
        if(!quantityIsValid(product, cartItemDto.getQuantity())){
            throw new BusinessLogicException("quantity too large");
        }

        //add product to cart
        Item cartItem = new Item(product, cartItemDto.getQuantity());
        myCart.addItem(cartItem);
        myCart.setTotalPrice(myCart.getTotalPrice() + calculateItemPrice(cartItem));
        //save cart

       cartRepository.save(myCart);

       return buildCartResponse(myCart);
    }

    private Double calculateItemPrice(Item item){
        return item.getProduct().getPrice() * item.getQuantityAdded();
    }




    private CartResponseDto buildCartResponse(Cart cart){
        return CartResponseDto.builder()
                .cartItems(cart.getItemList())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    private boolean quantityIsValid(Product product, int quantity){
        return product.getQuantity() >= quantity;

    }

    @Override
    public CartResponseDto viewCart(Long userId) throws BusinessLogicException, UserNotFoundException {

        AppUser appUser = appRepository.findById(userId).orElse(null);
        if (appUser == null) {
            throw new UserNotFoundException("user with id" + userId + "does not  exist");
        }

        Cart cart = appUser.getMyCart();
        return buildCartResponse(cart);

    }


    @Override
    public CartResponseDto updateCartItem(CartUpdateDto updateDto) throws UserNotFoundException, BusinessLogicException {

        AppUser appUser = appRepository.findById(updateDto.getUserId()).orElse(null);

        if ( appUser == null){
            throw new UserNotFoundException("user with id" + updateDto.getUserId() + "not found");
        }

        //get user cart
        Cart myCart = appUser.getMyCart();

        Item item = findCartItem(updateDto.getItemId(), myCart).orElse(null);
        if (item == null){
            throw new BusinessLogicException("item not in cart");
        }

        if (updateDto.getQuantityOperation() == QuantityOperation.INCREASE){
            item.setQuantityAdded(item.getQuantityAdded() + 1);
            myCart.setTotalPrice(myCart.getTotalPrice() + item.getProduct().getPrice());
        }
        else if(updateDto.getQuantityOperation() == QuantityOperation.DECREASE ){
            item.setQuantityAdded(item.getQuantityAdded() - 1 );
            myCart.setTotalPrice(myCart.getTotalPrice() - item.getProduct().getPrice());
        }
        cartRepository.save(myCart);
        return buildCartResponse(myCart);
        //find item in cart

        //perform update to Item

    }

    private Optional<Item> findCartItem(Long itemId, Cart cart){
        Predicate<Item> itemPredicate = i -> i.getId().equals(itemId);
        return cart.getItemList().stream().filter(itemPredicate).findFirst();

    }


}
