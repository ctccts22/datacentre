package ai.datacentre.v2.common.security.service;

import ai.datacentre.v2.common.model.dto.MemberDTO;
import ai.datacentre.v2.common.model.security.UserDetailsImpl;
import ai.datacentre.v2.common.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        MemberDTO memberEntity = memberRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("아이디 또는 이메일을 찾을수 없습니다. " + usernameOrEmail));
        return new UserDetailsImpl(memberEntity);
    }
}
