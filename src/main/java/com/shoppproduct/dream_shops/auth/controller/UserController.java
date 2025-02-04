package com.shoppproduct.dream_shops.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.service.Imp.IUserService;
import com.shoppproduct.dream_shops.auth.utils.dto.UserDTO;
import com.shoppproduct.dream_shops.exception.UserNotFoundException;
import com.shoppproduct.dream_shops.model.Orders;
import com.shoppproduct.dream_shops.service.cart.Imp.ICartService;
import com.shoppproduct.dream_shops.service.order.IOrderService;
import com.shoppproduct.dream_shops.utils.dto.CartDTO;
import com.shoppproduct.dream_shops.utils.dto.OrderDTO;
import com.shoppproduct.dream_shops.utils.request.UpdateUserRequest;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.prefix}/users")
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICartService iCartService;

    @Autowired
    private IOrderService iOrderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId){
        try {
            User user = iUserService.getUserById(userId);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            CartDTO cartDTO = iCartService.convertCartToDTO(user.getCart());
            List<Orders> orders = user.getOrders();
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for(Orders o : orders){
                OrderDTO orderDTO = iOrderService.convertToDTo(o);
                orderDTOs.add(orderDTO);
            }
            userDTO.setCartDTO(cartDTO);
            userDTO.setOrderDTOs(orderDTOs);
            return ResponseEntity.ok( new ApiResponse("Get user success", userDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest,@PathVariable Long userId){
        try {
            User user = iUserService.updateUser(updateUserRequest, userId);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Update user success!", userDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            iUserService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete user success with id = " + userId, null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
