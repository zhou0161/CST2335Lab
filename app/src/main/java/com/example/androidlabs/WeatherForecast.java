package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.HttpURLConnection;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecast extends AppCompatActivity {

    public static final String WACTIVITY = "WEATHER_ACTIVITY";
    private ProgressBar weatherProgBar;
    private TextView currentTemperature, minTemperature, maxTemperature, uvRate;
    private ImageView currentWeatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

        WeatherQuery runQuery = new WeatherQuery();
        runQuery.execute(queryURL);

        weatherProgBar = findViewById(R.id.weatherProgressBar);
        currentTemperature = findViewById(R.id.currTemp);
        minTemperature = findViewById(R.id.minTemp);
        maxTemperature = findViewById(R.id.maxTemp);
        currentWeatherIcon = findViewById(R.id.weatherIcon);
        uvRate = findViewById(R.id.uvRating);

    }

    private class WeatherQuery extends AsyncTask<String, Integer, String> {

        private String maxTemp, minTemp, currentTemp, uvRating, iconName;
        private Bitmap image = null;

        @Override
        protected String doInBackground(String... strings) {
            String ret = null;


            try{    //Connect to the server:
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //set up the XML parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                //Iterate over the XML tags:
                int EVENT_TYPE;     //while not end of the document:
                while((EVENT_TYPE = xpp.getEventType())!= XmlPullParser.END_DOCUMENT){
                    switch (EVENT_TYPE){
                        case START_TAG:
                            String tagName = xpp.getName();//What kind of tag?
                            if(tagName.equals("temperature")){
                                maxTemp = xpp.getAttributeValue(null,"max");
                                Log.i("AsyncTask","Found max temperature: "+maxTemp);
                                publishProgress(25);

                                minTemp = xpp.getAttributeValue(null,"min");
                                Log.i("AsyncTask","Found minimum temperature: "+minTemp);
                                publishProgress(50);

                                currentTemp = xpp.getAttributeValue(null,"value");
                                Log.i("AsyncTask","Found current temperature: "+currentTemp);
                                publishProgress(75);
                            }
                            else if (tagName.equals("weather")){
                                iconName = xpp.getAttributeValue(null,"icon");
                                Log.i("AsyncTask","Found icon name: "+iconName);
                                publishProgress(80);
                            }
                            break;
                        case END_TAG:
                            break;
                        case TEXT:
                            break;
                    }
                    xpp.next();
                }


                // Weather Icon

                if(fileExistance(iconName+".png")){
                    Log.i(WACTIVITY,"looking for file" + iconName+".png");
                    Log.i(WACTIVITY, "Image found locally");
                    File file = getBaseContext().getFileStreamPath(iconName+".png");
                    FileInputStream fis = null;
                    try {    fis = openFileInput(iconName + ".png");   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    image = BitmapFactory.decodeStream(fis);
                }else{
                    Log.i(WACTIVITY,"looking for file" + iconName+".png");
                    Log.i(WACTIVITY, "Image does not found, new image downloaded");
                    url = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                    }
                    FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }

                //JSON
                URL uvUrl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection uvConnection = (HttpURLConnection) uvUrl.openConnection();
                inStream = uvConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"UTF-8"),8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while((line = reader.readLine())!=null){
                    sb.append(line+"\n");
                }
                String result = sb.toString();

                JSONObject jsonObject = new JSONObject(result);
                Double jObj = jsonObject.getDouble("value");
                uvRating = jObj.toString();
                Log.i("AsyncTask","Found UV Rating: "+uvRating);
                publishProgress(100);


            } catch (MalformedURLException e) {
                ret = "Malformed URL exception";
            } catch (IOException e) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (XmlPullParserException e) {
                ret = "XML Pull exception. The XML is not properly formed" ;
            } catch (JSONException e) {
                ret = "JSON Exception.";
            }
            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }

        @Override                   //Type 3
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            //update GUI Stuff:
            currentWeatherIcon.setImageBitmap(image);
            currentTemperature.setText("Current Temperature: "+currentTemp + "°C");
            minTemperature.setText("Minimum Temperature: " + minTemp + "°C");
            maxTemperature.setText("Maximum Temperature: " + maxTemp + "°C");
            uvRate.setText("UV Rating: " + uvRating);
            weatherProgBar.setVisibility(View.INVISIBLE);
        }

        @Override                       //Type 2
        protected void onProgressUpdate(Integer... values) {
            weatherProgBar.setVisibility(View.VISIBLE);
            weatherProgBar.setProgress(values[0]);
            //Update GUI stuff only:

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }
    }
}
