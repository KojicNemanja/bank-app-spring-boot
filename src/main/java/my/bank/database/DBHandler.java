package my.bank.database;

import my.bank.database.configuration.DBConfigFile;
import my.bank.database.models.Database;

import java.sql.*;

public class DBHandler {

    private static Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private Statement st = null;
    private static Database database = null;
    private static String connectionString = null;

    private static Connection create() throws SQLException {

        if (conn == null) {
            database = (database==null ? DBConfigFile.readData() : database);// DBConfigFile.readData();
            if (connectionString == null) {
                connectionString = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s",
                        database.getHost(), database.getPort(), database.getName(),
                        database.getUser(), database.getPassword());
            }
            conn = DriverManager.getConnection(connectionString);
            System.out.println("New connection: " + conn);
        }
        return conn;
    }

    public DBHandler() throws SQLException {
        conn = create();
    }

    public Connection getConn() {
        return conn;
    }

    public void commit() throws SQLException {
        conn.commit();
    }
    public void disable_auto_commit() throws SQLException {
        this.conn.setAutoCommit(false);
    }

    public void enable_auto_commit() throws SQLException {
        this.conn.setAutoCommit(true);
    }

    public void roll_back(){
        try{
            this.conn.rollback();
            System.out.println("Roll back connection" + conn);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void close_connection(){
        try{
            if(conn != null) {
                this.conn.close();
                System.out.println("Close connection: " + conn);
                this.conn = null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int transactional_insert_update_delete(String query){
        int result = 0;
        try{
            disable_auto_commit();

            st = conn.createStatement();
            result = st.executeUpdate(query);

            commit();
        }catch (Exception ex){
            roll_back();
            ex.printStackTrace();
        }finally {
            try{st.close(); st=null;}catch (Exception ex){}
            close_connection();
        }
        return result;
    }

    public int transactional_insert_with_generated_keys(String query){
        int result = 0;
        try{
            disable_auto_commit();

            ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            st.executeUpdate(query);
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                result = rs.getInt(1);
            }
            commit();
        }catch (Exception ex){
            roll_back();
            ex.printStackTrace();
        }finally {
            try{
                rs.close();
                rs = null;
                st.close();
                st = null;
            }catch (Exception ex){}
            close_connection();
        }
        return result;
    }

    public int insert_update_delete(String query) throws SQLException {
        int result = 0;
        st = conn.createStatement();
        result = st.executeUpdate(query);
        try{st.close(); st = null;}catch (Exception ex){}

        return result;
    }

    public int insert_with_generated_keys(String query) throws SQLException {
        int generated_id = 0;
        ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        if(rs.next()){
            generated_id = rs.getInt(1);
        }
        try {
            rs.close(); rs = null;
            ps.close();; ps = null;
        }catch (Exception ex){}

        return generated_id;
    }
}
