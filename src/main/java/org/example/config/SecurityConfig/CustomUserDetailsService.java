package org.example.config.SecurityConfig;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = userRepository.selectByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        }
//
//        return User.builder().username(user.getUserName())
//                .password("{noop}" + user.getPassword())
//                .authorities(Collections.singletonList(new SimpleGrantedAuthority(
//                        user.getIsAdmin() == 1 ? "ROLE_ADMIN" : "ROLE_USER"
//                )))
//                .accountLocked(false)
//                .accountExpired(false)
//                .credentialsExpired(false)
//                .disabled(user.getIsActive() == (short) 0)
//                .build();
        return null;
    }
}
