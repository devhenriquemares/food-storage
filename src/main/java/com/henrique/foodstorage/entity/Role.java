package com.henrique.foodstorage.entity;

import com.henrique.foodstorage.enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "\"Role\"")
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Roles name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserAccount> users;
}
