package pl.edu.agh.webChess.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.agh.webChess.entity.UserStatistics;
import pl.edu.agh.webChess.service.UserService;

@Controller
public class MainController {

    private UserService userService;

    MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        UserStatistics userStatistics = userService.findUserStatisticsByUserName(userName);

        model.addAttribute("points", userStatistics.getPoints());

        int totalGames = userStatistics.getGamesDrown() + userStatistics.getGamesWon() +
                         userStatistics.getGamesLost();

        model.addAttribute("totalGames", totalGames);

        if(totalGames == 0) {
            model.addAttribute("percentage", "-");
        }
        else {

            int percentage = userStatistics.getGamesWon() / totalGames;
            model.addAttribute("percentage", percentage + "%");
        }


        return "home";
    }
}
