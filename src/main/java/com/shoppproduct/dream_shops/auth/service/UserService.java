package com.shoppproduct.dream_shops.auth.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.repository.UserRepository;
import com.shoppproduct.dream_shops.auth.service.Imp.IUserService;
import com.shoppproduct.dream_shops.auth.utils.dto.UserDTO;
import com.shoppproduct.dream_shops.exception.UserNotFoundException;
import com.shoppproduct.dream_shops.utils.request.UpdateUserRequest;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Not found user with id = " + userId));
    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest, Long userId) {
        return userRepository.findById(userId)
            .map(existingUser -> {
                existingUser.setFirstName(updateUserRequest.getFirstName());
                existingUser.setLastName(updateUserRequest.getLastName());
                return userRepository.save(existingUser);
            }).orElseThrow(() -> new UserNotFoundException("Not found user with id = " + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
            .ifPresentOrElse(userRepository :: delete, () -> new UserNotFoundException("Not found user with id = " + userId));
    }
    
    @Override
    public UserDTO convertUserToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

}
