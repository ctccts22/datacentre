package ai.datacentre.v2.common.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenDTO {
    private String token;
    private String username;
    private Date expiryDate;
}
