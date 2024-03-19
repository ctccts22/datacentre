package ai.datacentre.v2.common.model.security;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    @NotBlank(message = "Username or Email 공란일수 없습니다.")
    private String usernameOrEmail;

    @NotBlank(message = "비밀번호는 공란일수 없습니다.")
    private String password;
}
