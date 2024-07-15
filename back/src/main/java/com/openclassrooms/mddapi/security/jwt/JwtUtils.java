package com.openclassrooms.mddapi.security.jwt;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Service
@Data
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    //private final String KEY = System.getenv("JWT_KEY");
    private final String KEY = "9Yb$Pc5Jd8Af#Bg2Ek3Hm6Np9Rs4Tu7Wx0Zq3Rv6Yb9Ec2Vf5Ih8Mj1Lk4Nm7Qo#";
    private final String SECRET_KEY_STRING = this.encodeStringToBase64(KEY);
    private final SecretKey SECRET_KEY = this.getSigningKey();
    private static final long EXPIRATION_TIME = 3600 * 1000;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        LOGGER.info("Generating token...");

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(authToken).getPayload();
            return true;
        } catch (InvalidKeyException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Get the signing key used to generate and validate tokens.
     *
     * @return SecretKey the signing key used for token generation and
     * validation
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY_STRING);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Encodes a string to Base64.
     *
     * @param string the string to be encoded
     * @return the encoded string in Base64 format
     */
    private String encodeStringToBase64(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

}
