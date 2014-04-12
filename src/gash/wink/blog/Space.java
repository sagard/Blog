package gash.wink.blog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "space")
public class Space {
	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "count")
	private int count;

	public void setName(String name) {
		this.name = name;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSpaceName() {
		return name;
	}

	public int getSpaceCount() {
		return count;
	}

}
