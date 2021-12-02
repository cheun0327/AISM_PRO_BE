package com.upvote.aismpro.security;

import com.upvote.aismpro.entity.User;
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

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
        return userRepository.findAllByUserId(Long.parseLong(id))
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(id + " -> 해당 아이디는 DB에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(com.upvote.aismpro.entity.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getUserId()),
                "{noop}"+user.getEmail(),
                Collections.singleton(grantedAuthority)
        );
    }
}
