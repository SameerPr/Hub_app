package com.example.nakkaanilkumar.outlab9;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    Button click;
    TextView textBox;
    public  static TextView data;
    public static ListView listview;

    private ProgressDialog pDialog;

    ArrayList<String> array_list= new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textBox = (TextView) findViewById(R.id.editText1);
        click = (Button) findViewById(R.id.search_button);
        data = findViewById(R.id.listView);
      //  listview = (ListView) findViewById(R.id.list);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchdata process = new fetchdata();
                process.execute();

            }
        });
    }

    public class fetchdata extends AsyncTask<Void,Void,Void> {
        String data ="";
        String dataParsed = "";
        String singleParsed ="";

        //@Override
        //protected void onPreExecute() {
        //    super.onPreExecute();
        //    displayProgressBar("Downloading...");
       //}
        //private void displayProgressBar(String s) {
        //}

        @Override
        protected Void doInBackground(Void... voids) {
            String username = textBox.getText().toString();
            try {
                URL baseurl = new URL("https://api.github.com/");
                URL url = new URL( baseurl + "search/users?" + "q=" + username + "&" + "sort=repositories" + "&" + "order=desc");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONObject Jsonobj = new JSONObject(data);
                JSONArray JA = Jsonobj.getJSONArray("items");
                for(int i =0 ;i <JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    singleParsed =  "Username: " + JO.get("login") + "\n";
                    dataParsed = dataParsed + singleParsed +"\n" ;
                    array_list.add(dataParsed);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
             //       android.R.layout.activity_list_item, array_list));

            //setListAdapter(mArrayAdapter)

            SearchActivity.data.setText(this.dataParsed);
            //listview.setAdapter(adapter);


        }
    }
}
