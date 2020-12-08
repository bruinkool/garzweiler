package land.bruinkool.garzweiler.api.controller;

import land.bruinkool.garzweiler.api.request.LoginRequest;
import land.bruinkool.garzweiler.api.request.SignupRequest;
import land.bruinkool.garzweiler.api.response.MessageResponse;
import land.bruinkool.garzweiler.api.role.RoleService;
import land.bruinkool.garzweiler.repository.UserRepository;
import land.bruinkool.garzweiler.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    private AuthController authController;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        this.authController = new AuthController(authenticationManager, userRepository, roleService, passwordEncoder, jwtUtils);
    }

    @Test
    void ping() {
        assertEquals(ResponseEntity.noContent().build(), authController.ping());
    }

    @Test
    void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmailAddress("test@local.dev");
        loginRequest.setPassword("secret");

        Mockito.when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenReturn(
                new UsernamePasswordAuthenticationToken("foo", "bar", AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE_USER")));
        Mockito.when(jwtUtils.generateJwtToken(Mockito.<Authentication>any())).thenReturn("SOMEJWTTOKEN");

        assertEquals(ResponseEntity.ok("SOMEJWTTOKEN"), authController.login(loginRequest));
    }

    @Test
    void signUp() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmailAddress("new@local.dev");
        signupRequest.setName("New user");
        signupRequest.setPassword("reallySecret");

        Mockito.when(userRepository.existsByEmailAddress("new@local.dev")).thenReturn(false);

        assertEquals(
                ResponseEntity.ok(new MessageResponse("User registered successfully.")),
                authController.signUp(signupRequest)
        );
    }

    @Test
    void signUpAlreadyExists() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmailAddress("new@local.dev");
        signupRequest.setName("New user");
        signupRequest.setPassword("reallySecret");

        Mockito.when(userRepository.existsByEmailAddress("new@local.dev")).thenReturn(true);

        assertEquals(
                ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email address is already in use.")),
                authController.signUp(signupRequest)
        );
    }
}