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


import it.polimi.modaclouds.cpimlibrary.mffactory.MF;
import it.polimi.modaclouds.cpimlibrary.taskqueuemng.CloudTask;
import it.polimi.modaclouds.cpimlibrary.taskqueuemng.CloudTaskQueue;
import it.polimi.modaclouds.cpimlibrary.taskqueuemng.CloudTaskQueueException;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class UpdateAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateAnswerServlet() {
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
		MF mf=MF.getFactory();
		String usermail= (String) request.getSession(true).getAttribute("actualUser");
		if(usermail!=null){
		CloudTaskQueue q = mf.getTaskQueueFactory().getQueue("queuetask");

		CloudTask t = new CloudTask();
		t.setMethod(CloudTask.POST);
		t.setParameters("user", usermail);
		t.setParameters("edit", "true");
		
		t.setServletUri("/computeSimilarity");
		Date date = new Date();
		String taskname=(usermail+date.toString()).replace(' ','_').replace(':', '-');
		t.setTaskName(taskname);
		try {
			q.add(t);
			response.getWriter().write("Request sent...");
					
		} catch (CloudTaskQueueException e) {
			response.getWriter().write(e.getMessage());
		}
		}
		else
			response.getWriter().write("Error");	}
}