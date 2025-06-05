package com.course.java.web.elearning.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String name;
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;
    private Date createdOn;

    @Transient
    private String imageBase64;

    @ManyToMany(mappedBy = "startedCourses")
    private List<User> participants;

    @ManyToMany(mappedBy = "completedCourses")
    private List<User> studentsCompletedCourse;

    @OneToMany
    private List<Lesson> lessons;

    @OneToMany
    private List<Question> questions;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Quiz quiz;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<StudentResult> highScores;

    public void addParticipant(User user) {
        participants.add(user);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }

    public void addStudentCompletedCourse(User user) {
        studentsCompletedCourse.add(user);
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public Lesson getLessonById(Long lessonId) {
        return lessons.stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
    }
}
