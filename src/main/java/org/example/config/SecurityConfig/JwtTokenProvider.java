package org.example.config.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.common.enums.TokenTypeEnum;
import org.example.common.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    @Value("${config.jwt.secret-key-access-token}")
    private String secretKeyAccessToken;

    @Value("${config.jwt.access-token-valid-time}")
    private int accessTokenValidTime;

    @Value("${config.jwt.secret-key-refresh-token}")
    private String secretKeyRefreshToken;

    @Value("${config.jwt.refresh-token-valid-time}")
    private int refreshTokenValidTime;

    public Map<String, Object> generateToken(
            TokenTypeEnum tokenType,
            String username,
            Map<String, Object> claims
    ) {
        int validTimeMs = getValidTimeMs(tokenType);

        String token = Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validTimeMs))
                .signWith(getSignInKey(getSecretKey(tokenType)))
                .compact();

        return Map.of(
                "token", token,
                "expiresIn", validTimeMs
        );
    }

    public Claims extractAllClaims(String token, TokenTypeEnum tokenType) {
        return Jwts.parser()
                .verifyWith(getSignInKey(getSecretKey(tokenType)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(
            String token,
            TokenTypeEnum tokenType,
            Function<Claims, T> resolver
    ) {
        return resolver.apply(extractAllClaims(token, tokenType));
    }

    public boolean isTokenValid(
            String token,
            TokenTypeEnum tokenType,
            UserDetails userDetails
    ) {
        String username = extractClaim(token, tokenType, Claims::getSubject);
        Date expiration = extractClaim(token, tokenType, Claims::getExpiration);

        return username.equals(userDetails.getUsername())
                && expiration.after(new Date());
    }

    private String getSecretKey(TokenTypeEnum tokenType) {
        return tokenType == TokenTypeEnum.ACCESS_TOKEN
                ? secretKeyAccessToken
                : secretKeyRefreshToken;
    }

    private int getValidTimeMs(TokenTypeEnum tokenType) {
        int minutes = tokenType == TokenTypeEnum.ACCESS_TOKEN
                ? accessTokenValidTime
                : refreshTokenValidTime;
        return minutes * 60 * 1000;
    }

    private SecretKey getSignInKey(String secretKey) {
        byte[] keyBytes = CryptoUtil.md5Hash(secretKey).getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
