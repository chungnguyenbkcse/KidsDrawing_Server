package com.app.kidsdrawing.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role {
    @Id
    @GenericGenerator(name = "id", strategy = "com.app.kidsdrawing.entity.generator.RoleIdGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id")
    private Long  id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "role_has_privilege",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "privilege_id") })
    private Set<Privilege> privileges;
}
