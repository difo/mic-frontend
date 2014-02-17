/**
 * Copyright 2013 deib-polimi
 * Contact: deib-polimi <marco.miglierina@polimi.it>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package it.polimi.modaclouds.cloudapp.mic.servlet;

import it.polimi.modaclouds.cloudapp.mic.entity.UserRatings;
import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ComputeSimilarity {
	private static final long serialVersionUID = 1L;
	PairsUser reset = new PairsUser("", -2);
	PairsUser[] rank = new PairsUser[3];
	MF mf=MF.getFactory();
	public ComputeSimilarity(String usermail, String editS)
	{	
		System.out.println("compute");
		boolean edit=new Boolean(editS);
		@SuppressWarnings("unchecked")
		List<UserRatings> ratings =mf.getEntityManagerFactory().createCloudEntityManager().createQuery("SELECT ur FROM UserRatings ur WHERE ur.email=:email").setParameter("email", usermail).getResultList();
		String topicName = null;
		for (UserRatings myRatings : ratings) {
			initRanking();
			topicName = myRatings.getTopicName();
			@SuppressWarnings("unchecked")
			List<UserRatings> resultList = (List<UserRatings>) mf
					.getEntityManagerFactory()
					.createCloudEntityManager()
					.createQuery(
							"SELECT ur FROM UserRatings ur WHERE ur.topicName=:topic")
					.setParameter("topic", topicName).getResultList();

			
			float sim = 0;
            ///////////////////////
			ArrayList<Integer> myRatings_int = new ArrayList<Integer>();
			List<String> myRatings_str = myRatings.getRatings();
			for(int i = 0; i < myRatings_str.size(); i++) {
				myRatings_int.add(0);
			}
			System.out.println("my ratings");
			for(int i = 0; i < myRatings_str.size(); i++) {
				System.out.println(myRatings_str.get(i));
				String rat = myRatings_str.get(i);
				Integer index = Integer.parseInt(rat.substring(0, rat.indexOf("=")));
				Integer element = Integer.parseInt(rat.substring(rat.indexOf("=")+1));
				myRatings_int.add(index, element);
			}
			//Map<Integer, Integer> ur_map = myRatings.getRatings();
			//for(int i = 0; i < myRatings_map.size(); i ++)
			//	myRatings_list.add(myRatings_map.get(i));
			
			///////////////////////
			System.out.println("ur");
			for (UserRatings ur : resultList) {
				if (ur.getEmail().compareTo(usermail) != 0) {
					///////////////////////
					ArrayList<Integer> ur_int = new ArrayList<Integer>();
					List<String> ur_str = ur.getRatings();
					for(int i = 0; i < ur_str.size(); i++) {
						ur_int.add(0);
					}
					for(int i = 0; i < ur_str.size(); i++) {
						System.out.println(ur_str.get(i));
						String rat = ur_str.get(i);
						Integer index = Integer.parseInt(rat.substring(0, rat.indexOf("=")));
						Integer element = Integer.parseInt(rat.substring(rat.indexOf("=")+1));
						ur_int.add(index, element);
					}
					//Map<Integer, Integer> ur_map = ur.getRatings();
					//for(int i = 0; i < ur_map.size(); i ++)
					//	ur_list.add(ur_map.get(i));
					///////////////////////
					sim = computeSimilarity(myRatings_int,
							ur_int);
					check(new PairsUser(ur.getEmail(), sim), rank);
				}
			}
			sendResult(usermail, topicName, edit);
		}
	}

	

	private  void initRanking() {
		for (int i = 0; i < rank.length; i++) {
			rank[i] = reset;
		}

	}

	public void sendResult(String email, String topicName, boolean edit) {
		Connection c = mf.getSQLService().getConnection();
		Statement statement;
		Logger l=Logger.getLogger("com.applcloud.mic.servlet");
		try {
			statement = c.createStatement();
			String emailstm = "'" + email + "'";
			String topicstm = "'" + topicName + "'";
			String firststm = null;
			String secondstm = null;
			String thirdstm = null;
			if (!rank[0].getEmail().equals(""))
				firststm = "'" + rank[0].getEmail() + "'";
			if (!rank[1].getEmail().equals(""))
				secondstm = "'" + rank[1].getEmail() + "'";
			if (!rank[2].getEmail().equals(""))
				thirdstm = "'" + rank[2].getEmail() + "'";
			String stm=null;
			
			if(!edit){
			
			stm = "INSERT INTO UserSimilarity VALUES(" + emailstm + ", "
					+ topicstm + ", " + firststm + ", " + secondstm + ", "
					+ thirdstm + ")";
			}
			else{
				stm = "UPDATE UserSimilarity SET FirstUser="+ firststm + ", SecondUser=" + secondstm + ", ThirdUser="+ thirdstm + " WHERE Email="+emailstm+" AND Topic=" + topicstm;
			}
			statement.executeUpdate(stm);
			statement.close();
			c.close();
		} catch (SQLException e) {
			l.info("SQLEXCEPTION CS:");
			e.printStackTrace();
		}

		String messageBody = "The best contacts for you are been calculated\n\n"
				+ " \n\n\nThanks for using our service. \n\nGood Meeting";

	}

	public void check(PairsUser check, PairsUser[] rank) {

		if (rank[0].getSim() < check.getSim()) {
			rank[2] = rank[1];
			rank[1] = rank[0];
			rank[0] = check;

		} else if (rank[1].getSim() < check.getSim()) {
			rank[2] = rank[1];
			rank[1] = check;

		} else if (rank[2].getSim() < check.getSim()) {
			rank[2] = check;
		}
	}

	// -----------------------------SIMILARITY---------------//

	public static float computeSimilarity(ArrayList<Integer> a, ArrayList<Integer> b) {
		float num = 0;
		float den = 0;
		int sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum = sum + a.get(i);
		}
		float avgA = sum / a.size();

		sum = 0;
		for (int i = 0; i < b.size(); i++) {
			sum = sum + b.get(i);
		}
		float avgB = sum / b.size();

		for (int i = 0; i < a.size(); i++) {
			num = num + ((a.get(i) - avgA) * (b.get(i) - avgB));
		}

		float D1 = 0;
		float D2 = 0;
		for (int i = 0; i < a.size(); i++) {
			D1 = (float) (D1 + (Math.pow((a.get(i) - avgA), 2)));
			D2 = (float) (D2 + (Math.pow((b.get(i) - avgB), 2)));
		}

		den = (float) (Math.sqrt(D1) * Math.sqrt(D2));

		return num / den;

	}

	// ************************************************CLASS*************************************************************//
	private class PairsUser {
		private String email;
		private float sim;

		public PairsUser(String email, float sim) {
			this.email = email;
			this.sim = sim;
		}

		public String getEmail() {
			return email;
		}

	
		public float getSim() {
			return sim;
		}

	

	}

}
