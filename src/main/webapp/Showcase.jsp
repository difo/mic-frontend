<%--
  *****************************
  mic
  *****************************
  Copyright (C) 2013 deib-polimi
  *****************************
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  *****************************
  --%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.polimi.modaclouds.cloudapp.mic.entity.UserRatings"%>
<%@ page import="it.polimi.modaclouds.cpimlibrary.entitymng.CloudEntityManager"%>
<%@ page import="it.polimi.modaclouds.cpimlibrary.memcache.CloudMemcache"%>
<%@ page import="it.polimi.modaclouds.cpimlibrary.mffactory.MF"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Vector"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Showcase - MiC</title>
<link href="stile.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="image/favicon.png" type="image/png" />
<script type="text/javascript">
	function createRequestObject() {
		var ro
		var browser = navigator.appName
		if (browser == "Microsoft Internet Explorer") {
			ro = new ActiveXObject("Microsoft.XMLHTTP")
		} else {
			ro = new XMLHttpRequest()
		}
		return ro
	}

	function redirect(){
		window.location.href="Redirect"
	}
	/* funzione Controllo Inserimento */
	function update(topic) {
		var http = createRequestObject()
		var el = document.getElementById("corpo")
		http.open("POST", "AjaxMessageShowcase.jsp", false)
		http.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		http.send("topicSelected=" + topic);
		var resp =http.responseText
		if(resp=="Error")
			redirect()
		else
			el.innerHTML = http.responseText
	}

	function updateSimilarity(topic) {
		var http = createRequestObject()
		http.open("POST", "UpdateAnswer", false)
		http.send()
		var el = document.getElementById("updatemsg")
		var resp =http.responseText
		if(resp=="Error")
			redirect()
		else
			el.innerHTML = http.responseText
		

	}
	
	function deleteMsg(msg){
		
		alert("Message will be deleted"); 
		var http = createRequestObject();
		http.open("POST", "DeleteMessage", false);
		http.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		http.send("msgId=" + msg.value);
		update(document.getElementById("topic").value);
	}
	

</script>

</head>

<%

MF mf=MF.getFactory();
CloudMemcache cmc=mf.getCloudMemcache();
String usermail = (String) request.getSession(true).getAttribute(
"actualUser");
if(usermail==null){
	RequestDispatcher disp;
	request.setAttribute(
			"message","Session expired!!!");
	
	disp = request.getRequestDispatcher("Home.jsp");
	disp.forward(request, response);
	return;
}

if(request.getParameter("refresh")!=null)
	{
	
	if(request.getParameter("refresh").equals("true")&& cmc.contains(usermail)){
		for (String s: (Vector<String>)cmc.get(usermail))
			{
				cmc.delete(usermail+"$"+s);
			}
			cmc.delete(usermail);
		}

	}
	

%>
<body onload="update(topic.value)">
	<div id="content">
		<div id="subcontent">
			<select id="topic" name="topic" onchange="update(this.value)">
				<%
				Vector<String> topics =new Vector<String>(7);
				    if(cmc.contains(usermail))
				    {
				    	topics=(Vector<String>)cmc.get(usermail);
				    	for(String t:topics)
				    	{
				    		%>
							<option value="<%=t%>"><%=t%></option>
							<%
				    	}
				    }
				    else
				    {
					
				 	CloudEntityManager em = mf.getEntityManagerFactory().createCloudEntityManager();
					List<UserRatings> ratings =em.createQuery("SELECT ur FROM UserRatings ur WHERE email=:email")
							.setParameter("email", usermail).getResultList();
					
					
					for (UserRatings ur : ratings) {
					topics.add(ur.getTopicName());
				%>
				<option value="<%=ur.getTopicName()%>"><%=ur.getTopicName()%></option>
				<%
					}
					
					cmc.put(usermail,topics);
					
				    }
				    cmc.close();
				%>

			</select>
			<div id="corpo">
			</div>
			<div id="esterno">
				<%
					Connection c = mf.getSQLService().getConnection();
					Statement statement = c.createStatement();
					String stm2 = "SELECT Email,FirstName,LastName,Picture FROM UserProfile WHERE Email='"
							+ usermail + "'";
					String myPicture = null;
					String myFirstName = null;
					String myLastName = null;
					ResultSet images = statement.executeQuery(stm2);
					while (images.next()) {
						if (images.getString("Email").equals(usermail)) {
							myPicture = images.getString("Picture");
							myFirstName = images.getString("FirstName");
							myLastName = images.getString("LastName");
						}
					}

					statement.close();
					c.close();
				%>
				<div id="mypicture">
					<b><%=myFirstName.toUpperCase()%> <%=myLastName.toUpperCase()%></b><img
						class="myPic" src='<%="DownloadFile?fileName=" + myPicture%>' />
				</div>
				
				

				<button name="updateSym" onclick="updateSimilarity()">
					Update Similarity</button>
				<a href="SelectTopic.jsp?editprofile=true">
				
				<button
						name="editPro">Edit Profile</button></a>
						 <a href="Showcase.jsp?refresh=true"><button
						name="rfsh">Refresh</button></a>
				<div id="updatemsg" style="color: black; font-weight: bold;">
				</div>
				
				<a href="Logout">
				<button name="logout" onclick="logout()">Exit</button>
				</a>
			</div>

			<form action="NewPost.jsp" method="post" name="write">
				<div id="write">
					<input type="image" name="submit" src="image/write.png"
						alt="NEW POST" value="" />
				</div>
			</form>

		</div>
	</div>

</body>
</html>