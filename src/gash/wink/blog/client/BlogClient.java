package gash.wink.blog.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gash.wink.blog.Blog;

import org.json.JSONException;
import org.json.JSONObject;
import gash.wink.blog.BlogEntry;
import gash.wink.blog.BlogEntryList;
import gash.wink.blog.Space;
import gash.wink.blog.SpaceList;


import javax.ws.rs.core.MediaType;

import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import javax.ws.*;
import javax.ws.rs.*;
import javax.ws.rs.ext.*;
import org.apache.*;

import org.apache.wink.common.internal.application.*;
import org.apache.wink.common.internal.application.ApplicationFileLoader.*;
public class BlogClient {
	private String endPoint = "http://localhost:8080/blog/rest/blogspace";

	public BlogClient() {
	}

	public BlogClient(String url) {
		endPoint = url;
	}


	
	//This is use to get a particualr blog
	public void GetBlog() {
		System.out.println("\n** Get Blog: retrieve a  particular blog description:");
		try {
            int blogid = 2;
			String url = endPoint + "/blog/" + blogid;
			System.out.println("Clent url is "+url);
			RestClient client = new RestClient();
			Resource resource = client.resource(url);
			//JSONObject obj = new JSONObject();
		//	obj = resource.accept(MediaType.APPLICATION_JSON).get(JSONObject.class);
			JSONObject obj = new JSONObject(resource.accept(MediaType.APPLICATION_JSON).get(String.class));
			System.out.println("BLOG at client side is : " + obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	//get list of blog entries
	public void GetBlogEntries() {
		System.out.println("\n** Get blog Entries: retrieve a blog's entries:");
		try {
			int blogid = 1;
			String url = endPoint + "/blog/" + blogid + "/blogentry";
			RestClient client = new RestClient();
			Resource resource = client.resource(url);

			// we can get the return as a string or converted to an object
			// String response =
			// resource.accept(MediaType.APPLICATION_XML).get(String.class);
			JSONObject obj  = new JSONObject(resource.accept(MediaType.APPLICATION_JSON).get(String.class));
			System.out.println("BLOG at client side is : " + obj);
			String error = obj.getString("Error Code");
			if(!error.equals("404"))
			{
			List timestamp = Arrays.asList(obj.get("timestamp"));
		    List content = Arrays.asList(obj.get("content"));
		    List userid = Arrays.asList(obj.get("userid"));
		    List bid = Arrays.asList(obj.get("blogid"));
		    List blogentryid = Arrays.asList(obj.get("blogentryid"));
		    
		    int size = bid.size();
		    System.out.println("Size is :"+size);
		    for(int i =0;i<size;i++)
		    {
		    	System.out.println("Blog id are :"+bid.get(i));
		    	System.out.println("Blog entry are :"+blogentryid.get(i));
		       	System.out.println("Content of blog are :"+content.get(i));
		    	System.out.println("User id is :"+userid.get(i));
		    	System.out.println("TimeStamps is :"+timestamp.get(i));
		    }	         
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//This function creates a new blog
	public void CreateBlog() {
		System.out.println("\n** Add Blog: create a blog:");
		try {
			JSONObject object=new JSONObject();
			object.put("subject","New ruchita blog");
			object.put("description","ruchita blog");
			object.put("userid","ruchu");	
			object.put("timestamp",new Date());	
			System.out.println("Json object: "+ object);
			String url = endPoint;
			RestClient client = new RestClient();
			Resource resource = client.resource(url);
			JSONObject obj = new JSONObject(resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(object.toString()));
			System.out.println("Created blog id: " + obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void DeleteBlog() {
		System.out.println("\n** Remove Blog: remove a blog:");
		try {

			int blogid = 1;
			String userid = "ruchu";
			
			String url = endPoint + "/blog/" + blogid + "/"+userid;
			
			RestClient client = new RestClient();
			Resource resource = client.resource(url);
			//Blog created = resource.contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Blog.class, b);
			JSONObject obj = new JSONObject(resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete());
			System.out.println("deleted blog id: " + obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//use to create a individual create blog entry
	public void CreateBlogEntry() {
		System.out.println("\n** Add entry: append an entry to a blog:");
		try {
			int blogid = 1;
			String url = endPoint + "/blogentry/"+ blogid;
			JSONObject object = new JSONObject();
			//object.put("blogid",1);
			object.put("content","HI");
			object.put("userid", 101);
			object.put("timestamp",new Date());
			System.out.println("JSON Object"+object);			
			RestClient client = new RestClient();
			Resource resource = client.resource(url);
			
			JSONObject obj = new JSONObject(resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(object.toString()));
			System.out.println("Output at CLient side: "+obj);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	
	//This is use to get all sblog
	public void GetAllBlogs() {
		System.out.println("\n** Get Blogs: retrieve all blog description:");
		try {
			String url = endPoint + "/blog/" ;
			System.out.println("Clent url is "+url);
			RestClient client = new RestClient();
			Resource resource = client.resource(url);
			JSONObject obj = new JSONObject(resource.accept(MediaType.APPLICATION_JSON).get(String.class));
			String error = obj.getString("Error Code");
			System.out.println("BLOG at client side is : " + obj);
			if(!error.equals("404"))
			{			
			List timestamp = Arrays.asList(obj.get("timestamp"));
		    List subject = Arrays.asList(obj.get("subject"));
		    List description = Arrays.asList(obj.get("description"));
		    List userid = Arrays.asList(obj.get("userid"));
		    List blogid = Arrays.asList(obj.get("blogid"));
		    int size = blogid.size();
		    System.out.println("Size is :"+size);
		    for(int i =0;i<size;i++)
		    {
		    	System.out.println("Blog id are :"+blogid.get(i));
		    	System.out.println("Subjects are :"+subject.get(i));
		    	System.out.println("Description of blog are :"+description.get(i));
		    	System.out.println("User id is :"+userid.get(i));
		    	System.out.println("TimeStamps is :"+timestamp.get(i));
		    }		    		    	               
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void DeleteBlogEntry() {
		System.out.println("\n** Remove Blog: remove a blog:");
		try {

			int blogid = 2;
			String userid = "101";
			int blogentryid = 3;
			String url = endPoint + "/blog/" + blogid + "/blogentries/"+blogentryid + "/"+ userid;
			
			RestClient client = new RestClient();
			Resource resource = client.resource(url);
			//Blog created = resource.contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Blog.class, b);
			JSONObject obj = new JSONObject(resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(String.class));
			System.out.println("deleted blog id: " + obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BlogClient bc = new BlogClient("http://localhost:8080/blog/rest/blogspace");
		//bc.CreateBlog();
       //bc.GetBlog();
         //bc.CreateBlogEntry();

		bc.GetAllBlogs();
		//bc.GetBlogEntries();
		//bc.DeleteBlog();
		//bc.DeleteBlogEntry();
	}
}
