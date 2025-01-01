package com.shoppproduct.dream_shops.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.dto.CartDTO;
import com.shoppproduct.dream_shops.dto.CartItemDTO;
import com.shoppproduct.dream_shops.dto.ProductDTO;
import com.shoppproduct.dream_shops.exception.CartNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.model.CartItem;
import com.shoppproduct.dream_shops.response.ApiResponse;
import com.shoppproduct.dream_shops.service.cart.ICartService;
import com.shoppproduct.dream_shops.service.product.IProductService;

@RestController
@RequestMapping(path = "${api.prefix}/carts")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @Autowired
    private IProductService iProductService;

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable int cartId){

        try {
            Cart cart = iCartService.getCart(cartId);
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
            cartDTO.setId(cartId);
            cartDTO.setTotalAmount(cart.getTotalAmount());
            cartDTO.setCartItemDTOs(cartItemDTOs);
            return ResponseEntity.ok(new ApiResponse("Get cart success", cartDTO));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable int cartId){
        try {
            iCartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear cart success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/totalAmount/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable int cartId){
        try {
            BigDecimal totalPrice = iCartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Get total price success", totalPrice));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
}
