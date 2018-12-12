package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QnaService qnaService;

    @GetMapping("/form")
    public String questionForm(@LoginUser User loginUser) {
        return "/qna/form";
    }

    @PostMapping("")
    public String inquire(@LoginUser User loginUser, Question question) {
        try {
            qnaService.create(loginUser, question);
            return "redirect:/";
        } catch (ConstraintViolationException cve) {
            cve.printStackTrace();
            return "redirect:/question/form";
        } catch (Exception e) {
            e.printStackTrace();
            return "/user/login_failed";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        try {
            qnaService.deleteQuestion(loginUser, id);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "/user/login_failed";
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("question", qnaService.findById(id).orElse(null));
            return "/qna/show";
        } catch (Exception e) {
            e.printStackTrace();
            return "/user/login_failed";
        }
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        try {
            model.addAttribute("question", qnaService.findById(id).orElse(null));
            return "/qna/updateForm";
        } catch (Exception e) {
            e.printStackTrace();
            return "/user/login_failed";
        }
    }

    @PutMapping("")
    public String update(@LoginUser User loginUser, Long id, Question updatedQuestion) {
        try {
            Question question = qnaService.update(id, updatedQuestion);
            return "redirect:/questions/" + Long.valueOf(question.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return "/user/login_failed";
        }
    }

}
