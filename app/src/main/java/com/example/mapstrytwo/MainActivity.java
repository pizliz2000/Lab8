package com.example.mapstrytwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private String allJSon = "";
    private LatLng latLng;
    private LatLng latLng1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        final AutoCompleteTextView autoCompleteTextView=findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(MainActivity.this,android.R.layout.simple_list_item_1));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Address : ",autoCompleteTextView.getText().toString());
                latLng=getLatLngFromAddress(autoCompleteTextView.getText().toString());


            }
        });
        final AutoCompleteTextView autoCompleteTextView1=findViewById(R.id.autocomplete1);
        autoCompleteTextView1.setAdapter(new PlaceAutoSuggestAdapter(MainActivity.this,android.R.layout.simple_list_item_1));

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Address : ",autoCompleteTextView1.getText().toString());
                latLng1=getLatLngFromAddress(autoCompleteTextView1.getText().toString());
                Log.d("To", String.valueOf(latLng1));
            }
        });

        Button nextPage_btn=(Button)findViewById(R.id.nextPage);
        nextPage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putParcelable("from_position", latLng1);
                args.putParcelable("to_position", latLng);

                Intent intent=new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("bundle", args);
                startActivity(intent);
            }
        });

    }

    private LatLng getLatLngFromAddress(String address) {

//        address = address.replaceAll(", ", "+");
//        address = address.replaceAll(" ", "+");
//        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address
//                + "&key=AIzaSyDanAw2LwnwbLI4oBCWhFdodcLlqXLsLn0";
//       Log.d("URL here", url);
        Geocoder geocoder=new Geocoder(MainActivity.this);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList!=null){
                Address singleaddress=addressList.get(0);
                LatLng latLng=new LatLng(singleaddress.getLatitude(),singleaddress.getLongitude());
                return latLng;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

