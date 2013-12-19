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
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class WritePostServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WritePostServlet() {
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
		
		
		parseReq(request, response);
		

	}

	private void parseReq(HttpServletRequest req, HttpServletResponse response) {

		try {
			req.setCharacterEncoding("UTF-8");
			String usermail = (String) req.getSession(true).getAttribute("actualUser");
			if(usermail==null){
				RequestDispatcher disp;
				req.setAttribute(
						"message","Session expired!!!");
				
				disp = req.getRequestDispatcher("Home.jsp");
				disp.forward(req, response);
				return;
			}
			String[] topicList = req.getParameterValues("topic");
			Connection c= MF.getFactory().getSQLService().getConnection();
			PreparedStatement pstm= c.prepareStatement("INSERT INTO Message VALUES(?,?,?,?,?)");
			for (int i = 0; i < topicList.length; i++) {
				String textmsg = req.getParameter("newpost");
				int hashMsg=textmsg.hashCode();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date=new Date();
				String dateS=df.format(date);
				String msgId=String.valueOf((usermail + topicList[i] + date.toString()+hashMsg).hashCode());
				pstm.setString(1, msgId);
				pstm.setString(2, usermail);
				pstm.setString(3, dateS);
				pstm.setString(4, textmsg);
				pstm.setString(5, topicList[i]);
				pstm.executeUpdate();
			}
			
			pstm.close();
			c.close();
			
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("Showcase.jsp");
			disp.forward(req, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
