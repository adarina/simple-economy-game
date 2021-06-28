package pl.adabawolska.simpleeconomygamespringboot.unit.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUnitRequest {

    private Long goblinArcherQuantity;

    private Long orcWarriorQuantity;

    private Long uglyTrollQuantity;

    public static BiFunction<Unit, pl.adabawolska.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest, Unit> dtoToEntityUpdater() {
        return (unit, request) -> {
            unit.setGoblinArcherQuantity(request.getGoblinArcherQuantity());
            unit.setOrcWarriorQuantity(request.getOrcWarriorQuantity());
            unit.setUglyTrollQuantity(request.getUglyTrollQuantity());
            return unit;
        };
    }
}
