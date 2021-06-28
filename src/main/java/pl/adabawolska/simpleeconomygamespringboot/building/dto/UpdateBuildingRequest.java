package pl.adabawolska.simpleeconomygamespringboot.building.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateBuildingRequest {

    private Long mudGatherersCottage;

    private Long stoneQuarry;

    private Long huntersHut;

    private boolean goblinsCavern;

    private boolean orcsPit;

    private boolean trollsCave;

    public static BiFunction<Building, UpdateBuildingRequest, Building> dtoToEntityUpdater() {
        return (building, request) -> {
            building.setMudGatherersCottageQuantity(request.getMudGatherersCottage());
            building.setStoneQuarryQuantity(request.getStoneQuarry());
            building.setHuntersHutQuantity(request.getHuntersHut());
            building.setGoblinsCavernOwnership(request.isGoblinsCavern());
            building.setOrcsPitOwnership(request.isOrcsPit());
            building.setTrollsCaveOwnership(request.isTrollsCave());
            return building;
        };
    }
}
