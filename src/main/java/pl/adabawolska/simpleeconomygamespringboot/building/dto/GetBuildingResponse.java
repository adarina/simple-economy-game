package pl.adabawolska.simpleeconomygamespringboot.building.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetBuildingResponse {

    private Long id;

    private String type;

    private Long userId;

    public static Function<Building, GetBuildingResponse> entityToDtoMapper() {
        return building -> GetBuildingResponse.builder()
                    .id(building.getId())
                    .type(building.getType())
                    .userId(building.getUser().getId())
                    .build();
    }
}
