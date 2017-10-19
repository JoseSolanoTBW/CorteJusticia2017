/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Christian Adrian Obando Leiton
 */
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Connect {
    
  private static String host;
  private static String username;
  private static String password;
  private static String database;
  
  private   void jsonParse() throws FileNotFoundException, IOException, ParseException{
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader("src/data/connectionSettings.json")); //the location of the file
      JSONObject jsonObject = (JSONObject) obj;
      host = (String) jsonObject.get("Hostname");
      username = (String) jsonObject.get("Username");
      password = (String) jsonObject.get("Password");
      database =(String) jsonObject.get("DbName");
  }
  
  protected Connection getConnection()throws IOException, FileNotFoundException, ParseException{
    Connection connection = null;

    try {
        jsonParse();
        System.out.println(host);
	connection = DriverManager
            .getConnection("jdbc:mysql://"+host+"/"+database,username, password);

	} catch (SQLException | FileNotFoundException | ParseException e) {
            System.out.println("Connection Failed! Check output console " + e.toString());
            
	}

	if (connection != null) {
		System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
	}
        return connection;
  }

  
}
