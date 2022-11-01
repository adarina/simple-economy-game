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
public class BuildingPropertiesTest {

    @Autowired
    BuildingProperties buildingProperties;

    @Test
    public void whenBuildingQueried_thenReturnsNotNull() {
        Assert.assertNotNull("Building is null!", buildingProperties.getMudGatherersCottageMudCost());
    }

    @Test
    public void whenBuildingQueried_thenReturnsEquals() {
        Assert.assertEquals("Building is not equal!", 100L, (long) buildingProperties.getMudGatherersCottageMudCost());
    }

    @Test
    public void givenBuildingMudGatherersCottageMudCost_whenVariableOneRetrieved_thenReturnsEqual() {
        Long output = buildingProperties.getMudGatherersCottageMudCost();
        assertThat(output).isEqualTo(100L);
    }
}