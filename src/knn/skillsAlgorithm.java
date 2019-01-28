package knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import Bean.JobDAO;
import db.HelpingFunctions;
import model.Job;
import model.User;

public class skillsAlgorithm {

	public static List<Job> skillsImplementation(User user) {
		List<Job> trainingset = JobDAO.selectJobsAfter(user.getLastLogin());
		List<Job> joblisttoreturn = new ArrayList<Job>();

		List<Integer> distance = new ArrayList<Integer>();
		String str = user.getSkills();
		for (int i = 0; i < trainingset.size(); i++) {
			int dis;
			int min=6;                                      //gia na vroume to skill me th mikroterh apostash
			for (String token : str.split(",")) {
				if (!(token.equals("not yet filled"))) {
					dis = HelpingFunctions.LevenshteinDistance(trainingset.get(i).getJobtitle(), token);
					if(dis<min)
						min=dis;
				}
			}
			distance.add(min);
		}

		List<knnClass> skillslist = new ArrayList<knnClass>();
		for (int i = 0; i < trainingset.size(); i++) {
			knnClass temp = new knnClass();
			temp.setValue(distance.get(i));
			temp.setId(trainingset.get(i).getJobsId());
			skillslist.add(i, temp);
		}
		skillslist.sort(Comparator.comparingInt(knnClass::getValue));
		for (int i = 0; i < skillslist.size(); i++) {
			if (skillslist.get(i).getValue() <= 5) {   //exw valei mikrotero tou 5 gia na pairnei ta pio sxetika
				Job job = JobDAO.searchJob(String.valueOf(skillslist.get(i).getId()));
				joblisttoreturn.add(job);
			}
		}

		return joblisttoreturn;
	}

}