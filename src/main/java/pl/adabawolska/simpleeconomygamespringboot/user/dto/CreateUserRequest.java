package pl.adabawolska.simpleeconomygamespringboot.user.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    private Long id;

    private String username;

    private String password;

    private Resource resource;

    public static Function<CreateUserRequest, User> dtoToEntityMapper(Resource resource) {
        return request -> User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(request.getPassword())
                .resource(resource)
                .build();
    }
}