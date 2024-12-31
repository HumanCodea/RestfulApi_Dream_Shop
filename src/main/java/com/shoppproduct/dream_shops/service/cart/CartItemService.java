package com.shoppproduct.dream_shops.service.cart;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.exception.ProductNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.model.CartItem;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.repostitory.CartItemRepository;
import com.shoppproduct.dream_shops.repostitory.CartRepository;
import com.shoppproduct.dream_shops.service.product.IProductService;

@Service
public class CartItemService implements ICartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ICartService iCartService;

    @Autowired
    private IProductService iProductService;

    @Override
    public void addItemToCart(int cartId, int productId, int quantity) {

        Cart cart = iCartService.getCart(cartId);
        Product product = iProductService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getProductId() == productId)
                .findFirst().orElse(new CartItem());
        
        if (cartItem.getId() == 0) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getProductPrices());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(int cartId, int productId) {
        Cart cart = iCartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(int cartId, int productId, int quantity) {
        Cart cart = iCartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getProductId() == productId)
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getProductPrices());
                    item.setTotalPrice();
                }); 

            BigDecimal totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount);
            cartRepository.save(cart);
    }
    
    @Override
    public CartItem getCartItem(int cartId, int productId){
        Cart cart = iCartService.getCart(cartId);
        return cart.getCartItems()
            .stream()
            .filter(item -> item.getProduct().getProductId() == productId)
            .findFirst().orElseThrow(() -> new ProductNotFoundException("Not found product with id = " + cartId));
    }

}
