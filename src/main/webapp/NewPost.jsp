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

<%@ page import="it.polimi.modaclouds.cpimlibrary.entitymng.CloudEntityManager"%>

<%@ page import="it.polimi.modaclouds.cloudapp.mic.entity.UserRatings"%>

<%@ page import="it.polimi.modaclouds.cpimlibrary.mffactory.MF"%>

<%@ page import="java.util.List"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>Insert new post - MiC</title>

<link href="stile.css" rel="stylesheet" type="text/css" />

<link rel="icon" href="image/favicon.png" type="image/png" />

<script type="text/javascript">

	function ContaCaratteri() {

		var massimo = 500;

		document.write.conta.value = massimo

				- document.write.newpost.value.length;



		if (document.write.conta.value <= 0) {

			document.write.newpost.value = document.write.newpost.value.substr(

					0, massimo);

			document.modulo.conta.value = 0;

		}

	}



	function checkAll(field) {

		if (typeof field.length != 'undefined') {

			for (i = 0; i < field.length; i++)

				field[i].checked = true;

		} else {

			field.checked = true;

		}

	}



	function controlcheck(field) {

		var trovato = false;

		if (typeof field.length != 'undefined') {

			for (i = 0; i < field.length; i++) {

				if (field[i].checked) {

					trovato = true;

				}

			}

		} else if(field.checked) {

			trovato=true;

		}

		if (!trovato) {

			alert("select at least one topic");

		}

		return trovato;

	}



	function uncheckAll(field) {

		if (typeof field.length != 'undefined') {

			for (i = 0; i < field.length; i++)

				field[i].checked = false;

		} else {

			field.checked = false;

		}

	}

</script>

</head>

<body>

	<div id="content">

		<div id="subcontent">

			<div id="corpo">

				<div id="titlepost">

					<center>

						<h1 id="title">New Post</h1>

					</center>

				</div>

				<br />

				<form action="WritePost" method="post" name="write"

					onsubmit="return controlcheck(topic)">

					<div id="postarea">

						Characters remaining: <input type="text" id="count" name="conta"

							value="500" readonly />

						<textarea id="newpost" name="newpost" rows="12" cols="50"

							wrap="hard" onkeyup="ContaCaratteri()"></textarea>

					</div>

					<div id="checkarea">

						<h3>

							Topic: <input type="button" name="all" value="Select All"

								onclick="checkAll(topic)" /> <input type="button" name="none"

								value="Unselect All" onclick="uncheckAll(topic)" />

						</h3>

						<%

						MF mf=MF.getFactory();

						String usermail =(String)request.getSession(true).getAttribute("actualUser");

						if(usermail==null){

							RequestDispatcher disp;

							request.setAttribute(

									"message","Session expired!!!");

							

							disp = request.getRequestDispatcher("Home.jsp");

							disp.forward(request, response);

							return;

						}

						@SuppressWarnings("unchecked")

							List<UserRatings> ratings = mf

									.getEntityManagerFactory()

									.createCloudEntityManager()

									.createQuery(

											"SELECT ur FROM UserRatings ur WHERE ur.email=:email")

									.setParameter("email",usermail											)

									.getResultList();

							for (UserRatings ur : ratings) {

						%>

						<input type="checkbox" name="topic" value='<%=ur.getTopicName()%>' />

						<%=ur.getTopicName()%><br />

						<%

							}

						%>



					</div>



					<div id="send">

						<input type="image" name="submit" src="image/send.png" alt="SEND"

							value="" />

					</div>

				</form>

			</div>

		</div>

	</div>



</body>

</html>