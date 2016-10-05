package com.example.a60440.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private Button button_login;
    private Button button_reset;
    private String resString = "";
    private EditText textView_name;
    private EditText textView_key;
    private EditText textxdvfb;
    private ImageView imageView;
    final static String Tag = "login";
    final static String Tag_2 = "html";
    private String timetableUrl;
    private boolean flag = false;
    private String[] sessionid;
    private String session;
    private String pwd;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_login = (Button) findViewById(R.id.button_login);
        textView_name = (EditText)findViewById(R.id.editText2);
        textView_key = (EditText) findViewById(R.id.editText);
        textxdvfb = (EditText)findViewById(R.id.editText3);
        imageView = (ImageView)findViewById(R.id.imageView);
        button_reset = (Button)findViewById(R.id.button_reset);
        new DownImgAsyncTask().execute("http://210.42.121.241/servlet/GenImg");
        flag = false;
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(button_login);
                //输入错误提醒
//                if(flag==true){
//                    Toast.makeText(MainActivity.this, "输入错误，请重试", Toast.LENGTH_SHORT).show();
//
//                }
                session = new String(sessionid[0]);
                new DownImgAsyncTask().execute("http://210.42.121.241/servlet/GenImg");


                        /* 新建一个Intent对象 */





            }

        });

        button_reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this,"验证码已刷新",Toast.LENGTH_SHORT).show();
                new DownImgAsyncTask().execute("http://210.42.121.241/servlet/GenImg");


            }


        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void login(View v){
        //Log.i(Tag,"error");
        flag = false;

        final String userName = textView_name.getText().toString();
        final String password = textView_key.getText().toString();
        final String xdvfb = textxdvfb.getText().toString();
        pwd = getMD5Str(password);

        if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "账号密码不能为空，请重试", Toast.LENGTH_LONG).show();
                }else {
                    Thread thread = new Thread() {
                        public void run(){
                            //Log.i(Tag,"error-1");

                            Log.i(Tag_2,pwd+"password");
                            loginByPost(userName, pwd,xdvfb);
                        }

                    };
                    thread.start();


        }
    }

    public void loginByPost(String userName,String password,String xdvfb){
        try {
            String address = "http://210.42.121.241/servlet/Login";
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);

            String data = "id=" + URLEncoder.encode(userName,"UTF-8")+"&pwd="+URLEncoder.encode(password,"UTF-8")+"&xdvfb="+URLEncoder.encode(xdvfb,"UTF-8");
        //    String data = "id=" + URLEncoder.encode(userName,"UTF-8")+"&pwd=b18367fcc44b765a20414c449eec506c"+"&xdvfb="+URLEncoder.encode(xdvfb,"UTF-8");
            Log.i(Tag_2,data);
            urlConnection.setRequestProperty("Proxy-Connection","keep-alive");
            urlConnection.setRequestProperty("Cache-Control","max-age=0");

            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Length",String.valueOf(data.getBytes().length));
        //    urlConnection.setRequestProperty("Content-Length",String.valueOf(64));
            // 设置请求的头
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
            //Log.i(Tag_2,sessionid[0]);


            urlConnection.setRequestProperty("Cookie",sessionid[0]);

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
/*
* 没有设置登录失败的情况
* */
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if(urlConnection.getResponseCode() == 200){
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                int len = 0;
                byte buffer[] = new byte[1024];
                StringBuilder content = new StringBuilder();

                while ((len = is.read(buffer))!=-1){
                //   baos.write(buffer,0,len);
                    content.append(new String(buffer, 0, len, "gb2312"));
                }
                is.close();
                baos.close();

                final String result = new String(content.toString());
                /*
                * 获取课表的iframe里面的html，设置sessionid和referer
                *
                *
                * */
                org.jsoup.nodes.Document doc = Jsoup.parse(result);
                //判断返回是否正确

                final String titleName = doc.title();
                Log.i(Tag_2,titleName);

                if(!titleName.equals("武汉大学教务管理系统")){
                    org.jsoup.nodes.Element titleEle = doc.getElementById("left_bar");
                    org.jsoup.nodes.Element frameEle = titleEle.getElementById("page_iframe");
                    String tableUrl = new String(frameEle.attr("src"));
                    System.out.println("succeed");
                    Log.i(Tag_2,"succeed");
                    //Log.i(Tag_2,result);
                    timetableUrl = new String("http://210.42.121.241"+tableUrl);
                    //Log.i(Tag_2,result);

                    Log.i(Tag_2,timetableUrl);
//                    try {
//                        URL resurl = null;
//                        resurl = new URL(timetableUrl);
//                        HttpURLConnection resurlConnection = (HttpURLConnection) resurl.openConnection();
//                        resurlConnection.setRequestMethod("POST");
//                        resurlConnection.setRequestProperty("Cookie",sessionid[0]);
//                        resurlConnection.setReadTimeout(5000);
//                        resurlConnection.setConnectTimeout(5000);
//                        resurlConnection.connect();
//                        if (resurlConnection.getResponseCode() == 200) {
//                            InputStream resis = resurlConnection.getInputStream();
//                            ByteArrayOutputStream resbaos = new ByteArrayOutputStream();
//
//                            int reslen = 0;
//                            byte resbuffer[] = new byte[1024];
//                            StringBuilder rescontent = new StringBuilder();
//
//                            while ((reslen = resis.read(resbuffer))!=-1){
//                                //   baos.write(buffer,0,len);
//                                rescontent.append(new String(resbuffer, 0, reslen, "gb2312"));
//                            }
//                            resis.close();
//                            resbaos.close();
//
//                            final String resresult = new String(rescontent.toString());
//                            Log.i(Tag_2,"result"+resresult);
//                        }else {
//                            Log.i(Tag_2,"disconneted");
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    //调用timetableActivity并传值
                    Thread thread_1 = new Thread(){
                        public void run(){
                            Intent intent = new Intent();
                            //intent.putExtra("timetableUrl",timetableUrl);
                            intent.putExtra("result",timetableUrl);
                            intent.putExtra("Sessionid",session);
                            /* 指定intent要启动的类 */
                            intent.setClass(MainActivity.this, TimetableActivity.class);
        /* 启动一个新的Activity */
                            MainActivity.this.startActivity(intent);
        /* 关闭当前的Activity */
                            MainActivity.this.finish();
                        }

                    };
                    thread_1.start();

                }else{
                    flag=true;
                }

            }else{
                System.out.println("unconnected...");
                Log.i("login","fail");
            }





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private Bitmap getImageBitmap(String url) {
        URL imgurl = null;
        Bitmap bitmap = null;

        HttpURLConnection urlConnection;
        try {
            imgurl = new URL(url);
            urlConnection = (HttpURLConnection)
                    imgurl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            String sessionid_value = urlConnection.getHeaderField("Set-Cookie");
            sessionid = sessionid_value.split(";");
            Log.i(Tag,sessionid[0]);
            InputStream is = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageView.setImageBitmap(null);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap b = getImageBitmap(params[0]);
            return b;
        }

    }

    private String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        //16位加密，从第9位到25位
        return md5StrBuff.toString().toLowerCase();
    }




}





