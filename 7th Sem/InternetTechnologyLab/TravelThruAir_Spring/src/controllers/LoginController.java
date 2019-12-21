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
@RequestMapping("/login")
public class LoginController {
	@Autowired
	FlightService flightService;
	@RequestMapping("")
	public String getForm(Model model) {
		UserLogin u = new UserLogin();
		model.addAttribute("user",u);
		return "login";
	}
	@RequestMapping("/processForm")
	public String processForm(@Valid @ModelAttribute("user") UserLogin u,BindingResult br) {
	System.out.println(u.getUserId().length());
	if(br.hasErrors()) {
		System.out.println(br.toString());
		return "login";
	}
	if(flightService.validateUser(u.getUserId(),u.getPassword())) return "redirect:/home";
	else return "invalidCredentials";
	}
}
