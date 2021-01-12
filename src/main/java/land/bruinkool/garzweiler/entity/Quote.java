package land.bruinkool.garzweiler.entity;

import land.bruinkool.garzweiler.api.role.RoleEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "quotes",
        indexes = {@Index(name = "text",  columnList="text", unique = true)})
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 250)
    private String text;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime created;

    public Quote() {
        this.created = LocalDateTime.now();
    }

    public Quote(String text) {
        this();
        this.text = text;
    }
}