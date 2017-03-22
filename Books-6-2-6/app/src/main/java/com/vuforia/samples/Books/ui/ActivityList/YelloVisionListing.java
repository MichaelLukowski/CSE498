package com.vuforia.samples.Books.ui.ActivityList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Filter;

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

public class YelloVisionListing extends Activity implements SearchView.OnQueryTextListener{

    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<Company> companyArrayList;
    private CompanyAdapter companyAdapter;
    private Filter filter;



    public YelloVisionListing() throws IOException, JSONException { }


    ArrayList<String> array = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);
        mSearchView=(SearchView) findViewById(R.id.searchView1);
        mListView=(ListView) findViewById(R.id.listView1);


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

            companyArrayList = new ArrayList<Company>();


            for (int i = 0; i < temparr.length(); i++) {
                JSONObject temp = temparr.getJSONObject(i);
                companyArrayList.add(new Company(temp.getString("name")));
//                array.add(temp.getString("name"));
            }
//            adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1,
//                    array);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        companyAdapter=new CompanyAdapter(YelloVisionListing.this, companyArrayList);

        mListView.setAdapter(companyAdapter);

        mListView.setTextFilterEnabled(false);
        setupSearchView();

    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setIconified(true);
        mSearchView.setOnQueryTextListener(this);
//        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }


    @Override
    public boolean onQueryTextChange(String newText)
    {
        filter = companyAdapter.getFilter();

        filter.filter(newText);
//        if (TextUtils.isEmpty(newText)) {
//            mListView.clearTextFilter();
//        } else {
//            mListView.setFilterText(newText);
//        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }
}
