package ai.datacentre.v2.common.repository.token;

import ai.datacentre.v2.common.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String>, RefreshTokenRepositoryCustom, QuerydslPredicateExecutor {

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
