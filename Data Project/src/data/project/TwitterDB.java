package data.project;

import twitter4j.*;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.List;
import java.util.Date;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class TwitterDB {
    public String twitterLocation;
    public String twitterMessage;  
    public Date twitterDate;
    public java.sql.Date tweetTime;
    
    public void tweetsRetrieve1() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("o1EWhGiS4jNC6zL7h1hRZ2v8I");
        cb.setOAuthConsumerSecret("qAWPnkCd3fBREfgxnWZmwYXCR8Jwo1kALFBX30OY6XOIV87cUm");
        cb.setOAuthAccessToken("192675086-v0OFPNPFFJyafSlLJgHIvA26EMp8xatD9pgYzn3w");
        cb.setOAuthAccessTokenSecret("5f7vPKZR9d4l51rmLW6a8S3BejyRIZMLXSVv57d04G2cx");
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        
        try {
            Query query = new Query("Euromast");
            query.count(20);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                System.out.println(tweet.getCreatedAt() +  " - " + "@" + tweet.getUser().getScreenName() + " - " + tweet.getUser().getLocation() + " - " + tweet.getText());
                twitterMessage = tweet.getText();
                twitterLocation = tweet.getUser().getLocation();
                twitterDate = tweet.getCreatedAt();
                tweetTime = new java.sql.Date(twitterDate.getTime());
                twitterDataImport();
            }
            weatherAPICurrent w = new weatherAPICurrent();
            w.run();

            System.exit(0);
            } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
            }
    }
    
    public void twitterDataImport() {       
        try{
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        String user = "kimberley";
        String pass ="euromast";
        String url = "jdbc:mysql://localhost:3306/projectdb";

            //1. get connection to database
            myConn = DriverManager.getConnection(url, user, pass);            
            //2. Create a statement
            myStmt = myConn.createStatement();
            //3. Execute SQL query
            
            //TwitterLocation
            String location = "INSERT INTO twitter_user_location"
                    + " (twitter_user_location_id, twitter_user_location) "
                    + " VALUES (DEFAULT, \""+twitterLocation+"\");";
            myStmt.executeUpdate(location); 
            
            //TwitterMessage
            String tweet = "INSERT INTO twitter_time"
                    + " (twitter_time_id, twitter_date) "
                    + " VALUES (DEFAULT, \""+tweetTime+"\");";
            myStmt.executeUpdate(tweet); 
            
            //TwitterTime
            String message = "INSERT INTO twitter_message"
                    + "(twitter_message, twitter_time_twitter_time_id)"
                    + "VALUES (\""+twitterMessage+"\", LAST_INSERT_ID());";
            myStmt.executeUpdate(message);           
           
            //TwitterUser
            String twitteruser = "INSERT INTO twitter_user"
                    + " (twitter_user_id, twitter_user_location_id, twitter_message_id)"
                    + "VALUES (DEFAULT, LAST_INSERT_ID(), LAST_INSERT_ID());";
            myStmt.executeUpdate(twitteruser);
            
            myRs = myStmt.executeQuery("select * from twitter_message");
            
            //4. Process the result set
            while(myRs.next()){
            //System.out.println(myRs.getString(twitterLocation));       
            }
            
        }
        catch(Exception exc){
            System.out.println(exc);
            exc.printStackTrace();
        }
    }
    
}
