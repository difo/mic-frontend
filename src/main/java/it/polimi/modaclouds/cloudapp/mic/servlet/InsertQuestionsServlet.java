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


import it.polimi.modaclouds.cloudapp.mic.entity.Topic;
import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 5909797442154638761L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertQuestionsServlet() {
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
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Logger l=Logger.getLogger(this.getClass().getName());
		l.info("Inserting questions...");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		MF mf=MF.getFactory();
		l.info("Manager Factory loaded.");
		ArrayList<String> reading=new ArrayList<String>();
		reading.add("How much do you love reading?");
		reading.add("How much do you appreciate the use of ebooks as a means of reading?");
		reading.add("How much do you love novels?");
		reading.add("How much do you love yellow books?");
		reading.add("How much do you love classics books?");
		reading.add("How much do you love fantasy books?");
		reading.add("How much do you love historical Books?");
		reading.add("How much do you love poems?");
		reading.add("For your opinion, how much is the weight of the consumerism in the publishing?");
		reading.add("For your opinion, how much is the weight of the media in the choice of readers?");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Reading",reading));
		l.info("Reading questions added.");
			
		ArrayList<String> cinema=new ArrayList<String>();
		cinema.add("How much do you love movies?");
		cinema.add("How much do you love going to cinema???");
		cinema.add("How much do you love 3D movies?");
		cinema.add("How much do you love horror movies?");
		cinema.add("How much do you love yellow movies?");
		cinema.add("How much do you love comedy movies?");
		cinema.add("How much do you love documentary movies?");
		cinema.add("How much do you love drama movies?");
		cinema.add("How much do you love action movies?");
		cinema.add("How much is the media effect in the choice of an individual to go see a movie?");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Cinema",cinema));
		l.info("Cinema questions added.");
		
		ArrayList<String> sport=new ArrayList<String>();
		sport.add("How much do you love practice sports?");
		sport.add("How much do you love watch sports?");
		sport.add("How much do you football?");
		sport.add("How much do you basket?");
		sport.add("How much do you rugby?");
		sport.add("How much do you love volletball?");
		sport.add("How much do you love tennis?");
		sport.add("How much do you love athletics?");
		sport.add("How much do you love boxe?");		
		sport.add("How much do you love swimming???");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Sport",sport));
		l.info("Sport questions added.");
				
		ArrayList<String> technology=new ArrayList<String>();
		technology.add("How much do you love technology?");
		technology.add("How often do you follow technologies news???[1-never][2-seldom][3- occasionally][4-often][5-always]");
		technology.add("How much do you consider yourself a nerd?");
		technology.add("How much do you love Apple's products?");
		technology.add("How much do you love Microsoft's products? ");
		technology.add("How much do you love and follow  the Open Source world?");
		technology.add("How much would you like to work in the technology field?");
		technology.add("How many Internet connectable device do you have?");		
		technology.add("How often do you frequent chat??? [1-never][2-seldom][3- occasionally][4-often][5-always]");
		technology.add("In how many social network are you registered?");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Technology",technology));
		l.info("Technology questions added.");
				
		ArrayList<String> love=new ArrayList<String>();
		love.add("How much do you feel a complete person next to your partner?");
		love.add("At how many men/women did you say 'I love you' ?");
		love.add("How many times did you fall in love without being loved???");
		love.add("How important is being able to listen in a relationship?");
		love.add("How important is sex in a relationship?");
		love.add("How many times did you suffer for love?");
		love.add("What weight would you give to a possible betrayal of your partner?");
		love.add("How important is the honesty in a relationship?");		
		love.add("How much do you believe in the eternal love?");
		love.add("How many children would you have?");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Love",love));
		l.info("Love questions added.");
		
		ArrayList<String> music=new ArrayList<String>();
		music.add("How much do you love music?");
		music.add("How much do you love going to a concert?");
		music.add("How much do you lovw rock music?");
		music.add("How much do you love pop music?");
		music.add("How much do you love lyric?");
		music.add("How much do you love classical music?");
		music.add("How much do you love elettronic music?");
		music.add("How much do you love singer-songwriters's music?");		
		music.add("How Much do you love jazz music?");
		music.add("How much do you love dancing?");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Music",music));
		l.info("Music questions added.");
		                                                                                                                  
		ArrayList<String> politcs=new ArrayList<String>();
		politcs.add("How much is important for you to participate actively in politics??? [1-never][2-seldom][3- occasionally][4-often][5-always]");
		politcs.add("How often do you inform yourself of political developments?");
		politcs.add("How much do you feel satisfied with the current political situation in your country?");
		politcs.add("How much do you agree in the recognition of common-law marriage?");
		politcs.add("How important is for you the integration of immigrants?");
		politcs.add("How much do you agree in the conservatism ideals?");
		politcs.add("How much do you agree in the liberal ideals?");
		politcs.add("How much do you agree to a profound renewal of the political class??");		
		politcs.add("How much do you agree in the military action as a means of liberation of peoples under a dictatorship?");
		politcs.add("How much do you agree in a more frequent use of the referendum?");
		mf.getEntityManagerFactory().createCloudEntityManager().persist(new Topic("Politics",politcs));
		l.info("Politics questions added.");
		
	

		request.setAttribute("message", "Questions loaded...");
		RequestDispatcher disp;
		disp = request.getRequestDispatcher("Home.jsp");
			disp.forward(request, response);
		

	}

}
