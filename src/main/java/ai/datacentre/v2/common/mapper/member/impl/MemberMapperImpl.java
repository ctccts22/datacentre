package ai.datacentre.v2.common.mapper.member.impl;

import ai.datacentre.v2.common.mapper.member.MemberMapper;
import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapperImpl implements MemberMapper {
    @Override
    public MemberDTO toDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .name(member.getName())
                .email(member.getEmail())
                .status(member.getStatus())
                .role(member.getRole())
                .rdate(member.getRdate())
                .ldate(member.getLdate())
                .build();
    }

    @Override
    public Member toEntity(MemberDTO memberDTO) {
        return Member.builder()
                .id(memberDTO.getId())
                .username(memberDTO.getUsername())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .status(memberDTO.getStatus())
                .role(memberDTO.getRole())
                .rdate(memberDTO.getRdate())
                .ldate(memberDTO.getLdate())
                .build();
    }
}
