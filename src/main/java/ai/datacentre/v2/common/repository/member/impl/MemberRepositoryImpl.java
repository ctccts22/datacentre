package ai.datacentre.v2.common.repository.member.impl;

import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.dto.QMemberDTO;
import ai.datacentre.v2.common.repository.member.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static ai.datacentre.v2.common.model.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<MemberDTO> findByUsernameOrEmail(String username, String email) {
        return Optional.ofNullable(queryFactory
                .select(new QMemberDTO(
                        member.id,
                        member.username,
                        member.password,
                        member.name,
                        member.email,
                        member.role,
                        member.status,
                        member.rdate,
                        member.ldate
                ))
                .from(member)
                .where(member.username.eq(username).or(member.email.eq(email)))
                .fetchOne());
    }
}
