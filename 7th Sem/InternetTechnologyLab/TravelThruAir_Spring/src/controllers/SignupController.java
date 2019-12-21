package controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import domain.*;
import service.*;

@Controller
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	FlightService flightService;
	@RequestMapping("")
	public String getForm(Model model) {
		User u = new User();
		model.addAttribute("user",u);
		return "signup";
	}
	@RequestMapping("/processForm")
	public String processForm(@Valid @ModelAttribute("user") User u,BindingResult br) {
		if(br.hasErrors()) return "signup";
		if(flightService.createUser(u)) return "redirect:/home";
		return "usernameExists";
	}
}
