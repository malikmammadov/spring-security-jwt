package springsecurity.jwt.filter;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import springsecurity.jwt.constant.JWTConstant;
import springsecurity.jwt.model.User;
import springsecurity.jwt.repository.UserRepository;
import springsecurity.jwt.service.UserPrincipal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTConstant.TOKEN_HEADER);
        if (header == null || !header.startsWith(JWTConstant.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWTConstant.TOKEN_HEADER)
                .replace(JWTConstant.TOKEN_PREFIX, "");
        if (!token.isEmpty()) {
            Date expiration = JWT.require(HMAC512(JWTConstant.JWT_SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getExpiresAt();
            if (expiration.after(new Date(System.currentTimeMillis()))) {
                String email = JWT.require(HMAC512(JWTConstant.JWT_SECRET.getBytes()))
                        .build()
                        .verify(token)
                        .getSubject();
                if (email != null) {
                    User user = userRepository.findUserByEmail(email);
                    UserPrincipal principal = new UserPrincipal(user);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email,
                            null,
                            principal.getAuthorities());
                    return auth;
                }
            }
        }
        return null;
    }
}
