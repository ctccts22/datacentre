package ai.datacentre.v2.common.repository.token;

import ai.datacentre.v2.common.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
