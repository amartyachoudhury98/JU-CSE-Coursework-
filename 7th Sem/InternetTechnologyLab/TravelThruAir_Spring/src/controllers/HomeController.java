package controllers;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.*;
import domain.*;
import service.*;
@Controller
@RequestMapping("/home")
public class HomeController {
	FlightService flightservice;
	@Autowired
	public void setFlightService(FlightService fs) {
		flightservice = fs;
	}
	@RequestMapping("")
	public String getForm(Model model) {
		SearchFlights s= new SearchFlights();
		model.addAttribute("search",s);
		System.out.println("in home getForm");
		return "home";
	}
	@RequestMapping("/processForm")
	public ModelAndView processForm(@Valid @ModelAttribute("search") SearchFlights s,BindingResult br) {
		if(br.hasErrors()) {
			
			ModelAndView mav = new ModelAndView("home");
			return mav;
		}
		List<Flight> flights = flightservice.getFlights(s);
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("flights",flights);
		return mav;
	}
}
