package com.savannapeguins.droid.rwjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static final String URL="https://api.reliefweb.int/v1/references/career-categories";
    private static final String TAG ="MainActivity" ;
    private static final String URL2 ="https://jsonplaceholder.typicode.com/todos/1" ;
    private static final String puppy_="http://www.recipepuppy.com/api/";
    private static final String URL_OMDP="http://www.omdbapi.com/?i=tt3896198&apikey=1af79b10";
    private TextView tvResults;
    private Button parse,bOpen;
    private RequestQueue queue;
   private static final String URL_Yahoo="http://www.accuweather.com/en/ke/eldoret/225630/daily-weather-forecast/225630?day=5&lang=en-us";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     tvResults=(TextView)findViewById(R.id.mTextViewResult);
     parse=(Button)findViewById(R.id.bParse);
        queue= Volley.newRequestQueue(this);
        bOpen=(Button)findViewById(R.id.btnOpen);
        parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //parseData();
                //jsonPlaceHolder();
                //onaApi();
              omdpJson();
            }
        });

        bOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PageViewActivity.class));
            }
        });

    }

    private void omdpJson() {
        String URL="http://www.omdbapi.com/?i=tt3896198&apikey=1af79b10";
        JsonObjectRequest movieObject=new JsonObjectRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("Object: ",response.getString("Title").toString());
                   // JSONObject jsonObject=response.getJSONObject("Metascore");
                    for(int i=0;i<response.length();i++ ){
                        JSONObject jsonObject=response.getJSONObject(String.valueOf(i));

                    }

                   Log.d("Object :",response.getString("Year").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /**iterates through jsonObject**/
                  /*  for (Iterator<String>iter=response.keys();iter.hasNext();){
                            String code=iter.next();
                            VolleyLog.d("O");
                           tvResults.append(code);
                               //--tvResults.setText("Response: " + response.toString());
                  }
*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(movieObject);
    }

    //******ona api data******************
    public void onaApi(){
      JsonObjectRequest reciepepuppyObject=new JsonObjectRequest(Request.Method.GET,URL_Yahoo
              , null, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
             /* VolleyLog.d("PuppyObject:",response.toString());
              tvResults.setText("Results:"+ response.toString());*/

              try {
                  JSONArray jsonArray=response.getJSONArray("forecast");
                  for (int i=0;i<response.length();i++){
                      JSONObject jsonObject=jsonArray.getJSONObject(i);

                      String High=jsonObject.getString("text");
                      //String ingredients=jsonObject.getString("ingredients");
                      VolleyLog.d("code:",response.toString());
                      tvResults.append(High);
                  }
              } catch (JSONException e) {
                  e.printStackTrace();
              }

          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });

      queue.add(reciepepuppyObject);
    }

    //******end here**********************

    //******json parsing********
    public void jsonPlaceHolder(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, puppy_,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.d("Placeholder:",response.toString());
                tvResults.setText("Results " +response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }

    //***end parsing****************


    public void parseData(){


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("data");
                    for (int i=0; i<jsonArray.length();i++){
                        /* JSONObject rwObject=response.getJSONObject();*/
                        JSONObject dataObject=jsonArray.getJSONObject(i);
                        String id=dataObject.getString("id");
                       // String fields=dataObject.getString("fields");
                       // String desc=dataObject.getString("");

                        //Log.d("Response:",response.getString("data").toString());
                        //Log.d("Response:",dataObject.getString("name"));
                        //tvResults.append(id);
                        Log.d("Response:",dataObject.getString("id"));
                        tvResults.append(id);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
