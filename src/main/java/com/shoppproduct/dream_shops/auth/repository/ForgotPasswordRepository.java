package com.shoppproduct.dream_shops.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shoppproduct.dream_shops.auth.model.ForgotPassword;
import com.shoppproduct.dream_shops.auth.model.User;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer>{

    @Query("Select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

}
