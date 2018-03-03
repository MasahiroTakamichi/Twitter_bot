package timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;

import model.DeleteMutterLogic;
import model.GetMutterListLogic;
import model.Tweet;
import model.UserTweet;
import twitter4j.Twitter;

///////////////////////////////////////
public class Timer {
	java.util.Timer t;

    public Timer() {}

    public Timer(Twitter twitter) {

        this.t = new java.util.Timer();

        TimerLabelTask tlt = new TimerLabelTask(twitter);

        //遅延処理(変数.schedule(new TimeLabel.TimerLableTask(),0,遅延秒数60000で1分))
        t.schedule(tlt, 0, 1000);

    }

    //スレッド止まれーーーーー
    public void stop(){
    	 t.cancel();
    }
}

////////////////////////////////
class TimerLabelTask extends TimerTask {

	private Calendar cal;
	Twitter twitter;

	TimerLabelTask(){}

	TimerLabelTask(Twitter twitter){
		this.twitter = twitter;
	}

	public void run() {
        tweetStart();
    }

    public void tweetStart() {

        cal = Calendar.getInstance(Locale.JAPAN);
        //フォーマット(時間:分:秒)
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:00");
        String time = sdf.format(cal.getTime());

        //データベース取得
        GetMutterListLogic gm = new GetMutterListLogic();
        List<Tweet> list = gm.execute();

        //ツイート開始
       System.out.println("ツイート　比較　開始");
       for(Tweet tweet: list){
    	   System.out.println("ツイート比較 現在時刻："+time+"設定時刻："+tweet.getTime());
    	   if( time.compareTo( tweet.getTime().toString() ) == 0 ){
    		   System.out.println("ツイート　時間　同じ");
    		   new UserTweet(tweet,twitter);
    		   System.out.println("ツイート　成功");
    		   new DeleteMutterLogic().execute(tweet.getUserId(), tweet.getId());
    		   System.out.println("ツイート　削除");
    	   }
       }
       System.out.println("ツイート　比較　終了");
    }

}
