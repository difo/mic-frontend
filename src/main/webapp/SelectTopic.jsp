<%--

    Copyright 2013 deib-polimi
    Contact: deib-polimi <marco.miglierina@polimi.it>

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

--%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>Select the topics - MiC</title>

<link href="stile.css" rel="stylesheet" type="text/css" />

<link rel="icon" href="image/favicon.png" type="image/png" />

</head>

<%



String usermail = (String) request.getSession(true).getAttribute("actualUser");

if(usermail==null){

	RequestDispatcher disp;

	request.setAttribute(

			"message","Session expired!!!");

	

	disp = request.getRequestDispatcher("Home.jsp");

	disp.forward(request, response);

	return;

} %>

<body>

	<div id="content">

		<div id="subcontent">

			<div id="corpo">

				<div id="topicheader">

					<center>

						<h1 id="title">Select the topics that interest you</h1>

					</center>

				</div>

				<br />

				<%

				if(request.getParameter("editprofile")!=null)

				{

				if(request.getParameter("editprofile").equals("true"))

				{

					request.getSession(true).setAttribute("edit", "true");

					}

				

				else

				{

					request.getSession(true).setAttribute("edit", "false");

					}

				

				}

				%>

				<form action="AnswerQuestions" method="post" name="answer">

					<div id="questions">

						<p>

							<div id="domanda">

							<input type="checkbox" name="reading" value="true">Reading</input>

							</div>

						</p>

						<p>

							<div id="domanda">

							<input type="checkbox" name="cinema" value="true">Cinema</input>

							</div>

						</p>

						<p>

							<div id="domanda">

							<input type="checkbox" name="sport" value="true">Sport</input>

							</div>

						</p>

						<p>

							<div id="domanda">

							<input type="checkbox" name="technology" value="true">Technology</input>

							</div>

						</p>

						<p>

							<div id="domanda">

							<input type="checkbox" name="love" value="true">Love</input>

							</div>

						</p>

						<p>

							<div id="domanda">

							<input type="checkbox" name="music" value="true">Music</input>

							</div>

						</p>

						<p>

							<div id="domanda">

							<input type="checkbox" name="politics" value="true">Politics</input>

							</div>

						</p>

					</div>

					<div id="meet">

						<input type="image" name="submit" src="image/meet.png" alt="MEET"

							value="" />

					</div>

				</form>

			</div>

		</div>

	</div>



</body>

</html>