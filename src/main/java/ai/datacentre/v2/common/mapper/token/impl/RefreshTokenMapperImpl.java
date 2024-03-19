package ai.datacentre.v2.common.mapper.token.impl;

import ai.datacentre.v2.common.mapper.token.RefreshTokenMapper;
import ai.datacentre.v2.common.model.dto.RefreshTokenDTO;
import ai.datacentre.v2.common.model.entity.RefreshToken;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

    @Override
    public RefreshTokenDTO toDto(RefreshToken refreshToken) {
        return RefreshTokenDTO.builder()
                .token(refreshToken.getToken())
                .username(refreshToken.getUsername())
                .expiryDate(refreshToken.getExpiryDate())
                .build();
    }

    @Override
    public RefreshToken toEntity(RefreshTokenDTO refreshTokenDTO) {
        return RefreshToken.builder()
                .token(refreshTokenDTO.getToken())
                .username(refreshTokenDTO.getUsername())
                .expiryDate(refreshTokenDTO.getExpiryDate())
                .build();
    }
}
