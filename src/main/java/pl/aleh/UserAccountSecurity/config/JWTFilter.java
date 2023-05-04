package pl.aleh.UserAccountSecurity.config;


import com.auth0.jwt.exceptions.JWTVerificationException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.aleh.UserAccountSecurity.security.JWTUtil;
import pl.aleh.UserAccountSecurity.services.PersonDetailsService;

@Component
public class JWTFilter extends OncePerRequestFilter {

  private final PersonDetailsService personDetailsService;
  private final JWTUtil jwtUtil;

  @Autowired
  public JWTFilter(final PersonDetailsService personDetailsService, final JWTUtil jwtUtil) {
    this.personDetailsService = personDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {
    var authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
      var jwt = authorizationHeader.substring(7);

      if (jwt.isBlank()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
      } else {
        try {

          var username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
          var userDetails = personDetailsService.loadUserByUsername(username);
          var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
              userDetails.getPassword(),
              userDetails.getAuthorities());

          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          }
        } catch (JWTVerificationException e) {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
