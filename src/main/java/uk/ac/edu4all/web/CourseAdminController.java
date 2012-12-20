package uk.ac.edu4all.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.service.IEduService;


@Controller
public class CourseAdminController {
	
	@Inject private IEduService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), false));
	}

	@RequestMapping(value="/admin/branch/{branchID}/course/new", method=RequestMethod.GET)
	public String createNewAuthorisationGroup(Model model, @PathVariable int branchID) {
		Course course = new Course();
		course.setStartDate(new Date());
		model.addAttribute("course", course);
		model.addAttribute("heading", "Create New Course");
		return "courseEditor";
	}
	
	@RequestMapping(value="/admin/branch/{branchID}/course/new", method=RequestMethod.POST)
	public String saveNewAuthorisationGroup(Model model, @PathVariable int branchID,
			@ModelAttribute("course") Course course, @RequestParam("imageFile") MultipartFile image) throws IOException {
		// scale image
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
		BufferedImage scaledImg = new BufferedImage(350, (int)(img.getHeight() * (350.0 / img.getWidth())), img.getType());
		Graphics2D g = scaledImg.createGraphics();
		g.drawImage(img, 0, 0, 350, (int)(img.getHeight() * (350.0 / img.getWidth())), null);
		g.dispose();
	//	img.getScaledInstance(350, (int)(img.getHeight() * (350.0 / img.getWidth())), java.awt.Image.SCALE_DEFAULT);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(scaledImg, "jpeg", baos);
		course.setImage(baos.toByteArray());
		baos.close();
		course.setTreebranch(service.getBranch(branchID));
		service.saveCourse(course);
		return "courseEditor";
	}
	
}
