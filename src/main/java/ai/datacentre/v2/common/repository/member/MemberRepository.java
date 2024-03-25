package ai.datacentre.v2.common.repository.member;

import ai.datacentre.v2.common.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, QuerydslPredicateExecutor {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
}
