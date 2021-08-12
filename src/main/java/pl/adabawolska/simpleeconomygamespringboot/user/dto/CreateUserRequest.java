package pl.adabawolska.simpleeconomygamespringboot.user.dto;

import lombok.*;
import pl.adabawolska.simpleeconomygamespringboot.building.entity.Building;
import pl.adabawolska.simpleeconomygamespringboot.resource.entity.Resource;
import pl.adabawolska.simpleeconomygamespringboot.unit.entity.Unit;
import pl.adabawolska.simpleeconomygamespringboot.user.entity.User;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    //private Long id;

    private String username;

    private String password;

    private String role;

    private List<Resource> resources;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                //.id(request.getId())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }
}