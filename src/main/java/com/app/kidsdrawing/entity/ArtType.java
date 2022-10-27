package com.app.kidsdrawing.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "Art_Type")
public class ArtType {
    @Id
    @GenericGenerator(name = "id", strategy = "com.app.kidsdrawing.entity.generator.ArtTypeIdGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id")
    private Long  id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy="artTypes")
    private Set<Contest> contests;

    @OneToMany(mappedBy="artTypes")
    private Set<Course> courses;
}
