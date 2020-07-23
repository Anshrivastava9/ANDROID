package com.example.jsondemo;
//ca9f8ff49a8786475765d3b3535861f0
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;

public class MainActivity extends AppCompatActivity {

    EditText cityname;
    TextView textView;

    public void button(View view){
        InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityname.getWindowToken(),0);

        Downloadtask task=new Downloadtask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q="+cityname.getText().toString()+"&appid=ca9f8ff49a8786475765d3b3535861f0");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityname=(EditText)findViewById(R.id.text);
        textView=(TextView)findViewById(R.id.textView);
    }

    public class Downloadtask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;

            try {
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                int data=reader.read();

                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;

            } catch (Exception e) {
                //e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Enter Correct City Name", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try {
                String message="";
                JSONObject jsonObject=new JSONObject(result);
                JSONObject jsonObject1=new JSONObject(result);
                String weatherinfo=jsonObject.getString("weather");
                //String weatheradvanceinfo=jsonObject1.getString("main");
                JSONArray arr=new JSONArray(weatherinfo);
               // Toast.makeText(getApplicationContext(),weatheradvanceinfo,Toast.LENGTH_LONG).show();
                // fetch JSONObject named employee
                // set employee name and salary in TextView'


                for(int i=0;i<arr.length();i++){
                    JSONObject jsonpart=arr.getJSONObject(i);

                    String main="";
                    String description="";

                    main=jsonpart.getString("main");
                    description=jsonpart.getString("description");
                    if(main!="" && description!=""){
                        message+=(main+ ": "+description+"\r\n");
                    }
                }
                JSONObject employee = jsonObject1.getJSONObject("main");
                // get employee name and salary
                String t = employee.getString("temp");
                String p = employee.getString("pressure");
                String h=employee.getString("humidity");

                message+=("\r\n" + "Temp: "+ t+"\r\n"+"Humidity: "+h+"\r\n"+"Pressure: "+p);

                if(message!=""){
                    textView.setText(message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}