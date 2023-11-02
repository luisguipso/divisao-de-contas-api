package gomes.luis.divisaodecontas.security.jwt;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            authorizeRequest(request, response, filterChain);
        } catch (Exception e){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
    }

    private static void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.isEmpty(accessToken) || !accessToken.equals("123"))
            throw new AuthenticationCredentialsNotFoundException("Invalid token.");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("username", null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
