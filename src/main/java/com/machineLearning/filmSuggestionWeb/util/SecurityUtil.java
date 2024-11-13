package com.machineLearning.filmSuggestionWeb.util;


import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.ResLoginDTO;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public String createAccessToken(Authentication authentication, UserDTO user, boolean isRefresh) {

        Instant now = Instant.now();
        Instant validity = now.plus(accessExpiration, ChronoUnit.SECONDS);


        List<String> authorities= new ArrayList<>();
        if(isRefresh) {
            authorities.add("ROLE_" + user.getRoleCode());
        }


        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(isRefresh?user.getUserName():authentication.getName())
                .claim("user", isRefresh?user:authentication)
                .claim("authorities", isRefresh?authorities:authentication.getAuthorities().stream()
                        .map(s->s.getAuthority())
                        .collect(Collectors.toList()) )
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        System.out.println(this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue());
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
    public Jwt checkValidateRefreshToken(String refreshToken){
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(this.getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM)
                .build();

        try{
            return jwtDecoder.decode(refreshToken);
        }
        catch (Exception e){
            System.out.println("error: "+ e.getMessage());
            throw e;
        }

    }

    public String createRefreshToken(String userName, UserDTO userDTO){
        Instant now = Instant.now();
        Instant validity = now.plus(refreshExpiration, ChronoUnit.SECONDS);


        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(userName)
                .claim("user", userDTO)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

    }

    public static Optional<String> getCurrentUserLogin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication){
        if(authentication==null){
            return null;
        } else if(authentication.getPrincipal() instanceof UserDetails springSecurityUser){
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof  Jwt jwt){
            return jwt.getSubject();
        } else if(authentication.getPrincipal() instanceof String s){
            return s;
        }
        return null;
    }


    public static Optional<String> getCurrentUserJWT(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }
}
