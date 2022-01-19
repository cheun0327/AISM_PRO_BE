package com.upvote.aismpro.security;

import com.upvote.aismpro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Transactional
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // DB에서 Id로 사용자 이름 가져오기
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(id))
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(id + " -> 해당 아이디는 DB에서 찾을 수 없습니다."));
    }

    // spring security UserDetail 객체 생성
    private UserDetails createUserDetails(com.upvote.aismpro.entity.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getUserId()),
                null,
                Collections.singleton(grantedAuthority)
        );
    }
}
