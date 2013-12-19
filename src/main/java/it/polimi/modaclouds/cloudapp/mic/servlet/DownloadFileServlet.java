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


import it.polimi.modaclouds.cpimlibrary.blobmng.CloudDownloadBlob;
import it.polimi.modaclouds.cpimlibrary.mffactory.MF;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		MF mf=MF.getFactory();
		
		String fileName=req.getParameter("fileName");
        CloudDownloadBlob cdb = mf.getBlobManagerFactory().createCloudBlobManager().downloadBlob(fileName);
		InputStream streamDown = cdb.getFileStream();
	


	        resp.setContentLength((int)cdb.getSize());
	        resp.setContentType(cdb.getContentType());

	        resp.setHeader("Content-Disposition", "attachment; filename=\""
	                + fileName + "\"");

	        ServletOutputStream out = resp.getOutputStream();

	        BufferedOutputStream bufferOut = new BufferedOutputStream(out);

	        int b = 0;

	        byte[] bufferData = new byte[8192];

	        while ((b = streamDown.read(bufferData)) != -1) {
	            bufferOut.write(bufferData, 0, b);
	        }

	        bufferOut.flush();

	        bufferOut.close();
	        out.close();
	        streamDown.close();

	    }
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}


}
