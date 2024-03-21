package ai.datacentre.v2.common.service.auth.impl;

import ai.datacentre.v2.common.mapper.member.MemberMapper;
import ai.datacentre.v2.common.mapper.token.RefreshTokenMapper;
import ai.datacentre.v2.common.model.dto.AuthDTO;
import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.dto.RefreshTokenDTO;
import ai.datacentre.v2.common.model.entity.Member;
import ai.datacentre.v2.common.model.entity.RefreshToken;
import ai.datacentre.v2.common.model.security.JWTAuthResponse;
import ai.datacentre.v2.common.model.security.LoginResponse;
import ai.datacentre.v2.common.repository.member.MemberRepository;
import ai.datacentre.v2.common.repository.token.RefreshTokenRepository;
import ai.datacentre.v2.common.security.provider.JwtTokenProvider;
import ai.datacentre.v2.common.service.auth.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Value("${app.refresh-token-expiration-milliseconds}")
    private long refreshTokenExpirationDate;

    @Override
    @Transactional
    public JWTAuthResponse login(LoginResponse loginResponse) {
        String input = loginResponse.getUsernameOrEmail();
        MemberDTO authenticationEntity = memberRepository.findByUsernameOrEmail(input, input)
                .orElseThrow(() -> new EntityNotFoundException("아이디 또는 이메일을 찾을 수 없습니다."));


        Member authReq = memberMapper.toEntity(MemberDTO.builder()
                .username(authenticationEntity.getUsername())
                .password(loginResponse.getPassword())
                .build());

        try {
            Authentication authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authReq.getUsername(),
                            authReq.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authResult);
            // AccessToken
            String jwt = jwtTokenProvider.generateToken(authResult);

            // RefreshToken -> sending username
            String username = authReq.getUsername();
            boolean existUsername = refreshTokenRepository.existsByUsername(username);

            if (existUsername) {
                refreshTokenRepository.deleteByUsername(username);
            }
            // RefreshToken generator
            RefreshTokenDTO newRefreshToken = jwtTokenProvider.generateRefreshToken(username);
            jwtTokenProvider.validateRefreshToken(newRefreshToken);

            var convertNewRefreshToken = refreshTokenMapper.toEntity(newRefreshToken);
            refreshTokenRepository.save(convertNewRefreshToken);
            return new JWTAuthResponse(jwt, "Bearer", newRefreshToken.getToken());

        } catch (Exception e) {
            log.error("Error during authentication: ", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    @Override
    @Transactional
    public JWTAuthResponse refreshToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Refresh token is missing.");
        }
        RefreshToken refreshToken = refreshTokenRepository.findById(token)
                .orElseThrow(() -> new IllegalCallerException("Invalid or expired refresh token."));

        UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String jwt = jwtTokenProvider.generateToken(authentication);

        refreshTokenRepository.deleteById(token);
        RefreshTokenDTO newRefreshToken = jwtTokenProvider.generateRefreshToken(refreshToken.getUsername());

        RefreshToken newRefreshTokenEntity = refreshTokenMapper.toEntity(newRefreshToken);
        refreshTokenRepository.save(newRefreshTokenEntity);

        return new JWTAuthResponse(jwt, "Bearer", newRefreshTokenEntity.getToken());
    }

    @Override
    @Transactional
    public AuthDTO getUserDetails(String usernameOrEmail) {
        MemberDTO memberDTO = memberRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new EntityNotFoundException("정보를 찾을수 없습니다."));
        return AuthDTO.builder()
                .username(memberDTO.getUsername())
                .role(memberDTO.getRole())
                .build();
    }
}
