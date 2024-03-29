package ai.datacentre.v2.common.model.dto;

import ai.datacentre.v2.common.model.enums.Role;
import ai.datacentre.v2.common.model.enums.Status;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private Role role;
    private Status status;
    private LocalDateTime rdate;
    private LocalDateTime ldate;

    @QueryProjection
    public MemberDTO(Long id, String username, String password, String name, String email, Role role, Status status, LocalDateTime rdate, LocalDateTime ldate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.rdate = rdate;
        this.ldate = ldate;
    }
}