package com.example.a60440.myapplication;

import java.util.ArrayList;

/**
 * Created by 60440 on 2016/10/3.
 */

public class Timetable {

    private static ArrayList<String> lessons = new ArrayList<String>();
    private static String lessonName = "";//课程名
    private static String beginWeek;
    private static String endWeek;
    private static String beginTime;
    private static String endTime;
    private static String classRoom;
    private static String detail;//详细信息
    private static String teacherName;
    private static String credit;//学分
    private static String academicTeach;//教授院系
    private static String planType;//选修or必修
    private static int numTimetable;

    public static void init(String timetableHtml) {

        String timetable;
        int indexofBeginTimetable;
        int indexofEndTimetable;
        int numLessons = 0;



        indexofBeginTimetable = timetableHtml.indexOf("lessonName");
        timetable = timetableHtml.substring(indexofBeginTimetable+10);
        indexofEndTimetable = timetable.indexOf("lessonName");
        while(indexofBeginTimetable!=-1||numLessons==0){
            numLessons++;
            //每四个lesson第一个为需要的string
            if(numLessons%4==1){
                lessons.add(timetable.substring(0,indexofEndTimetable));
            }
            indexofBeginTimetable = timetable.indexOf("lessonName");
            timetable = timetable.substring(indexofBeginTimetable+10);
            indexofEndTimetable = timetable.indexOf("lessonName");

        }
        numTimetable = lessons.size();
//		System.out.println(indexofBeginTimetable);
//		System.out.println(indexofEndTimetable);


//			System.out.println(lessons.get(0));
//to set all element



    }


    public static void setTimetable(int index){
        String lesson = lessons.get(index);
        int beginIndex;
        int endIndex;
        beginIndex = lesson.indexOf("=");
        lesson = lesson.substring(beginIndex+1);
        endIndex = lesson.indexOf(";");
        lessonName = lesson.substring(2,endIndex-1);
//	    System.out.println(lessonName);

        beginIndex = lesson.indexOf("beginWeek");
        lesson = lesson.substring(beginIndex+13);
        endIndex = lesson.indexOf(";");
        beginWeek = lesson.substring(0,endIndex-1);
//	    System.out.println(beginWeek);

        beginIndex = lesson.indexOf("endWeek");
        lesson = lesson.substring(beginIndex+11);
        endIndex = lesson.indexOf(";");
        endWeek = lesson.substring(0,endIndex-1);
//	    System.out.println(endWeek);

        beginIndex = lesson.indexOf("beginTime");
        lesson = lesson.substring(beginIndex+13);
        endIndex = lesson.indexOf(";");
        beginTime = lesson.substring(0,endIndex-1);
//	    System.out.println(beginTime);

        beginIndex = lesson.indexOf("endTime");
        lesson = lesson.substring(beginIndex+11);
        endIndex = lesson.indexOf(";");
        endTime = lesson.substring(0,endIndex-1);
//	    System.out.println(endTime);

        beginIndex = lesson.indexOf("detail");
        lesson = lesson.substring(beginIndex+8);
        endIndex = lesson.indexOf("课程");
        detail = lesson.substring(0,endIndex-4);
//	    System.out.println(detail);
//	    System.out.println(endIndex);


        beginIndex = lesson.indexOf("classRoom");
        lesson = lesson.substring(beginIndex+13);
        endIndex = lesson.indexOf(";");
        classRoom = lesson.substring(0,endIndex-1);
        //System.out.println(classRoom);

        beginIndex = lesson.indexOf("teacherName");
        lesson = lesson.substring(beginIndex+15);
        endIndex = lesson.indexOf(";");
        teacherName = lesson.substring(0,endIndex-1);
        //  System.out.println(teacherName);

        beginIndex = lesson.indexOf("planType");
        lesson = lesson.substring(beginIndex+12);
        endIndex = lesson.indexOf(";");
        planType= lesson.substring(0,endIndex-1);
        //    System.out.println(planType);

        beginIndex = lesson.indexOf("credit");
        lesson = lesson.substring(beginIndex+10);
        endIndex = lesson.indexOf(";");
        credit= lesson.substring(0,endIndex-1);
//	    System.out.println(credit);

        beginIndex = lesson.indexOf("academicTeach");
        lesson = lesson.substring(beginIndex+17);
        endIndex = lesson.indexOf(";");
        academicTeach= lesson.substring(0,endIndex-1);
//	    System.out.println(academicTeach);


    }
//    private static String lessonName;//课程名
//    private static String beginWeek;
//    private static String endWeek;
//    private static String beginTime;
//    private static String endTime;
//    private static String classRoom;
//    private static String detail;//详细信息
//    private static String teacherName;
//    private static String credit;//学分
//    private static String academicTeach;//教授院系
//    private static String planType;//选修or必修
    public String getLessonName(){
        return lessonName;
    }
    public String getBeginWeek(){
        return beginWeek;
    }
    public String getEndWeek(){
        return endWeek;
    }
    public String getBeginTime(){
        return beginTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public String getClassRoom(){
        return classRoom;
    }
    public String getDetail(){
        return detail;
    }
    public String getTeacherName(){
        return teacherName;
    }
    public String getCredit(){
        return credit;
    }
    public String getAcademicTeach(){
        return academicTeach;
    }
    public String getPlanType(){
        return planType;
    }
    public int getNumTimetable(){return numTimetable;}
}
