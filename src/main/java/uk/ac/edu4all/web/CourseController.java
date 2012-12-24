package uk.ac.edu4all.web;

import java.security.Principal;
import java.util.*;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import uk.ac.edu4all.domain.*;
import uk.ac.edu4all.service.IEduService;

@Controller
public class CourseController {
	@Inject IEduService service;
	private String[] courseSortNames = new String[] {"Start Date", "Price", "Duration", "Name"};
	private String[] commentSortNames = new String[] {"Date & Time", "Rating (Positive First)", "Rating (Negative First)"};
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/course", method=RequestMethod.GET)
	public String viewCourseList(Model model, @RequestParam(value="cat", required=false) Integer categoryId,
			@RequestParam(value="sort", required=false) Integer sort, Principal principal) {
		if(categoryId == null) categoryId = 0; 
		if(sort == null) sort = 0;
		List<Course> courses = service.getCoursesByCategory(categoryId);
		switch(sort){
			case 1:	Collections.sort(courses, new PropertyComparator("price", true, true)); break;
			case 2: Collections.sort(courses, new PropertyComparator("length", true, true)); break;
			case 3: Collections.sort(courses, new PropertyComparator("title", true, true)); break;
			default: Collections.sort(courses, new PropertyComparator("startDate", true, true)); break;
		}
		Map<String, Integer> categories = service.getCategoryTree();
		categories.put("All Courses", 0);
		model.addAttribute("courses", courses)
			.addAttribute("sortNames", courseSortNames)
			.addAttribute("sortIndex", sort)
			.addAttribute("catId", categoryId)
			.addAttribute("categoryMap", categories);
		if(principal != null) {
			model.addAttribute("userCourses", service.getCoursesByUser(service.getUserByUsername(principal.getName())));
		} else {
			model.addAttribute("userCourses", Collections.EMPTY_LIST);
		}
		return "courseList";
	}
	
	@RequestMapping(value="/course/{courseId}", method=RequestMethod.GET)
	public String viewCourseDetails(Model model, @PathVariable int courseId,
			@RequestParam(value="sort", required=false) Integer sort, Principal principal) {
		if(sort == null) sort = 0;
		model.addAttribute("comment", new Comment());
		return processCourseDetailsModel(model, courseId, sort, principal);
	}
	
	@RequestMapping(value="/course/{courseId}", method=RequestMethod.POST)
	public String addCourseComment(Model model, @PathVariable int courseId, Principal principal,
			@ModelAttribute("comment") @Valid Comment comment, BindingResult result) {
		if(! result.hasErrors()) {
			comment.setUser(service.getUserByUsername(principal.getName()));
			comment.setCourse(service.getCourse(courseId));
			service.saveComment(comment);
			return "redirect:/course/" + courseId;
		}
		return processCourseDetailsModel(model, courseId, 0, principal);
	}
	
	@SuppressWarnings("unchecked")
	private String processCourseDetailsModel(Model model, int courseId, int sort, Principal principal ) {
		Course course = service.getCourse(courseId);
		List<Comment> comments = new ArrayList<Comment>(course.getComments());
		switch(sort){
			case 1:	Collections.sort(comments, new PropertyComparator("rating", false, false)); break;
			case 2: Collections.sort(comments, new PropertyComparator("rating", false, true)); break;
			default: Collections.sort(comments, new PropertyComparator("posted", false, false)); break;
		}
		model.addAttribute("course", course)
			.addAttribute("sortNames", commentSortNames)
			.addAttribute("sortIndex", sort)
			.addAttribute("comments", comments)
			.addAttribute("recommendations", service.getCourseRecommendations(course, 3));
		if(principal != null) {
			model.addAttribute("registered", service.isUserRegisted(service.getUserByUsername(principal.getName()), course));
		} else {
			model.addAttribute("registered", false);
		}
		return "courseDetails";
	}
}
