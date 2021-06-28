package pl.adabawolska.simpleeconomygamespringboot.unit.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUnitResponse {

    private Long goblinArcherQuantity;

    private Long orcWarriorQuantity;

    private Long uglyTrollQuantity;

    public static Function<Unit, GetUnitResponse> entityToDtoMapper() {
        return unit -> GetUnitResponse.builder()
                .goblinArcherQuantity(unit.getGoblinArcherQuantity())
                .orcWarriorQuantity(unit.getOrcWarriorQuantity())
                .uglyTrollQuantity(unit.getUglyTrollQuantity())
                .build();
    }
}
