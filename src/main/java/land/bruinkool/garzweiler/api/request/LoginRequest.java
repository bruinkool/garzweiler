package land.bruinkool.garzweiler.api.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank(message = "Please enter a valid email address")
    @Size(min = 6, max = 30)
    @Email
    private String emailAddress;

    @NotBlank(message = "Please enter a password")
    @Size(min = 7, max = 200)
    private String password;
}
