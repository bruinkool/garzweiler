package land.bruinkool.garzweiler.entity;

import land.bruinkool.garzweiler.api.role.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Getter
    @Setter
    private RoleEnum name;

    public Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }
}