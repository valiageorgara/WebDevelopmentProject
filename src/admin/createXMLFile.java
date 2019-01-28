package admin;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Bean.CommentDAO;
import Bean.ContactDAO;
import Bean.JobDAO;
import Bean.LikeDAO;
import Bean.PostDAO;
import Bean.UserDAO;
import model.Comment;
import model.Contact;
import model.Job;
import model.Like;
import model.Post;
import model.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.*;

public class createXMLFile {

	public static String createXml(String UserEmail) {
		// TODO Auto-generated method stub
		String xmlReq=null;
		User user = UserDAO.searchUser(UserEmail);
		List<Comment> commentlist = CommentDAO.searchCommentsBySpecificUser(UserEmail);
		List<Like> likelist = LikeDAO.searchLikesBySpecificUser(UserEmail);
		List<Post> postlist = PostDAO.searchPostList(UserEmail);
		List<Job> joblist = JobDAO.searchJobList(UserEmail);
		List<Contact> contactlist = ContactDAO.searchContactList(UserEmail);
		
		try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            /////////////////////root element // Users 
            Element rootElement = doc.createElement("Users");
            doc.appendChild(rootElement);
            
            /////////////////////user element
            Element User = doc.createElement("User");
            rootElement.appendChild(User);
            
            ////////////////////userInformation
            Element userInformation = doc.createElement("UserInformation");
            User.appendChild(userInformation);

            ///////////////////UserPosts
            Element userPosts = doc.createElement("UserPosts");
            if(postlist.size()==0){
                userPosts.appendChild(doc.createTextNode("NONE"));
            }
            User.appendChild(userPosts);

            ////////////////////UserComments
            Element userComments = doc.createElement("UserComments");
            if(commentlist.size()==0){
            	userComments.appendChild(doc.createTextNode("NONE"));
            }
            User.appendChild(userComments);
            
            ////////////////////UserLikes
		    Element userLikes = doc.createElement("UserLikes");
		    if(likelist.size()==0){
		    	userLikes.appendChild(doc.createTextNode("NONE"));
		    }
		    User.appendChild(userLikes);

            ////////////////////UserContacts
            Element userContacts = doc.createElement("UserContacts");
            if(contactlist.size()==0){
            	userContacts.appendChild(doc.createTextNode("NONE"));
            }
            User.appendChild(userContacts);

            //////////////////UserJobs
            Element userJobs = doc.createElement("UserJobs");
            if(joblist.size()==0){
            	userContacts.appendChild(doc.createTextNode("NONE"));
            }
            User.appendChild(userJobs);

            ///////////elements UserInformation
            Element firstname = doc.createElement("firstname");
            System.out.println(user.getFirstname());
            if(user.getFirstname() != null)
            	firstname.appendChild(doc.createTextNode(user.getFirstname()));
            else
            	firstname.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(firstname);

            Element lastname = doc.createElement("lastname");
            System.out.println(user.getLastname());
            if(user.getLastname() != null)
            	lastname.appendChild(doc.createTextNode(user.getLastname()));
            else
            	lastname.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(lastname);

            Element email = doc.createElement("email");
            if(user.getEmail() != null)
            	email.appendChild(doc.createTextNode(user.getEmail()));
            else
            	email.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(email);

            Element password = doc.createElement("password");
            if(user.getPassword() != null)
            	password.appendChild(doc.createTextNode(user.getPassword()));
            else            	
            	password.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(password);

            Element number = doc.createElement("number");
            if(user.getNumber() != null)
            	number.appendChild(doc.createTextNode(user.getNumber()));
            else
            	number.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(number);

            Element imageURL = doc.createElement("imageURL");
            if(user.getImageURL() != null)
            	imageURL.appendChild(doc.createTextNode(user.getImageURL()));
            else
            	imageURL.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(imageURL);

            Element carrier = doc.createElement("carrier");
            if(user.getCarrier() != null)
            	carrier.appendChild(doc.createTextNode(user.getCarrier()));
            else
            	carrier.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(carrier);

            Element carrierRadio = doc.createElement("carrierRadio");
            if(user.getCarrierRadio() != null)
            	carrierRadio.appendChild(doc.createTextNode(user.getCarrierRadio()));
            else
            	carrierRadio.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(carrierRadio);

            Element company = doc.createElement("company");
            if(user.getCompany() != null)
            	company.appendChild(doc.createTextNode(user.getCompany()));
            else
            	company.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(company);

            Element companyRadio = doc.createElement("companyRadio");
            if(user.getCompanyRadio() != null)
            	companyRadio.appendChild(doc.createTextNode(user.getCompanyRadio()));
            else
            	companyRadio.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(companyRadio);

            Element jobexperience = doc.createElement("jobexperience");
            if(user.getJobexperience() != null)
            	jobexperience.appendChild(doc.createTextNode(user.getJobexperience()));
            else
            	jobexperience.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(jobexperience);

            Element jobexperienceRadio = doc.createElement("jobexperienceRadio");
            if(user.getJobexperienceRadio() != null)
            	jobexperienceRadio.appendChild(doc.createTextNode(user.getJobexperienceRadio()));
            else
            	jobexperienceRadio.appendChild(doc.createTextNode("none"));            
            userInformation.appendChild(jobexperience);

            Element education = doc.createElement("education");
            if(user.getEducation() != null)
            	education.appendChild(doc.createTextNode(user.getEducation()));
            else
            	education.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(education);

            Element educationRadio = doc.createElement("educationRadio");
            if(user.getEducationRadio() != null)
            	educationRadio.appendChild(doc.createTextNode(user.getEducationRadio()));
            else
            	educationRadio.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(educationRadio);

            Element skills = doc.createElement("skills");
            if(user.getSkills() != null)
            	skills.appendChild(doc.createTextNode(user.getSkills()));
            else
            	skills.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(skills);

            Element skillsRadio = doc.createElement("skillsRadio");
            if(user.getSkillsRadio() != null)
            	skillsRadio.appendChild(doc.createTextNode(user.getSkillsRadio()));
            else
            	skillsRadio.appendChild(doc.createTextNode("none"));
            userInformation.appendChild(skillsRadio);

            
////////////////////////////////////////userPosts
            if(postlist.size()>0){
                for(int i=0; i< postlist.size(); i++ ){
                    Element text = doc.createElement("text");
                    if(postlist.get(i).getText() != null)
                    	text.appendChild(doc.createTextNode(postlist.get(i).getText()));
                    else
                    	text.appendChild(doc.createTextNode("none"));
                    userPosts.appendChild(text);

                    Element datetime = doc.createElement("datetime");
                    if(postlist.get(i).getDatetime() != null)
                    	datetime.appendChild(doc.createTextNode(postlist.get(i).getDatetime()));
                    else
                    	datetime.appendChild(doc.createTextNode("none"));
                    userPosts.appendChild(datetime);

                    Element imageUrl = doc.createElement("imageUrl");
                    if(postlist.get(i).getImageUrl() != null)
                    	imageUrl.appendChild(doc.createTextNode(postlist.get(i).getImageUrl()));
                    else
                    	imageUrl.appendChild(doc.createTextNode("none"));
                    userPosts.appendChild(imageUrl);

                    Element videoUrl = doc.createElement("videoUrl");
                    if(postlist.get(i).getVideoUrl() == null)
                    	videoUrl.appendChild(doc.createTextNode(postlist.get(i).getVideoUrl()));
                    else
                    	videoUrl.appendChild(doc.createTextNode("none"));
                    userPosts.appendChild(videoUrl);

                    Element audioUrl = doc.createElement("audioUrl");
                    if(postlist.get(i).getAudioUrl() != null)
                    	audioUrl.appendChild(doc.createTextNode(postlist.get(i).getAudioUrl()));
                    else
                    	audioUrl.appendChild(doc.createTextNode("none"));
                    userPosts.appendChild(audioUrl);

                    Element postsId = doc.createElement("postsId");
                    postsId.appendChild(doc.createTextNode(String.valueOf(postlist.get(i).getPostsId())));
                    userPosts.appendChild(postsId);
                    
                    Element users_email = doc.createElement("users_email");
                    users_email.appendChild(doc.createTextNode(postlist.get(i).getUser().getEmail()));
                    userPosts.appendChild(users_email);
                }
            }
////////////////////////////////////userComments
            if(commentlist.size()>0){
                for(int i=0; i< commentlist.size(); i++){
                	Element idcomment = doc.createElement("idcomment");
                    idcomment.appendChild(doc.createTextNode(String.valueOf(commentlist.get(i).getIdcomments())));
                    userComments.appendChild(idcomment);

                    Element commentstext = doc.createElement("commentstext");
                    if(commentlist.get(i).getCommentstext() != null)
                    	commentstext.appendChild(doc.createTextNode(commentlist.get(i).getCommentstext()));
                    else
                    	commentstext.appendChild(doc.createTextNode("none"));
                    userComments.appendChild(commentstext);

                    Element datetime = doc.createElement("datetime");
                    if(commentlist.get(i).getDatetime() != null)
                    	datetime.appendChild(doc.createTextNode(commentlist.get(i).getDatetime()));
                    else
                    	datetime.appendChild(doc.createTextNode("none"));
                    userComments.appendChild(datetime);

                    Element commenterName = doc.createElement("commenterName");
                    if(commentlist.get(i).getCommenterName() != null)
                    	commenterName.appendChild(doc.createTextNode(commentlist.get(i).getCommenterName()));
                    else
                    	commenterName.appendChild(doc.createTextNode("none"));
                    userComments.appendChild(commenterName);

                    Element commenterImage = doc.createElement("commenterImage");
                    if(commentlist.get(i).getCommenterImage() != null)
                    	commenterImage.appendChild(doc.createTextNode(commentlist.get(i).getCommenterImage()));
                    else
                    	commenterImage.appendChild(doc.createTextNode("none"));
                    userComments.appendChild(commenterImage);

                    Element emailComment = doc.createElement("email");
                    emailComment.appendChild(doc.createTextNode(commentlist.get(i).getEmail()));
                    userComments.appendChild(emailComment);

                    Element postidComment = doc.createElement("postid");
                    postidComment.appendChild(doc.createTextNode(String.valueOf(commentlist.get(i).getPost().getPostsId())));
                    userComments.appendChild(postidComment);
                }
            }
////////////////////userLikes
            if(likelist.size()>0){
            	for(int i=0; i< likelist.size(); i++){
                	Element likeId = doc.createElement("likeId");
                    likeId.appendChild(doc.createTextNode(String.valueOf(likelist.get(i).getLikeId())));
                    userLikes.appendChild(likeId);

                    Element emailLike = doc.createElement("email");
                    emailLike.appendChild(doc.createTextNode(likelist.get(i).getEmail()));
                    userLikes.appendChild(emailLike);
                    
                    Element postidLike = doc.createElement("postid");
                    postidLike.appendChild(doc.createTextNode(String.valueOf(likelist.get(i).getPost().getPostsId())));
                    userLikes.appendChild(postidLike);
                }
            }
            
////////////////////userContacts
		    if(contactlist.size()>0){
		    	for(int i=0; i< contactlist.size(); i++){
		    		
		        	Element idcontacts = doc.createElement("idcontacts");
		        	idcontacts.appendChild(doc.createTextNode(String.valueOf(contactlist.get(i).getIdcontacts())));
		            userContacts.appendChild(idcontacts);
		
		            Element firstnameContacts = doc.createElement("firstname");
		            if(contactlist.get(i).getFirstname() != null)
		            	firstnameContacts.appendChild(doc.createTextNode(contactlist.get(i).getFirstname()));
                    else
                    	firstnameContacts.appendChild(doc.createTextNode("none"));
		            userContacts.appendChild(firstnameContacts);
		            
		            Element lastnameContacts = doc.createElement("lastname");
		            if(contactlist.get(i).getLastname() != null)
		            	lastnameContacts.appendChild(doc.createTextNode(contactlist.get(i).getLastname()));
                    else
                    	lastnameContacts.appendChild(doc.createTextNode("none"));
		            userContacts.appendChild(lastnameContacts);
		            
		            Element imageURLContacts = doc.createElement("imageURL");
		            if(contactlist.get(i).getImageURL() != null)
		            	imageURLContacts.appendChild(doc.createTextNode(contactlist.get(i).getImageURL()));
                    else
                    	imageURLContacts.appendChild(doc.createTextNode("none"));
		            userInformation.appendChild(imageURLContacts);
		            
		            Element jobtitle = doc.createElement("jobtitle");
		            if(contactlist.get(i).getJobtitle() != null)
		            	jobtitle.appendChild(doc.createTextNode(contactlist.get(i).getJobtitle()));
                    else
                    	jobtitle.appendChild(doc.createTextNode("none"));
		            userContacts.appendChild(jobtitle);
		            
		            Element department = doc.createElement("department");
		            if(contactlist.get(i).getDepartment() != null)
		            	department.appendChild(doc.createTextNode(contactlist.get(i).getDepartment()));
                    else
                    	department.appendChild(doc.createTextNode("none"));
		            userContacts.appendChild(department);
		            
		            Element emailContacts = doc.createElement("contactEmail");
		            if(contactlist.get(i).getEmail() != null)
		            	emailContacts.appendChild(doc.createTextNode(contactlist.get(i).getEmail()));
                    else
                    	emailContacts.appendChild(doc.createTextNode("none"));
		            userContacts.appendChild(emailContacts);
		            
		            Element users_emailContacts = doc.createElement("email");
		            users_emailContacts.appendChild(doc.createTextNode(contactlist.get(i).getUser().getEmail()));
		            userContacts.appendChild(users_emailContacts);
		        }
		    }


////////////////////userJobs
		    if(joblist.size()>0){
		    	for(int i=0; i< joblist.size(); i++){
		    		
		        	Element jobsId = doc.createElement("jobsId");
		        	jobsId.appendChild(doc.createTextNode(String.valueOf(joblist.get(i).getJobsId())));
		            userJobs.appendChild(jobsId);
		
		            Element companyJobs = doc.createElement("company");
		            if(joblist.get(i).getCompany() != null)
		            	companyJobs.appendChild(doc.createTextNode(joblist.get(i).getCompany()));
                    else
                    	companyJobs.appendChild(doc.createTextNode("none"));
		            userJobs.appendChild(companyJobs);
		            
		            Element jobtitleJobs = doc.createElement("jobtitle");
		            if(joblist.get(i).getJobtitle() != null)
		            	jobtitleJobs.appendChild(doc.createTextNode(joblist.get(i).getJobtitle()));
                    else
                    	jobtitleJobs.appendChild(doc.createTextNode("none"));
		            userJobs.appendChild(jobtitleJobs);
		            
		            Element location = doc.createElement("location");
		            if(joblist.get(i).getLocation() != null)
		            	location.appendChild(doc.createTextNode(joblist.get(i).getLocation()));
                    else
                    	location.appendChild(doc.createTextNode("none"));
		            userJobs.appendChild(location);
		            
		            Element users_emailJobs = doc.createElement("email");
		            users_emailJobs.appendChild(doc.createTextNode(joblist.get(i).getUser().getEmail()));
		            userJobs.appendChild(users_emailJobs);
		        }
		    }
    /*
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            OutputStream out = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(out);
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, streamResult);
            xmlReq = streamResult.getOutputStream().toString();

            System.out.println("XML File saved!"); */
		    
		 // write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(new File("C:\\file.xml"));

			// Output to console for testing
			StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        return xmlReq;
		
	}
}
