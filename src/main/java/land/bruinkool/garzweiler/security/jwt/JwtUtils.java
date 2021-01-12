package land.bruinkool.garzweiler.security.jwt;

import io.jsonwebtoken.*;
import land.bruinkool.garzweiler.security.userdetails.BruinkoolUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtAudience}")
    private String jwtAudience;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${jwtKeyId}")
    private int jwtKeyId;

    public String generateJwtToken(Authentication authentication) {
        BruinkoolUserDetails userPrincipal = (BruinkoolUserDetails) authentication.getPrincipal();

        Map<String, Object> claims = Map.of(
                "kid", jwtKeyId,
                "uid", userPrincipal.getId(),
                "roles", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray()
        );
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .setAudience(jwtAudience)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getSubjectFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
