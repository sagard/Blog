package gash.wink.blog;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spacelist")
public class SpaceList {
	@XmlElementRef
	ArrayList<Space> data;

	/**
	 * must have
	 */
	public SpaceList() {
	}

	public SpaceList(ArrayList<Space> data) {
		this.data = data;
	}

	public ArrayList<Space> getList() {
		return data;
	}

	public void setData(ArrayList<Space> data) {
		this.data = data;
	}
}
