package uk.ac.edu4all.data;

public class Tweet {
	private String content, author;
	
	public Tweet(String content, String author) {
		this.content = content; this.author = author;
	}

	public String getContent() {return content;}

	public String getAuthor() {return author;}
}
