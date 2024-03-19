package ai.datacentre.v2.common.service.auth;

import ai.datacentre.v2.common.model.dto.AuthDTO;
import ai.datacentre.v2.common.model.security.JWTAuthResponse;
import ai.datacentre.v2.common.model.security.LoginResponse;

public interface AuthService {

    JWTAuthResponse login(LoginResponse loginResponse);
    void logout(String refreshToken);
    JWTAuthResponse refreshToken(String refreshToken);
    AuthDTO getUserDetails(String memberId);
}
