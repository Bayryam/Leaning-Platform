package course.spring.elearningplatform.web.data;

import course.spring.elearningplatform.entity.Response;

import java.util.List;

public record QuizSubmissionRequest(List<Response> answers, long elapsedTime) {

}
