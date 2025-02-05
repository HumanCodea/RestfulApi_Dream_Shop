package com.shoppproduct.dream_shops.service.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.repository.UserRepository;
import com.shoppproduct.dream_shops.exception.CartNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.model.CartItem;
import com.shoppproduct.dream_shops.repostitory.CartItemRepository;
import com.shoppproduct.dream_shops.repostitory.CartRepository;
import com.shoppproduct.dream_shops.service.cart.Imp.ICartService;
import com.shoppproduct.dream_shops.service.product.IProductService;
import com.shoppproduct.dream_shops.utils.dto.CartDTO;
import com.shoppproduct.dream_shops.utils.dto.CartItemDTO;
import com.shoppproduct.dream_shops.utils.dto.ProductDTO;

import jakarta.transaction.Transactional;

@Service
public class CartService implements ICartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart getCart(int id) {
        return cartRepository.findById(id)
            .orElseThrow(() -> new CartNotFoundException("Not found cart by id = " + id));
    }

    @Transactional
    @Override
    public void clearCart(int id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);

        User user = cart.getUser();
        if (user != null) {
            user.setCart(null);
            userRepository.save(user);
        }

        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(int id) {
        Cart cart = getCart(id);
        return cart.getCartItems()
                .stream()
                .map(CartItem :: getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal :: add); // tổng giá trị của tất cả các items trong cart
    }

    @Override
    public Cart initializeNewCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
            .orElseGet(() -> {
                Cart cart = new Cart();
                cart.setUser(user);
                return cartRepository.save(cart);
            });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    
    @Override
    public CartDTO convertCartToDTO(Cart cart){
        Set<CartItem> cartItems = cart.getCartItems();
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        CartDTO cartDTO = new CartDTO();
        for(CartItem c : cartItems){
            CartItemDTO cartItemDTO = new CartItemDTO();
            ProductDTO productDTO = iProductService.convertToDTO(c.getProduct());
            cartItemDTO.setId(c.getId());
            cartItemDTO.setQuantity(c.getQuantity());
            cartItemDTO.setUnitPrice(c.getUnitPrice());
            cartItemDTO.setTotalPrice(c.getTotalPrice());
            cartItemDTO.setProductDTO(productDTO);
            cartItemDTOs.add(cartItemDTO);
        }
        cartDTO.setId(cart.getId());
        cartDTO.setTotalAmount(cart.getTotalAmount());
        cartDTO.setCartItemDTOs(cartItemDTOs);
        return cartDTO;
    }
    
}
