package com.machineLearning.filmSuggestionWeb.util;


import com.machineLearning.filmSuggestionWeb.dto.response.ResLoginDTO;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SecurityUtil {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Value("${booksuggestion.jwt.base64-secret}")
    private String jwtKey;

    @Value("${booksuggestion.jwt.access-token-validity-in-seconds}")
    private Long accessExpiration;
    @Value("${booksuggestion.jwt.refresh-token-validity-in-seconds}")
    public Long refreshExpiration;

    private final JwtEncoder jwtEncoder;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public SecretKey getSecretKey(){
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes,0,keyBytes.length,JWT_ALGORITHM.getName());
    }

    public String createAccessToken(Authentication authentication) {

        Instant now = Instant.now();
        Instant validity = now.plus(accessExpiration, ChronoUnit.SECONDS);
        List<String> authorities=authentication.getAuthorities().stream()
                .map(s->s.getAuthority())
                .collect(Collectors.toList());


        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("user", authentication)
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(s->s.getAuthority())
                        .collect(Collectors.toList()))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String createRefreshToken(String userName, ResLoginDTO resLoginDTO){
        Instant now = Instant.now();
        Instant validity = now.plus(refreshExpiration, ChronoUnit.SECONDS);


        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(userName)
                .claim("user", resLoginDTO.getUser())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

    }



}
