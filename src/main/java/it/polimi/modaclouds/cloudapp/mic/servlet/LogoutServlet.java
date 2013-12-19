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


import it.polimi.modaclouds.cpimlibrary.memcache.CloudMemcache;
import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
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
		RequestDispatcher disp;
		String user=(String) request.getSession(true).getAttribute("actualUser");
		
		
		if(user!=null)
		{
			CloudMemcache cmc =MF.getFactory().getCloudMemcache();
			if(cmc.contains(user)){
				for (String s: (Vector<String>)cmc.get(user))
					{
						cmc.delete(user+"$"+s);
					}
					cmc.delete(user);
				}
			cmc.close();
		request.setAttribute("message","Bye Bye "+ user+" !!! See you soon....");
		}
		else
			request.setAttribute("message", "Bye Bye!!!");
		disp = request.getRequestDispatcher("Home.jsp");
		request.getSession(true).setAttribute("actualUser",null);
		disp.forward(request, response);

		
	}


}
