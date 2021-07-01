package pl.adabawolska.simpleeconomygamespringboot.unit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
