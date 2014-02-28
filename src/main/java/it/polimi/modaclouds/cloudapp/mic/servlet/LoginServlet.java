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



import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**

 * Servlet implementation class LoginServlet

 */

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 5909797442154638761L;



	/**

	 * @see HttpServlet#HttpServlet()

	 */

	public LoginServlet() {

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



		try {

			request.setCharacterEncoding("UTF-8");

			response.setCharacterEncoding("UTF-8");

			MF mf=MF.getFactory();

			String mail= request.getParameter("mail");

			String password=request.getParameter("password");

			Connection c=	mf.getSQLService().getConnection();

			String stm="SELECT Password FROM UserProfile WHERE Email='"+mail+"'";

			Statement statement;

			statement = c.createStatement();

			ResultSet result=statement.executeQuery(stm);

			if(result.next())

			{

				String resultPass=result.getString("Password");

				if(resultPass.equals(password))

				{

					access(request, response,mail);

				}

				else{

					reject(request,response);

				}

			}

			else

			{

				reject(request,response);

			}

			statement.close();

			c.close();

			

		} catch (SQLException e) {

			e.printStackTrace();

		}

		

	}



	



	private void reject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher disp;

		request.setAttribute(

				"message","Mail or password wrong!!");

		

		disp = request.getRequestDispatcher("Home.jsp");

		disp.forward(request, response);



		

	}



	private void access(HttpServletRequest request, HttpServletResponse response, String mail) throws ServletException, IOException {

		RequestDispatcher disp;

		request.getSession(true).setAttribute("actualUser",mail);

		disp = request.getRequestDispatcher("Showcase.jsp");

		disp.forward(request, response);

	}

	



	 





}

