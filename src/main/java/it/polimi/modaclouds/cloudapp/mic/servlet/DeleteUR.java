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
import it.polimi.modaclouds.cpimlibrary.entitymng.CloudEntityManagerFactory;
import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used only for testing purpose, to delete automatically the ratings saved during the test
 */
public class DeleteUR extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUR() {
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
		
		MF mf=MF.getFactory();
		CloudEntityManagerFactory emf = mf.getEntityManagerFactory();
		
		CloudEntityManager em =emf.createCloudEntityManager();
		List<UserRatings> oldRatings = em
		.createQuery(
				"SELECT ur FROM UserRatings ur WHERE ur.todelete=:email")
		.setParameter("email","true").getResultList();

		response.getWriter().write("CANCELLO "+oldRatings.size()+" RATINGS");
		for (UserRatings old : oldRatings) {
			
			em.remove(old);
			
			
		}
		em.close();
	}
}