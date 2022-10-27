package com.app.kidsdrawing.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "exercise_level")
public class ExerciseLevel {
    @Id
    @GenericGenerator(name = "id", strategy = "com.app.kidsdrawing.entity.generator.ExerciseLevelIdGenerator")
    @GeneratedValue(generator = "id")
    @Column(name = "id")
    private Long  id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private Float weight;

    @OneToMany(mappedBy="exerciseLevel")
    private Set<Exercise> exercise;
}
