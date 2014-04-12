package gash.wink.blog.service;

import gash.wink.blog.Blog;
import gash.wink.blog.BlogEntry;
import gash.wink.blog.BlogEntryList;
import gash.wink.blog.BlogSpace;
import gash.wink.blog.Space;
import gash.wink.blog.SpaceList;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.common.model.synd.SyndContent;
import org.apache.wink.common.model.synd.SyndEntry;
import org.apache.wink.common.model.synd.SyndText;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.server.utils.LinkBuilders;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class BlogServiceImpl implements BlogService {

	private DBCollection collection;
	private DBCollection collection1;
	private Properties conf;
	Mongo m;
	int count = 0;

	protected String sCollection = "blog";
	protected String sDB = "blog";
	protected String sHost = "localhost";
	// POS singleton (better solution should be
	// provided!)
	private static BlogServiceImpl svc;

	// simulate persistent storage (singleton svc ensures only one copy)
	private BlogStorageMemory storage = new BlogStorageMemory();

	public synchronized static BlogServiceImpl getInstance() {
		if (svc == null) {
			System.out.println("---> SVR: creating demo data");

			// TODO here we should add the storage the service uses (voldemort,
			// file system, db)
			svc = new BlogServiceImpl();

			// we need data for demonstration
			List<Blog> demo = FakeData.generate();
			for (Blog b : demo)
				svc.storage.add(b);
		}

		return svc;
	}

	// this is done it creates n new blog
	@Override
	public String CreateBlog(LinkBuilders linksBuilders, UriInfo uriInfo,
			String blog) {
		System.out.println("Adding new blog");
		try {

			JSONObject json = new JSONObject(blog);
			DBCollection col = connect();
			BasicDBObject query1 = new BasicDBObject("blogid", -1);
			DBCursor cursor1 = col.find().sort(query1).limit(1);
			if (cursor1.size() == 0) {
				json.put("blogid", 1);
			} else {
				String rtn = translateFromMongo(cursor1);
				System.out.println("OUtput: " + rtn);
				int id = Integer.parseInt(rtn);
				json.put("blogid", ++id);

			}
			// json.put("blogid",2);
			System.out.println(json.getString("description"));
			System.out.println(json.getString("blogid"));
			if (json != null) {

				DBObject dob = convertToMongo(json);
				col.insert(dob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blog;
	}

	// jst sme chng to b done
	@Override
	public String DeleteBlog(LinkBuilders linksBuilders, UriInfo uriInfo,
			int blogid, String userid) {
		System.out.println("deleting new blog");
		JSONObject json = new JSONObject();
		try {
			System.out.println("Printing the Blog details received in JSON");

			DBCollection col = connect();
			BasicDBObject b = new BasicDBObject("blogid", blogid + "");
			DBCursor cursor = col.find(b);

			if (cursor.size() >= 1) {
				String obj = cursorTouserid(cursor);
				System.out.println("from cursor " + obj);
				if (obj.equals(userid)) {
					System.out.println("Removing..." + obj);
					BasicDBObject b1 = new BasicDBObject("blogid", blogid + "");
					System.out.println("remove query" + b1);
					col.remove(b1);
					System.out.println("Group Removed!!");
					delete_entry(blogid);
					try {
						json.put("Error Code", "200");
						json.put("Error String", "Success");
					} catch (JSONException e) {
					}
				} else {
					try {
						json.put("Error Code", "404");
						json.put("Error String", "Resource Not Found");
					} catch (JSONException e) {
					}
				}
			} else {
				throw new WebApplicationException(Response.Status.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json.toString();
	}

	// done
	@Override
	public String createBlogEntry(LinkBuilders linksBuilders, UriInfo uriInfo,
			int blogid, String entry) {
		System.out.println("---> SVR: add entry '" + entry);
		try {
			System.out.println("entry in server :" + entry);
			JSONObject json = new JSONObject(entry);
			json.put("blogid", blogid + "");
			DBCollection col = connectToEntry();
			System.out.println("Collection name: " + col);
			BasicDBObject query1 = new BasicDBObject("blogentryid", -1);
			System.out.println("Query: " + query1);
			DBCursor cursor1 = col.find().sort(query1).limit(1);
			System.out.println("cursor before: " + cursor1);
			if (cursor1.size() == 0) {
				json.put("blogentryid", 1);
			} else {
				String rtn = translateEntry(cursor1);
				System.out.println("OUtput: " + rtn);
				int id = Integer.parseInt(rtn);
				json.put("blogentryid", ++id);
			}

			if (json != null) {
				System.out.println("inside json ");
				System.out.println(json.getString("blogentryid"));
				System.out.println(json.getString("content"));
				DBObject dob = convertToMongoEntry(json);
				col.insert(dob);
				System.out.println("Record added");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entry;
	}

	// done
	@Override
	public String GetBlog(int blogid) {
		System.out.println("---> SVR: get blog: " + blogid);

		DBCollection col = connect();
		BasicDBObject query1 = new BasicDBObject("blogid", blogid + "");
		System.out.println("Query is " + query1);
		DBCursor cursor1 = col.find(query1);
		if (cursor1.size() == 0) {
			System.out.println("nullllllllllllllllllllllllllllll");
			JSONObject json = new JSONObject();
			try {
				json.put("Error Code", "404");
				json.put("Error String", "Resource Not Found");
			} catch (JSONException e) {
			}
			return json.toString();
		} else {
			JSONObject obj = cursorToJson(cursor1);
			System.out.println("Server get blog is " + obj);

			return obj.toString();
		}

	}

	// done .. gets all blog
	@Override
	public String GetAllBlogs() {
		System.out.println("---> SVR: get all blogs");
		JSONObject obj = new JSONObject();

		DBCollection col = connect();
		DBCursor cursor = col.find();
		System.out.println("Cursor size is :" + cursor.size());
		JSONArray jarr = new JSONArray();
		if (cursor.size() == 0) {
			System.out.println("No data");
			JSONObject json = new JSONObject();
			try {
				json.put("Error Code", "404");
				json.put("Error String", "Resource Not Found");
			} catch (JSONException e) {
			}
			return json.toString();
		} else {

			while (cursor.hasNext()) {
				DBObject db = cursor.next();

				try {
					obj.append("blogid", db.get("blogid").toString());
					obj.append("subject", db.get("subject").toString());
					obj.append("description", db.get("description").toString());
					obj.append("userid", db.get("userid").toString());
					obj.append("timestamp", db.get("timestamp").toString());
					System.out.println("Server get blog is " + obj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		System.out
				.println("The to string converted jarr is :" + obj.toString());
		return obj.toString();
	}

	// done
	@Override
	public String getBlogEntries(int blogid) {
		System.out.println("---> SVR: get blog: " + blogid);

		DBCollection col = connectToEntry();
		BasicDBObject query1 = new BasicDBObject("blogid", blogid + "");
		System.out.println("Query is " + query1);
		DBCursor cursor1 = col.find(query1);
		if (cursor1.size() == 0) {
			System.out.println("null");
			JSONObject json = new JSONObject();
			try {
				json.put("Error Code", "404");
				json.put("Error String", "Resource Not Found");
			} catch (JSONException e) {
			}
			return json.toString();
		} else {
			JSONObject obj = curtoJsonBlogEntry(cursor1);
			System.out.println("Server get blog is " + obj);
			return obj.toString();
		}

	}

	@Override
	public String DeleteBlogEntry(LinkBuilders linksBuilders, UriInfo uriInfo,
			int blogid, int blogentryid, String userid) {
		System.out.println("deleting new blog");
		JSONObject json = new JSONObject();
		try {
			System.out.println("Printing the Blog details received in JSON");

			DBCollection col = connectToEntry();
			BasicDBObject b = new BasicDBObject("blogid", blogid + "");
			DBCursor cursor = col.find(b);
			if (cursor.size() == 0) {
				// System.out.println("N");
				throw new WebApplicationException(Response.Status.NO_CONTENT);
			}

			String obj = cursorTouserid(cursor);
			System.out.println("from cursor " + obj);
			System.out.println("The user id :" + userid);
			BasicDBObject b2 = new BasicDBObject("blogentryid", blogentryid
					+ "");
			DBCursor cursor1 = col.find(b2);
			System.out.println("Cursor size outside: " + cursor1.size());
			String obj1 = cursorToblogentryid(cursor1);
			if (obj.equals(userid) && !obj1.equals("")) {
				System.out.println("Removing..." + obj);
				BasicDBObject b1 = new BasicDBObject("blogentryid", blogentryid
						+ "");
				System.out.println("remove query" + b1);
				col.remove(b1);
				System.out.println("Group Removed!!");
				try {
					json.put("Error Code", "200");
					json.put("Error String", "Success");
				} catch (JSONException e) {
				}
			} else {
				try {
					json.put("Error Code", "404");
					json.put("Error String", "Resource Not Found");
				} catch (JSONException e) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public void delete_entry(int blogid) {
		System.out.println("deleting corresponding blog entries");
		try {
			DBCollection col1 = connectToEntry();
			System.out.println("Collection name in delete_entry: " + col1);
			BasicDBObject b1 = new BasicDBObject("blogid", blogid + "");
			System.out.println("Blogid in delete_entry: " + blogid);
			System.out.println("remove query" + b1);
			col1.remove(b1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DBCollection connect() {
		conf = new Properties();
		conf.setProperty(sHost, "localhost");
		conf.setProperty(sDB, "blog");
		conf.setProperty(sCollection, "blog");
		try {
			if (collection != null && collection.getName() != null) {
				System.out.println("Collection Name: " + collection);
				return collection;
			}
		} catch (Exception ex) {
			collection = null;
		}

		try {
			m = new Mongo(conf.getProperty(sHost));
			DB db = m.getDB(conf.getProperty(sDB));
			collection = db.getCollection(conf.getProperty(sCollection));
			if (collection == null)
				throw new RuntimeException("Missing collection: "
						+ conf.getProperty(sCollection));

			return collection;
		} catch (Exception ex) {
			// should never get here unless no directory is available
			throw new RuntimeException("Unable to connect to mongodb on "
					+ conf.getProperty(sHost));
		}
	}

	// to connect to blog entries
	private DBCollection connectToEntry() {
		conf = new Properties();
		conf.setProperty(sHost, "localhost");
		conf.setProperty(sDB, "blogentry");
		conf.setProperty(sCollection, "blogentry");
		try {
			if (collection1 != null && collection1.getName() != null) {
				System.out.println("Collection Name: " + collection1);
				return collection1;
			}
		} catch (Exception ex) {
			collection1 = null;
		}

		try {
			m = new Mongo(conf.getProperty(sHost));
			DB db = m.getDB(conf.getProperty(sDB));
			System.out.println("db name:" + conf.getProperty(sDB));
			System.out.println("db name:" + db);
			collection1 = db.getCollection(conf.getProperty(sCollection));
			if (collection1 == null)
				throw new RuntimeException("Missing collection: "
						+ conf.getProperty(sCollection));

			return collection1;
		} catch (Exception ex) {
			// should never get here unless no directory is available
			throw new RuntimeException("Unable to connect to mongodb on "
					+ conf.getProperty(sHost));
		}
	}

	private DBObject convertToMongoEntry(JSONObject p) {
		BasicDBObject rtn = new BasicDBObject();
		try {
			if (p.getString("blogentryid") != null)
				rtn.append("blogentryid", p.getString("blogentryid"));
			if (p.getString("blogid") != null)
				rtn.append("blogid", p.getString("blogid"));
			if (p.getString("content") != null)
				rtn.append("content", p.getString("content"));
			if (p.getString("userid") != null)
				rtn.append("userid", p.getString("userid"));
			if (p.getString("timestamp") != null)
				rtn.append("timestamp", p.getString("timestamp"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtn;
	}

	private String translateEntry(DBCursor cursor) {
		String v = "";
		System.out.println("---> cursor size: " + cursor.size());
		System.out.println("Cursor  " + cursor);
		for (int n = 0, N = cursor.size(); n < N; n++) {
			DBObject data = cursor.next();
			v = data.get("blogentryid").toString();
			System.out.println("Cursor Size: " + v);
		}
		return v;
	}

	private DBObject convertToMongo(JSONObject p) {
		BasicDBObject rtn = new BasicDBObject();
		try {
			if (p.getString("blogid") != null)
				rtn.append("blogid", p.getString("blogid"));
			if (p.getString("subject") != null)
				rtn.append("subject", p.getString("subject"));
			if (p.getString("description") != null)
				rtn.append("description", p.getString("description"));
			if (p.getString("userid") != null)
				rtn.append("userid", p.getString("userid"));
			if (p.getString("timestamp") != null)
				rtn.append("timestamp", p.getString("timestamp"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtn;
	}

	// used in delete
	private String cursorTouserid(DBCursor cursor) {
		String userid = "";
		System.out.println("Inside cursor");
		for (int n = 0, N = cursor.size(); n < N; n++) {
			DBObject data = cursor.next();
			userid = (String) data.get("userid");
		}
		return userid;

	}

	private String cursorToblogentryid(DBCursor cursor) {
		String blogentryid = "";
		System.out.println("Inside cursor2");

		for (int n = 0, N = cursor.size(); n < N; n++) {
			DBObject data = cursor.next();
			blogentryid = (String) data.get("blogentryid");
		}

		return blogentryid;

	}

	private String translateFromMongo(DBCursor cursor) {
		String v = "";
		System.out.println("---> cursor size: " + cursor.size());
		for (int n = 0, N = cursor.size(); n < N; n++) {
			DBObject data = cursor.next();
			v = data.get("blogid").toString();
		}
		return v;
	}

	private JSONObject cursorToJson(DBCursor cursor) {
		String v = "";
		String blogid = null, subject = "", description = "", userid = "", timestamp = "";
		StringBuffer s = new StringBuffer();
		System.out.println("---> cursor size: " + cursor.size());
		for (int n = 0, N = cursor.size(); n < N; n++) {
			DBObject data = cursor.next();

			blogid = (String) data.get("blogid");
			subject = (String) data.get("subject");
			description = (String) data.get("description");
			userid = (String) data.get("userid");
			timestamp = (String) data.get("timestamp");

		}
		JSONObject obj = new JSONObject();
		try {
			obj.put("blogid", blogid);
			obj.put("subject", subject);
			obj.put("description", description);
			obj.put("userid", userid);
			obj.put("timestamp", timestamp);
		} catch (Exception e) {
		}
		return obj;
	}

	// for get blog entry to convert to json obj
	private JSONObject curtoJsonBlogEntry(DBCursor cursor) {
		String v = "";
		String blogid = null, content = "", userid = "", timestamp = "", blogentryid = "";
		StringBuffer s = new StringBuffer();
		System.out.println("---> cursor size: " + cursor.size());
		JSONObject obj = new JSONObject();

		for (int n = 0, N = cursor.size(); n < N; n++) {
			DBObject data = cursor.next();

			blogid = (String) data.get("blogid");
			blogentryid = (String) data.get("blogentryid");
			content = (String) data.get("content");
			userid = (String) data.get("userid");
			timestamp = (String) data.get("timestamp");

			try {
				obj.append("blogid", blogid);
				obj.append("blogentryid", blogentryid);
				obj.append("content", content);
				obj.append("userid", userid);
				obj.append("timestamp", timestamp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return obj;
	}
}
