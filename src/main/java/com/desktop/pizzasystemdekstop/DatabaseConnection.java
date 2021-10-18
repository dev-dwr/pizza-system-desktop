package com.desktop.pizzasystemdekstop;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "db";
        String username = "root";
        String password = "Mamaitata1";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, username,password);

        }catch (Exception e){

        }
        return databaseLink;
    }
}
