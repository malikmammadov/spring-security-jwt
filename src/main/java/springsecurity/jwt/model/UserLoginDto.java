package springsecurity.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto implements Serializable {

    private static final long serialVersionUID = 4004617881471915396L;

    @NotNull(message = "input.validation.constraints.email.required.message")
    @Size(max = 255, message = "input.validation.constraints.email.max.size.message")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "input.validation.constraints.email.format.message")
    private String email;

    @NotNull(message = "input.validation.constraints.password.required.message")
    private String password;
}
