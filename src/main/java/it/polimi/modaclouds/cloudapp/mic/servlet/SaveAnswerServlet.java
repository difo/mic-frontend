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

import it.polimi.modaclouds.cpimlibrary.entitymng.CloudEntityManager;

import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import it.polimi.modaclouds.cpimlibrary.taskqueuemng.CloudTask;

import it.polimi.modaclouds.cpimlibrary.taskqueuemng.CloudTaskQueue;

import it.polimi.modaclouds.cpimlibrary.taskqueuemng.CloudTaskQueueException;



import java.io.IOException;

import java.util.ArrayList;

import java.util.Date;



import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;





/**

 * Servlet implementation class LoginServlet

 */

public class SaveAnswerServlet extends HttpServlet {

	MF mf=MF.getFactory();

	private static final long serialVersionUID = 5909797442154638761L;



	/**

	 * @see HttpServlet#HttpServlet()

	 */

	public SaveAnswerServlet() {

		super();

	}



	/**

	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse

	 *      response)

	 */

	protected void doGet(HttpServletRequest request,

			HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);

	}



	/**

	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse

	 *      response)

	 */

	protected void doPost(HttpServletRequest request,

			HttpServletResponse response) throws ServletException, IOException {



		request.setCharacterEncoding("UTF-8");

		response.setCharacterEncoding("UTF-8");

		String usermail = (String) request.getSession(true).getAttribute("actualUser");

		if(usermail==null){

			RequestDispatcher disp;

			request.setAttribute(

					"message","Session expired!!!");

			

			disp = request.getRequestDispatcher("Home.jsp");

			disp.forward(request, response);

			return;

		}

		@SuppressWarnings("unchecked")

		ArrayList<String> topicList=(ArrayList<String>) request.getSession(true).getAttribute("topicList");

		int pointer= (Integer) request.getSession(true).getAttribute("pointer");

		String actualTopic = topicList.get(pointer);

		ArrayList<Integer> ratings = new ArrayList<Integer>();

		CloudEntityManager em=mf.getEntityManagerFactory().createCloudEntityManager();

		int count = (Integer) request.getSession(true).getAttribute("count");

	

		for (int i = 1; i <= count; i++) {

			ratings.add(Integer.parseInt(request.getParameter(actualTopic+i)));

		}

		////////////////////////

		//Map<Integer, Integer> ratings_map = new HashMap<Integer, Integer>();

		//for(int i = 0; i < ratings.size(); i++)

		//	ratings_map.put(i, ratings.get(i));

		ArrayList<String> ratings_list = new ArrayList<String>();

		for (int i = 0; i < ratings.size(); i++){

			ratings_list.add(i+"="+ratings.get(i));

		}

		////////////////////////

		UserRatings ur = new UserRatings(usermail,actualTopic,ratings_list);

		em.persist(ur);

		

		boolean edit= new Boolean((String)request.getSession(true).getAttribute("edit"));

		RequestDispatcher disp;

		pointer++;

		if(pointer<topicList.size()){

			request.getSession(true).setAttribute("pointer", pointer);

			disp= request.getRequestDispatcher("DisplayQuestion.jsp");

			disp.forward(request, response);

		}else if(edit){

			request.getSession(true).setAttribute("edit", "false");

			sendRequestToTheQueue(usermail, request);

			disp = request.getRequestDispatcher("Showcase.jsp");

			disp.forward(request, response);

		}else{

			sendRequestToTheQueue(usermail, request);

			request.getSession(true).removeAttribute("actualUser");

			request.getSession(true).removeAttribute("count");

			request.getSession(true).removeAttribute("pointer");

			request.getSession(true).removeAttribute("topicList");

			disp = request.getRequestDispatcher("Home.jsp");

			disp.forward(request, response);

		}



	}



	private void sendRequestToTheQueue(String email, HttpServletRequest request) {

		/*

		CloudTaskQueue q = mf.getTaskQueueFactory().getQueue("queuetask");

		

		CloudTask t = new CloudTask();

		t.setMethod(CloudTask.POST);

		t.setParameters("user", email);

		t.setParameters("edit", (String) request.getSession(true).getAttribute("edit"));

		

		t.setServletUri("/computeSimilarity");

		Date date = new Date();

		String taskname=email+date.toString().replace(' ','_').replace(':', '-');

		t.setTaskName(taskname);

		try {

			q.add(t);

			request.setAttribute(

					"message",

					"Your answer has been saved. The system will process them and you will receive by email the best contact for you");

		} catch (CloudTaskQueueException e) {

			request.setAttribute(

					"message",

					e.getMessage());

		}

		*/

CloudTaskQueue q = mf.getTaskQueueFactory().getQueue("queuetask");

		

		CloudTask t = new CloudTask();

		t.setMethod(CloudTask.POST);

		t.setParameters("user", email);

		t.setParameters("edit", (String) request.getSession(true).getAttribute("edit"));

		String serverPath = request.getScheme() + "://"

				+ request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		

		t.setServletUri("/computeSimilarity");

		Date date = new Date();

		String taskname=email+date.toString().replace(' ','_').replace(':', '-');

		t.setTaskName(taskname);

		try {

			q.add(t);

			request.setAttribute(

					"message",

					"Your answer has been saved. The system will process them and you will receive by email the best contact for you");

		} catch (CloudTaskQueueException e) {

			request.setAttribute(

					"message",

					e.getMessage());

		}

	}

	

//If you want to use Message Queues

	

//	private void sendRequestToTheQueueMSG(String email, HttpServletRequest request) {

//		CloudMessageQueue q = mf.getMessageQueueFactory().getQueue("queue");

//		msgConsumer(q);

//		String msg=email+"&&"+request.getSession(true).getAttribute("edit");

//		try {

//			q.add(msg);

//		} catch (CloudMessageQueueException e) {

//			e.printStackTrace();

//		}

//		

//		System.out.println("Task incodato.....");		

//	}

//	

//	public void msgConsumer(CloudMessageQueue q) 

//	{

//	 final CloudMessageQueue queue = q;

//		Runnable r=new Runnable(){

//			public void run()

//			{

//				CloudMessage msg=null;

//				while(true)

//				{

//				if(queue!=null)

//				{

//				msg=queue.getMessage();

//				

//				if(msg!=null)

//					{

//						String[] info=msg.getMsg().split("&&");

//						String email=info[0];

//						String edit=info[1];

//						

//						new ComputeSimilarity(email,edit);

//						try {

//							queue.deleteMessage(msg);

//						} catch (CloudMessageQueueException e) {

//							e.printStackTrace();

//						}

//					}

//				

//				}

//				else

//					System.out.println("Errore setQueue...null");

//				try {

//					System.out.println("Relax....");

//					Thread.sleep(10000); //aggiornare con eventuale valore in configurations.xml

//				} catch (InterruptedException e) {

//					e.printStackTrace();

//				} 

//				}

//				

//				

//			}

//			

//			

//			

//			

//		};

//		

//		Thread t=null;

//		try {

//			t = CloudThread.getThread(r,CloudMetadata.getCloudMetadata());

//		} catch (ParserConfigurationFileException e) {

//			e.printStackTrace();

//		}

//		if(!t.isAlive())

//			t.start();

//	}

	

	 	

//	 public void sendmail(String email)

//	 {   

//		 String messageBody ="Your answer has been saved.\n The system will process them and you will receive by email the best contacts for you"+"\n\n\n Meeting in the Cloud" ;

//			CloudMail msgToSend=new CloudMail(email,"Welcome to mic.com!",messageBody);

//			mf.getMailManager().sendMail(msgToSend);

//	 }

	 





}

