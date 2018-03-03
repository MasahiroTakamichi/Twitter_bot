package model;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class UserTweet {

	Tweet tweet;
	Twitter twitter;

	UserTweet(){}

	public UserTweet(Tweet tweet, Twitter twitter){
		this.tweet = tweet;
		this.twitter = twitter;
		tweet();
	}

	private void tweet(){
		try {
			twitter.updateStatus(tweet.getText());
			System.out.println("ユーザーツイート成功");
		} catch (TwitterException e) {
			System.out.println("ユーザーツイート失敗");
			e.printStackTrace();
		}
	}
}