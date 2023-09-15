package pl.edu.agh.webChess.controller;

import java.util.logging.Logger;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.service.UserService;

@Controller
public class RegistrationController {

	private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/register")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("user", new User());
		
		return "authentication/registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("user") User user,
			BindingResult theBindingResult,
			Model theModel,
			RedirectAttributes attributes) {


		String userName = user.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "authentication/registration-form";
		 }

		// check the database if user already exists
        User existing = userService.findUserByName(userName);
        if (existing != null){
        	theModel.addAttribute("user", new User());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "authentication/registration-form";
        }

        // create user account and store in the databse
        userService.save(user);

        logger.info("Successfully created user: " + userName);

		// place user in the web http session for later use
		//session.setAttribute("user", user);


        return "redirect:/login?register";
	}
}
