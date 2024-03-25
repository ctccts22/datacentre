package ai.datacentre.v2.common.model.dto;

import ai.datacentre.v2.common.model.enums.Role;
import ai.datacentre.v2.common.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchConditionDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private Role role;
    private Status status;
    private LocalDateTime rdateStart;
    private LocalDateTime rdateEnd;
}
