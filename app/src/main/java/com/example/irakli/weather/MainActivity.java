package com.example.irakli.weather;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.irakli.weather.adapter.WeatherAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModelArray = new ArrayList<>();

    final String DEGREE  = "\u00b0";
    final String CELSIUS = "\u2103";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TabHost th = (TabHost) findViewById(R.id.tabHost);
        th.setup();

        TabHost.TabSpec spec = th.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("NOW");
        th.addTab(spec);

        spec = th.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("DAILY");
        th.addTab(spec);

        // Change tab title color
        for (int i = 0; i < th.getTabWidget().getTabCount(); i++) {
            TextView tv = (TextView) th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#E0EEEE"));
        }


        TextView v = (TextView) findViewById(R.id.time);
        final String date = new SimpleDateFormat("MMM/dd/EEE").format(new Date());
        v.setText(date);


         // Json object request
         requestQueue = Volley.newRequestQueue(getApplicationContext());
         JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.openweathermap.org/data/2.5/forecast/daily?q=tbilisi&mode=Json&units=metric&cnt=14&appid=f550bc7203a1aa922c37ed40539b9186", "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray listArray = response.getJSONArray("list");
                            JSONObject listArrayItem = (JSONObject) listArray.get(0);
                            JSONObject tempObj = listArrayItem.getJSONObject("temp");
                            int dayTemperature = tempObj.getInt("day");
                            int nightTemperature = tempObj.getInt("night");

                            int windSpeed = listArrayItem.getInt("speed");
                            int windDirection = listArrayItem.getInt("deg");
                            int clouds = listArrayItem.getInt("clouds");
                            int pressure = listArrayItem.getInt("pressure");
                            int humidity = listArrayItem.getInt("humidity");

                            JSONArray weatherArray = listArrayItem.getJSONArray("weather");
                            JSONObject weatherArrayItem = (JSONObject) weatherArray.get(0);
                            //int weatherId = weatherArrayItem.getInt("id");
                            //String main = weatherArrayItem.getString("main");
                            String description = weatherArrayItem.getString("description");
                            //String weatherIcon = weatherArrayItem.getString("icon");


                            // Set values in user interface
                            TextView mainWeatherView = (TextView) findViewById(R.id.mainWeather);
                            mainWeatherView.setText(dayTemperature + CELSIUS);

                            TextView dayView = (TextView) findViewById(R.id.day);
                            dayView.setText("Day    " + dayTemperature + DEGREE);

                            TextView nightView = (TextView) findViewById(R.id.night);
                            nightView.setText("Night " + nightTemperature + DEGREE);

                            TextView descriptionView = (TextView) findViewById(R.id.weatherDescription);
                            descriptionView.setText(description.substring(0,1).toUpperCase() + description.substring(1));

                            TextView windSpeedView = (TextView) findViewById(R.id.windSpeed);
                            windSpeedView.setText("Wind speed " + windSpeed + "m/s");

                            TextView windDirectionView = (TextView) findViewById(R.id.windDirection);
                            windDirectionView.setText("Wind direction " + windDirection + DEGREE);

                            TextView cloudinessView = (TextView) findViewById(R.id.cloudiness);
                            cloudinessView.setText("Cloudiness " + clouds + "%");

                            TextView humidityView = (TextView) findViewById(R.id.humidity);
                            humidityView.setText("Humidity " + humidity + "%");

                            TextView pressureView = (TextView) findViewById(R.id.pressure);
                            pressureView.setText("Pressure "+ pressure + "hpa");

                            for (int i = 0; i < 14; i++) {
                                JSONObject listArrayItem2 = (JSONObject) listArray.get(i);
                                JSONObject tempObj2 = listArrayItem2.getJSONObject("temp");
                                long newTime = listArrayItem2.getInt("dt");

                                SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/EEE");
                                String dt = sdf.format(new Date(newTime*1000));
                                int dayTemperature2 = tempObj2.getInt("day");
                                int nightTemperature2 = tempObj2.getInt("night");

                                JSONArray weatherArray2 = listArrayItem2.getJSONArray("weather");
                                JSONObject weatherArrayItem2 = (JSONObject) weatherArray2.get(0);
                                String description2 = weatherArrayItem2.getString("description");
                                String weatherIcon2 = weatherArrayItem2.getString("icon");

                                DataModel model = new DataModel(dayTemperature2, nightTemperature2, description2, weatherIcon2, dt);
                                dataModelArray.add(model);
                            }

                            WeatherAdapter adapter = new WeatherAdapter(dataModelArray, getApplicationContext());
                            ListView listView = (ListView) findViewById(R.id.listView);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Please try again, no internet connection.", Toast.LENGTH_LONG).show();
                    }
        });

        // Json object request for sunrise and sunset
        JsonObjectRequest newJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.openweathermap.org/data/2.5/weather?q=tbilisi&mode=Json&units=metric&cnt=7&appid=f550bc7203a1aa922c37ed40539b9186", "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject sysObj = response.getJSONObject("sys");
                            long sunrise = sysObj.getLong("sunrise");
                            long sunset = sysObj.getLong("sunset");

                            SimpleDateFormat df = new SimpleDateFormat("HH:MM");
                            String convertedSunriseTime = df.format(new Date(sunrise*1000));
                            String convertedSunsetTime = df.format(new Date(sunset*1000));

                            TextView sunriseView = (TextView) findViewById(R.id.sunrise);
                            sunriseView.setText("sunrise  " + convertedSunriseTime);

                            TextView sunsetView = (TextView) findViewById(R.id.sunset);
                            sunsetView.setText("sunset   " + convertedSunsetTime);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        });


        requestQueue.add(objectRequest);
        requestQueue.add(newJsonObjectRequest);
    }
}

