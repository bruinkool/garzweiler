package land.bruinkool.garzweiler.security;

import io.sentry.Sentry;
import land.bruinkool.garzweiler.entity.User;
import land.bruinkool.garzweiler.repository.UserRepository;
import land.bruinkool.garzweiler.security.userdetails.BruinkoolUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Component
public class AuthenticationFacade implements Authenticatable {
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private BruinkoolUserDetails getUserDetails() {
        return (BruinkoolUserDetails) this.getAuthentication().getPrincipal();
    }

    public User getUser() {
        Optional<User> user = userRepository.findById(this.getUserDetails().getId());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization missing");
        }

        return user.get();
    }
}