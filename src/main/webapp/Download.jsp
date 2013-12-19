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
<%@ page import="it.polimi.modaclouds.cpimlibrary.entitymng.CloudEntityManager"%>
<%@ page import="it.polimi.modaclouds.cpimlibrary.mffactory.MF"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="it.polimi.modaclouds.cpimlibrary.exception.ParserConfigurationFileException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Select the file to download</title>
<link href="stile.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="image/favicon.png" type="image/png" />
</head>
<body>
	<div id="content">
		<div id="subcontent">


			<div id="corpo">
				<div id="header">
					<center>
						<h1 id="title">Files</h1>
					</center>
				</div>
				<br />
					<div id="questions">

						<%
						MF mf=MF.getFactory();
							ArrayList<String> fileList=mf.getBlobManagerFactory().createCloudBlobManager().getAllBlobFileName();
							int count = 0;
							for (String s : fileList) {
								count++;
						%>
						<p>
							<div id="domanda">
							<a href=<%="DeleteFile?fileName="+s%>>x</a> &nbsp&nbsp&nbsp <a href=<%="DownloadFile?fileName="+s%>> <%=s%> </a>
							</div>
							
						</p>
						<%
						}
							%>


					</div>
					<form action="Home.jsp" method="post">
					<div id="meet">
						<input type="image" name="submit" src="image/home.png" alt="HOME"
							value=""/>
					</div>
					</form>
				
				
			</div>
		</div>
	</div>

</body>
</html>