package com.springrest.springrest.controller;

/**
*@author kshitij
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.google.gson.Gson; 
import com.google.gson.JsonObject;

import com.springrest.springrest.services.CourseService;


@RestController
public class MyController {
	
	@Autowired
	CourseService courseService;
//	TableQueriesDao tableQueriesDao;
	
//	GsonBuilder builder = new GsonBuilder();
	Gson gson = new Gson();
		
	@PostMapping("/users")
	public String columnCheck(@RequestBody String user) {
		JsonObject body = gson.fromJson(user, JsonObject.class);
		System.out.println("Json body size "+body.size());
		ArrayList<String> keys = new ArrayList<>();
		String valueString = "";
		for(String key: body.keySet()) {
//			System.out.println(key+" "+body.get(key));
			keys.add(key);
		}
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con = DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/testdb","root","root@123");  
			Statement stmt = con.createStatement();  
			ResultSet rs = stmt.executeQuery("show columns from userM;");
			
			ArrayList<String> rsList = new ArrayList<>();
			
			while(rs.next()){
				System.out.print(rs.getRow()+". "+rs.getString(1)+", ");
				System.out.println();
				rsList.add(rs.getString(1));
				}

			String fieldString = "";
			for(int i=0;i<keys.size();i++) {	
				System.out.print(keys.get(i)+":"+body.get(keys.get(i))+", ");
				System.out.println();
				if(i == 0) {
					fieldString = "`"+keys.get(i)+"`";
					valueString = ""+body.get(keys.get(i));
				}else {
					fieldString = fieldString + ",`"+keys.get(i)+"`"; 
					valueString = valueString + ","+body.get(keys.get(i))+"";
				}
			}
			System.out.println("initial size "+keys.size());
			keys.removeAll(rsList);
			System.out.println("size after comparing "+keys.size());
//			int size = keys.size();

			for(int i=0;i<keys.size();i++) {	
				System.out.println(keys.get(i));
			    stmt.executeUpdate("alter table userM add "+keys.get(i)+" varchar(205) DEFAULT NULL;");
			}
			// the mysql insert statement
		      String query = "insert into userM ("+fieldString+")"
		        + " values ("+valueString+")";
		      
		      System.out.println(query);
		      
		      stmt.executeUpdate(query);
		      
			con.close();  
			}
		catch(Exception e){ 
				System.out.println(e);
			}  
			
		
		return user;
	}
	
}
