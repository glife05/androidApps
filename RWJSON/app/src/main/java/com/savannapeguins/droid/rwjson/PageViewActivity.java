package com.savannapeguins.droid.rwjson;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarException;

public class PageViewActivity extends AppCompatActivity {
private RecyclerView mRecyclerView;
private RecyclerView.Adapter mAdapter;
private List<Forcasts>forcastsList;
private DividerItemDecoration dividerItemDecoration;

private RequestQueue mQueue;
//private static final String URL="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22london%22)&format=json";
private static final String URL_Yahoo="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22london%22)&format=json";
private static final String URL_TEST="https://api.myjson.com/bins/kp9wz";
    String URL_EQ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);

        mQueue= Volley.newRequestQueue(this);
        mRecyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);

        forcastsList=new ArrayList<>();

        mAdapter=new RecyclerViewPage(getApplicationContext(),forcastsList);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//???
        dividerItemDecoration=new DividerItemDecoration(mRecyclerView.getContext(),mLayoutManager.getOrientation());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
       //getForcastsList();
      getData();
      //  flatJson();//>>working

      //passData();
       // earthQuake();


    }

    private void earthQuake(){
        String URL_EQ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
        JsonObjectRequest jsonObjectRequestEQ=new JsonObjectRequest(Request.Method.GET, URL_EQ,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {


                    Log.d("EQ",response.getString("type").toString());
                    //response it the main object
                    JSONObject metadata=response.getJSONObject("metadata");
                    Log.d("Metadata:",metadata.toString());
                    Forcasts forcasts=new Forcasts();
                    forcasts.setVarDay(metadata.getString("generated"));
                    forcasts.setVarDate(metadata.getString("title"));
                    forcasts.setCount(metadata.getInt("status"));
                    forcastsList.add(forcasts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(jsonObjectRequestEQ);
    }


    private void passData(){

        String URL="http://www.omdbapi.com/?i=tt3896198&apikey=1af79b10";
        JsonObjectRequest omdbapi=new JsonObjectRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Object: ",response.getString("Title").toString());
                    for(int i=0;i<response.length();i++ ){
                        //JSONObject jsonObject=response.getJSONObject("Title");
                        JSONObject jsonObject=new JSONObject(String.valueOf(response));
                        Forcasts forcasts=new Forcasts();

                        forcasts.setVarDay(jsonObject.getString("Title"));
                        forcasts.setVarDate(jsonObject.getString("Year"));
                        forcastsList.add(forcasts);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(omdbapi);

    }
    private void flatJson(){
        final String URL="http://www.omdbapi.com/?i=tt3896198&apikey=1af79b10";
        final JsonObjectRequest movieRequest=new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /*working JsonArray*/

                try {
                    JSONArray movieArray = response.getJSONArray("Ratings");

                    for (int i=0;i<response.length();i++){
                        JSONObject forObj=movieArray.getJSONObject(i);

                        Forcasts forcasts=new Forcasts();

                        forcasts.setVarDay(forObj.getString("Source"));
                        forcasts.setVarDate(forObj.getString("Value"));
                        forcastsList.add(forcasts);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(movieRequest);
    }
    private void getData() {
       /* final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/

       final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,URL_EQ , null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {


               try {

                    //response is the main object
                   Log.d("Type:",response.getString("type").toString());//<<gets the whole object and converts it toString
                   //get object inside an object
                   JSONObject metadata=response.getJSONObject("metadata");
                   Log.d("Metadata",metadata.toString());
                   Log.d("MetaInfo:",metadata.getString("generated"));//get inside metadata object and get informations
                   Log.d("MetaUrl",metadata.getString("url"));
                   Log.d("MetaTitle",metadata.getString("title"));//>>getting info ends here

                   //getJSONARRAY feature
                   JSONArray features=response.getJSONArray("features");//gets the whole array
                   Log.d("Feature",features.toString());//>>logs the whole jsonArray
                   //---get objects inside the features array
                   for (int i=0;i<features.length();i++){
                       //get Jsonbjects
                       JSONObject typeObject=features.getJSONObject(i);

                       Log.d("FeatureType",typeObject.getString("type").toString());//<<gets the whole object toString
                       //get object inside an object
                       JSONObject typeObj=features.getJSONObject(i).getJSONObject("properties");//gets the obj nested inside
                       Log.d("NestedType",typeObj.toString());//gets the whole nested obj
                       Log.d("InsideNestedObj Mag:",typeObj.getString("mag").toString());//gets values inside the NestedType
                       Log.d("InsideNestedObj Place:",typeObj.getString("place").toString());
                       Log.d("InsideNestedObj Time: ",typeObj.getString("time").toString());//ends here
                       Forcasts forcasts=new Forcasts();
//                       forcasts.setCount(Integer.parseInt(typeObj.getString("mag")));
                       forcasts.setVarDate(typeObj.getString("mag"));
                       forcasts.setVarDay(typeObj.getString("place"));
                       forcastsList.add(forcasts);

                       JSONObject geometry=features.getJSONObject(i).getJSONObject("geometry"); //gets the obj
                       Log.d("Geometry :",geometry.toString());//gets the whole object
                       Log.d("InsideGemtryObj Type:",geometry.getString("type").toString());//
                       //get inside coordinates array nested inside Geometry
                       JSONArray objGeo=features.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");//get inside Geometry--coord
                       Log.d("Coordinates: ",objGeo.toString());//

                   }

                    } catch (JSONException e) {
                   e.printStackTrace();


               }

               mAdapter.notifyDataSetChanged();

                   /*for(int i=0;i<response.length();i++){
                       JSONObject jsonObject=response.getJSONObject(String.valueOf(response));
                       Forcasts forcasts=new Forcasts();
                       forcasts.setVarDate(jsonObject.getString(""));

                       forcastsList.add(forcasts);*/


               //mAdapter.notifyDataSetChanged();
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });
        mQueue.add(jsonObjectRequest);

       /* JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL_Yahoo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            for(int i=0;i<response.length();i++){


                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        Forcasts forcasts=new Forcasts();
                        forcasts.setVarDate(jsonObject.getString("high"));
                        forcasts.setVarDay(jsonObject.getString("low"));
                        forcastsList.add(forcasts);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }


                }
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley",error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);*/
    }

    //***get Forecasts*****************************************************************************
    public List<Forcasts>getForcastsList(){
//        forcastsList.clear();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                URL_TEST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("employees");
                    //loop through
                    for(int i=0;i<response.length();i++){
                        JSONObject forObj=jsonArray.getJSONObject(i);

                        Forcasts forcasts=new Forcasts();


                        forcasts.setVarDate(forObj.getString("firstname"));
                        forcasts.setVarDay(forObj.getString("mail"));

                        Log.d("Employees: ",forcasts.getVarDate());
                        Log.d("Employees: ",forcasts.getVarDay());

                        forcastsList.add(forcasts);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PageViewActivity.this, "Error retrieving data..", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(jsonObjectRequest);
        return forcastsList;
    }






    //****ends here*********************************************************************************

}
