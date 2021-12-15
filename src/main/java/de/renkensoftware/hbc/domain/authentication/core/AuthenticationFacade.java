package de.renkensoftware.hbc.domain.authentication.core;

import de.renkensoftware.hbc.domain.authentication.InvalidPasswordException;
import de.renkensoftware.hbc.domain.authentication.core.model.Authentication;
import de.renkensoftware.hbc.domain.authentication.core.model.Token;
import de.renkensoftware.hbc.domain.authentication.core.ports.AuthenticationIncomingPort;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade implements AuthenticationIncomingPort {

    private final UserIncomingPort userIncomingPort;

    public static final long JWT_TOKEN_VALIDITY = 5L * 60L * 60L;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Token createToken(final Authentication authentication) {
        authenticate(authentication);

        return new Token(generateTokenString(authentication.getEmail()));
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private void authenticate(final Authentication authentication) {
        User user = userIncomingPort.findByEmail(authentication.getEmail());

        if (!Objects.equals(user.getPassword(), authentication.getPassword())) {
            throw new InvalidPasswordException();
        }
    }

    private String generateTokenString(final String email) {
        Map<String, Object> claims = new HashMap<>();

        String idString = userIncomingPort.findByEmail(email).getId().toString();

        return Jwts.builder().setClaims(claims).setSubject(idString).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
