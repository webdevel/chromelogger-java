/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

/*
 * @author Shazron Abdullah, shazron (a t) g mail dot com, 2013
 */

package com.chromelogger;

import java.util.*;
import org.json.*;
import org.apache.commons.codec.binary.Base64;

public class ChromeLogger {
	
	public interface SetHeaderInterface {
		public void setHeader(String name, String value);
	}
	
	private SetHeaderInterface setHeaderInterface = null;
	
	private final String HEADER = "X-ChromeLogger-Data";
	private final String VERSION = "1.0";
	private HashMap<String, Object> data;
	
	public ChromeLogger() {
		reset();
	}
	
	private void internalLog(String logLevel, String backtrace, Object ... logData) {
		Object row[] = { logData, backtrace, logLevel };
		
		synchronized(this) {
			ArrayList<Object> rows = (ArrayList<Object>)data.get("rows");
			rows.add(row);
			data.put("rows", rows);

			if (this.setHeaderInterface != null) {
				Map.Entry<String, String> header = getHeader();
				this.setHeaderInterface.setHeader(header.getKey(), header.getValue());
			}
		}
	}
	
	public void log(String backtrace, Object ... logData) {
		// the empty string is recommended for 'log' logLevel
		internalLog("", backtrace, logData);
	}

	public void warn(String backtrace, Object ... logData) {
		internalLog("warn", backtrace, logData);
	}

	public void error(String backtrace, Object ... logData) {
		internalLog("error", backtrace, logData);
	}
	
	public void info(String backtrace, Object ... logData) {
		internalLog("info", backtrace, logData);
	}
	
	public void group(String backtrace, Object ... logData) {
		internalLog("group", backtrace, logData);
	}

	public void groupEnd(String backtrace, Object ... logData) {
		internalLog("groupEnd", backtrace, logData);
	}

	public void groupCollapsed(String backtrace, Object ... logData) {
		internalLog("groupCollapsed", backtrace, logData);
	}
	
	///
	
	public void log(Object ... logData) {
		log(null, logData);
	}

	public void warn(Object ... logData) {
		warn(null, logData);
	}

	public void info(Object ... logData) {
		info(null, logData);
	}
	
	public void group(Object ... logData) {
		group(null, logData);
	}

	public void groupEnd(Object ... logData) {
		groupEnd(null, logData);
	}

	public void groupCollapsed(Object ... logData) {
		groupCollapsed(null, logData);
	}
	
	///
	public String getVersion() {
		return this.VERSION;
	}
	
	public Map.Entry<String, String> getHeader() {
		return getHeader(true);
	}

	public Map.Entry<String, String> getHeader(boolean flush) {
		JSONObject jo = new JSONObject(this.data);
		byte[] jsonString;
		
		try {
			jsonString = jo.toString().getBytes("UTF-8");
			Map.Entry<String, String> retVal = new AbstractMap.SimpleEntry<String, String>(HEADER, Base64.encodeBase64String(jsonString));
			if (flush) {
				reset();
			}
			return retVal;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void setHeader(ChromeLogger.SetHeaderInterface setHeaderInterface) {
		this.setHeaderInterface = setHeaderInterface;
	}

	public void reset() {
		data = new HashMap<String, Object >()
		{
			private static final long serialVersionUID = 1L;
			{
				put("version", VERSION);
				put("columns", new String[]{"log", "backtrace", "type"});
				put("rows", new ArrayList<Object>());
			}
		};
	}
}
