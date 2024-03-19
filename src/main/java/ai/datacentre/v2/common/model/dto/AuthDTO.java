package ai.datacentre.v2.common.model.dto;

import ai.datacentre.v2.common.model.enums.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthDTO {
    private String username;
    private Role role;

}