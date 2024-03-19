package ai.datacentre.v2.common.mapper.member;

import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.entity.Member;

public interface MemberMapper {
    MemberDTO toDto(Member member);

    Member toEntity(MemberDTO memberDTO);
}
