package com.course.java.web.elearning.platform.dto;

import com.course.java.web.elearning.platform.wrapper.Response;

import java.util.List;

public class QuizSubmissionRequest {
    private List<Response> answers;
    private long elapsedTime;
    public List<Response> getAnswers() {
        return answers;
    }
    public long getElapsedTime() {
        return elapsedTime;
    }

}
