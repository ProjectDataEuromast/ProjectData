package data.project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.json.JSONException;

public class weatherAPICurrent {
    public float rain;
    public double tempmax;
    public double  tempmin;
   // public String weatherlocation;
    //public float weathertime;
    //public float weatherdate;
    
 public  void run(){
    try {
    // declaring object of "OpenWeatherMap" class
    OpenWeatherMap owm = new OpenWeatherMap("Rotterdam");
    // getting current weather data for the "Inserted" city
    CurrentWeather cwd = owm.currentWeatherByCityCode(2747891);
    // printing city name from the retrieved data
    System.out.println("City: " + cwd.getCityName());
    // printing the max./min. temperature
    System.out.println("Temperature Max/Min: "
        + (Math.round(((cwd.getMainInstance().getMaxTemperature() - 32)*5/9 )*100)/100.0) + "/"
        + (Math.round(((cwd.getMainInstance().getMinTemperature() - 32)*5/9 )*100)/100.0) + "\'C");
 
    System.out.println("Current Temperature: "
        +  Math.round(((cwd.getMainInstance().getTemperature() - 32)*5/9 )*100)/100.0 + "'C");
  
    System.out.println("Rain Prediction next hour:"
            +   cwd.getRainInstance().getRain1h() + "mm");
    
     rain = cwd.getRainInstance().getRain1h();
     tempmax = (Math.round(((cwd.getMainInstance().getMaxTemperature() - 32)*5/9 )*100)/100.0);
     tempmin = (Math.round(((cwd.getMainInstance().getMinTemperature() - 32)*5/9 )*100)/100.0) ;
     //weatherlocation = "Rotterdam";
     //weathertime = ;
     //weatherdate =;
    
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        String user = "kimberley";
        String pass = "euromast";
        String url = "jdbc:mysql://localhost:3306/projectdb";
        
            //1. Get connection to database
            myConn = DriverManager.getConnection(url, user, pass);            
            //2. Create a statement
            myStmt = myConn.createStatement();
            //3. Execute SQL query
            
            //rain query
            String rainmax = "insert into weather_rain"
                    + " (rain_max) "
                    + " values ("+rain+")";
            myStmt.executeUpdate(rainmax);
            
            //temperature max and min query
            String tempamax = "insert into weather_temp"
                    + " (temp_max, temp_min) "
                    + " values ("+tempmax+", "+tempmin+")";
            myStmt.executeUpdate(tempamax);
            
            //weather location query
           /* String wlocation = "insert into weather_temp"
                    + " (weather_location, weather_time, weather_date) "
                    + " values ("+tempmax+", "+tempmin+", "+tempmin+" )";
            myStmt.executeUpdate(wlocation);*/
            
            
            myRs = myStmt.executeQuery("select * from weather_rain");
            
            //4. Process the result set
            while(myRs.next()){
            //System.out.println(myRs.getString("RainInstance"));       
            }
            
        }
        catch(Exception exc){
            System.out.println(exc);
            exc.printStackTrace();
        }
}
}