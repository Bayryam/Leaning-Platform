package com.course.java.web.elearning.platform.wrapper;

import lombok.Data;

@Data
public class QuestionWrapper {
    private long id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
