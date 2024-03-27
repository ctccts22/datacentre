package ai.datacentre.v2.common.repository.member.impl;

import ai.datacentre.v2.common.model.dto.*;
import ai.datacentre.v2.common.model.entity.Member;
import ai.datacentre.v2.common.model.enums.Role;
import ai.datacentre.v2.common.model.enums.Status;
import ai.datacentre.v2.common.repository.member.MemberRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ai.datacentre.v2.common.model.entity.QMember.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
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

    @Override
    public Page<MemberFindConditionDTO> findAllMembers(MemberSearchConditionDTO condition, Pageable pageable) {
        List<MemberFindConditionDTO> content = queryFactory
                .select(new QMemberFindConditionDTO(
                        member.username,
                        member.name,
                        member.email,
                        member.role,
                        member.status,
                        member.rdate,
                        member.ldate
                ))
                .from(member)
                .where(nameEq(condition.getName()),
                        statusEq(condition.getStatus()),
                        roleEq(condition.getRole()),
                        dateBetween(condition.getRdateStart(), condition.getRdateEnd())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .fetch();
        JPAQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .where(nameEq(condition.getName()),
                        statusEq(condition.getStatus()),
                        roleEq(condition.getRole()),
                        dateBetween(condition.getRdateStart(), condition.getRdateEnd())
                );

        log.info("pageable: {}", pageable);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
        return sort.stream()
                .map(o -> new OrderSpecifier(
                        o.getDirection().isAscending() ? Order.ASC : Order.DESC,
                        Expressions.stringPath(o.getProperty())
                ))
                .toArray(OrderSpecifier[]::new);
    }

    private BooleanExpression dateBetween(LocalDateTime dateStart, LocalDateTime dateEnd) {
        if (dateStart == null || dateEnd == null) {
            return null;
        }
        return member.rdate.between(dateStart, dateEnd);
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? member.name.contains(name) : null;
    }
    private BooleanExpression statusEq(Status status) {
        return status != null ? member.status.eq(status) : null;
    }

    private BooleanExpression roleEq(Role role) {
        return role != null ? member.role.eq(role) : null;
    }

}
