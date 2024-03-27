package ai.datacentre.v2.common.model.dto;

import ai.datacentre.v2.common.model.enums.Role;
import ai.datacentre.v2.common.model.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMemberDTO {
    private String username;
    private String password;
    private String checkPassword;
    private String name;
    private Role role;
    private Status status;
    private LocalDateTime ldate;

}
