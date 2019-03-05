package com.example.a12201027.interndcr;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    String URL="https://raw.githubusercontent.com/appinion-dev/intern-dcr-data/master/data.json";
    ArrayList<String> productGroup;
    ArrayList<String> literature;
    ArrayList<String> physicalSample;
    ArrayList<String> gift;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productGroup = new ArrayList<>();
        literature = new ArrayList<>();
        physicalSample = new ArrayList<>();
        gift = new ArrayList<>();


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);

        loadSpinnerData(URL);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String productGroup = spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(), productGroup, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }



     private void loadSpinnerData(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(DownloadManager.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("product_group_list")=="product_group_list"){
                        JSONArray jsonArray=jsonObject.getJSONArray("product_group_list");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String product=jsonObject1.getString("product_group");
                            productGroup.add(product);
                        }
                    }
                    if(jsonObject.getString("literature_list")=="literature_list"){
                        JSONArray jsonArray=jsonObject.getJSONArray("literature_list");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String literatureName=jsonObject1.getString("literature");
                            literature.add(literatureName);
                        }
                    }
                    if(jsonObject.getString("physician_sample_list")=="physician_sample_list"){
                        JSONArray jsonArray=jsonObject.getJSONArray("physician_sample_list");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String sample=jsonObject1.getString("sample");
                            physicalSample.add(sample);
                    }
                    if(jsonObject.getString("gift_list")=="gift_list"){
                        JSONArray jsonArray=jsonObject.getJSONArray("gift_list");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String giftName=jsonObject1.getString("gift");
                            gift.add(giftName);
                        }
                    }
                    spinner1.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, productGroup));
                    spinner2.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, literature));
                    spinner3.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, physicalSample));
                    spinner4.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, gift));
                }
                catch (JSONException e)
                    {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
}

