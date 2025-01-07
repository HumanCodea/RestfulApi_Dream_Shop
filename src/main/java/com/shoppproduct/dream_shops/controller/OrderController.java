package com.shoppproduct.dream_shops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.exception.OrderNotFoundException;
import com.shoppproduct.dream_shops.exception.UserNotFoundException;
import com.shoppproduct.dream_shops.model.Orders;
import com.shoppproduct.dream_shops.response.ApiResponse;
import com.shoppproduct.dream_shops.service.order.IOrderService;

@RestController
@RequestMapping(path = "${api.prefix}/orders")
public class OrderController {
    
    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/order/{userId}")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId){

        try {
            Orders order = iOrderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item order success", order));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
        }

    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id){
        try {
            Orders orders = iOrderService.getOrder(id);
            return ResponseEntity.ok(new ApiResponse("Get order success", orders));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/getUserOrder/{userId}")
    public ResponseEntity<ApiResponse> getUserOrder(@PathVariable Long userId){
        try {
            List<Orders> orders = iOrderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Get order success", orders));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

}
