package com.shoppproduct.dream_shops.auth.utils.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
    
}
