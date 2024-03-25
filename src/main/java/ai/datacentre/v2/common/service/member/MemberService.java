package ai.datacentre.v2.common.service.member;

import ai.datacentre.v2.common.model.dto.MemberFindConditionDTO;
import ai.datacentre.v2.common.model.dto.MemberSearchConditionDTO;
import ai.datacentre.v2.common.model.dto.RegisterMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    void registerMember(RegisterMemberDTO registerMemberDTO);

    Page<MemberFindConditionDTO> getAllMembers(MemberSearchConditionDTO condition, Pageable pageable);

}
