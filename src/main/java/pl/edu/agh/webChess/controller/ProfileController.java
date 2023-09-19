package pl.edu.agh.webChess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.entity.UserStatistics;
import pl.edu.agh.webChess.service.UserService;

@Controller
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findUserByName(userName);

        model.addAttribute("user", user);

        long ranking = userService.findUserRankingByUserName(userName);
        model.addAttribute("ranking", ranking);

        return "profile";
    }
}
