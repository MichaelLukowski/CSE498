package com.vuforia.samples.Books.ui.ActivityList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.vuforia.samples.Books.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

import java.io.IOException;

import java.util.ArrayList;

/**
 * Created by cse498 on 2/19/17.
 */

public class YelloVisionListing extends ListActivity {


    public YelloVisionListing() throws IOException, JSONException {
    }

    public JSONArray readFile() throws java.io.IOException, org.json.JSONException{
        String json = null;



        InputStream is = getAssets().open("companies.json");



        int size = is.available();

        byte[] buffer = new byte[size];

        is.read(buffer);

        is.close();

        json = new String(buffer, "UTF-8");


        JSONObject jsonObject = new JSONObject(json);
        JSONArray jarr = new JSONArray(jsonObject.getJSONArray("companies").toString());

        return jarr;
    }

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS


    public ArrayList<String> getNames(JSONArray temparr) throws JSONException {

        return array;
    }

//    ArrayList<String> listItems= getNames(temparr);

    ArrayList<String> array = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        try {
            String json = null;


            InputStream is = getAssets().open("companies.json");


            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


            JSONObject jsonObject = new JSONObject(json);
            JSONArray temparr = new JSONArray(jsonObject.getJSONArray("companies").toString());

            for (int i = 0; i < temparr.length(); i++) {
                JSONObject temp = temparr.getJSONObject(i);
                array.add(temp.getString("name"));
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    array);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.listing);
        setListAdapter(adapter);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
////        String value = intent.getStringExtra("key");
//    }
}
