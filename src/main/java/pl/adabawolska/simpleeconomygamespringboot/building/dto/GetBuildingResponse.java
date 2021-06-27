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

    private Long mudGatherersCottage;

    private Long stoneQuarry;

    private Long huntersHut;

    private boolean goblinsCavern;

    private boolean orcsPit;

    private boolean trollsCave;

    public static Function<Building, GetBuildingResponse> entityToDtoMapper() {
        return building -> GetBuildingResponse.builder()
                .mudGatherersCottage(building.getMudGatherersCottageQuantity())
                .stoneQuarry(building.getStoneQuarryQuantity())
                .huntersHut(building.getHuntersHutQuantity())
                .goblinsCavern(building.isGoblinsCavernOwnership())
                .orcsPit(building.isOrcsPitOwnership())
                .trollsCave(building.isTrollsCaveOwnership())
                .build();
    }
}
