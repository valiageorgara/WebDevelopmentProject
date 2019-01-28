package knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Bean.JobDAO;
import Bean.PostDAO;
import db.HelpingFunctions;
import model.Application;
import model.Comment;
import model.Job;
import model.Like;
import model.Post;
import model.User;

public class knnAlgorithm {

	public static List<Job> knnImplementationJob(User user, List<Job> skillsjoblist) {
		int k = 3; // change it
		List<Job> joblist = new ArrayList<Job>();
		List<Job> dataset = JobDAO.selectJobsBefore(user.getLastLogin());
		List<Job> trainingset = JobDAO.selectJobsAfter(user.getLastLogin());
		
		List<knnClass> knnlist = knnListJob(dataset, user.getEmail());  //gemizei to pedio value gia to dataset
		for (int i = 0; i < trainingset.size(); i++) {
			List<Integer> distance = new ArrayList<Integer>();
			for (int j = 0; j < dataset.size(); j++) {
				int dis = 0;
				dis += HelpingFunctions.LevenshteinDistance(dataset.get(j).getCompany(),
						trainingset.get(i).getCompany());
				dis += HelpingFunctions.LevenshteinDistance(dataset.get(j).getJobtitle(),
						trainingset.get(i).getJobtitle());
				dis += HelpingFunctions.LevenshteinDistance(dataset.get(j).getLocation(),
						trainingset.get(i).getLocation());

				distance.add(dis);
			}
			List<knnClass> distancelist = new ArrayList<knnClass>();
			
			for (int j = 0; j < dataset.size(); j++) {
				knnClass temp = new knnClass();
				temp.setValue(distance.get(j));
				temp.setId(dataset.get(j).getJobsId());
				distancelist.add(j, temp);

			}
			distancelist.sort(Comparator.comparingInt(knnClass::getValue));
			
			int count = 0;
			int j;
			for (j = 0; j < k && j < distancelist.size(); j++)
				for (int n = 0; n < knnlist.size(); n++) {
					if (knnlist.get(n).getId() == distancelist.get(j).getId()) {
						count += knnlist.get(n).getValue();
						break;
					}
			}
			
			if (count > j / 2)
				joblist.add(JobDAO.searchJob(String.valueOf(trainingset.get(i).getJobsId())));

		}
		
		List<Job> knnjoblist = new ArrayList<Job>();
		for(int i=0;i<joblist.size();i++) {
			boolean flag=true;
			for(int j=0;j<skillsjoblist.size();j++)
				if(joblist.get(i).getJobsId()==skillsjoblist.get(j).getJobsId()) {
					flag=false;
					break;
				}
			if(flag)
				knnjoblist.add(joblist.get(i));
		}
		
		return knnjoblist;
		
	}

	// vazei times sto dataset, 0 an den exw kanei apply 1 an exw kanei, dhmiourgei
	// to knnlist, class me jobid kai value
	public static List<knnClass> knnListJob(List<Job> joblist, String email) {
		List<knnClass> knnlist = new ArrayList<knnClass>();

		for (int i = 0; i < joblist.size(); i++) {
			knnClass temp = new knnClass();
			temp.setId(joblist.get(i).getJobsId());
			temp.setValue(0);
			List<Application> applicationlist = joblist.get(i).getApplications();
			for (int j = 0; j < applicationlist.size(); j++) {
				if (applicationlist.get(j).getEmail().equals(email)) {
					temp.setValue(1);
					break;
				}
			}
			knnlist.add(temp);
		}
		return knnlist;

	}
	
	public static List<Post> knnImplementationPost(User user) {
		int k = 3; // change it
		List<Post> postlist = new ArrayList<Post>();
		List<Post> dataset = PostDAO.selectPostsBefore(user,user.getLastLogin());
		List<Post> trainingset = PostDAO.selectPostsAfter(user,user.getLastLogin());
		
		List<knnClass> knnlist = knnListPost(dataset, user.getEmail());  //gemizei to pedio value gia to dataset
		for (int i = 0; i < trainingset.size(); i++) {
			List<knnClass> distancelist = new ArrayList<knnClass>();
			for (int j = 0; j < dataset.size(); j++) {
				knnClass temp = new knnClass();
				temp.setId(dataset.get(j).getPostsId());
				if(dataset.get(j).getUser().equals(trainingset.get(i).getUser()))
					temp.setValue(0);
				else
					temp.setValue(1);
				distancelist.add(j, temp);
			}
			distancelist.sort(Comparator.comparingInt(knnClass::getValue));
			
			int count = 0;
			int j;
			for (j = 0; j < k && j < distancelist.size(); j++)
				for (int n = 0; n < knnlist.size(); n++) {
					if (knnlist.get(n).getId() == distancelist.get(j).getId()) {
						count += knnlist.get(n).getValue();
						break;
					}
			}
			
			if (count > j / 2)
				postlist.add(PostDAO.searchPost(String.valueOf(trainingset.get(i).getPostsId())));

		}
		
		return postlist;
	}

	// vazei times sto dataset, 0 an den exw kanei like comment 1 an exw kanei, dhmiourgei
	// to knnlist, class me postid kai value
	public static List<knnClass> knnListPost(List<Post> postlist, String email) {
		List<knnClass> knnlist = new ArrayList<knnClass>();

		for (int i = 0; i < postlist.size(); i++) {
			knnClass temp = new knnClass();
			temp.setId(postlist.get(i).getPostsId());
			temp.setValue(0);
			List<Like> likelist = postlist.get(i).getLikes();
			List<Comment> commentlist = postlist.get(i).getComments();
			boolean flag=true;
			for (int j = 0; j < likelist.size()&&flag; j++) {
				if (likelist.get(j).getEmail().equals(email))
					temp.setValue(1);
			}
			for (int j = 0; j < commentlist.size()&&flag; j++) {
				if (commentlist.get(j).getEmail().equals(email))
					temp.setValue(1);
			}
			knnlist.add(temp);
		}
		return knnlist;

	}

}