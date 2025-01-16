package com.shoppproduct.dream_shops.auth.service.Imp;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.utils.dto.UserDTO;
import com.shoppproduct.dream_shops.utils.request.UpdateUserRequest;

public interface IUserService {
    
    User getUserById(Long userId);
    User updateUser(UpdateUserRequest updateUserRequest, Long userId);
    void deleteUser(Long userId);
    UserDTO convertUserToDTO(User user);
    User getAuthenticatedUser();

}
