package com.course.java.web.elearning.platform.wrapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    private long questionId;
    private String answer;
}
