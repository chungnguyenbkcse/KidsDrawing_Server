package com.app.kidsdrawing.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.RoleRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthUtil {
    private final MessageSource messageSource;

    @Value("${jwt_secret}")
    private String secret;
    private long accessTokenDuration = 600000;
    private long refreshTokenDuration = 3600000;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public String generateAccessToken(String username, List<String> role_privilege)
            throws IllegalArgumentException, JWTCreationException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id", user.getId())
                .withClaim("username", username)
                .withClaim("role_privilege", role_privilege)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenDuration))
                .withIssuedAt(new Date())
                .withIssuer("kidspainting/backdend/kidspainting")
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenDuration))
                .withIssuedAt(new Date())
                .withIssuer("kidspainting/backdend/kidspainting")
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT validateTokenAndRetrieveDecoded(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("kidspainting/backdend/kidspainting")
                .build();
        return verifier.verify(token);
    }

    public Optional<String> getJwtFromBearer(String authHeader) {
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                messageSource.getMessage("exception.jwt.bearer_invalid", null, LocaleContextHolder.getLocale());
                return Optional.empty();
            } else {
                return Optional.ofNullable(jwt);
            }
        } else {
            messageSource.getMessage("exception.jwt.bearer_invalid", null, LocaleContextHolder.getLocale());
            return Optional.empty();
        }
    }

    public UsernamePasswordAuthenticationToken getAuthenticationFromToken(final String jwt) {
        DecodedJWT decodedJWT = validateTokenAndRetrieveDecoded(jwt);
        // Get authorization info
        String username = decodedJWT.getSubject();
        String[] roles_privileges = decodedJWT.getClaim("role_privilege").asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = parseAuthoritiesFromRoleNames(roles_privileges);
        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    public Collection<SimpleGrantedAuthority> parseAuthoritiesFromRoleNames(String[] roleNames) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roleNames).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    public List<String> parseRoleNamesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        List<String> roleNames = authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return roleNames;
    }

    public Collection<SimpleGrantedAuthority> parseAuthoritiesFromRoles(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    public Set<GrantedAuthority> getAuthorities(List<String> rolesName) {

        List<Role> roles = new ArrayList<>();
        rolesName.forEach(role -> {
            Optional<Role> roleOpt = roleRepository.findByName(role);
            Role role_item = roleOpt.orElseThrow(() -> {
                throw new EntityNotFoundException("exception.role.not_found");
            });
            roles.add(role_item);
        });

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().forEach(privilege -> {
                authorities.add(new SimpleGrantedAuthority(privilege.getName()));
            });
        });
        return authorities;
    }
}
