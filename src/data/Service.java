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
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mean
 */
public class Service {

    private static String host;
    private static String username;
    private static String password;
    private static String database;

    /**
     * 
     */
    protected Connection con = null;
    /**
     * Parsea un json y obtiene los parametros para la base de datos
     * @throws IOException 
     */
    private void jsonParse() throws IOException {
        try {
            String currentDirectory = System.getProperty("user.dir");
            Path projectDirectory = Paths.get(currentDirectory, new String[] { "ConnectionSettings.json" });
            
            if(CheckFileExistence(projectDirectory.toString()))
            {
                JSONParser parser = new JSONParser();
                File file = new File(projectDirectory.toString());
                String readFile = FileUtils.readFileToString(file);
                Object obj = parser.parse(readFile); //the location of the file
                JSONObject jsonObject = (JSONObject) obj;
                host = (String) jsonObject.get("Hostname");
                username = (String) jsonObject.get("Username");
                password = (String) jsonObject.get("Password");
                database = (String) jsonObject.get("DbName");
            }
            else
            {
                throw new IOException();
            }
            
        } catch (ParseException e) {

        }

    }

    /**
     *
     * @return Retorna una conexion 
     * @throws IOException
     * @throws SQLException
     */
    protected Connection getConnection() throws IOException, SQLException {
        if (con == null || con.isClosed()) {
            try {
                jsonParse();
                
                con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);

            } catch (SQLException | FileNotFoundException e) {
                System.out.println("Connection Failed! Check output console " + e.toString());

            }

        }

        return con;
    }
    
    /**
     *
     * @param pPath
     * @return
     */
    public static boolean CheckFileExistence(String pPath) {
        boolean res = false;

        File file = new File(pPath);
        if ((file.exists()) && (!file.isDirectory())) {
            res = true;
        }
        return res;
    }

    protected void finalize() {
        try {
            con.close();
        } catch (Exception e) {
            /*este mï¿½todo es llamado por el
			 *garbage collector, por lo tanto
			 *se atrapa la excepciï¿½n pero no se
			 *reporta*/
        }
    }

    private void PrintInputStream(InputStream resourceAsStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void InputStream(InputStream resourceAsStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
