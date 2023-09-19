package pl.edu.agh.webChess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/profile/{userName}")
    public String showProfilePage(Model model, @PathVariable String userName) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = auth.getName();

        User user = userService.findUserByName(userName);

        model.addAttribute("user", user);

        long ranking = userService.findUserRankingByUserName(userName);
        model.addAttribute("ranking", ranking);

        boolean checkAuthentication = currentUserName.equals(user.getUserName());
        model.addAttribute("condition", checkAuthentication);

        return "profile";
    }
}
