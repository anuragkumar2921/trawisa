package com.backend.trawisa.security.jwt;

import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.model.entity.UserEntity;
import com.backend.trawisa.utils.CommonApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private final AuthenticationManager manager;

    private final JwtHelper helper;

    private final UserDetailsService userDetailsService;
    private final MultiLangMessage langMessage;

    private final CommonApiUtils commonApiUtils;

    @Autowired
    public JwtUtils(AuthenticationManager manager, JwtHelper helper, UserDetailsService userDetailsService, MultiLangMessage langMessage, CommonApiUtils commonApiUtils) {
        this.manager = manager;
        this.helper = helper;
        this.userDetailsService = userDetailsService;
        this.langMessage = langMessage;
        this.commonApiUtils = commonApiUtils;
    }

    public void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(langMessage.getLocalizeMessage("invalidUserNamePass"));
        }
    }

    public String getJwtToken(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        String token = this.helper.generateToken(userDetails);
        return token;
    }

    public String getJwtUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }


    public UserEntity getCurrentUser() {
        return commonApiUtils.getUserById(getJwtUserName());
    }

}
