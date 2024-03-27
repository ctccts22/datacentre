package ai.datacentre.v2.common.service.member.impl;

import ai.datacentre.v2.common.mapper.member.MemberMapper;
import ai.datacentre.v2.common.model.dto.*;
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

    private boolean checkPassword(String checkPassword, String password) {
        return Objects.equals(checkPassword, password);
    }

    @Override
    @Transactional
    public void registerMember(RegisterMemberDTO registerMemberDTO) {
        var checkDuplicate = isDuplicate(registerMemberDTO);
        if (checkDuplicate) {
            throw new DuplicateKeyException("중복된 값이 존재합니다.");
        }
        var checkPassword = checkPassword(registerMemberDTO.getCheckPassword(), registerMemberDTO.getPassword());
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

    @Override
    @Transactional
    public void updateMember(String username, UpdateMemberDTO updateMemberDTO) {
        var member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        var checkPassword = checkPassword(updateMemberDTO.getCheckPassword(), updateMemberDTO.getPassword());
        if (!checkPassword) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        UpdateMemberDTO updateMemberData = UpdateMemberDTO.builder()
                .username(username)
                .password(passwordEncoder.encode(updateMemberDTO.getPassword()))
                .name(updateMemberDTO.getName())
                .role(updateMemberDTO.getRole())
                .status(updateMemberDTO.getStatus())
                .ldate(LocalDateTime.now())
                .build();
        // convert DTO to Entity
        Member updateMemberEntity = Member.builder()
                .username(updateMemberData.getUsername())
                .password(updateMemberData.getPassword())
                .name(updateMemberData.getName())
                .role(updateMemberData.getRole())
                .status(updateMemberData.getStatus())
                .ldate(updateMemberData.getLdate())
                .build();
        memberRepository.save(updateMemberEntity);
    }

}
