package pl.adabawolska.simpleeconomygamespringboot.user.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;

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
public class GetUsersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        private Long id;

        private String username;

        //private List<Building> building;

        //private List<Resource> resource;

    }

    @Singular
    private List<User> users;

    public static Function<Collection<pl.adabawolska.simpleeconomygamespringboot.user.entity.User>,
            GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            //.building(user.getBuildings())
                            //.resource(user.getResources())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}

