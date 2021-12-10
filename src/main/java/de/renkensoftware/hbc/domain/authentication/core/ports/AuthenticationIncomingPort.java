package de.renkensoftware.hbc.domain.authentication.core.ports;

import de.renkensoftware.hbc.domain.authentication.core.model.Authentication;
import de.renkensoftware.hbc.domain.authentication.core.model.Token;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface AuthenticationIncomingPort {

    Token createToken(final Authentication toAuthentication);

    Boolean validateToken(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
}
