package pl.adabawolska.simpleeconomygamespringboot.resource.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "resource")
public class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mud_quantity")
    private Long mudQuantity;

    @Column(name = "stone_quantity")
    private Long stoneQuantity;

    @Column(name = "meat_quantity")
    private Long meatQuantity;

    @OneToOne(mappedBy = "resource")
    @JsonIgnore
    private User user;
}
