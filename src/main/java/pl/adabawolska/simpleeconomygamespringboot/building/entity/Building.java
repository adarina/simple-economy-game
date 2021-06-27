package pl.adabawolska.simpleeconomygamespringboot.building.entity;

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
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mud_gatherers_cottage_quantity")
    private Long mudGatherersCottageQuantity;

    @Column(name = "stone_quarry_quantity")
    private Long stoneQuarryQuantity;

    @Column(name = "hunters_hut_quantity")
    private Long huntersHutQuantity;

    @Column(name = "goblins_caverns_ownership")
    private boolean goblinsCavernOwnership;

    @Column(name = "orcs_pit_ownership")
    private boolean orcsPitOwnership;

    @Column(name = "trolls_cave_ownership")
    private boolean trollsCaveOwnership;

    @OneToOne(mappedBy = "building")
    @JsonIgnore
    private User user;
}
