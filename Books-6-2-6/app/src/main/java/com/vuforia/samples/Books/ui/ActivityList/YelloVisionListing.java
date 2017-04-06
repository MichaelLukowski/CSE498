package com.vuforia.samples.Books.ui.ActivityList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.vuforia.VIEW;
import com.vuforia.samples.Books.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            ArrayList<String> qualifiedMajors = new ArrayList<String>();
            ArrayList<String> availablePositions = new ArrayList<String>();

            for (int i = 0; i < temparr.length(); i++) {
                JSONObject temp = temparr.getJSONObject(i);
                String company_name = temp.getString("name");

                JSONArray company_majors = new JSONArray(temp.getJSONArray("primary_majors").toString());
                for (int k = 0; k < company_majors.length(); k++)
                {
                    qualifiedMajors.add(company_majors.getString(k).toLowerCase());
                }

                JSONArray company_positions = new JSONArray(temp.getJSONArray("open_positions").toString());
                for (int k = 0; k < company_positions.length(); k++)
                {
                    availablePositions.add(company_positions.getString(k).toLowerCase());
                }

                Boolean isMatch = checkCompanyMatch(qualifiedMajors, availablePositions);

                companyArrayList.add(new Company(company_name, isMatch));

                qualifiedMajors.clear();
                availablePositions.clear();
            }
//                array.add(temp.getString("name"));

//            adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1,
//                    array);

            //Trying to get the text from list

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        companyAdapter=new CompanyAdapter(YelloVisionListing.this, companyArrayList);

        mListView.setAdapter(companyAdapter);

        mListView.setTextFilterEnabled(false);
        setupSearchView();

        /* enable clicks for items in list (companies in list)*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iClicked = ((TextView)view.findViewById(R.id.txtName)).getText().toString();
                //TextView v = (TextView) findViewById(R.id.txtName);

                Intent intent = new Intent(YelloVisionListing.this, CompanyDetails.class);
                Log.d("CREATION", iClicked);
                intent.putExtra("COMPANY_NAME", iClicked);

                startActivity(intent);
            }
        });
    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setIconified(true);
        mSearchView.setOnQueryTextListener(this);
//        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    private Boolean checkCompanyMatch(ArrayList<String> companyMajors,
                                      ArrayList<String> companyPositions) {
        try {

            FileInputStream fileIn = openFileInput("user.txt");
            InputStreamReader inputRead = new InputStreamReader(fileIn);
            BufferedReader buffReader = new BufferedReader(inputRead);

            String readStr = buffReader.readLine();
            StringBuffer majorsData = new StringBuffer("");
            StringBuffer positionsData = new StringBuffer("");

            String majors;
            String positions;

            int lineNumber = 0;

            while(readStr != null){
                if(lineNumber == 0){
                    majorsData.append(readStr);
                    lineNumber += 1;
                }else{
                    positionsData.append(readStr);
                }
                readStr = buffReader.readLine();
            }

            inputRead.close();

            majors = majorsData.toString().toLowerCase();
            positions = positionsData.toString().toLowerCase();

            List<String> majorsMatch = new ArrayList<String>(Arrays.asList(majors.split(",")));
            List<String> positionsMatch = new ArrayList<String>(Arrays.asList(positions.split(",")));

            for(int i = 0; i < majorsMatch.size(); i++){
                for(int j = 0; j < companyMajors.size(); j++){
                    if(majorsMatch.get(i).trim().equals(companyMajors.get(j).trim())){
                        return true;
                    }
                }
            }

            for(int i = 0; i < positionsMatch.size(); i++){
                for(int j = 0; j < companyPositions.size(); j++){
                    if(positionsMatch.get(i).trim().equals(companyPositions.get(j).trim())){
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
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
