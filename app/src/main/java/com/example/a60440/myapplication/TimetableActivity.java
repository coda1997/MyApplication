package com.example.a60440.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class TimetableActivity extends AppCompatActivity {
    private TextView textUrl;


    private String timetableUrl;
    private String sessionid;
    private static String Tag_3 = "URL and sessionid";
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        textUrl = (TextView) findViewById(R.id.text_Url);
        Intent intent = getIntent();
        //timetableUrl = intent.getStringExtra("timetableUrl");
        sessionid = intent.getStringExtra("Sessionid");
        result = intent.getStringExtra("result");
        //Log.i(Tag_3, result + sessionid);
        textUrl.setText(result);





//                InputStream is = urlConnection.getInputStream();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                int len = 0;
//                byte buffer[] = new byte[1024];
//                StringBuilder content = new StringBuilder();
//
//                while ((len = is.read(buffer)) != -1) {
//                    //   baos.write(buffer,0,len);
//                    content.append(new String(buffer, 0, len, "gb2312"));
//                }
//                is.close();
//                baos.close();

//                final String result = new String(content.toString());
//                textUrl.setText(result);



    }
}
