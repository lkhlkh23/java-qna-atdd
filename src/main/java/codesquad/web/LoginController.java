package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping()
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping()
    public String login(String userId, String password, HttpSession httpSession) {
        try {
            httpSession.setAttribute(HttpSessionUtils.USER_SESSION_KEY, userService.login(userId, password));
            return "redirect:/";
        } catch (UnAuthenticationException e) {
            e.printStackTrace();
            return "/user/login_failed";
        }
    }
}
