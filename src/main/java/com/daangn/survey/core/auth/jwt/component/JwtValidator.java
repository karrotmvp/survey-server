package com.daangn.survey.core.auth.jwt.component;


import com.daangn.survey.core.error.ErrorCode;
import io.jsonwebtoken.*;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Slf4j
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
    // TODO : 인증 예외 잡아내도록 고치기
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
            log.error("UnsupportedJwtException, MalformedJwtException");
            Sentry.captureException(e);
            return ErrorCode.UNSUPPORTED_JWT.getMessage();
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException");
            Sentry.captureException(e);
            return ErrorCode.EXPIRED_JWT.getMessage();
        } catch (SignatureException e) {
            log.error("SignatureException");
            Sentry.captureException(e);
            return ErrorCode.SIGNATURE_INVALID_JWT.getMessage();
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException");
            Sentry.captureException(e);
            return ErrorCode.JWT_NOT_FOUND.getMessage();
        }
    }
}