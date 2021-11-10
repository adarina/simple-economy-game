package com.ada.simpleeconomygamespringboot.building.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BuildingProperties.class)
@TestPropertySource(locations = "/building.properties")
public class BuildingPropertiesIntegrationTest {

    @Autowired
    BuildingProperties buildingProperties;

    @Test
    public void whenBuildingQueriedthenReturnsBuilding() {
        Assert.assertNotNull("Building is null!", buildingProperties.getMudGatherersCottageMudCost());
    }

    @Test
    public void whenBuildingValueQueriedthenReturnsBuildingValue() {
        Assert.assertTrue("Building is not equal!", buildingProperties.getMudGatherersCottageMudCost() == 100L);
    }

    @Test
    public void givenBuildingPropertiesTest_whenVariableOneRetrieved_thenValueInFileReturned() {
        Long output = buildingProperties.getMudGatherersCottageMudCost();
        assertThat(output).isEqualTo(100L);
    }
}