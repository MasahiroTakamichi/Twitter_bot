package model;

import java.io.Serializable;
import java.sql.Time;

public class Tweet implements Serializable {
    private Long userId;
	private int tweetId;
    private String text;
    private Time time;
    private boolean flag;


    public Tweet() {
    }

    //postで使用
    public Tweet(Long userId, int tweetId, String text, String time) {
    	this.userId = userId;
    	this.tweetId = tweetId;
    	this.text = text;
        this.time = Time.valueOf(time);
        this.flag = true;
    }

    public Tweet(long UserId,int TweetId,String text, Time time,boolean flag) {
        this.userId = UserId;
    	this.tweetId = TweetId;
        this.text = text;
        this.time = time;
        this.flag = flag;
    }
    public long getUserId(){return userId;}
    public int getId(){return tweetId;}
    public String getText() {return text;}
    public Time getTime() {return time;}
    public boolean getFlag(){return flag;}
}
