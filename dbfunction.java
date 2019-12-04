package web;

import java.sql.*;

public class dbfunction {

    private Connection con;
    private Statement stat;
    private ResultSet result;

    public void createConnection() {
        try {
            Class.forName(web.Resource.getdbDriver());
            con = DriverManager.getConnection(web.Resource.getDbUrl(), web.Resource.getDbUser(), web.Resource.getDbPass());
        } catch (Exception ex) {
        }
    }

    public void createConnection(String driver, String url) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
        } catch (Exception ex) {
        }
    }

    public void createConnection(String driver, String dataSource, String userName, String password) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(dataSource, userName, password);
        } catch (Exception ex) {
        }
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }

    public boolean updateRecord(String strSQL) {
        try {
            stat = con.createStatement();

            int status = stat.executeUpdate(strSQL);
            if (status == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public ResultSet queryRecord(String strSQL) {
        try {
            stat = con.createStatement();
            result = stat.executeQuery(strSQL);
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
}