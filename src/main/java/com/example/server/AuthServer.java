package com.example.server;

import java.sql.*;

public class AuthServer {
    private static Connection connection;
    private static Statement statement;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");

            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginPass(String login, String password){
        String sql = String.format("select nickname from users where login = '%s' and password = '%s'", login, password);

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
