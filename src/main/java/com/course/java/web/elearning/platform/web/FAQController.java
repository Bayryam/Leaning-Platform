package com.course.java.web.elearning.platform.web;

import com.course.java.web.elearning.platform.dto.FAQDto;
import com.course.java.web.elearning.platform.security.CustomUserDetails;
import com.course.java.web.elearning.platform.service.ActivityLogService;
import com.course.java.web.elearning.platform.service.FAQService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FAQController {

    private final FAQService faqService;
    private final ActivityLogService activityLogService;

    public FAQController(FAQService faqService, ActivityLogService activityLogService) {
        this.faqService = faqService;
        this.activityLogService = activityLogService;
    }

    @GetMapping("/help")
    public String getAllQuestions(Model model) {
        model.addAttribute("faqs", faqService.getAllQuestions());
        return "faq";
    }

    @PostMapping("/help")
    public String createQuestion(@ModelAttribute FAQDto faqDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        faqService.addQuestion(faqDto);
        activityLogService.logActivity("FAQ added", customUserDetails.getUsername());
        return "redirect:/admin/faq";
    }

    @PostMapping("/help/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        faqService.deleteQuestion(id);
        model.addAttribute("articles", faqService.getAllQuestions());
        activityLogService.logActivity("FAQ deleted", customUserDetails.getUsername());
        return "redirect:/admin/faq";
    }
}
