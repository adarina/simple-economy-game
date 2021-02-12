package pl.adabawolska.simpleeconomygamespringboot.user.dto;

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
public class GetUsersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        private String username;
    }

    @Singular
    private List<User> users;

    public static Function<Collection<pl.adabawolska.simpleeconomygamespringboot.user.entity.User>,
            GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .username(user.getUsername())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}

