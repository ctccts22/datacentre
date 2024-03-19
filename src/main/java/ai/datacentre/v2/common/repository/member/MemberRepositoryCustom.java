package ai.datacentre.v2.common.repository.member;

import ai.datacentre.v2.common.model.dto.MemberDTO;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<MemberDTO> findByUsernameOrEmail(String username, String email);
}
