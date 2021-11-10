package com.ada.simpleeconomygamespringboot.building.dto;

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
public class GetBuildingsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Building {

        private Long id;

        private String type;
    }

    @Singular
    private List<Building> buildings;

    public static Function<Collection<com.ada.simpleeconomygamespringboot.building.entity.Building>,
            GetBuildingsResponse> entityToDtoMapper() {
        return buildings -> {
            GetBuildingsResponseBuilder response = GetBuildingsResponse.builder();
            buildings.stream()
                    .map(building -> Building.builder()
                            .id(building.getId())
                            .type(building.getType())
                            .build())
                    .forEach(response::building);
            return response.build();
        };
    }
}
