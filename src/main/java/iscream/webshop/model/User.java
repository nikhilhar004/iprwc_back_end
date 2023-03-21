package iscream.webshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@ToString
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean default_pass;

    public User(String name, String email, UserRole role, String password, boolean default_pass) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.default_pass = default_pass;
    }

    public String getEmail() {
        return email;
    }
}
