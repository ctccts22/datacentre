package ai.datacentre.v2.common.controller;

import ai.datacentre.v2.common.model.dto.MemberFindConditionDTO;
import ai.datacentre.v2.common.model.dto.MemberSearchConditionDTO;
import ai.datacentre.v2.common.model.dto.RegisterMemberDTO;
import ai.datacentre.v2.common.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/registration")
    public ResponseEntity<String> memberRegister(@Valid @RequestBody RegisterMemberDTO registerMemberDTO) {
        try {
            memberService.registerMember(registerMemberDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
        } catch (ServiceException e) {
            log.error("회원가입 도중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 오류");
        }
    }
    @GetMapping("/member-management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<MemberFindConditionDTO>> getAllMembers(MemberSearchConditionDTO condition, Pageable pageable) {
        log.info("pageable: {}", pageable);
        log.info("condition: {}", condition);
        Page<MemberFindConditionDTO> members = memberService.getAllMembers(condition, pageable);
        return ResponseEntity.ok(members);
    }
}
