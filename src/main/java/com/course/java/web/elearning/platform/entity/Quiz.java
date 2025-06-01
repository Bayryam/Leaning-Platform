package com.course.java.web.elearning.platform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<StudentResult> highScores;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

}
