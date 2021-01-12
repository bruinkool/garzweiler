package land.bruinkool.garzweiler.api.controller;

import land.bruinkool.garzweiler.api.request.LoginRequest;
import land.bruinkool.garzweiler.api.request.SignupRequest;
import land.bruinkool.garzweiler.api.response.JwtResponse;
import land.bruinkool.garzweiler.api.response.MessageResponse;
import land.bruinkool.garzweiler.api.role.RoleEnum;
import land.bruinkool.garzweiler.api.role.RoleService;
import land.bruinkool.garzweiler.entity.Role;
import land.bruinkool.garzweiler.entity.User;
import land.bruinkool.garzweiler.repository.UserRepository;
import land.bruinkool.garzweiler.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    final AuthenticationManager authenticationManager;

    final UserRepository userRepository;

    final RoleService roleService;

    final PasswordEncoder encoder;

    final JwtUtils jwtUtils;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleService roleService,
            PasswordEncoder encoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Validate if current session is still valid. Returns HTTP 204 No Content when successful.
     */
    @GetMapping("/ping")
    @io.swagger.v3.oas.annotations.Operation(tags = {"app"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> ping() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.Operation(tags = {"app"})
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(authentication)));
    }

    @PostMapping("/signup")
    @io.swagger.v3.oas.annotations.Operation(tags = {"app"})
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmailAddress(signUpRequest.getEmailAddress())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email address is already in use."));
        }

        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmailAddress(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        //TODO: do not automatically add ROLE_USER to unverified users
        roles.add(roleService.getRole(RoleEnum.ROLE_USER));
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }
}
