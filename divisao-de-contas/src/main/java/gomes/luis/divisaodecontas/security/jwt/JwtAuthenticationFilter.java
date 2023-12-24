package gomes.luis.divisaodecontas.security.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    private final JwtDecoder tokenDecoder;

    JwtAuthenticationFilter(JwtDecoder jwtDecoder){
        this.tokenDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            authorizeRequest(request, response, filterChain);
        } catch (Exception e){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
    }

    private void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = getAccessTokenFromHeader(request);
        if(StringUtils.isEmpty(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        JWTClaimsSet claimsSet = tokenDecoder.decode(accessToken)
                .orElseThrow(() -> new AccessDeniedException("Could not decode token."));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claimsSet.getSubject(),
                accessToken,
                getSimpleGrantedAuthorities(claimsSet));
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

    private List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(JWTClaimsSet claimsSet) {
        return tokenDecoder.getAuthoritiesFromClaimsSet(claimsSet).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
