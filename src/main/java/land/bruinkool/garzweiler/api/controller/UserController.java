package land.bruinkool.garzweiler.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import land.bruinkool.garzweiler.api.model.UserModel;
import land.bruinkool.garzweiler.entity.User;
import land.bruinkool.garzweiler.security.Authenticatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "users")
@RequestMapping("/api/v1/user")
public class UserController {
    private final Authenticatable auth;

    @Autowired
    public UserController(Authenticatable auth) {
        this.auth = auth;
    }

    @GetMapping("/")
    @Operation(tags = {"app"})
    UserModel getUser() {
        User user = auth.getUser();
        return new UserModel(user.getId(), user.getEmailAddress(), user.getName());
    }
}
