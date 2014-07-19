package com.test.mockaroo.apì;

import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mockaroo.api.MockarooApi;
import com.mockaroo.api.MockarooDataAccess;
import com.mockaroo.api.MockarooExcel;
import com.mockaroo.api.MockarooJSON;
import com.mockaroo.api.MockarooXML;
//import com.mockaroo.api.MockarooXML;
import com.mockaroo.api.classes.MockarooCreateJSONObject;
import com.mockaroo.api.classes.MockarooFile;

public class Test {
	public static void main(String[] args) throws Exception {
		
		MockarooApi mockarooApi = new MockarooApi("60391b90", "application/json");
		HttpURLConnection conn = mockarooApi.getUrl().openConnecion();
		
		MockarooCreateJSONObject creater = mockarooApi.getCreater();
		
		JSONArray values = new JSONArray();
		values.put("R+D");
		values.put("Marketing");
		values.put("HR");

		JSONArray columns = new JSONArray();
		//columns.put(creater.createNumber("yearsEmployed", 1, 30, 0));
		columns.put(creater.createCustomList("department", values));
		//columns.put(creater.createDate("dob", "1/1/1950", "1/1/2000", "%m/%d/%Y"));
		columns.put(creater.createFirstName("name"));
		//columns.put(creater.createTime("time", "05:00 AM", "06:59 AM", MockarooTimeType.H));

		JSONObject data = mockarooApi.getJSONObject(conn, columns);
	
		try
		{
			MockarooDataAccess dataAccess = new MockarooDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/testmockarooapi", "root", "mtrlnk");
			String[] tableColumns = new String[2];
			tableColumns[0] = "data";
			tableColumns[1] = "data2";
			
			dataAccess.Insert("table_test", data, tableColumns);
		}
		catch(Exception e)
		{
		}
		
		MockarooFile excel = new MockarooExcel("c:/temp/","test","testing","en", "EN");
	    excel.write(data); //Generate a Excel file

	    MockarooFile json = new MockarooJSON("c:/temp/", "test");
	    json.write(data); //Generate a .json file
	    
	    MockarooFile xml = new MockarooXML("c:/temp/", "test", "xmltest");
		xml.write(data);
		
		//System.out.println(data.getInt("yearsEmployed"));
		System.out.println(data.getString("department"));
		//System.out.println(data.getString("dob"));
		System.out.println(data.getString("name"));
		//System.out.println(data.getString("time"));
	}
}