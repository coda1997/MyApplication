package com.example.a60440.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.example.a60440.myapplication.Timetable;

public class TimetableActivity extends AppCompatActivity {
    private RecyclerView recyclerView_one;
    private HomeAdapter mAdapter;
    private List<String> mDatas ;

    private String sessionid;
    private static String Tag_3 = "URL and sessionid";
    private String result;
    private String timeTableHtml;
    private Timetable timetable = new Timetable();

    private int numLesson = -1;
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = getIntent();
        //this is for inital
        sessionid = intent.getStringExtra("Sessionid");
        result = intent.getStringExtra("result");
        Log.i(Tag_3, result + "timetableHtml");



        //todo
        //mAdapter = new T


        getTimetbale();

        while (isStop != true) {

        }


        initData();
        System.out.println(mDatas.size());
        recyclerView_one = (RecyclerView) findViewById(R.id.id_recyclerview);
        recyclerView_one.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_one.setAdapter(mAdapter = new HomeAdapter());




    }


    private void getTimetbale() {
        //获取到课表的html finish
        Thread thread_get = new Thread() {
            public void run() {
                try {
                    URL timetableUrl = new URL(result);
                    HttpURLConnection connectionTimetable = (HttpURLConnection) timetableUrl.openConnection();
                    connectionTimetable.setRequestMethod("GET");
                    connectionTimetable.setRequestProperty("Cookie", sessionid);
                    connectionTimetable.connect();
                    if (connectionTimetable.getResponseCode() == 200) {
                        InputStream is = connectionTimetable.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        int len = 0;
                        byte buffer[] = new byte[1024];
                        StringBuilder content = new StringBuilder();

                        while ((len = is.read(buffer)) != -1) {
                            //   baos.write(buffer,0,len);
                            content.append(new String(buffer, 0, len, "gb2312"));
                        }
                        is.close();
                        baos.close();

                        timeTableHtml = new String(content.toString());




                        isStop = true;


                    } else {
                        Log.i(Tag_3, "connect fail");
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        thread_get.start();



    }
//    private void initData(){
//        for (int i=0;i<numLesson;i++){
//            timetable.setTimetable(i);
//            mDatas.add("课程名："+timetable.getLessonName()+"\n课程类型："+timetable.getPlanType()+"\n授课学院："
//                +timetable.getAcademicTeach()+"\n教师："+timetable.getTeacherName()+"\n学分："+timetable.getCredit()+"\n上课时间："+timetable.getDetail());
//
//        }
//  }
    private void initData(){
        mDatas = new ArrayList<String>();
        timetable.init(new String(timeTableHtml));
        numLesson = timetable.getNumTimetable();
//        timetable.setTimetable(0);
//        System.out.println(timetable.getLessonName());
//        System.out.println(numLesson);
        for (int i=0;i<numLesson;i++){
            timetable.setTimetable(i);
            //mDatas.add(timetable.getLessonName());
            mDatas.add("课程名："+timetable.getLessonName()+" 课程类型："+timetable.getPlanType()+"\n授课学院："
                +timetable.getAcademicTeach()+" 教师："+timetable.getTeacherName()+" 学分："+timetable.getCredit()+"\n上课时间："+timetable.getDetail());
            System.out.println(timetable.getLessonName());
        }

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    TimetableActivity.this).inflate(R.layout.items, parent,
                    false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }




}





