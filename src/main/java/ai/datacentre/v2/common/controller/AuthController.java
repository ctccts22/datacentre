package ai.datacentre.v2.common.controller;

import ai.datacentre.v2.common.model.dto.AuthDTO;
import ai.datacentre.v2.common.model.security.JWTAuthResponse;
import ai.datacentre.v2.common.model.security.LoginResponse;
import ai.datacentre.v2.common.security.service.UserDetailsServiceImpl;
import ai.datacentre.v2.common.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.springframework.util.StringUtils.hasText;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final int TOKEN_EXPIRATION_SEC = 60 * 60; // 1hr

    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private void createCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        String sameSiteAttribute = "SameSite=Strict";
        response.setHeader("Set-Cookie", String.format("%s; %s", cookie.toString(), sameSiteAttribute));
        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        createCookie(response, name, "", 0);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginResponse loginResponse, HttpServletResponse response) {
        try {
            JWTAuthResponse jwtResponse = authService.login(loginResponse);
            createCookie(response, "refreshToken", jwtResponse.getRefreshToken(), TOKEN_EXPIRATION_SEC);
            return ResponseEntity.ok(jwtResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies)
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue).ifPresent(authService::logout);
        } else {
            // when user logout with forcibly delete cookie
            authService.logout(null);
        }
        //Invalidate cookies
        deleteCookie(response, "refreshToken");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No refresh token provided"));

        JWTAuthResponse jwtResponse = authService.refreshToken(refreshToken);
        createCookie(response, "refreshToken", jwtResponse.getRefreshToken(), TOKEN_EXPIRATION_SEC);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Authentication | UserDetails를 통해 유저정보 get
     */
    @GetMapping("/info")
    public ResponseEntity<AuthDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userInfo = userDetailsServiceImpl.loadUserByUsername(authentication.getName());

        String usernameOrEmail = userInfo.getUsername();
        if (hasText(usernameOrEmail)) {
            log.info("memberId : {}", usernameOrEmail);
            AuthDTO authInfoDTO = authService.getUserDetails(usernameOrEmail);
            return ResponseEntity.ok(authInfoDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
