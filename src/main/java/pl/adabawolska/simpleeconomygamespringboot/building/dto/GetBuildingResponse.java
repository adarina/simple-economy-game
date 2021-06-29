package pl.adabawolska.simpleeconomygamespringboot.building.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetBuildingResponse {

    private Long mudGatherersCottageQuantity;

    private Long stoneQuarryQuantity;

    private Long huntersHutQuantity;

    private boolean goblinsCavernOwnership;

    private boolean orcsPitOwnership;

    private boolean trollsCaveOwnership;

    public static Function<Building, GetBuildingResponse> entityToDtoMapper() {
        return building -> GetBuildingResponse.builder()
                .mudGatherersCottageQuantity(building.getMudGatherersCottageQuantity())
                .stoneQuarryQuantity(building.getStoneQuarryQuantity())
                .huntersHutQuantity(building.getHuntersHutQuantity())
                .goblinsCavernOwnership(building.isGoblinsCavernOwnership())
                .orcsPitOwnership(building.isOrcsPitOwnership())
                .trollsCaveOwnership(building.isTrollsCaveOwnership())
                .build();
    }
}
