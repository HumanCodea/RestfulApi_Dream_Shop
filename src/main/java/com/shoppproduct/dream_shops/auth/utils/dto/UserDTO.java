package com.shoppproduct.dream_shops.auth.utils.dto;

import java.util.List;

import com.shoppproduct.dream_shops.utils.dto.CartDTO;
import com.shoppproduct.dream_shops.utils.dto.OrderDTO;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CartDTO cartDTO;
    private List<OrderDTO> orderDTOs;

}
