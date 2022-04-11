package cl.client.DatabaseLogic;

import java.sql.*;
import java.util.ArrayList;

class DBManager {
    public static Connection conn;
    public static PreparedStatement statmt;
    public static ResultSet resSet;
    // Open Database
    public static void OpenDB(String url) throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(url);
    }
    // Write new data to database
    public static void WriteDB(String table, ArrayList<String> rows, ArrayList<String> values) throws SQLException {
        String rowsToSQL="";
        for (String rws : rows) {
            rowsToSQL += rws + ", ";
        }
        rowsToSQL=rowsToSQL.substring(0, rowsToSQL.length()-2);
        int i = 0;
        String valuesSum="";
        for (String vl : values)
        {
            i++;
            valuesSum+="?,";
        }
        valuesSum=valuesSum.substring(0,valuesSum.length()-1);
        String query = "INSERT INTO "+table+" ("+rowsToSQL+") VALUES ("+valuesSum+"); ";
        statmt = conn.prepareStatement(query);
        int j = 0;
        for (; j<i; j++) {
            statmt.setString(j+1, values.get(j));
        }
        statmt.executeUpdate();
        statmt.close();
    }
    public static void WriteDB(String table, String row, String value) throws SQLException {

        String query = "INSERT INTO "+table+" ('"+row+"') VALUES (?); ";
        System.out.println(query);
        statmt = conn.prepareStatement(query);
        statmt.setString(1, value);
        statmt.executeUpdate();
        statmt.close();
    }
    public static void UpdateDB(String table, String row, String value, String WHERE_DATA, String WHERE_EQ_DATA) throws SQLException {

        String query = "UPDATE "+table+" SET '"+row+"' = ? WHERE "+WHERE_DATA+"=?; ";
        System.out.println(query);
        statmt = conn.prepareStatement(query);
        statmt.setString(1, value);
        statmt.setString(2, WHERE_EQ_DATA);
        System.out.println(statmt.toString());
        statmt.executeUpdate();
        statmt.close();
    }
    // Rewrite data in database
    public static void UpdateDB(String table, ArrayList<String> rows, ArrayList<String> values, String WHERE_DATA, String WHERE_EQ_DATA) throws SQLException {
        String rowsToSQL ="";
        int g=0;
        for (String rws : rows) {
            rowsToSQL +="'"+ rws +"' = "+ values.get(g)+", ";
            g++;
        }
        rowsToSQL=rowsToSQL.substring(0, rowsToSQL.length()-2);
        String query = "UPDATE "+table+" SET "+rowsToSQL+" WHERE "+WHERE_DATA+"='?'; ";
        statmt = conn.prepareStatement(query);
        int j = 0;

        for (; j<g; j++) {
            statmt.setString(j+1, values.get(j));
        }
        statmt.setString(j+1, WHERE_EQ_DATA);
        statmt.executeQuery();
        statmt.close();
    }
    //Get data from database
    public static ArrayList<String> GetStringListDB(String table, String data) throws SQLException {
        String query = "SELECT "+data+" FROM "+table+"; ";
        statmt = conn.prepareStatement(query);

        resSet = statmt.executeQuery();
        ArrayList<String> SearchString = null;
        while (resSet.next()) {
            SearchString.add(resSet.getString(data));
        }

        statmt.close();
        resSet.close();

        return SearchString;
    }
    //Get data from database with key
    public static ArrayList<String> GetStringListDB(String table, String data, String WHERE_DATA, String WHERE_EQ_DATA) throws SQLException {
        String query = "SELECT "+data+" FROM "+table+" WHERE "+WHERE_DATA+" = ?; ";
        statmt = conn.prepareStatement(query);
        statmt.setString(1,WHERE_EQ_DATA);

        resSet = statmt.executeQuery();
        ArrayList<String> SearchString = new ArrayList<String>();
        while (resSet.next()) {
            SearchString.add(resSet.getString(data));
        }

        statmt.close();
        resSet.close();

        return SearchString;
    }
    public static String GetStringDB(String table, String data, String WHERE_DATA, String WHERE_EQ_DATA) throws SQLException {
        String query = "SELECT "+data+" FROM "+table+" WHERE "+WHERE_DATA+" = ?; ";
        statmt = conn.prepareStatement(query);
        statmt.setString(1,WHERE_EQ_DATA);

        resSet = statmt.executeQuery();
        String SearchString = "";
        while (resSet.next()) {
            SearchString+=resSet.getString(data);
        }

        statmt.close();
        resSet.close();

        return SearchString;
    }
    // Close Database
    public static void CloseDB() throws SQLException
    {
        conn.close();

        System.out.println("All Connections are closed");
    }
}
