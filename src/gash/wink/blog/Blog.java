package gash.wink.blog;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description of a blog (using JAXB) - note we are breaking referential
 * integrity through loosely managed one-to-many blog to blog-entries this way
 * we can use the Blog object for meta descriptions (lightweight returns) -
 * tailoring the data objects to the behavior of our application (not some
 * perceived OOD rule)!
 * 
 * TODO add tags, links to other blogs, owner object
 * 
 * @author gash
 * 
 */
@XmlRootElement(name = "blog")
@XmlAccessorType(XmlAccessType.FIELD)
public class Blog {

	@XmlAttribute(name = "id")
	private int id;

	@XmlElement(name = "subject")
	private String subject;

	@XmlElement(name = "description")
	private String description;

	@XmlElement(name = "owner")
	private String owner;

	@XmlElement(name = "created")
	private Date created;

	@XmlAttribute(name = "space")
	private String space;

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
