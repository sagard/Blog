package gash.wink.blog.service;

import gash.wink.blog.Blog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeData {
	public static List<Blog> generate() {
		ArrayList<Blog> list = new ArrayList<Blog>();

		Blog b = new Blog();
		b.setCreated(new Date());
		b.setOwner("Ann Frank");
		b.setSubject("Ann's Diary");
		b.setDescription("Sample blog created for demonstration");
		b.setSpace("Diary");
		list.add(b);

		b = new Blog();
		b.setCreated(new Date());
		b.setOwner("John Smith");
		b.setSubject("Using Apache Wink");
		b.setDescription("Sample blog created for demonstration");
		b.setSpace("Apache");
		list.add(b);

		b = new Blog();
		b.setCreated(new Date());
		b.setOwner("Ayn Rand");
		b.setSubject("Apache Tomcat");
		b.setDescription("Sample blog created for demonstration");
		b.setSpace("Apache");
		list.add(b);

		b = new Blog();
		b.setCreated(new Date());
		b.setOwner("Scott Free");
		b.setSubject("Apache Axis2");
		b.setDescription("Sample blog created for demonstration");
		b.setSpace("Apache");
		list.add(b);

		b = new Blog();
		b.setCreated(new Date());
		b.setOwner("Alice Sebold");
		b.setSubject("Fans of Lovely Bones");
		b.setDescription("Sample blog created for demonstration");
		b.setSpace("Books");
		list.add(b);

		b = new Blog();
		b.setCreated(new Date());
		b.setOwner("Ayn Rand");
		b.setSubject("About my books");
		b.setDescription("Sample blog created for demonstration");
		b.setSpace("Books");
		list.add(b);

		return list;
	}

}
