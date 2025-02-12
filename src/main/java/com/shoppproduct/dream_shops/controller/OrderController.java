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

import com.shoppproduct.dream_shops.exception.InventoryOutException;
import com.shoppproduct.dream_shops.exception.OrderNotFoundException;
import com.shoppproduct.dream_shops.exception.UserNotFoundException;
import com.shoppproduct.dream_shops.model.Orders;
import com.shoppproduct.dream_shops.service.order.IOrderService;
import com.shoppproduct.dream_shops.utils.dto.OrderDTO;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.prefix}/orders")
@Tag(name = "Order Controller")
public class OrderController {
    
    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/order/{userId}")
    @Operation(summary = "Add order", description = "Api to create new order")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId){

        try {
            Orders order = iOrderService.placeOrder(userId);
            OrderDTO orderDTO = iOrderService.convertToDTo(order);
            return ResponseEntity.ok(new ApiResponse("Item order success", orderDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
        } catch (InventoryOutException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ex.getMessage(), null));
        }

    }

    @GetMapping("/getOrder/{id}")
    @Operation(summary = "Get order", description = "Api to get order by id")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id){
        try {
            OrderDTO orders = iOrderService.getOrder(id);
            return ResponseEntity.ok(new ApiResponse("Get order success", orders));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/getUserOrder/{userId}")
    @Operation(summary = "Get order", description = "Api to get order by userId")
    public ResponseEntity<ApiResponse> getUserOrder(@PathVariable Long userId){
        try {
            List<OrderDTO> orders = iOrderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Get order success", orders));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

}
