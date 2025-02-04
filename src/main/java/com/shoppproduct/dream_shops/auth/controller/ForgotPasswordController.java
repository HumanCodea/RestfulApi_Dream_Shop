package com.shoppproduct.dream_shops.auth.controller;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppproduct.dream_shops.auth.model.ForgotPassword;
import com.shoppproduct.dream_shops.auth.model.User;
import com.shoppproduct.dream_shops.auth.repository.ForgotPasswordRepository;
import com.shoppproduct.dream_shops.auth.repository.UserRepository;
import com.shoppproduct.dream_shops.auth.service.EmailService;
import com.shoppproduct.dream_shops.auth.utils.dto.MailBody;
import com.shoppproduct.dream_shops.auth.utils.request.ChangePasswordRequest;
import com.shoppproduct.dream_shops.exception.ForgotPasswordNotFoundException;
import com.shoppproduct.dream_shops.exception.UserNotFoundException;
import com.shoppproduct.dream_shops.utils.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "${api.prefix}/forgotPassword")
@Tag(name = "ForgotPassword Controller")
public class ForgotPasswordController {
        
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/verifyEmail/{email}")
    @Operation(summary = "Verify email", description = "Api to verify email and create otp code")
    public ResponseEntity<ApiResponse> verifyEmailHandler(@PathVariable String email){

        User user = Optional.ofNullable(userRepository.findByEmail(email))
            .orElseThrow(() -> new UserNotFoundException("Not found user with email = " + email));

        Integer otp = generateOtp();

        MailBody mailBody = MailBody.builder()
                    .to(email)
                    .text("This is the Otp for your Forgot password request: " + otp)
                    .subject("Otp for your forgot password request!")
                    .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                        .otp(otp)
                        .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                        .user(user)
                        .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(forgotPassword);

        return ResponseEntity.ok(new ApiResponse("Verify email successfully! Email sent for verification", null));

    }


    @PostMapping("/verifyOtp/{otp}/{email}")
    @Operation(summary = "Verify otp", description = "Api to otp code from message send to email")
    public ResponseEntity<ApiResponse> verifyOtpHandler(@PathVariable Integer otp, @PathVariable String email){

        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UserNotFoundException("Please provide an valid email"));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp,user)
                .orElseThrow(() -> new ForgotPasswordNotFoundException("Invalid OTP for email: " + email));

        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("OTP has expired!", null));
        }

        return ResponseEntity.ok(new ApiResponse("OTP has verified!", null));
    }

    @PostMapping("/changePassword/{email}")
    @Operation(summary = "Change password", description = "Api to create new password")
    public ResponseEntity<ApiResponse> changePasswordHandler(@PathVariable String email, 
                                                            @RequestBody ChangePasswordRequest changePasswordRequest)
    {

        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UserNotFoundException("Please provide an valid email"));

        if (!Objects.equals(changePasswordRequest.password(), changePasswordRequest.repeatPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Please enter the password again!", null));
        }
        
        String encodePassword = passwordEncoder.encode(changePasswordRequest.password());
        user.setPassword(encodePassword);
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse("Change password successfully!", null));
        
    }

    public Integer generateOtp(){
        Random random = new Random();

        return random.nextInt(100_000, 999_999);
    }


}
