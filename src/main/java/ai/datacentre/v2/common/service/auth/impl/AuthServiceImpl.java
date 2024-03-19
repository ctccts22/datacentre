package ai.datacentre.v2.common.service.auth.impl;

import ai.datacentre.v2.common.mapper.member.MemberMapper;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Value("${app.refresh-token-expiration-milliseconds}")
    private long refreshTokenExpirationDate;

    @Override
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
            // RefreshToken generator
            RefreshTokenDTO refreshToken = jwtTokenProvider.generateRefreshToken(username);

            // check refreshToken available
            Optional<RefreshToken> existingRefreshTokenOpt = refreshTokenRepository.findById(authenticationEntity.getUsername());
            if (existingRefreshTokenOpt.isPresent()) {
                RefreshToken existingRefreshToken = existingRefreshTokenOpt.get();
                existingRefreshToken.setToken(refreshToken.getToken()); // update refreshToken
                refreshTokenRepository.save(existingRefreshToken); // update
            } else {
                refreshTokenRepository.save(refreshToken); // insert
            }
            return new JWTAuthResponse(jwt, "Bearer", refreshToken.getToken());
            
        } catch (Exception e) {
            log.error("Error during authentication: ", e);
            throw e;
        }
    }

    @Override
    public void logout(String refreshToken) {

    }

    @Override
    public JWTAuthResponse refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public AuthDTO getUserDetails(String memberId) {
        return null;
    }
}
