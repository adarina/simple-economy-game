package pl.adabawolska.simpleeconomygamespringboot.unit.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
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

    private Long id;

    private String type;

    private Long amount;

    private Boolean active;

    private Long userId;

    public static Function<Unit, GetUnitResponse> entityToDtoMapper() {
        return unit -> GetUnitResponse.builder()
                .id(unit.getId())
                .type(unit.getType())
                .amount(unit.getAmount())
                .active(unit.getActive())
                .userId(unit.getUser().getId())
                .build();
    }
}
