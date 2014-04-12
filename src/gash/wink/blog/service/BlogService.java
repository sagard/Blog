package gash.wink.blog.service;

import gash.wink.blog.Blog;
import gash.wink.blog.BlogEntry;
import gash.wink.blog.BlogEntryList;
import gash.wink.blog.BlogSpace;
import gash.wink.blog.SpaceList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.wink.server.utils.LinkBuilders;

/**
 * JAX-RS service definition
 * 
 * @author gash
 * 
 */
@Path("/blogspace")
public interface BlogService {

	/**
	 * create a blog. Note this is a post not a put as we do not want to enforce
	 * uniqueness in blogs (where as if this was a user registration, we would
	 * want to use a PUT).
	 * 
	 * POST rest/blogspace
	 * 
	 * @param linksBuilders
	 * @param uriInfo
	 * @param blog
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String CreateBlog(@Context LinkBuilders linksBuilders, @Context UriInfo uriInfo, String blog);
	
	
	
	/**
	 * delete a blog. Note this is a post not a put as we do not want to enforce
	 * uniqueness in blogs (where as if this was a user registration, we would
	 * want to use a PUT).
	 * 
	 * POST rest/blogspace
	 * 
	 * @param linksBuilders
	 * @param uriInfo
	 * @param blog
	 * @return
	 */
	@Path("/blog/{blogid}/{userid}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteBlog(@Context LinkBuilders linksBuilders, @Context UriInfo uriInfo,@PathParam("blogid") int blogid,@PathParam("userid") String userid);
	

	@Path("/blog/{blogid}/blogentries/{blogentryid}/{userid}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String DeleteBlogEntry(@Context LinkBuilders linksBuilders, @Context UriInfo uriInfo,@PathParam("blogid") int blogid,@PathParam("blogentryid") int blogentryid,@PathParam("userid") String userid);


	
	/**
	 * create an entry in a blog
	 * 
	 * @param linksBuilders
	 * @param uriInfo
	 * @param entry
	 * @return
	 */
	@Path("/blogentry/{blogid}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public String createBlogEntry(@Context LinkBuilders linksBuilders, @Context UriInfo uriInfo, @PathParam("blogid") int blogid,String entry);


	

	/**
	 * request descriptions on all the blogs
	 * 
	 * GET /rest/blogspace/
	 * 
	 * @return
	 */
	@Path("/blog")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllBlogs();

	/**
	 * request a specific blog
	 * 
	 * GET /rest/blogspace/blog/{id}
	 * 
	 * @param id
	 * @return
	 */
	@Path("/blog/{blogid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String GetBlog(@PathParam("blogid") int blogid);
	
	/**
	 * request a specific blog (HTML)
	 * 
	 * GET /rest/blogspace/blog/{id}
	 * 
	 * @param id
	 * @return
	 */
	

	/**
	 * request descriptions on all the blogs
	 * 
	 * GET /rest/blogspace/
	 * 
	 * @return
	 */
	@Path("/blog/{blogid}/blogentry")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getBlogEntries(@PathParam("blogid") int blogid);
}
