package it.polimi.modaclouds.cloudapp.mic.servlet;

/*
 * *****************************
 * mic
 * *****************************
 * Copyright (C) 2013 deib-polimi
 * *****************************
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *****************************
 */


import it.polimi.modaclouds.cloudapp.mic.entity.UserRatings;
import it.polimi.modaclouds.cpimlibrary.entitymng.CloudEntityManager;
import it.polimi.modaclouds.cpimlibrary.memcache.CloudMemcache;
import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class AnswerQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnswerQuestionsServlet() {
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
		String usermail = (String) request.getSession(true).getAttribute("actualUser");
		if(usermail==null){
			RequestDispatcher disp;
			request.setAttribute(
					"message","Session expired!!!");
			
			disp = request.getRequestDispatcher("Home.jsp");
			disp.forward(request, response);
			return;
		}
		MF mf=MF.getFactory();
		ArrayList<String> topicList = new ArrayList<String>();

		if (request.getParameter("reading") != null)
			topicList.add("Reading");
		if (request.getParameter("cinema") != null)
			topicList.add("Cinema");
		if (request.getParameter("sport") != null)
			topicList.add("Sport");
		if (request.getParameter("technology") != null)
			topicList.add("Technology");
		if (request.getParameter("love") != null)
			topicList.add("Love");
		if (request.getParameter("music") != null)
			topicList.add("Music");
		if (request.getParameter("politics") != null)
			topicList.add("Politics");

		int pointer = 0;
		boolean edit = new Boolean((String) request.getSession(true)
				.getAttribute("edit"));
		
		if (edit) {
			CloudEntityManager em = mf.getEntityManagerFactory()
					.createCloudEntityManager();
			@SuppressWarnings("unchecked")
			List<UserRatings> oldRatings = em
					.createQuery(
							"SELECT ur FROM UserRatings ur WHERE email=:email")
					.setParameter("email",usermail).getResultList();
			
			CloudMemcache mc= mf.getCloudMemcache();
			mc.delete(usermail);
			for (UserRatings old : oldRatings) {
				em.remove(old);
				mc.delete(usermail+"$"+old.getTopicName());
			}
			mc.close();
			em.close();
			try {
				Connection c =mf.getSQLService().getConnection();
				Statement statement = c.createStatement();
				String stm = "DELETE FROM UserSimilarity WHERE Email='"+usermail+"'";
				statement.executeUpdate(stm);
				statement.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		request.getSession(true).setAttribute("topicList", topicList);
		request.getSession(true).setAttribute("pointer", pointer);
		RequestDispatcher disp = null;
		disp = request.getRequestDispatcher("DisplayQuestion.jsp");
		disp.forward(request, response);
		
	}
}