Blog
====

This is restful web service using apache wink.It uses mongodb as the data store.

Project gives the following functionalites:

i. Creation of blog
ii. Deletion of a blog
iii. Create Blog entry
iv. Delete Blog entry
v. Get Blog
vi. Get All Blogs
vii. Get Blog Entries.

======
**The request and response parameters are as follows:**

**CreateBlog** 

Example URL :
http://{Server IP or DNS e.g. localhost}/ikat/blog

use http POST i.e use @POST where ikatblog is the name of the war file.

Request Parameters
These parameters are used for requests to this API

|Attributename | datatype |
|subject | string |
|description |  string |
|userid	 |  string |
| timestamp |    string |

Response Parameters 
general http response

**GetBlog** 

Example URL :
http://{Server IP or DNS e.g. localhost}/ikat/blog/{blogid}

Response
as json object

Attributename |	 value |
blogid	   |    string |
subject	   |    string  |
description   |	   string |
userid	   |    string |
timestamp    |	     string |


**GetAllBlogs**

Example URL :
http://{Server IP or DNS e.g. localhost}/ikat/blog

use http Get i.e. @GET


Response

list of blogs as json object

Attributename  | value |
bogid	   |      string |
subject	   |    string   |
description    |	   string |
userid	   |    string  |
timestamp     |	     string |


**DeleteBlog**

Example URL :
http://{Server IP or DNS e.g. localhost}/ikat/blog/{blogid}/{userid}

use http DELETE method i.e. annotation @DELETE

Response
general http response

**CreateBlogEntry**

Example URL :
http://{Server IP or DNS e.g. localhost}/ikat/blog/{blogid}/blogentry

use http post i.e. @POST

Request Parameters
These parameters are used for requests to this API

Attributename |	datatype |
content	   |    string  |
userid	   |    string  |
timestamp     |	     string  |

Response Parameters
general http response



**GetBlogEntries**
Example URL :
http://{Server IP or DNS e.g.localhost}/ikat/blog/{blogid}/blogentry

Response

List of Blog Entry objects

Blog Entry 
Attributename |	 datatype  |
blogentryid    |	    string |
content	  |      string |
userid	    |    string  |
timestamp    |	      string |
 

**DeleteBlogEntry**

Example URL :
http://localhost/ikat/blog/{blogid}/blogentries/{blogentryid}/{userid}
use http DELETE i.e. @Delete

Response
general http response

===============

Software requirement:
 - Java: jdk 1.5 or later
 - MongoDB
 - Apache wink
 - Apache Tomcat
		
List of jar dependency (Library files):

Activation-1.1
Commons-lang-2.3
Jaxb-api-2.2
Jaxb-impl-2.2.1.1
Json-20080701
Jsr311-api-1.1.1
junit-4.8.1.jar
Mongo2.4
Slf4j-simple.1.6.1
Slf4j-api.1.6.1
Stax-api-1.0.2
Wink-1.1.2-incubating
Wink-client-1.1.2-incubating
Wink-common-1.1.2-incubating
Wink-server-1.1.2-incubating
		
MongoDB configuration:

1) Database : blog
Collection Name: blog
2) Database : blogentry
Collection Name: blogentry


