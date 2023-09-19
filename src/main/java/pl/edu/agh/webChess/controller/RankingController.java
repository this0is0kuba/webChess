package pl.edu.agh.webChess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.agh.webChess.data.UserRanking;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class RankingController {

    private UserService userService;

    @Autowired
    public RankingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/ranking")
    public String showRankingPage(Model model) {

        Collection<User> users = userService.findTop10UsersByPoints();

        Collection<UserRanking> userRankingCollection = new ArrayList<>();

        for(User user: users) {

            long ranking = userService.findUserRankingByUserName(user.getUserName());

            UserRanking userRanking = new UserRanking(user, ranking);
            userRankingCollection.add(userRanking);
        }

        model.addAttribute("usersRanking", userRankingCollection);

        return "ranking";
    }
}
