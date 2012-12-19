package uk.ac.soton;

import java.text.DateFormat;
import java.util.*;
import java.net.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uk.ac.edu4all.data.Tweet;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/tweet", method = RequestMethod.GET)
	@ResponseBody
	public List<Tweet> jsonTweets() {
		List<Tweet> tweets = new ArrayList<Tweet>();
		try {
			URL yahoo = new URL("http://search.twitter.com/search.atom?q=%23coursera&rpp=5&include_entities=true&result_type=mixed");
	        URLConnection yc = yahoo.openConnection();
	     //   BufferedReader in = new BufferedReader( new InputStreamReader(yc.getInputStream()));
	       // String inputLine = in.readLine();
	       	  
	        
	      //  System.out.println(inputLine);
	        
	        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc = dBuilder.parse(yc.getInputStream());
	     //   in.close();
			
			NodeList nodes = doc.getElementsByTagName("entry");
			for(int i=0; i<nodes.getLength(); i++) {
				String content = ((Element)nodes.item(i)).getElementsByTagName("content").item(0).getChildNodes().item(0).getNodeValue();
				String author = ((Element)nodes.item(i)).getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
				String href = ((Element)nodes.item(i)).getElementsByTagName("uri").item(0).getChildNodes().item(0).getNodeValue();
								
				tweets.add(new Tweet(
					content.replaceAll("(?i)Coursera", "edu4all"), 
					"<a href=\"" + href + "\">" + author + "</a>"
				));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        		
		return tweets;
	}
	
}
