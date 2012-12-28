package uk.ac.edu4all.web;

import java.beans.PropertyEditorSupport;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.domain.Treebranch;
import uk.ac.edu4all.service.IEduService;


@Controller
@RequestMapping("/admin")
@SessionAttributes({"course"})
public class CourseAdminController {
	
	@Inject private IEduService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), false));
	    binder.registerCustomEditor(Treebranch.class, new PropertyEditorSupport() {
	    	@Override
	    	public void setAsText(String text) throws IllegalArgumentException {
	    		setValue(service.getBranch(Integer.parseInt(text)));
	    	}
	    	@Override
	    	public String getAsText() {
	    		if(getValue() == null) return "0";
	    		return new Integer(((Treebranch) getValue()).getId()).toString();
	    	}
	    });
	}

	@RequestMapping(value="/course", method=RequestMethod.GET)
	public String createNewCourse(Model model) {
		Course course = new Course();
		course.setStartDate(new Date());
		model.addAttribute("course", course);
		model.addAttribute("categories", service.getCategoryTree());
		return "courseEditor";
	}
	
	@RequestMapping(value="/course/{courseID}", method=RequestMethod.GET)
	public String editCourse(Model model, @PathVariable int courseID) {
		model.addAttribute("course", service.getCourse(courseID));
		model.addAttribute("categories", service.getCategoryTree());
		return "courseEditor";
	}
	
	@RequestMapping(value={"/course", "/course/{courseID}"}, method=RequestMethod.POST)
	public String saveCourse(Model model, @ModelAttribute("course") @Valid Course course, BindingResult result, 
			SessionStatus status) throws IOException {
		if(course.getImageFile() == null || course.getImageFile().getContentType().indexOf("image") == -1) {
			course.setImageFile(null);
			if(course.getId() == 0) result.rejectValue("imageFile", "", "A valid Course Image is Required");
		}
		if(! result.hasErrors()) {
			service.saveCourse(course);
			status.setComplete();
			return "redirect:/course/" + course.getId();
		}
		model.addAttribute("categories", service.getCategoryTree());
		return "courseEditor";
	}
	
}
