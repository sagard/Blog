package gash.wink.blog;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents an entry in a blog
 * 
 * TODO add tags
 * 
 * @author gash
 * 
 */
@XmlRootElement(name = "blogentry")
@XmlAccessorType(XmlAccessType.FIELD)
public class BlogEntry {
	@XmlAttribute(name = "id")
	private int id;

	@XmlAttribute(name = "parentid")
	private int parentid;

	@XmlElement(name = "title")
	private String title;

	@XmlElement(name = "content")
	private String content;

	@XmlElement(name = "owner")
	private String owner;

	@XmlElement(name = "created")
	private Date created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
