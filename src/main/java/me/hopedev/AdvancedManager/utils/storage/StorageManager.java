package me.hopedev.AdvancedManager.utils.storage;

import java.sql.*;

public enum StorageManager {
    /*
    tagName
    tagContent
    imageAttachmentURL
     */

    ADD_ENTRY("INSERT INTO " +
            "tagData(tagName, " +
            "tagContent, " +
            "imageAttachmentURL) " +
            "VALUES (?, ?, ?);"),
    REMOVE_ENTRY("DELETE FROM " +
            "tagData " +
            "WHERE tagName = ?;"),

    UPDATE_ENTRY("UPDATE " +
            "tagData " +
            "SET " +
            "tagContent = ?, " +
            "imageAttachmentURL = ? " +
            "WHERE " +
            "tagName = ?;");

    public final String sql;

    StorageManager(String sql) {
        this.sql = sql;
    }

    private static Connection connection;
    private final static String sqlitePath = "storage.sqlite";

    public static void connect() throws ClassNotFoundException {
        System.out.println("\n[DATABASE] Connecting to database");
        String url = "jdbc:sqlite:"+sqlitePath;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("-----------------------");
            System.out.println("-- Driver Information");
            System.out.println("Driver Name: "
                    + meta.getDriverName());
            System.out.println("Driver Version: "
                    + meta.getDriverVersion());
            System.out.println("-- Database Information ");
            System.out.println("Database Name: "
                    + meta.getDatabaseProductName());
            System.out.println("Database Version: "+
                    meta.getDatabaseProductVersion());
            System.out.println("-----------------------\n");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static PreparedStatement prepareStatement(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void threadUpdate(PreparedStatement statement) {
        new Thread(() -> {
            try {
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
        }).start();


    }


}
