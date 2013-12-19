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

import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;

public class CPUMonitoring {
	
	private static Boolean monitoringMode=false;
	public static void saveLogs(String servlet, int diff, int start, int end)
	{
		Logger l=Logger.getLogger("it.polimi.modaclouds.cloudapp.mic.servlet");
		Connection c=MF.getFactory().getSQLService().getConnection();
		long time=System.currentTimeMillis();
		String mykey=servlet+time;
		String stm="INSERT INTO cpulogs VALUES('"+mykey+"', '"+ servlet+"', "+diff+", "+ start+", "+end+")" ;
		
		try {
			Statement statement=c.createStatement();
			statement.executeUpdate(stm);
			statement.close();
			c.close();
		} catch (Exception e) {
			l.info("NON RIUSCITO SALVATAGGIO CPU LOGS:"+e.getMessage());
		}
	}
	public static Boolean isActive()
	{
		return monitoringMode; 
	}
	public static void setMonitoringMode()
	{
		monitoringMode=true;
	}

}
