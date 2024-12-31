package com.shoppproduct.dream_shops.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {

    // ModelMapper là famework dùng để convert sang DTO
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }   

}
