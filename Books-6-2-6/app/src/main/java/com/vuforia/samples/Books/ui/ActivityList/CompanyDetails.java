package com.vuforia.samples.Books.ui.ActivityList;

/**
 * Created by Sakura on 2/19/2017.
 */
import com.vuforia.samples.Books.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
//import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CompanyDetails extends AppCompatActivity {

    private String mCompanyName;
    private String mCompanyUrl;
    private ImageView mCompanyImage;
    private TextView mCompanyLocation;
    private TextView mCompanyDescription;
    private TextView mCompanyMajors;
    private TextView mCompanyPositions;

    private ShareActionProvider mShareActionProvider;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_details_layout);

        Bundle extras = getIntent().getExtras();
        String nameString = extras.getString("COMPANY_NAME");

        Toolbar detailsToolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(detailsToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        mCompanyName = "";
//        mCompanyImage = (ImageView) findViewById(R.id.more_details_image);

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
                    mCompanyName = temp.getString("name");
                    new DownloadImageTask((ImageView) findViewById(R.id.more_details_image))
                            .execute(temp.getString("logo"));
//                    URL url = new URL(temp.getString("logo"));
//                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    mCompanyImage.setImageBitmap(bmp);
                    mCompanyLocation.setText(temp.getString("location"));
                    mCompanyDescription.setText(temp.getString("description"));
                    mCompanyUrl = temp.getString("url");

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

        actionBar.setTitle(mCompanyName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:

                String shareBodyText = "Career opportunity at " + mCompanyName + " in " +
                        mCompanyLocation.getText().toString() + "\n\nPrimary Majors: " +
                        mCompanyMajors.getText().toString() + "\n\nOpen Positions: " +
                        mCompanyPositions.getText().toString() + "\n\nFor more details visit: " + mCompanyUrl;

                Intent emailIntent = new Intent();
                emailIntent.setAction(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Career Opportunity at " + mCompanyName);
                emailIntent.setType("text/plain");

                PackageManager pm = getPackageManager();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                Intent inChooser = Intent.createChooser(emailIntent, "Choose sharing method..");
                List<ResolveInfo> resInfo = pm.queryIntentActivities(sharingIntent, 0);
                List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();

                for (int i = 0; i < resInfo.size(); i++) {
                    // Extract the label, append it, and repackage it in a LabeledIntent
                    ResolveInfo ri = resInfo.get(i);
                    String packageName = ri.activityInfo.packageName;
                    if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        if(packageName.contains("twitter")) {
                            String shareTwitterBodyText = "Career opportunity at " + mCompanyName + " in " +
                                    mCompanyLocation.getText().toString() + "\n\nFor more details visit: " + mCompanyUrl;
                            intent.putExtra(Intent.EXTRA_TEXT, shareTwitterBodyText);
                        } else if(packageName.contains("facebook")) {
                            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                        } else if(packageName.contains("mms")) {
                            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                        } else if(packageName.contains("outlook")) {
                            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Career Opportunity at " + mCompanyName);
                            intent.setType("message/rfc822");
                        } else if(packageName.contains("android.gm")){
                            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Career Opportunity at " + mCompanyName);
                            intent.setType("text/plain");
                        }


                        intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
                    }
                }

                // convert intentList to array
                LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

                inChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
                startActivity(inChooser);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
