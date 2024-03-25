package ai.datacentre.v2.common.service.member.impl;

import ai.datacentre.v2.common.mapper.member.MemberMapper;
import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.dto.MemberFindConditionDTO;
import ai.datacentre.v2.common.model.dto.MemberSearchConditionDTO;
import ai.datacentre.v2.common.model.dto.RegisterMemberDTO;
import ai.datacentre.v2.common.model.entity.Member;
import ai.datacentre.v2.common.model.enums.Role;
import ai.datacentre.v2.common.model.enums.Status;
import ai.datacentre.v2.common.repository.member.MemberRepository;
import ai.datacentre.v2.common.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    private boolean isDuplicate(RegisterMemberDTO registerMemberDTO) {
        var usernameDup = memberRepository.findByUsername(registerMemberDTO.getUsername());
        var emailDup = memberRepository.findByEmail(registerMemberDTO.getEmail());
        return usernameDup.isPresent() || emailDup.isPresent();
    }

    private boolean checkPassword(RegisterMemberDTO registerMemberDTO) {
        return Objects.equals(registerMemberDTO.getCheckPassword(), registerMemberDTO.getPassword());
    }

    @Override
    @Transactional
    public void registerMember(RegisterMemberDTO registerMemberDTO) {
        var checkDuplicate = isDuplicate(registerMemberDTO);
        if (checkDuplicate) {
            throw new DuplicateKeyException("중복된 값이 존재합니다.");
        }
        var checkPassword = checkPassword(registerMemberDTO);
        if (!checkPassword) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        MemberDTO memberData = MemberDTO.builder()
                .username(registerMemberDTO.getUsername())
                .password(passwordEncoder.encode(registerMemberDTO.getPassword()))
                .email(registerMemberDTO.getEmail())
                .name(registerMemberDTO.getName())
                .role(Role.USER)
                .status(Status.DELETED)
                .rdate(LocalDateTime.now())
                .ldate(null)
                .build();
        Member memberEntity = memberMapper.toEntity(memberData);
        memberRepository.save(memberEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberFindConditionDTO> getAllMembers(MemberSearchConditionDTO condition, Pageable pageable) {
        return memberRepository.findAllMembers(condition, pageable);
    }

}
