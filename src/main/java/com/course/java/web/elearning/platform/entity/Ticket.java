package com.course.java.web.elearning.platform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  private User issuer;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "course_id")
  private Course forCourse;

  @NonNull
  private Date createdOn;

  @NonNull
  @NotEmpty
  private String content;

  private Boolean resolved;
}
