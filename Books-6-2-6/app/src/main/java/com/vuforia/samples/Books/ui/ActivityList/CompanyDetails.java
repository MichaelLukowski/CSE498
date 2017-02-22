package com.vuforia.samples.Books.ui.ActivityList;

/**
 * Created by Sakura on 2/19/2017.
 */
import com.vuforia.samples.Books.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class CompanyDetails extends Activity {

    private TextView mCompanyName;
    private TextView mCompanyLocation;
    private TextView mCompanyDescription;
    private TextView mCompanyMajors;
    private TextView mCompanyPositions;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle extras = getIntent().getExtras();
        String nameString = extras.getString("COMPANY_NAME");

        setContentView(R.layout.more_details_layout);

        mCompanyName = (TextView) findViewById(R.id.more_details_company_name);
        mCompanyLocation = (TextView) findViewById(R.id.more_details_location);
        mCompanyDescription = (TextView) findViewById(R.id.more_details_description);
        mCompanyMajors = (TextView) findViewById(R.id.more_details_majors);
        mCompanyPositions = (TextView) findViewById(R.id.more_details_open_positions);

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
                if (temp.getString("name").equals(nameString)) {

//                    Log.d("CREATION", temp.getString("name"));
                    mCompanyName.setText(temp.getString("name"));
                    mCompanyLocation.setText(temp.getString("location"));
                    mCompanyDescription.setText(temp.getString("description"));

                    JSONArray majors = new JSONArray(temp.getJSONArray("primary_majors").toString());
                    String majorStr = "";
                    for (int k = 0; k < majors.length(); k++)
                    {
                        majorStr += majors.getString(k);
                        if (k != (majors.length() - 1) )
                            majorStr += ", ";
                    }
                    mCompanyMajors.setText(majorStr);

                    JSONArray openings = new JSONArray(temp.getJSONArray("open_positions").toString());
                    String openStr = "";
                    for (int k = 0; k < openings.length(); k++)
                    {
                        openStr += openings.getString(k);
                        if (k != (openings.length() - 1) )
                            openStr += ", ";
                    }
                    mCompanyPositions.setText(openStr);

                    break;
            }
        }


    } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
