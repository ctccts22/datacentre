package ai.datacentre.v2.common.mapper.token;

import ai.datacentre.v2.common.model.dto.RefreshTokenDTO;
import ai.datacentre.v2.common.model.entity.RefreshToken;

import java.util.Date;

public interface RefreshTokenMapper {

    RefreshTokenDTO toDto(RefreshToken refreshToken);

    RefreshToken toEntity(RefreshTokenDTO refreshTokenDTO);

}
