package com.example.trains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText s;
    EditText d;
   public void find(View view){
        InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(d.getWindowToken(),0);

        Downloadtask task=new Downloadtask();
        task.execute("https://indianrailapi.com/api/v2/TrainBetweenStation/apikey/63b455a61f31bc70fd355bf099fbc741/From/"+s.getText().toString()+"/To/"+d.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s=(EditText)findViewById(R.id.s);
        d=(EditText)findViewById(R.id.d);
        listView=(ListView)findViewById(R.id.listView);
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
        protected void onPostExecute(String result) {
           super.onPostExecute(result);

           try {
               ArrayList<String> timestableContent = new ArrayList<String>();
               JSONObject jsonObject = new JSONObject(result);
               String train = jsonObject.getString("Trains");
               //String weatheradvanceinfo=jsonObject1.getString("main");
               JSONArray arr = new JSONArray(train);
               // Toast.makeText(getApplicationContext(),weatheradvanceinfo,Toast.LENGTH_LONG).show();
               // fetch JSONObject named employee
               // set employee name and salary in TextView'

               for (int i = 0; i < arr.length(); i++) {
                   JSONObject jsonpart = arr.getJSONObject(i);

                   String number = "";
                   String name = "";
                   String SA="";
                   String SD="";


                   number = jsonpart.getString("TrainNo");
                   name = jsonpart.getString("TrainName");
                   SA = jsonpart.getString("ArrivalTime");
                   SD = jsonpart.getString("DepartureTime");
                   if (name != "" && number != "" && SA!="" && SD!="") {
                       timestableContent.add("\r\n"+"Train Number: " + number +"\r\n"+ "Train Name: " + name+"\r\n"+"Arrival Time: "+SA+"\r\n"+"Departure: Time: "+SD+"\r\n");
                   }
               }

               ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, timestableContent);
               listView.setAdapter(arrayAdapter);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }}  }


