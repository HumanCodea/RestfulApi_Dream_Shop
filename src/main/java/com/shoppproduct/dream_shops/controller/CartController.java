package com.shoppproduct.dream_shops.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.exception.CartNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.service.cart.Imp.ICartService;
import com.shoppproduct.dream_shops.utils.dto.CartDTO;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.prefix}/carts")
@Tag(name = "Cart Controller")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @GetMapping("/cart/{cartId}")
    @Operation(summary = "Get cart", description = "Api to get cart by id")
    public ResponseEntity<ApiResponse> getCart(@PathVariable int cartId){

        try {
            Cart cart = iCartService.getCart(cartId);
            CartDTO cartDTO = iCartService.convertCartToDTO(cart);
            return ResponseEntity.ok(new ApiResponse("Get cart success", cartDTO));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/clear/{cartId}")
    @Operation(summary = "Clear cart", description = "Api to remove all item in cart by cart id")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable int cartId){
        try {
            iCartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear cart success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/totalAmount/{cartId}")
    @Operation(summary = "Get total amount", description = "Api to get total amount by cartId")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable int cartId){
        try {
            BigDecimal totalPrice = iCartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Get total price success", totalPrice));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
}
