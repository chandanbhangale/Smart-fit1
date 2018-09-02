package com.example.chandan.bmicalculatordemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText etName,etAge,etPhone;
    Button btnRegister;
    RadioGroup rgGender;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=(EditText)findViewById(R.id.etName);
        etAge=(EditText)findViewById(R.id.etAge);
        etPhone=(EditText)findViewById(R.id.etPhone);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);


        sp=getSharedPreferences("p1",MODE_PRIVATE);


        String n=sp.getString("n","");
        if(n.length()!=0)
        {
            Intent i= new Intent(MainActivity.this, Activity2.class);
            startActivity(i);
            finish();

        }
        else{

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id= rgGender.getCheckedRadioButtonId();


                    String n= etName.getText().toString();
                if(n.length()==0||n.length()<3)
                {
                    etName.setError("Name is empty");
                    etName.requestFocus();
                    return;

                }
                String m= etAge.getText().toString();
                    if(m.length()==0||m.length()>3)
                {
                    etAge.setError("Age is empty");
                    etAge.requestFocus();
                    return;

                }
                String p= etPhone.getText().toString();
                 if(p.length()==0||p.length()!=10) {
                     etPhone.setError("Phone number is empty");
                     etPhone.requestFocus();
                     return;

                 }
                if (id==-1)


                {
                  Toast toast=  Toast.makeText(MainActivity.this, "Please select any one gender", Toast.LENGTH_SHORT);
                    View view1 = toast.getView();

                    view1.getBackground().setColorFilter(Color.rgb(1,185,245), PorterDuff.Mode.SRC_IN);

                    TextView text = (TextView) view1.findViewById(android.R.id.message);
                    text.setTextColor(Color.rgb(1,43,116));

                    toast.show();

                }
                else {
                    RadioButton rb = (RadioButton) rgGender.findViewById(id);
                    String gender = rb.getText().toString();

                String msg1= "Name : "+n+"\n"+"Age : "+m+"\n"+"Phone : "+p+"\n"+"Gender : "+gender;
                SharedPreferences.Editor editor= sp.edit();
                editor.putString("n",n);
                editor.putString("m",m);
                editor.putString("p",p);
                editor.putString("gender",gender);
                editor.putString("msg1",msg1);
                editor.commit();
                Intent i= new Intent(MainActivity.this, Activity2.class);
                Intent j= new Intent(MainActivity.this, Activity3.class);
                startActivity(j);
                startActivity(i);
                finish();




            }}
        });





    }
}}
