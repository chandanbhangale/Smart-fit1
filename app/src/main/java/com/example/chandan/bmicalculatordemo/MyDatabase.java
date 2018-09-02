package com.example.chandan.bmicalculatordemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Smart-Fit-Bros™ on 6/28/2018.
 */

public class MyDatabase extends SQLiteOpenHelper {

    Context context;
    public SQLiteDatabase db;
    MyDatabase(Context context){
        super(context,"bmidb",null,1);
        this.context= context;
        db= this.getWritableDatabase();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="create table bmidata(date1 text, "+ " bmi12 text, "+ " weight text)";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addReport(String date1, String bmi12,String weight){

        ContentValues cv =new ContentValues();
        cv.put("date1",date1);
        cv.put("bmi12",bmi12);
        cv.put("weight",weight);

        long rid =db.insert("bmidata",null,cv);
        if (rid<0)
            Toast.makeText(context, "Isuue", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

    }



    public String viewBmiHistory()
    {

        Cursor cursor =db.query("bmidata",null,null,null,null,null,null);
        StringBuffer sb =new StringBuffer();
        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do {
                sb.append("Date : "+ cursor.getString(0)+"\n"
                        + "BMI : "+ cursor.getString(1)+"\n"
                        +"Weight : "+ cursor.getString(2)+"\n"+"--------------------------"+"\n");
            }while (cursor.moveToNext());

        }
        return sb.toString();
    }

    public int viewBmiGraphCount() {

        Cursor cursor = db.query("bmidata", null, null, null, null, null, null);
        StringBuffer sb = new StringBuffer();
        cursor.moveToFirst();
        int count1=0;
        if (cursor.getCount() > 0)
            do {
                count1=count1+1;
            }while (cursor.moveToNext());
        return count1;
    }

    public String viewBmiGraph()
    {

        Cursor cursor =db.query("bmidata",null,null,null,null,null,null);
        StringBuffer sb =new StringBuffer();
        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do {
                sb.append(cursor.getString(1)+" ");
            }while (cursor.moveToNext());

        }
        return sb.toString();
    }




}
