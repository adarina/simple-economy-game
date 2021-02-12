package pl.adabawolska.simpleeconomygamespringboot.user.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    private String username;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .username(request.getUsername())
                .build();
    }
}