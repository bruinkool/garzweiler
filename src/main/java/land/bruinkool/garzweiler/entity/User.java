package land.bruinkool.garzweiler.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(indexes = { @Index(name = "IDX_EMAIL", columnList = "emailAddress") })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String emailAddress;

    private String passwordHash;

    public User() {
    }

    public User(String name, String emailAddress, String passwordHash) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
    }

    @Column
    private LocalDateTime disabledAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}