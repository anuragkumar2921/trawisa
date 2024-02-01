package com.backend.trawisa.security.jwt;

import com.app.base.project.utils.MultiLangMessage;
import com.app.base.project.utils.Print;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.app.base.project.utils.Utils.WriteErrorResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MultiLangMessage langMessage;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        logger.info(" Header :  {}", requestHeader);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            //looking good
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (SignatureException e) {
                logger.info("Invalid Jwt Token");
                WriteErrorResponse(response, langMessage.getLocalizeMessage("invalidJWt"));
                e.printStackTrace();
                return;
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                WriteErrorResponse(response, langMessage.getLocalizeMessage("illegalJwt"));
                e.printStackTrace();
                return;
            } catch (ExpiredJwtException e) {
                Print.log("Given jwt token is expired !!");
                WriteErrorResponse(response, langMessage.getLocalizeMessage("jwtExpire"));
                e.printStackTrace();
                return;
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                WriteErrorResponse(response, langMessage.getLocalizeMessage("malformedJwt"));
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            String errorMessage = "Missing Authorization in header Jwt Filetre";
            logger.info(errorMessage);

        }


        //
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                logger.info("Validation fails !!");
            }


        }

        filterChain.doFilter(request, response);


    }
}
