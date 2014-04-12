package gash.wink.blog.service;

import gash.wink.blog.Blog;
import gash.wink.blog.BlogEntry;
import gash.wink.blog.Space;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Simple in memory storage
 * 
 * @author gash
 * 
 */
public class BlogStorageMemory {
	private HashMap<String, ArrayList<Blog>> data = new HashMap<String, ArrayList<Blog>>();
	private HashMap<Integer, ArrayList<BlogEntry>> entries = new HashMap<Integer, ArrayList<BlogEntry>>();

	/**
	 * internal
	 * 
	 * @param data
	 */
	public void setData(HashMap<String, ArrayList<Blog>> data) {
		this.data = data;
	}

	/**
	 * list of the spaces and the number of blogs in each space
	 * 
	 * @return
	 */
	public ArrayList<Space> getSummary() {
		ArrayList<Space> list = new ArrayList<Space>();
		for (String k : data.keySet()) {
			Space s = new Space();
			s.setName(k);
			s.setCount(data.get(k).size());
			list.add(s);
		}

		return list;
	}

	/**
	 * all blogs
	 * 
	 * @return
	 */
	public ArrayList<Blog> getData() {
		ArrayList<Blog> all = new ArrayList<Blog>();
		for (String k : data.keySet())
			all.addAll(data.get(k));

		return all;
	}

	/**
	 * return blogs belonging to the specified space. If space is null, return
	 * all blogs.
	 * 
	 * TODO make space an List, add AND|OR rule
	 * 
	 * @param space
	 * @return
	 */
	public ArrayList<Blog> getData(String space) {
		if (space == null)
			return getData();

		return data.get(space);
	}

	/**
	 * entries for a blog
	 * 
	 * @param blogId
	 * @return
	 */
	public ArrayList<BlogEntry> getEntries(int blogId) {
		return entries.get(blogId);
	}

	/**
	 * find a blog
	 * 
	 * @param id
	 * @return
	 */
	public Blog getBlog(int id) {
		for (String k : data.keySet()) {
			for (Blog b : data.get(k)) {
				if (b.getId() == id)
					return b;
			}
		}

		return null;
	}

	/**
	 * add a new blog
	 * 
	 * @param blog
	 * @return
	 */
	public Blog add(Blog blog) {
		if (blog == null)
			return null;

		if (blog.getSpace() == null)
			blog.setSpace("undefined");

		// TODO better ID generation
		blog.setId(data.size() + 1);

		ArrayList<Blog> set = data.get(blog.getSpace());
		if (set == null) {
			set = new ArrayList<Blog>();
			data.put(blog.getSpace(), set);
		}

		set.add(blog);
		return blog;
	}

	/**
	 * add an entry to a blog
	 * 
	 * @param entry
	 * @return
	 */
	public BlogEntry add(BlogEntry entry) {
		if (entry == null)
			return null;

		Blog blog = lookupBlog(entry.getParentid());
		if (blog != null) {
			ArrayList<BlogEntry> e = entries.get(blog.getId());
			if (e == null) {
				e = new ArrayList<BlogEntry>();
				entries.put(blog.getId(), e);
			}

			entry.setId(e.size() + 1);
			e.add(entry);

			return entry;
		}

		return entry;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private Blog lookupBlog(int id) {
		for (String k : data.keySet()) {
			for (Blog b : data.get(k)) {
				if (b.getId() == id)
					return b;
			}
		}

		return null;
	}
}
