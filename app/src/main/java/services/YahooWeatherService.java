package services;

import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import data.Channel;

/**
 * Created by omar on 9/17/2015.
 */
public class YahooWeatherService {
    private YahooServiceCallBack callback;
    private String Location;
    private Exception error;

    public String getLocation() {
        return Location;
    }

    public YahooWeatherService(YahooServiceCallBack callback) {
        this.callback = callback;
    }

    public void refreshWeather(String l) {
        this.Location = l;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u ='c' ", params[0]);
                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url = new URL(endPoint);

                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line).append("\n");
                    }
                    return result.toString();

                } catch (Exception e) {
                    error = e;
                    // return  null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error != null) {
                    callback.servicefail(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");

                    if (count == 0) {
                        callback.servicefail(new LocationWeatherException("No weather information found for " + Location));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.servicesucsess(channel);

                } catch (JSONException e) {
                    callback.servicefail(e);
                }
            }
        }.execute(this.Location);
    }
    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }
}
