
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TwitterUser;

public class UserDAO {

    private final String DRIVER_NAME = "org.h2.Driver";
    //private final String JDBC_URL = "jdbc:h2:file:C:/Users/G015C1140/DB/schneit_bot;AUTO_SERVER=TRUE";    //DB???i?t???p?X?j
    private final String JDBC_URL = "jdbc:h2:C:/Users/Masa/Documents/DB/schneit;AUTO_SERVER=TRUE";
    private final String DB_USER = "masahiro";
    private final String DB_PASS = "masahiro";

    static Connection conn = null;

    public UserDAO(){

        if(conn == null){
            try{
            	// JDBCドライバを読み込み
                Class.forName(DRIVER_NAME);

            	// データベースへ接続
                conn = DriverManager.getConnection(
                        JDBC_URL,	DB_USER, DB_PASS);
            }catch(Exception  e){}

            if(conn != null)	System.out.println("connectしました");
            else System.out.println("connect失敗しました");
        }
    }

    //------------------------------
    //???[?U?[?f?[?^??????A?擾
    //------------------------------
    public List<TwitterUser> findAll() {

        List<TwitterUser> userList = new ArrayList<TwitterUser>();
        try {

            System.out.println("conn=" + conn);

            if(conn == null){
                System.out.println("DB接続できていません");
                return userList;
            }
            // SELECT文準備
            String sql =
                    "SELECT * FROM USER"
                    + " ORDER BY USER_ID ASC;";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            // SELECT実行
            ResultSet rs = pStmt.executeQuery();

            // SELECT????????ArrayList??i?[
            while (rs.next()) {
                long id = rs.getLong("USER_ID");
                TwitterUser user = new TwitterUser(id);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {

        }
        //???????????X?g????
        return userList;
    }

	//-----------------------------------
	//DB接続を切る
	//-----------------------------------
    public static void  close(){

		// データベース切断
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	//-----------------------------------
	//データ１件登録
	//-----------------------------------
    public boolean create(long l) {

        try {

            // INSERT???????(id??????A?????w?????????j
            String sql = "INSERT INTO USER(USER_ID) VALUES(?)";

            //?v???y?A?X?e?[?g?????g???
            PreparedStatement pStmt = conn.prepareStatement(sql);

            // INSERT??????u??v??g?p????l????SQL??????
            // create呼び出し時にgetIdをして引数として渡す（Twitterは使えない）
            pStmt.setLong(1, l);

            // INSERT???????s
            int result = pStmt.executeUpdate();

            if (result != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
