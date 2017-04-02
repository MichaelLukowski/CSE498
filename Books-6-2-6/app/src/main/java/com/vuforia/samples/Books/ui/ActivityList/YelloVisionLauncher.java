/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2015 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/


package com.vuforia.samples.Books.ui.ActivityList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vuforia.samples.Books.R;

import static android.R.attr.value;

// This activity starts activities which demonstrate the YelloVision start menu
public class YelloVisionLauncher extends Activity
{
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yello_start_menu);
        //setListAdapter(adapter);
    }

    public void scanLogo(View view){
        Intent intent = new Intent(this, AboutScreen.class);
        intent.putExtra("ACTIVITY_TO_LAUNCH",
                "app.Books.Books");
        intent.putExtra("ABOUT_TEXT", "Books/CR_about.html");
        /*intent.putExtra("ACTIVITY_TO_LAUNCH",
                "app.ui.ActivityList.AboutScreen");
        intent.putExtra("ABOUT_TEXT", "Books/CR_about.html");*/
        startActivity(intent);
    }

    public void searchCompanies(View view){
        Intent myIntent = new Intent(this, YelloVisionListing.class);
//        myIntent.putExtra("key", value); //Optional parameters
        startActivity(myIntent);
    }

    public void userInfo(View view){
        Intent something = new Intent(this, YelloVisionInfo.class);
        startActivity(something);
    }
}
