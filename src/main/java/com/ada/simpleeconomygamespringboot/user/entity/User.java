package com.ada.simpleeconomygamespringboot.user.entity;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import com.sun.istack.NotNull;
import lombok.*;
import com.ada.simpleeconomygamespringboot.building.entity.Building;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @javax.validation.constraints.NotNull
    private String username;

    @javax.validation.constraints.NotNull
    private String password;

    @javax.validation.constraints.NotNull
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Building> buildings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Resource> resources;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Unit> units;
}
