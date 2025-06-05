package com.course.java.web.elearning.platform.entity;

import com.course.java.web.elearning.platform.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User {

    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_INSTRUCTOR = "INSTRUCTOR";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_UNREGISTERED = "UNREGISTERED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @NonNull
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @OneToMany
    private Set<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "user_started_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> startedCourses;

    @ManyToMany
    @JoinTable(
            name = "user_completed_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> completedCourses;

    @OneToMany(mappedBy = "issuedTo", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public User(String username, String password, String firstName, String lastName, String email, @NonNull Set<String> roles) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.certificates = new ArrayList<>();
        this.startedCourses = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
    }

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN.getDescription());
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public void addStartedCourse(Course course) {
        startedCourses.add(course);
    }

    public void addCompletedCourse(Course course) {
        startedCourses.remove(course);
        completedCourses.add(course);
    }
}

