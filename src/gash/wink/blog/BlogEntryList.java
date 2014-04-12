package gash.wink.blog;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "blogentries")
public class BlogEntryList {
	@XmlElementRef
	ArrayList<BlogEntry> data;

	/**
	 * must have
	 */
	public BlogEntryList() {
	}

	public BlogEntryList(ArrayList<BlogEntry> data) {
		this.data = data;
	}

	public void setData(ArrayList<BlogEntry> data) {
		this.data = data;
	}

	/**
	 * cannot be named after data as a conflict is created with the attribute
	 * above
	 * 
	 * @return
	 */
	public ArrayList<BlogEntry> getList() {
		return data;
	}

}
