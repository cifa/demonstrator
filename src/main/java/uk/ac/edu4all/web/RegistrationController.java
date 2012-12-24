package uk.ac.edu4all.web;

import java.security.Principal;
import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import uk.ac.edu4all.data.CourseRegistration;
import uk.ac.edu4all.data.CourseRegistration.Status;
import uk.ac.edu4all.domain.*;
import uk.ac.edu4all.service.IEduService;

@Controller
@RequestMapping(value = "/user")
@SessionAttributes("registration")
public class RegistrationController {

	@Inject IEduService service;
	
	@ModelAttribute("registration")
	public CourseRegistration initUser(Principal principal) {
		return new CourseRegistration();
	}
	
	@RequestMapping(value = "/course/{courseId}/registration", method = RequestMethod.GET)
	public String initRegistrationProcess(Model model, Principal principal,
			@PathVariable int courseId, SessionStatus status) {
		User user = service.getUserByUsername(principal.getName());
		Course course = service.getCourse(courseId);
		if(! service.isUserRegisted(user, course)) {
			// not registered yet - begin registration process
			CourseRegistration reg = new CourseRegistration();
			reg.setUser(user);
			reg.setCourse(course);
			model.addAttribute("registration", reg);
			return "redirect:/user/registration/" + reg.getUuid();
		}
		// already registed - go to course page
		status.setComplete();
		return "redirect:/course/" + courseId;
	}
	
	@RequestMapping(value = "/registration/{uuid}",  method = RequestMethod.GET)
	public String processGetRegistrationStep(@PathVariable String uuid, HttpSession session,
			@ModelAttribute("registration") CourseRegistration reg) {
		if(reg.getUuid().equals(uuid)) {
			switch (reg.getRegistrationStatus()) {
			case BEGIN: 
				reg.setRegistartionStatus(Status.ADDRESS);
				return "registrationAddress";
			case ADDRESS:	return "registrationAddress";			
			case PAYMENT:	return "registrationCardDetails";			
			case CONFIRM: 	return "registrationConfirm";		
			case FINISH: 	return "registrationFinished";
			}
		} else {
			session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new IllegalAccessException("Course Registration Expired"));
		}		
		return "redirect:/user/registration/" + reg.getUuid() + "/finish";
	}
	
	@RequestMapping(value = "/registration/{uuid}", method = RequestMethod.POST)
	public String processRegistrationStep(@PathVariable String uuid,
			@ModelAttribute("registration") @Valid CourseRegistration reg, BindingResult result) {
		if(reg.getUuid().equals(uuid)) {
			switch (reg.getRegistrationStatus()) {
				case ADDRESS:
					if(result.hasErrors()) return "registrationAddress";
					reg.setRegistartionStatus(Status.PAYMENT);
					break;
				case PAYMENT:
					if(result.hasErrors()) return "registrationCardDetails";
					reg.setRegistartionStatus(Status.CONFIRM);
					break;
				case CONFIRM:
					// process payment and make registration
					if(service.makeRegistrationPayment(reg)) {
						reg.setRegistartionStatus(Status.FINISH);
						return "registrationFinished";
					}
					// payment failed
					reg.setRegistartionStatus(Status.PAYMENT);
					result.reject(null, "Payment failed. Please review our payment details.");
					return "registrationCardDetails";
				default:	// just fall through to the finish redirect (without error message)
			}
		} 		
		return "redirect:/user/registration/" + reg.getUuid();
	}
	
	@RequestMapping(value = "/registration/{uuid}/stepback/{steps}")
	public String stepBackInRegistrationProcess(@PathVariable String uuid, @PathVariable int steps,
			@ModelAttribute("registration") CourseRegistration reg) {
		if(reg.getUuid().equals(uuid)) {
			if (reg.getRegistrationStatus() == Status.PAYMENT) {
				reg.setCardNumber(null);
				reg.setCode(null);
				reg.setName(null);
				reg.setRegistartionStatus(Status.ADDRESS);
			} else if(reg.getRegistrationStatus() == Status.CONFIRM) {
				reg.setRegistartionStatus(steps == 1 ? Status.PAYMENT : Status.ADDRESS);
			}
		}		
		return "redirect:/user/registration/" + reg.getUuid();
	}
	
	@RequestMapping(value = "/registration/{uuid}/finish")
	public String endRegistrationProcess(@PathVariable String uuid, SessionStatus status,
			@ModelAttribute("registration") CourseRegistration reg) {
		status.setComplete();
		if(reg.getCourse() != null) {
			return "redirect:/course/" + reg.getCourse().getId();
		}
		return "redirect:/course";
	}
}
