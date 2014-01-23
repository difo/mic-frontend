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


import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class InitializedSQLTableServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitializedSQLTableServlet() {
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
		Logger l=Logger.getLogger("it.polimi.modaclouds.cloudapp");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Connection c=mf.getSQLService().getConnection();
		String stm = "CREATE TABLE UserProfile (Email VARCHAR(255) NOT NULL, Password VARCHAR(255) NOT NULL, FirstName VARCHAR(255), LastName VARCHAR(255), Date_of_birth DATE, Gender CHAR(1) NOT NULL, Picture VARCHAR(255), PRIMARY KEY(Email))";
		String stm2= "CREATE TABLE UserSimilarity (Email VARCHAR(255) NOT NULL, Topic VARCHAR(255) NOT NULL, FirstUser VARCHAR(255), SecondUser VARCHAR(255), ThirdUser VARCHAR(255), CONSTRAINT simID PRIMARY KEY (Email,Topic), FOREIGN KEY (FirstUser) REFERENCES UserProfile(Email), FOREIGN KEY (SecondUser) REFERENCES UserProfile(Email), FOREIGN KEY (ThirdUser) REFERENCES UserProfile(Email))";
		String stm3= "CREATE TABLE Message (Id VARCHAR(255) NOT NULL, UserId VARCHAR(255) NOT NULL, Date DATE, MessageTxt TEXT, Topic VARCHAR(255), PRIMARY KEY(Id), FOREIGN KEY (UserId) REFERENCES UserProfile(Email))";
		if(c==null)
			l.info("CONNECTION TO DB FAILED");
		Statement statement;
		try {
		
			statement = c.createStatement();
			statement.executeUpdate(stm);
			statement.executeUpdate(stm2);
			statement.executeUpdate(stm3);
			
			statement.close();
			c.close();
			
		} catch (Exception e) {
			
			l.info("ERROR CREATING CONNECTION TO DB");
			l.info("PRINTSTACKTRACE:"+e.getMessage());
		}
		
		request.setAttribute("message", "SQL Table initialized...");
		RequestDispatcher disp;
		disp = request.getRequestDispatcher("Home.jsp");
			disp.forward(request, response);
		

	}

}
