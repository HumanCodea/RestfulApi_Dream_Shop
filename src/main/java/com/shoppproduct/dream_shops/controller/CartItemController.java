package com.shoppproduct.dream_shops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.service.IUserService;
import com.shoppproduct.dream_shops.exception.CartNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.service.cart.Imp.ICartItemService;
import com.shoppproduct.dream_shops.service.cart.Imp.ICartService;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

@RestController
@RequestMapping(path = "${api.prefix}/cartItems")
public class CartItemController {
    
    @Autowired
    private ICartItemService iCartItemService;

    @Autowired
    private ICartService iCartService;

    @Autowired
    private IUserService iUserService;

    @PostMapping("/addItem")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long userId, @RequestParam int productId,
                                                    @RequestParam int quantity){
        try {
            User user = iUserService.getUserById(userId);
            Cart cart = iCartService.initializeNewCart(user);
            iCartItemService.addItemToCart(cart.getId(), productId, quantity);                                               
            return ResponseEntity.ok(new ApiResponse("Add item to cart success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteItem")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam int cartId, @RequestParam int productId){

        try {
            iCartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Remove item from cart success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/updateItem")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestParam int cartId, @RequestParam int productId,
                                                        @RequestParam int quantity){
        try {
            iCartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update item quantity success", null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }                                                      
    }

}
