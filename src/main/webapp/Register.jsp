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

<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>

<%@ page import="java.util.GregorianCalendar"%>

<%@ page import="java.util.Calendar"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>MiC - Registration</title>

<script type="text/javascript">

	/* funzione Controllo Inserimento */

	function check() {

		if (document.getElementById("mail").value == ""

				|| document.getElementById("firstName").value == ""

				|| document.getElementById("lastName").value == ""

				|| document.getElementById("day").value == "0"

				|| document.getElementById("month").value == "0"

					|| document.getElementById("year").value == "0"

				|| (!document.login.gender[0].checked && !document.login.gender[1].checked)) {

			alert("Please fill in all fields!");

			return false;

		}

		if (!ValidaData(document.getElementById("day").value,document.getElementById("month").value,document.getElementById("year").value)) {

			alert('Please enter a valid "Date of birth".')

			return false;

		}

		if(!checkmail)

			{

			return false;

			}

		return true;

	}

	

	function createRequestObject() {

	    var ro

	    var browser = navigator.appName

	    if(browser == "Microsoft Internet Explorer"){

	        	ro = new ActiveXObject("Microsoft.XMLHTTP")

	    	}else{

	        	ro = new XMLHttpRequest()

	    	}

	    	return ro

		}

	

	function checkmail(mail){

		var value = mail.value;

		if (!ValidaCampoMail(mail)) {

			alert('Please enter a valid mail address in field "Mail".');

			return false;

		}

		var http = createRequestObject()

		http.open("POST","AjaxCheckMail.jsp",false)

		http.setRequestHeader("Content-type","application/x-www-form-urlencoded");

		http.send("mail="+value)

		if(http.responseText.indexOf("true")!="-1")

			{

			 return true;

			}

		else

		{

			alert("Mail already registered");

			return false;

			}

	}

	

	function colorfieldmail(field){

		if(checkmail(field))

			{

			field.style.background="lime";

			return true;

			}

		else

		{

			field.style.background="red";

			return false;

			}

	}

	

	function ValidaCampoMail(mail){

		var stringmail = mail.value;

		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

		if (filter.test(stringmail)){

			return true;

		}

		return false;

	}

	

	

	function daysInFebruary (year){

		// February has 29 days in any year evenly divisible by four,

	    // EXCEPT for centurial years which are not also divisible by 400.

	    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );

	}

	

	function DaysArray(n) {

		for (var i = 1; i <= n; i++) {

			this[i] = 31

			if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}

			if (i==2) {this[i] = 29}

	   } 

	   return this

	}

	

	function ValidaData(day,month,year){

		var daysInMonth = DaysArray(12)

		if ((month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){

			return false

		}

	return true

	}

	

	function ValidaCampiNumerici(campo) {

		var numeri = "1234567890";

		var valida = campo.value;

		var veriﬁcato = true;

		var cifre = "";

		for (n = 0; n < valida.length; n++) {

			vl = valida.charAt(n);

			for (s = 0; s < numeri.length; s++)

				if (vl == numeri.charAt(s))

					break;

			if (s == numeri.length) {

				veriﬁcato = false;

				break;

			}

			cifre += vl;

		}

		if (!veriﬁcato) {

			campo.focus();

			return (false);

		}

		return (true);

	}

	</script>



	

<link href="stile.css" rel="stylesheet" type="text/css" />

<link rel="icon" href="image/favicon.png" type="image/png" />

</head>

<body>

	<div id="content">

		<div id="subcontent">

			<div id="corpo">



				<div id="form">

					<h1>Registration</h1>



					<form action="Register" method="post" onsubmit="return check()"

						name="login" enctype="multipart/form-data">



						<div id="first">

							E-mail: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <input type="text"

								name="mail" id="mail" onblur="colorfieldmail(this)" /> <br />Password: &nbsp&nbsp&nbsp<input type="password"

								name="password" id="password" /> <br /> First Name:&nbsp <input

								type="text" name="firstName" id="firstName" /> <br /> Last

							Name:&nbsp <input type="text" name="lastName" id="lastName" />

						</div>

						<br />

						<div id="last">

							Date of Birth: &nbsp&nbsp&nbsp

							 <select id="day" name="day">

									<option value="0">dd</option>

									<%

									String day=null;

									for(int i=1;i<=31;i++){

										if(i<10)

											day="0"+i;

										else

											day=""+i;

									%>

									<option value="<%=i%>"><%=day%></option>

									<%

									}%>

								</select>

								<select id="month" name="month">

									<option value="0">mm</option>

									<%

									String month=null;

									for(int i=1;i<=12;i++){

										if(i<10)

											month="0"+i;

										else

											month=""+i;

									%>

									<option value="<%=i%>"><%=month%></option>

									<%

									}%>

								</select>

								<select id="year" name="year">

									<option value="0">yyyy</option>

									<%

									int maxyear =GregorianCalendar.getInstance().get(Calendar.YEAR)-18;

									for(int i=maxyear;i>maxyear-80;i--){

									%>

									<option value="<%=i%>"><%=i%></option>

									<%

									}%>

								</select>

							<label for="gender">Gender:&nbsp&nbsp&nbsp&nbsp&nbsp</label> M:<input

								type="radio" name="gender" id="gender" value="M" /> F:<input

								type="radio" name="gender" id="gender" value="F" /> <br />

						 Picture<input type="file" name="file" accept="image/*" />

						 </div>

						     

						 

						<div id="tasto">

						

							<center>

								<input name="submit" type="image" value="" src="image/tasto3.png"

									alt="REGISTER" />

							</center>

						</div>

						

						 

					

       					 

        			</form>

        			</div>

				</div>



				<%

					String messaggio = (String) request.getAttribute("message");

					if (messaggio != null) {

						out.println("<div id='messaggio'><p>" + messaggio

								+ "</p></div>");

					}

				%>

			</div>

		</div>

	</div>

</body>

</html>