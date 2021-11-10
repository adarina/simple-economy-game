package com.ada.simpleeconomygamespringboot.unit.entity;

import lombok.*;
import com.ada.simpleeconomygamespringboot.user.entity.User;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
