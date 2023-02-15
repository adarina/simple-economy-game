package com.ada.simpleeconomygamespringboot.unit.properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UnitProperties.class)
@TestPropertySource(locations = "/unit.properties")
public class UnitPropertiesTest {

    @Autowired
    UnitProperties unitProperties;

    @Test
    public void whenUnitQueried_thenReturnsNotNull() {
        Assert.assertNotNull("Unit is null!", unitProperties.getGoblinArcherMeatCost());
    }

    @Test
    public void whenUnitQueried_thenReturnsEquals() {
        Assert.assertEquals("Unit is not equal!", 10L, (long) unitProperties.getGoblinArcherMudCost());
    }

    @Test
    public void givenUnitGoblinArcherMeatCost_whenVariableOneRetrieved_thenReturnsEqual() {
        Long output = unitProperties.getGoblinArcherMeatCost();
        assertThat(output).isEqualTo(3L);
    }
}
