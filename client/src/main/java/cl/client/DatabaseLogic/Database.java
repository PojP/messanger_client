package cl.client.DatabaseLogic;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    private String path;
    private String name;
    public Database(String p, String n)
    {
        path=p;
        name = n;
    }
    public void OpenDB(){
        try {
            DBManager.OpenDB("jdbc:sqlite:"+path+name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void CloseDB()  {
        try {
            DBManager.CloseDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void WriteToDB(String table, ArrayList<String> rows, ArrayList<String> values)
    {
        try {
            DBManager.WriteDB(table, rows, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void WriteToDB(String table, String row, String value)
    {
        try {
            DBManager.WriteDB(table, row, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void UpdateDB(String table, String row, String value, String WHERE_DATA, String WHERE_EQ_DATA)
    {
        try {
            DBManager.UpdateDB(table, row, value, WHERE_DATA, WHERE_EQ_DATA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void UpdateDB(String table, ArrayList<String> rows, ArrayList<String> values, String WHERE_DATA, String WHERE_EQ_DATA)
    {
        try {
            DBManager.UpdateDB(table, rows, values, WHERE_DATA, WHERE_EQ_DATA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> GetArrString(String table, String data) {
        try {
            return DBManager.GetStringListDB(table, data);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String GetString(String table, String data, String WHERE_DATA, String WHERE_EQ_DATA){
        try {
            String str = "";
            str = DBManager.GetStringDB(table, data, WHERE_DATA, WHERE_EQ_DATA);
            return str;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean CheckData(String table, String data, String WHERE_DATA, String WHERE_EQ_DATA){
        try {
            return !DBManager.GetStringListDB(table, data, WHERE_DATA, WHERE_EQ_DATA).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
