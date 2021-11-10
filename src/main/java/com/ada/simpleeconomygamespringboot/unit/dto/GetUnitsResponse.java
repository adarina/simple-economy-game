package com.ada.simpleeconomygamespringboot.unit.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUnitsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Unit {

        private Long id;

        private String type;

        private Long amount;

        private Boolean active;
    }

    @Singular
    private List<Unit> units;

    public static Function<Collection<com.ada.simpleeconomygamespringboot.unit.entity.Unit>,
            GetUnitsResponse> entityToDtoMapper() {
        return units -> {
            GetUnitsResponse.GetUnitsResponseBuilder response = GetUnitsResponse.builder();
            units.stream()
                    .map(unit -> GetUnitsResponse.Unit.builder()
                            .id(unit.getId())
                            .type(unit.getType())
                            .amount(unit.getAmount())
                            .active(unit.getActive())
                            .build())
                    .forEach(response::unit);
            return response.build();
        };
    }
}
