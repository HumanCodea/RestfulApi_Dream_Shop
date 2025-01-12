package com.shoppproduct.dream_shops.auth.service;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.utils.dto.UserDTO;
import com.shoppproduct.dream_shops.utils.request.CreateUserRequest;
import com.shoppproduct.dream_shops.utils.request.UpdateUserRequest;

public interface IUserService {
    
    User getUserById(Long userId);
    User createUser(CreateUserRequest createUserRequest);
    User updateUser(UpdateUserRequest updateUserRequest, Long userId);
    void deleteUser(Long userId);
    UserDTO convertUserToDTO(User user);
    
}
