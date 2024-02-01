package com.backend.trawisa.service;

import com.backend.trawisa.utils.CommonApiUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserInfoService implements UserDetailsService {

    private final CommonApiUtils apiUtils;

    public JwtUserInfoService(CommonApiUtils apiUtils) {
        this.apiUtils = apiUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return apiUtils.getUserById(username);
    }
}
