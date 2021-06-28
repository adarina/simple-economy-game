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

    @Column(name = "goblin_archer_quantity")
    private Long goblinArcherQuantity;

    @Column(name = "orc_warrior_quantity")
    private Long orcWarriorQuantity;

    @Column(name = "ugly_troll_quantity")
    private Long uglyTrollQuantity;

    @OneToOne(mappedBy = "unit")
    @JsonIgnore
    private User user;
}
