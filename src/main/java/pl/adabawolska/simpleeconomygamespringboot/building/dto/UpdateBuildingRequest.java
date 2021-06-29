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

    private Long mudGatherersCottageQuantity;

    private Long stoneQuarryQuantity;

    private Long huntersHutQuantity;

    private boolean goblinsCavernOwnership;

    private boolean orcsPitOwnership;

    private boolean trollsCaveOwnership;

    public static BiFunction<Building, UpdateBuildingRequest, Building> dtoToEntityUpdater() {
        return (building, request) -> {
            building.setMudGatherersCottageQuantity(request.getMudGatherersCottageQuantity());
            building.setStoneQuarryQuantity(request.getStoneQuarryQuantity());
            building.setHuntersHutQuantity(request.getHuntersHutQuantity());
            building.setGoblinsCavernOwnership(request.isGoblinsCavernOwnership());
            building.setOrcsPitOwnership(request.isOrcsPitOwnership());
            building.setTrollsCaveOwnership(request.isTrollsCaveOwnership());
            return building;
        };
    }
}
