package springsecurity.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_account")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1121571114006195880L;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    private String email;

    private String password;
}
