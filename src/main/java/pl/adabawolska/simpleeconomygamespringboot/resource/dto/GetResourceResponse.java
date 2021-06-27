package pl.adabawolska.simpleeconomygamespringboot.resource.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetResourceResponse {

    private Long mudQuantity;

    private Long stoneQuantity;

    private Long meatQuantity;

    public static Function<Resource, GetResourceResponse> entityToDtoMapper() {
        return resource -> GetResourceResponse.builder()
                .mudQuantity(resource.getMudQuantity())
                .stoneQuantity(resource.getStoneQuantity())
                .meatQuantity(resource.getMeatQuantity())
                .build();
    }
}
