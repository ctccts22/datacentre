package ai.datacentre.v2.common.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterMemberDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String checkPassword;
    @NotBlank
    @Email
    private String email;
    private String name;

    @QueryProjection
    public RegisterMemberDTO(String username, String password, String checkPassword, String email, String name) {
        this.username = username;
        this.password = password;
        this.checkPassword = checkPassword;
        this.email = email;
        this.name = name;
    }
}
