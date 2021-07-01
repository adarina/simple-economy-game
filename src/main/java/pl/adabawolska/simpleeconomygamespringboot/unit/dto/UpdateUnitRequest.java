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

    private String type;

    private Long amount;

    private Boolean active;

    public static BiFunction<Unit, pl.adabawolska.simpleeconomygamespringboot.unit.dto.UpdateUnitRequest, Unit> dtoToEntityUpdater() {
        return (unit, request) -> {
            unit.setAmount(request.getAmount());
            return unit;
        };
    }
}
