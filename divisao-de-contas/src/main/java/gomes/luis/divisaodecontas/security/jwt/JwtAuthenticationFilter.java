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
    public static final String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            authorizeRequest(request, response, filterChain);
        } catch (Exception e){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
    }

    private static void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = getAccessTokenFromHeader(request);
        if(StringUtils.isEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        if(!accessToken.equals("123"))
            throw new AuthenticationCredentialsNotFoundException("Invalid token.");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("username", null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private static String getAccessTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.isEmpty(header))
            return header;

        return header
                .replace(TOKEN_PREFIX, "")
                .trim();
    }
}
