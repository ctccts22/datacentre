package ai.datacentre.v2.common.repository.member;

import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.dto.MemberFindConditionDTO;
import ai.datacentre.v2.common.model.dto.MemberSearchConditionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<MemberDTO> findByUsernameOrEmail(String username, String email);

    Page<MemberFindConditionDTO> findAllMembers(MemberSearchConditionDTO condition, Pageable pageable);

}
