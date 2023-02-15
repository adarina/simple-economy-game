package com.ada.simpleeconomygamespringboot.user.dto;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.user.entity.User;
import lombok.*;

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

    private String username;

    private String password;

    private String role;

    private List<Resource> resources;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }
}