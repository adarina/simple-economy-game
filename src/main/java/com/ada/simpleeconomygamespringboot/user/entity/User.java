package com.ada.simpleeconomygamespringboot.user.entity;

import com.ada.simpleeconomygamespringboot.resource.entity.Resource;
import com.ada.simpleeconomygamespringboot.unit.entity.Unit;
import lombok.*;
import com.ada.simpleeconomygamespringboot.building.entity.Building;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "user")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
