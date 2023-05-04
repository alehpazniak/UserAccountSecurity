package pl.aleh.UserAccountSecurity.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(String username) {
    Date expirationDate = Date.from(ZonedDateTime.now().plusHours(1).toInstant());
    return JWT.create()
        .withSubject("User details")
        .withClaim("username", username)
        .withIssuedAt(new Date())
        .withIssuer("UserAccountSecurity")
        .withExpiresAt(expirationDate)
        .sign(Algorithm.HMAC256(secret));
  }

  public String validateTokenAndRetrieveClaim(String toke) throws JWTVerificationException {
    var jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
        .withSubject("User details")
        .withIssuer("UserAccountSecurity")
        .build();

    var jwt = jwtVerifier.verify(toke);
    return jwt.getClaim("username").asString();
  }

}
