package pl.adabawolska.simpleeconomygamespringboot.building.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:building.properties")
public class BuildingProperties {

    @Value("${building.Mud_Gatherers_Cottage_mud}")
    private Long mudGatherersCottageMudCost;
    @Value("${building.Mud_Gatherers_Cottage_stone}")
    private Long mudGatherersCottageStoneCost;
    @Value("${building.Stone_Quarry_mud}")
    private Long stoneQuarryMudCost;
    @Value("${building.Stone_Quarry_stone}")
    private Long stoneQuarryStoneCost;
    @Value("${building.Hunters_Hut_mud}")
    private Long huntersHutMudCost;
    @Value("${building.Hunters_Hut_stone}")
    private Long huntersHutStoneCost;
    @Value("${building.Goblins_Cavern_mud}")
    private Long goblinsCavernMudCost;
    @Value("${building.Goblins_Cavern_stone}")
    private Long goblinsCavernStoneCost;
    @Value("${building.Orcs_Pit_mud}")
    private Long orcsPitMudCost;
    @Value("${building.Orcs_Pit_stone}")
    private Long orcsPitStoneCost;
    @Value("${building.Trolls_Cave_mud}")
    private Long trollsCaveMudCost;
    @Value("${building.Trolls_Cave_stone}")
    private Long trollsCaveStoneCost;

    @Value("${building.Mud_Gatherers_Cottage_Prod}")
    private Long mudGatherersCottageProd;
    @Value("${building.Stone_Quarry_Prod}")
    private Long stoneQuarryProd;
    @Value("${building.Hunters_Hut_Prod}")
    private Long huntersHutProd;
}
