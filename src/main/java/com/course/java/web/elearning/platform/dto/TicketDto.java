package com.course.java.web.elearning.platform.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TicketDto {

  @NotEmpty
  @NonNull
  private String content;
}
