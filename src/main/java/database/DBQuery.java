package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Saschbot on 11.03.2017.
 */
public class DBQuery {
    private String url="";
    private String myDriver="";
    private String conName="";
    private String password="";
    private ResultSet rs = null;
    private List<String> tableNames = new ArrayList<String>();
    private static final Logger LOGGER = Logger
            .getLogger(DBQuery.class.getName());

    public DBQuery(String url, String myDriver, String conName, String password){
        this.url = url;
        this.myDriver = myDriver;
        this.conName = conName;
        this.password = password;
    }

    private Connection establishConnection(){
        Connection conn=null;
        try {
            String myUrl = url;
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, conName, password);

        }catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        return conn;
    }

    protected ResultSet query(String query,String[] parameters){
        try
        {
            Connection conn = establishConnection();

            PreparedStatement pstmt = conn.prepareStatement( query );
            int count = 0;
            for(String par:parameters){
                count++;
                pstmt.setString(count, par);
            }
            rs =  pstmt.executeQuery( );

            //conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return rs;
    }

    protected int queryUpdate(String query,String[] parameters){
        int lines = -1;
        try
        {
            Connection conn = establishConnection();

            PreparedStatement pstmt = conn.prepareStatement(query);
            int count = 0;
            for(String par:parameters){
                count++;
                pstmt.setString(count, par);
            }
            lines = pstmt.executeUpdate();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return lines;
    }

    public void truncate(String table){
        queryUpdate("TRUNCATE TABLE "+table,new String[0]);
    }

    public void dropTable(String table){
        queryUpdate("DROP TABLE "+table,new String[0]);
    }

    public boolean isTable(String table) {
        try{
            String myUrl = url;
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, conName, password);
            DatabaseMetaData dbm = conn.getMetaData();
            // check if "employee" table is there
            ResultSet tables = dbm.getTables(null, null, table, null);
            if (tables.next()) {
                return true;
            }
        }catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean next(){
        boolean isNext=false;
        try {
            if (rs.next()==true){
                isNext = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isNext;
    }

    public int getInt(String attribute){
        int result = 0;
        try {
            result = rs.getInt(attribute);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String getString(String attribute){
        String result = "";
        try {
            result = rs.getString(attribute);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    private void analyseDatabase(Connection con) {
        ResultSet result = null;
        try {
            DatabaseMetaData metaData = con.getMetaData();

            result = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (result.next()) {
                String tableName = result.getString("TABLE_NAME");
                if (!tableName.equals("languages")) {
                    tableNames.add(tableName);
                }

            }
        } catch (SQLException ex) {

        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public void dropAllTables() {

        Connection con=null;
        try {
            con = establishConnection();

            analyseDatabase(con);

            dropTables(con);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }

    }

    private void dropTables(Connection con) {

        for (int i = 0; !tableNames.isEmpty(); i++) {
            Iterator<String> tableNamesIt = tableNames.iterator();

            while (tableNamesIt.hasNext()) {
                try {
                    con.createStatement().executeUpdate("DROP TABLE "
                            +tableNamesIt.next());
                    tableNamesIt.remove();
                } catch (SQLException ex) {
                }
            }

        }
    }

}
