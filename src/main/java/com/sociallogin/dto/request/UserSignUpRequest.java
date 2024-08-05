package com.sociallogin.dto.request;

public record UserSignUpRequest(
        String name,
        String email,
        String password
) {
}
