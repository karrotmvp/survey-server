package com.daangn.survey.core.auth.jwt.component;


import com.daangn.survey.core.error.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    @Value("${jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public boolean validateToken(String jwtToken){
        return validateTokenBefore(jwtToken, new Date());
    }

    public boolean validateTokenBefore(String jwtToken, Date date){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(date);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Throws:
     * UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
     * MalformedJwtException – if the claimsJws string is not a valid JWS
     * SignatureException – if the claimsJws JWS signature validation fails
     * ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
     * IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
     */
    public String setInvalidAuthenticationMessage(String jwt){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
            return "Logic Error : Should Tell to Backend";
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            return ErrorCode.UNSUPPORTED_JWT.getMessage();
        } catch (ExpiredJwtException e) {
            return ErrorCode.EXPIRED_JWT.getMessage();
        } catch (SignatureException e) {
            return ErrorCode.SIGNATURE_INVALID_JWT.getMessage();
        } catch (IllegalArgumentException e) {
            return ErrorCode.JWT_NOT_FOUND.getMessage();
        }
    }
}