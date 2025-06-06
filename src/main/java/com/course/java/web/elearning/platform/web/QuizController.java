package com.course.java.web.elearning.platform.web;

import com.course.java.web.elearning.platform.dto.QuizDto;
import com.course.java.web.elearning.platform.dto.QuizSubmissionRequest;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.security.CustomUserDetails;
import com.course.java.web.elearning.platform.service.ActivityLogService;
import com.course.java.web.elearning.platform.service.CourseService;
import com.course.java.web.elearning.platform.service.QuizzesService;
import com.course.java.web.elearning.platform.service.UserService;
import com.course.java.web.elearning.platform.wrapper.QuestionWrapper;
import com.course.java.web.elearning.platform.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("quizzes")
public class QuizController {

    private final QuizzesService quizzesService;
    private final CourseService courseService;
    private final UserService userService;
    private final ActivityLogService activityLogService;

    @Autowired
    public QuizController(QuizzesService quizzesService, CourseService courseService, UserService userService, ActivityLogService activityLogService) {
        this.courseService = courseService;
        this.quizzesService = quizzesService;
        this.userService = userService;
        this.activityLogService = activityLogService;
    }

    @PostMapping("create")
    public String createQuiz(@RequestParam long courseId, @ModelAttribute QuizDto quizDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            quizzesService.addQuizToCourse(courseId, quizDto);
            redirectAttributes.addFlashAttribute("successMessage", "Quiz created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create quiz: " + e.getMessage());
        }
        activityLogService.logActivity("Quiz created", userDetails.getUsername());
        return "redirect:/courses/" + courseId;
    }


    @GetMapping("/quiz")
    public String showQuizPage(@RequestParam long courseId, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<QuestionWrapper> quizQuestions = courseService.getQuestionsForCourseQuiz(courseId);
            var quizId = courseService.getCourseQuizId(courseId);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) principal).getUsername();
            User loggedInUser = userService.getUserByUsername(username);

            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("loggedUser", loggedInUser);
            model.addAttribute("quizId", quizId);
            model.addAttribute("courseId", courseId);
            model.addAttribute("quizQuestions", quizQuestions);

            return "quiz-submission";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to show quiz: " + e.getMessage());
        }

        return "redirect:/courses/" + courseId;
    }

    @PostMapping("submit")
    public ResponseEntity<Map<String, Integer>> submitQuiz(@RequestParam("courseId") long courseId, @RequestParam("quizId") long quizId, @RequestBody
    QuizSubmissionRequest submission, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Response> answers = submission.getAnswers();
        long elapsedTime = submission.getElapsedTime();
        activityLogService.logActivity("Quiz submitted", userDetails.getUsername());
        return quizzesService.calculateQuizResult(courseId, quizId, answers, elapsedTime);
    }

    @GetMapping("create-quiz")
    public String showCreateQuizPage(@RequestParam long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "create-quiz";
    }
}
