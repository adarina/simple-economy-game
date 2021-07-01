package pl.adabawolska.simpleeconomygamespringboot.resource.dto;

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
public class GetResourcesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Resource {

        private Long id;

        private String type;

        private Long amount;
    }

    @Singular
    private List<Resource> resources;

    public static Function<Collection<pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource>,GetResourcesResponse> entityToDtoMapper() {
        return resources -> { GetResourcesResponse.GetResourcesResponseBuilder response = GetResourcesResponse.builder();
            resources.stream()
                    .map(resource -> GetResourcesResponse.Resource.builder()
                            .id(resource.getId())
                            .type(resource.getType())
                            .amount(resource.getAmount())
                            .build())
                    .forEach(response::resource);
            return response.build();
        };
    }
}
