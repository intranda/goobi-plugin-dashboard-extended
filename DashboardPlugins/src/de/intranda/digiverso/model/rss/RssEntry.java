package de.intranda.digiverso.model.rss;

public class RssEntry {

	private String title;
	private String author;
	private String publishedDate;
	private String description;
	private String link;

	public RssEntry() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String s) {
		title = s;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String s) {
		author = s;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String s) {
		publishedDate = s;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		description = s;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
}
