package com.shoppproduct.dream_shops.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.auth.dto.UserDTO;
import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.service.IUserService;
import com.shoppproduct.dream_shops.exception.AlreadyExistsException;
import com.shoppproduct.dream_shops.exception.UserNotFoundException;
import com.shoppproduct.dream_shops.request.CreateUserRequest;
import com.shoppproduct.dream_shops.request.UpdateUserRequest;
import com.shoppproduct.dream_shops.response.ApiResponse;

@RestController
@RequestMapping(path = "${api.prefix}/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId){
        try {
            User user = iUserService.getUserById(userId);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            return ResponseEntity.ok( new ApiResponse("Get user success", userDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        try {
            User user = iUserService.createUser(createUserRequest);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Create user success", userDTO));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error", e.getMessage()));
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
