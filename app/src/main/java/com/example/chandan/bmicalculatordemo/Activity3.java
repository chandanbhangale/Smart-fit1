package com.example.chandan.bmicalculatordemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity3 extends AppCompatActivity {
    AdView adView;

    TextView tvAct3Data,tvAct3Normal,tvAct3Below,tvAct3Over,tvAct3Obese,tvIdeal;
    Button btnAct3Share,btnAct3Save,btnAct3Back;
    SharedPreferences sp;
    static MyDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

            db =new MyDatabase(this);
        MobileAds.initialize(this, "ca-app-pub-1693474949040365/5943145334");

        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // For coloring the string
        SpannableStringBuilder builder = new SpannableStringBuilder();
       // String normal = "";
     //   SpannableString redSpannable= new SpannableString(normal);
    //    redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, normal.length(), 0);
       // builder.append(redSpannable);
        tvAct3Data=(TextView)findViewById(R.id.tvAct3Data);
        tvIdeal=(TextView)findViewById(R.id.tvIdeal);
        tvAct3Normal=(TextView)findViewById(R.id.tvAct3Normal);
        tvAct3Below=(TextView)findViewById(R.id.tvAct3Below);
        tvAct3Over=(TextView)findViewById(R.id.tvAct3Over);
        tvAct3Obese=(TextView)findViewById(R.id.tvAct3Obese) ;
        btnAct3Back=(Button)findViewById(R.id.btnAct3Back);
        btnAct3Save=(Button)findViewById(R.id.btnAct3Save);
        btnAct3Share=(Button)findViewById(R.id.btnAct3Share);



        Intent i=getIntent();
        final String n3=i.getStringExtra("n3");
        final String bmi1=i.getStringExtra("bmi1");
        final String mtr=i.getStringExtra("mtr1");
        final double report= Double.parseDouble(bmi1);
        final double actwt= Double.parseDouble(n3);
        final double ht= Double.parseDouble(mtr);
        String report1 ="";
        if(report<=18.5){

            report1 = "Underweight";
            double wt=22*(ht*ht);
            String idelwt="Your ideal weight should be "+String.format("%.2f",wt)+" kg"+"\n"+"So gain weight "+String.format("%.2f", (wt-actwt))+" kg";
            tvIdeal.setText(idelwt);
            SpannableString redSpannable = new SpannableString(report1);
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, report1.length(), 0);
            String msg="Your BMI is "+String.format("%.2f", report)+ " and You are ";
            builder.append(msg);
            builder.append(redSpannable);
            tvAct3Below.setTextColor(Color.RED);
            tvAct3Data.setText(builder, TextView.BufferType.EDITABLE);


        }else if(report>18.5 && report<=25){
            report1 = "Normal";
            SpannableString redSpannable = new SpannableString(report1);
            redSpannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, report1.length(), 0);
            String msg="Your BMI is "+String.format("%.2f", report)+ " and You are ";
            builder.append(msg);
            builder.append(redSpannable);
            tvAct3Normal.setTextColor(Color.GREEN);
            tvAct3Data.setText(builder, TextView.BufferType.EDITABLE);


        }
        else if(report>25&& report<=30){
            report1 = "Overweight";
            double wt=23*(ht*ht);
            String idelwt="Your ideal weight should be "+String.format("%.2f",wt)+" kg"+"\n"+"So loose weight "+String.format("%.2f", (actwt-wt))+" kg";
            tvIdeal.setText(idelwt);
            SpannableString redSpannable = new SpannableString(report1);
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, report1.length(), 0);
            String msg="Your BMI is "+String.format("%.2f", report)+ " and You are ";
            builder.append(msg);
            builder.append(redSpannable);

            tvAct3Over.setTextColor(Color.RED);
            tvAct3Data.setText(builder, TextView.BufferType.EDITABLE);


        }
        else{
           report1 = "Obese";
            double wt=25*(ht*ht);
            String idelwt="Your ideal weight should be "+String.format("%.2f",wt)+" kg"+"\n"+" So loose weight "+String.format("%.2f", (actwt-wt))+" kg";
            tvIdeal.setText(idelwt);

            SpannableString redSpannable = new SpannableString(report1);
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, report1.length(), 0);
            String msg="Your BMI is "+String.format("%.2f", report)+ " and You are ";
            builder.append(msg);
            builder.append(redSpannable);
            tvAct3Data.setText(builder, TextView.BufferType.EDITABLE);
            tvAct3Obese.setTextColor(Color.RED);

        }
        final String report2=report1;

        btnAct3Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Activity3.this,Activity2.class);
                startActivity(i);
            }
        });

        btnAct3Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp= getSharedPreferences("p1",MODE_PRIVATE);
                String msg1=sp.getString("msg1","");
                msg1=msg1+"\n"+"BMI : "+String.format("%.2f", report)+"\n"+"You are "+report2;

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,msg1);
                startActivity(i);


            }
        });

        btnAct3Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wt= String.format("%.2f", report);

                Date d = new Date();
                Timestamp ts=new Timestamp(d.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
                String date1234=formatter.format(ts);
                String date123= d.toString();
                Activity3.db.addReport(date1234,wt,n3);



            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Activity3.this, Activity2.class);
        startActivity(i);
        finish();
    }
}
