package pl.adabawolska.simpleeconomygamespringboot.resource.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
