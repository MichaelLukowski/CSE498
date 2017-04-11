package com.vuforia.samples.Books.ui.ActivityList;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Message;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vuforia.samples.Books.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.Object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.id.list;
import static android.R.id.message;

/**
 * Created by Michael on 3/28/2017.
 */

public class YelloVisionInfo extends Activity {

    private Context context;

    public YelloVisionInfo() { }

    private Button mSubmit;
    private Button mCancel;

    private EditText mMajor;
    private EditText mOccupation;

    private Spinner mMajorSpin;
    private Spinner mOccSpin;

    private String userMajor;
    private String userOcc;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        mMajorSpin = (Spinner) findViewById(R.id.spinnerMajor);
        mOccSpin = (Spinner) findViewById(R.id.spinnerOcc);

        mSubmit=(Button) findViewById(R.id.btnSubmit);
        mCancel=(Button) findViewById(R.id.btnCancel);
//        mMajor=(EditText) findViewById(R.id.editTextMajor);
//        mOccupation=(EditText) findViewById(R.id.editTextOcc);

        mSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                userMajor = mMajorSpin.getSelectedItem().toString();
                userOcc = mOccSpin.getSelectedItem().toString();
//                userMajor = mMajor.getText().toString();
//                userOcc = mOccupation.getText().toString();
                try {
                    saveInfo(userMajor, userOcc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                try {
                    FileInputStream fileIn=openFileInput("user.txt");
                    InputStreamReader InputRead= new InputStreamReader(fileIn);

                    char[] inputBuffer= new char[100];
                    String s="";
                    int charRead;

                    while ((charRead=InputRead.read(inputBuffer))>0) {
                        // char to string conversion
                        String readstring=String.copyValueOf(inputBuffer,0,charRead);
                        s +=readstring;
                    }
                    InputRead.close();
                    Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }


    public void saveInfo(String major, String occ) throws IOException {

        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("user.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(major);
            outputWriter.write("\n");
            outputWriter.write(occ);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeMessage(JsonWriter writer, String message) throws IOException {
        writer.beginObject();
        writer.name("text").value(message);
    }



}
