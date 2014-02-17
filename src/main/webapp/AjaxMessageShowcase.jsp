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

<%

String usermail = (String) request.getSession(true).getAttribute(

"actualUser");

if(usermail==null)

	{

	response.getWriter().write("Error");

	return;

	}

	%>





<%@page import="it.polimi.modaclouds.cpimlibrary.memcache.CloudMemcache"%>

<%@page import="java.util.Comparator"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"

	pageEncoding="UTF-8"%>

<%@ page import="it.polimi.modaclouds.cpimlibrary.mffactory.MF"%>

<%@ page import="java.sql.*"%>

<%@ page import="java.util.List"%>

<%@ page import="java.util.Vector"%>

<%@ page import="java.util.HashMap"%>

<%@ page import="java.util.ArrayList"%>

<%@ page import="java.util.Collections"%>



<%

	MF mf=MF.getFactory();

	

	String topicSelected = (String) request

			.getParameter("topicSelected");

	

	Connection c = null;

	c = mf.getSQLService().getConnection();

	Statement statement = null;

	ResultSet result = null;

	ResultSet msgs = null;

	PreparedStatement pstm = null;

	try {

		Vector<HashMap<String, String>> userInfo = null;

		CloudMemcache cache = MF.getFactory().getCloudMemcache();

		

		if (cache.contains(usermail + "$" + topicSelected)) {

			userInfo = (Vector<HashMap<String, String>>) cache

					.get(usermail + "$" + topicSelected);

		} else {

			userInfo = new Vector<HashMap<String, String>>(3);



			statement = c.createStatement();

			String stm = "SELECT up.Email,up.FirstName,up.LastName,up.Picture FROM UserSimilarity us, UserProfile up WHERE (us.Email='"

					+ usermail

					+ "'AND us.Topic='"

					+ topicSelected

					+ "') AND";

			String stmF = null;

			HashMap<String, String> map = null;

			for (int i = 1; i <= 3; i++) {

				switch (i) {

				case 1:

					stmF = stm + " up.Email=us.FirstUser";

					break;

				case 2:

					stmF = stm + " up.Email=us.SecondUser";

					break;

				case 3:

					stmF = stm + " up.Email=us.ThirdUser";

					break;

				}

				result = statement.executeQuery(stmF);



				while (result.next()) {

					map = new HashMap<String, String>();

					map.put("Email", result.getString(1));

					map.put("FirstName", result.getString(2));

					map.put("LastName", result.getString(3));

					map.put("Picture", result.getString(4));

					map.put("position", "" + i);

					userInfo.add(map);

				}

			}

			cache.put(usermail + "$" + topicSelected, userInfo);

		}

		String stringResult = "";

		String query = "SELECT * FROM Message WHERE (UserId=? OR UserId=? OR UserId=? OR UserId=?) AND Topic=? ORDER BY Date";

		pstm = c.prepareStatement(query);

		pstm.setString(1, usermail);

		for (int i = 0; i < 3; i++) {

			if (i < userInfo.size())

				pstm.setString(i + 2, userInfo.get(i).get("Email"));

			else

				pstm.setString(i + 2, null);

		}

		pstm.setString(5, topicSelected);

		msgs = pstm.executeQuery();

		while (msgs.next()) {



			if (msgs.getString("UserId").equals(usermail)) {

				stringResult = stringResult

						+ "<p><div id='messaggioBacheca'><button name='del' onclick='deleteMsg(this)' value='"

						+ msgs.getString("Id")

						+ "'>x</button><b>"

						+ msgs.getString("UserId")

						+ ":<br/></b> "

						+ msgs.getString("MessageTxt").replaceAll("\n",

								"<br/>") + "</div></p>";

			} else {

				stringResult = stringResult

						+ "<p><div id='messaggioBacheca'><b>"

						+ msgs.getString("UserId")

						+ ":<br/></b> "

						+ msgs.getString("MessageTxt").replaceAll("\n",

								"<br/>") + "</div></p>";

			}

		}

%>

<div id="corpo">

	<div id="sx">

		<div id="header">

			<center>

				<h1 id="title">Showcase - MiC</h1>

			</center>

		</div>

		<br />

		<div id="questions">

			<%=stringResult%>

		</div>

		<div id="dx">

			<div id="header">

				<center>

					<h1 id="title">Your similar</h1>

				</center>

			</div>

			<br />

			<div id="questions">

				<div id="favorites">

					<table id="tabFav">

						<%

							for (HashMap<String, String> userMap : userInfo) {

									out.print("<tr><td><img class='miniature'src='DownloadFile?fileName="

											+ userMap.get("Picture")

											+ "'/></td><td> "

											+ userMap.get("FirstName")

											+ " "

											+ userMap.get("LastName") + "</td></tr>");



								}

							} catch (Exception e) {

								e.printStackTrace();

							} finally {

								if (result!=null)

								result.close();

								if(msgs!=null)

								msgs.close();

								if(pstm!=null)

								pstm.close();

								if(statement!=null)

								statement.close();

								if(c!=null)

								c.close();

							}

						%>

					</table>

				</div>

			</div>

		</div>

	</div>

</div>