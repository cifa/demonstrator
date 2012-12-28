package uk.ac.edu4all.web;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.*;

import org.jfree.chart.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.*;
import org.jfree.ui.*;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.*;

import com.keypoint.PngEncoder;

import uk.ac.edu4all.data.Tweet;
import uk.ac.edu4all.domain.Course;
import uk.ac.edu4all.service.IEduService;

@Controller
@RequestMapping(value = "/data")
public class DataController {

	@Inject IEduService service;
	private String twitUrl = "http://search.twitter.com/search.atom?q=%23coursera&rpp=5&include_entities=true&result_type=mixed&lang=en";
	private List<Tweet> tweets = new ArrayList<Tweet>();
	private long twitExpired, chartExpired;
	private JFreeChart chart;

	@RequestMapping(value = "/tweet", method = RequestMethod.GET)
	@ResponseBody
	public synchronized List<Tweet> jsonTweets() {
		if (twitExpired < new Date().getTime()) {
			twitExpired = new Date().getTime() + 1000 * 60 * 2;  // update tweets every 2 minutes
			try {
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(new URL(twitUrl).openStream());

				NodeList nodes = doc.getElementsByTagName("entry");
				for (int i = 0; i < nodes.getLength(); i++) {
					String content = ((Element) nodes.item(i)).getElementsByTagName("content").item(0).getChildNodes().item(0).getNodeValue();
					String author = ((Element) nodes.item(i)).getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
					String href = ((Element) nodes.item(i)).getElementsByTagName("uri").item(0).getChildNodes().item(0).getNodeValue();

					tweets.add(new Tweet(content.replaceAll("(?i)Coursera","edu4all"), "<a href=\"" + href + "\">" + author + "</a>"));
				}
			} catch (Exception e) {
				// TODO log exception
			}
		}
		return tweets;
	}
	
	@RequestMapping("/popchart")
	public synchronized void popularChart(HttpServletResponse response) throws IOException {
	    if (chartExpired < new Date().getTime()) {
			chartExpired = new Date().getTime() + 1000 * 60 * 60;	// regenerate once an hour
			// get most popular courses and put them into a dataset
			List<Course> courses = service.getMostPopularCourses(5);
			DefaultCategoryDataset ds = new DefaultCategoryDataset();
			for(Course c : courses) {
				ds.addValue(c.getEnrollments().size(), c.getTitle(), "");
			}
			chart = ChartFactory.createBarChart3D("Most Popular Courses On Edu4All", "Courses", "Students", ds, PlotOrientation.VERTICAL, true, false, false);
			chart.getLegend().setPosition(RectangleEdge.RIGHT);
			chart.getTitle().setPaint(new Color(0,66,121));
			chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 32));
			// Adjust the colour of the title 
			CategoryPlot plot = chart.getCategoryPlot();
			plot.setBackgroundPaint(Color.white);      
			CategoryItemRenderer renderer = plot.getRenderer();
			renderer.setSeriesPaint(0, new Color(0,66,121));
			renderer.setSeriesPaint(1, new Color(93,164,35));
			renderer.setSeriesPaint(2, new Color(235,125,0));
			renderer.setSeriesPaint(3, new Color(198,15,19));
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setBaseItemLabelPaint(Color.white);
			renderer.setBaseItemLabelFont(new Font("SansSerif", Font.BOLD, 18));
			chart.getLegend().setItemFont(new Font("SansSerif", Font.BOLD, 14));
	    }
	    // send the chart
	    response.setContentType("image/png");
	    response.getOutputStream().write( new PngEncoder(chart.createBufferedImage(900, 300, null), true, 0, 9 ).pngEncode() );
	}	
	
	@RequestMapping("/course/{id}/image")
	public void courseImage(HttpServletResponse response, @PathVariable int id) throws IOException {
	    response.setContentType("image/jpeg");
	    response.getOutputStream().write(service.getCourse(id).getImage());
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/rss/course/upcoming")
	public String upcomingCoursesRSSFeed(Model model) {
		List<Course> courses = service.getCoursesByCategory(0); 	// 0 for all courses
		// get 6 courses starting soon
		Collections.sort(courses, new PropertyComparator("startDate", true, true));
		List<Course> upcoming = new ArrayList<Course>(6);
		for(Course c : courses) {
			if(! c.getStartDate().before(new Date())) upcoming.add(c);
			if(upcoming.size() == 6) break;
		}
		model.addAttribute("courses", upcoming);
		return "rssFeed";
	}
}
