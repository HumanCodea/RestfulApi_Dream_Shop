package com.shoppproduct.dream_shops.auth.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = true, length = 500)
    @NotBlank(message = "Please enter the refresh token value!")
    private String refreshToken;

    @Column(nullable = true)
    private Instant expirationTime;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

}
