package gash.wink.blog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * collection of blogs relating to 'space' => category
 * 
 * TODO space should be a list, implement space
 * 
 * @author gash
 * 
 */
@XmlRootElement(name = "blogspace")
public class BlogSpace {
	@XmlElement(name = "space")
	private String space;

	@XmlElementRef
	ArrayList<Blog> blogs;

	/**
	 * must have
	 */
	public BlogSpace() {
	}

	public BlogSpace(String space, List<Blog> blogs) {
		// this.space = space;
		this.blogs = new ArrayList<Blog>(blogs);
	}

	public void setBlogs(ArrayList<Blog> blogs) {
		this.blogs = blogs;
	}

	public ArrayList<Blog> getList() {
		return blogs;
	}

	public void setSpace(String space) {
		this.space = space;
	}
}
