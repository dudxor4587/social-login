package com.sociallogin.dto.request;

public record UserLoginRequest(
        String email,
        String password
) {
}
