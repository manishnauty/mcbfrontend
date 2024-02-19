package com.mcb.bankpropertyevaluation.dao.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(name = "username", unique = true, nullable = false, updatable=false)
    private String username;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Size(max = 20)
    @Column(updatable=false)
    private String buisnessUnit;

    @Size(max = 20)
    private String contactNumber;

    @Size(max = 120)
    private String password;
}
