package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Tweet;

/**
 * Created by Issei on 2017/07/12.
 */
public class tweetDAO {
    private final String DRIVER_NAME = "org.h2.Driver";
   // private final String JDBC_URL = "jdbc:h2:file:C:/Users/G015C1140/DB/schneit_bot;AUTO_SERVER=TRUE";    //DB???i?t???p?X?j
    private final String JDBC_URL = "jdbc:h2:C:/Users/Masa/Documents/DB/schneit;AUTO_SERVER=TRUE";
    private final String DB_USER = "masahiro";
    private final String DB_PASS = "masahiro";

    static Connection conn = null;

    public tweetDAO(){

        if(conn == null){
            try{
            	// JDBCドライバを読み込み
                Class.forName(DRIVER_NAME);

               	// データベースへ接続
                conn = DriverManager.getConnection( JDBC_URL,	DB_USER,DB_PASS);
            }catch(Exception  e){}

            if(conn != null)	System.out.println("connectしました");
            else System.out.println("connect失敗しました");
        }
    }
    /////////////////////////////////////////////////////////////////
    public List<Tweet> findAll() {

        List<Tweet> tweetList = new ArrayList<Tweet>();
        try {

            System.out.println("conn=" + conn);

            if(conn == null){
                System.out.println("DB接続してない tweetDAO");
                return tweetList;
            }
            // SELECT???????
            String sql =
            		"SELECT * FROM TWEET;";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            // SELECT?????s
            ResultSet rs = pStmt.executeQuery();

            // SELECT????????ArrayList??i?[
            while (rs.next()) {
                long Uid = rs.getLong("USER_ID");
                int Tid = rs.getInt("TWEET_ID");
                String text = rs.getString("TEXT");
                Time time = rs.getTime("T");
                boolean flag = rs.getBoolean("FLAG");

                Tweet tweet = new Tweet(Uid,Tid,text,time,flag);
                tweetList.add(tweet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
        	System.out.println("tweetDAO end");

        }
        //???????????X?g????
        return tweetList;
    }

    /////////////////////////////////////////////////////////////////

    //------------------------------
    //?c?C?[?g?f?[?^??????A?擾
    //------------------------------
    public List<Tweet> findAll(long l) {

        List<Tweet> tweetList = new ArrayList<Tweet>();
        try {

            System.out.println("conn=" + conn);

            if(conn == null){
                System.out.println("DB接続してない tweetDAO");
                return tweetList;
            }
            // SELECT???????
            String sql =
            		"SELECT * FROM TWEET WHERE TWEET_ID IN (SELECT TWEET_ID FROM CONNECT WHERE USER_ID = "+ l +");";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            // SELECT?????s
            ResultSet rs = pStmt.executeQuery();

            // SELECT????????ArrayList??i?[
            while (rs.next()) {
                long Uid = rs.getLong("USER_ID");
                int Tid = rs.getInt("TWEET_ID");
                String text = rs.getString("TEXT");
                Time time = rs.getTime("T");
                boolean flag = rs.getBoolean("FLAG");

                Tweet tweet = new Tweet(Uid,Tid,text,time,flag);
                tweetList.add(tweet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
        	System.out.println("tweetDAO end");

        }
        //???????????X?g????
        return tweetList;
    }

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
    public boolean create(Tweet tweet) {

		try {

			// INSERT文の準備(idは自動連番なので指定しなくてよい）
			String twSql = "INSERT INTO Tweet VALUES(?, ?, ?, ?, ?)";
			String conSql = "INSERT INTO Connect VALUES(?, ?)";

			//プリペアステートメントの作成
			PreparedStatement pStmt = conn.prepareStatement(twSql);
			PreparedStatement pStmt2 = conn.prepareStatement(conSql);

			// INSERT文中の「?」に使用する値を設定しSQLを完成
			pStmt.setLong(1, tweet.getUserId());
			pStmt.setInt(2, tweet.getId());
			pStmt.setString(3, tweet.getText());
			pStmt.setTime(4, tweet.getTime());
			pStmt.setBoolean(5, tweet.getFlag());

			pStmt2.setLong(1, tweet.getUserId());
			pStmt2.setInt(2, tweet.getId());

			// INSERT文を実行
			int result = pStmt.executeUpdate();
			int result2 = pStmt2.executeUpdate();

			if (result != 1 || result2 != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {

		}
		return true;
	}

    public boolean update(Tweet tweet){
    	try{
    		String sql = "UPDATE TWEET SET TEXT = ?, T = ?, FLAG = ? WHERE USER_ID = ? AND TWEET_ID = ?;";

    		PreparedStatement pStmt = conn.prepareStatement(sql);

    		pStmt.setString(1, tweet.getText());
			pStmt.setTime(2, tweet.getTime());
			pStmt.setBoolean(3, tweet.getFlag());
			pStmt.setLong(4, tweet.getUserId());
			pStmt.setInt(5, tweet.getId());

    		// INSERT文を実行
    		int result = pStmt.executeUpdate();

    		if (result != 1) {
				return false;
			}
    	}catch(SQLException e){
    		e.printStackTrace();
			return false;
    	}

    	return true;
    }

    public boolean delete(Long userId,int tweetId){
    	try{
    		String twSql = "DELETE FROM TWEET WHERE USER_ID = ? AND TWEET_ID = ?;";
    		String conSql = "DELETE FROM CONNECT WHERE USER_ID = ? AND TWEET_ID = ?;";

    		PreparedStatement pStmt1 = conn.prepareStatement(twSql);
    		PreparedStatement pStmt2 = conn.prepareStatement(conSql);

    		pStmt1.setLong(1, userId);
			pStmt1.setInt(2, tweetId);

			pStmt2.setLong(1, userId);
			pStmt2.setInt(2, tweetId);

    		// INSERT文を実行
			int result1 = pStmt1.executeUpdate();
			int result2 = pStmt2.executeUpdate();

			if (result1 != 1 || result2 != 1) {
				return false;
			}
    	}catch(SQLException e){
    		e.printStackTrace();
			return false;
    	}finally{

    	}
    	return true;
    }
}
