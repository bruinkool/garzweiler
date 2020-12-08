package land.bruinkool.garzweiler.api.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 1, max = 150)
    private String name;

    @NotBlank
    @Size(min = 6, max = 150)
    @Email
    private String emailAddress;

    @NotBlank(message = "Please enter a password")
    @Size(min = 7, max = 200)
    private String password;
}
