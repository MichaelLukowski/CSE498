package com.vuforia.samples.Books.ui.ActivityList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vuforia.samples.Books.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.Object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static android.R.id.message;

/**
 * Created by Michael on 3/28/2017.
 */

public class YelloVisionInfo extends Activity {

    public YelloVisionInfo() { }

    private Button mSubmit;
    private Button mCancel;

    private EditText mMajor;
    private EditText mOccupation;

    private String userMajor;
    private String userOcc;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        mSubmit=(Button) findViewById(R.id.btnSubmit);
        mCancel=(Button) findViewById(R.id.btnCancel);
        mMajor=(EditText) findViewById(R.id.editTextMajor);
        mOccupation=(EditText) findViewById(R.id.editTextOcc);

        mSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                userMajor = mMajor.getText().toString();
                userOcc = mOccupation.getText().toString();
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
                finish();
            }
        });
    }

    public void saveInfo(String major, String occ) throws IOException {

        ArrayList<String> things = new ArrayList<String>();
        things.add(major);
        things.add(occ);
        FileOutputStream outputStream;
        outputStream = openFileOutput("user.json", Context.MODE_PRIVATE);

        writeJsonStream(outputStream,things);
    }

    public void writeJsonStream(OutputStream out, ArrayList<String> messages) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeMessagesArray(writer, messages);
        writer.close();
    }

    public void writeMessagesArray(JsonWriter writer, ArrayList<String> messages) throws IOException {
        writer.beginArray();
        for (String message : messages) {
            writeMessage(writer, message);
        }
        writer.endArray();
    }

    public void writeMessage(JsonWriter writer, String message) throws IOException {
        writer.beginObject();
        writer.name("text").value(message);
    }




}
