package com.sociallogin.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sociallogin.auth.AuthInfo;
import com.sociallogin.common.UnAuthorizedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

    private final long accessTokenExpirationDayToMills;
    private final Algorithm algorithm;

    public JwtService(JwtProperty jwtProperty) {
        this.accessTokenExpirationDayToMills = jwtProperty.accessTokenExpirationDay() * 24 * 60 * 60 * 1000;
        this.algorithm = Algorithm.HMAC512(jwtProperty.secretKey());
    }

    public String createToken(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        return JWT.create()
                .withExpiresAt(new Date(
                        accessTokenExpirationDayToMills + System.currentTimeMillis()
                ))
                .withIssuedAt(new Date())
                .withClaim("body", claims)
                .sign(algorithm);
    }
    public AuthInfo extractMemberId(String token) {
        try {

            Map<String, Object> claims = JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getClaim("body")
                    .asMap();

            Object memberIdObj = claims.get("memberId");
            Long memberId = null;
            if (memberIdObj instanceof Integer) {
                memberId = ((Integer) memberIdObj).longValue();
            } else if (memberIdObj instanceof Long) {
                memberId = (Long) memberIdObj;
            }

            String role = (String) claims.get("role");

            if (memberId == null) {
                throw new UnAuthorizedException("유효하지 않은 토큰입니다.");
            }

            return new AuthInfo(memberId);
        } catch (JWTVerificationException e) {
            throw new UnAuthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
