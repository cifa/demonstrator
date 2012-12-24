package uk.ac.edu4all.data;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import uk.ac.edu4all.domain.Course;

import com.sun.syndication.feed.rss.*;

public class RssViewer extends AbstractRssFeedView {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	public RssViewer() {
		dateFormat.applyPattern("dd/MM/yyyy");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		List<Item> items = new ArrayList<Item>();
		for(Course course : (List<Course>) model.get("courses")) {
			Item item = new Item();
			Content content = new Content();
			content.setType("html");
			content.setValue(course.getDescription());
			item.setContent(content);
			item.setAuthor("www.Edu4All.com");
			item.setTitle(course.getTitle());
			item.setUri("http://" + request.getServerName() + ":" + request.getServerPort()
						+ request.getContextPath() + "/course/" + course.getId());
			items.add(item);
		}
		return items;
	}
	
	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
		HttpServletRequest request) {
 
		feed.setTitle("Upcoming Courses on Edu4All");
		feed.setDescription("Come and learn with us");
		feed.setLink("http://www.edu4all.com");
 
		super.buildFeedMetadata(model, feed, request);
	}

}
