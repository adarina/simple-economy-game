package com.ada.simpleeconomygamespringboot.unit.dto;

import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import lombok.*;

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

    public static BiFunction<Unit, UpdateUnitRequest, Unit> dtoToEntityUpdater() {
        return (unit, request) -> {
            unit.setAmount(request.getAmount());
            return unit;
        };
    }
}
