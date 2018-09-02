package com.example.chandan.bmicalculatordemo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Activity2 extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    TextView tvData, tvTemp, tvCity, tvWarn;
    SharedPreferences sp;
    GoogleApiClient gac;
    Location loc;
    EditText etWeight;
    Spinner spnInch, spnFeet;
    Button btnCalculateBmi;

    static MyDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        BottomNavigationView bottomnav = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomnav.setOnNavigationItemSelectedListener(navListner);
        db = new MyDatabase(this);

        tvData = (TextView) findViewById(R.id.tvData);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvWarn = (TextView) findViewById(R.id.tvWarn);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        btnCalculateBmi = (Button) findViewById(R.id.btnCalculateBmi);
        etWeight = (EditText) findViewById(R.id.etWeight);


        final ArrayList<String> feet = new ArrayList<>();
        feet.add("Select");
        feet.add("1");
        feet.add("2");
        feet.add("3");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        feet.add("8");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, feet);
        spnFeet.setAdapter(adapter);

        final ArrayList<String> inch = new ArrayList<>();
        inch.add("Select");
        inch.add("0");
        inch.add("1");
        inch.add("2");
        inch.add("3");
        inch.add("4");
        inch.add("5");
        inch.add("6");
        inch.add("7");
        inch.add("8");
        inch.add("9");
        inch.add("10");
        inch.add("11");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, inch);
        spnInch.setAdapter(adapter1);


        sp = getSharedPreferences("p1", MODE_PRIVATE);
        String n1 = sp.getString("n", "");
        Scanner scan = new Scanner(n1);
        scan.useDelimiter(" ");
        String n2 = scan.next();
        String msg = "Welcome " + n2;
        tvData.setText(msg);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        gac = builder.build();


        btnCalculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n3 = etWeight.getText().toString();
                if (n3.length() == 0) {
                    etWeight.setError("Please enter weight");
                    etWeight.requestFocus();
                    return;
                }
                double n4 = Double.parseDouble(n3);
                if (n4 > 999 || n4 == 0) {

                    etWeight.setError("Please enter a valid weight");
                    etWeight.requestFocus();
                    return;


                } else if (spnFeet.getSelectedItemPosition() == 0 || spnInch.getSelectedItemPosition() == 0) {

                    Toast.makeText(Activity2.this, "Please Select height", Toast.LENGTH_SHORT).show();

                } else {
                    int ft1 = spnFeet.getSelectedItemPosition();
                    int ft2 = ft1;
                    int in1 = spnInch.getSelectedItemPosition();
                    int in2 = in1;
                    double mtr = ((ft2 * 0.3048) + (in2 * 0.0254));

                    double bmi = (n4 / (mtr * mtr));
                    String bmi1 = new String(String.valueOf(bmi));
                    String mtr1 = new String(String.valueOf(mtr));
                    Intent i = new Intent(Activity2.this, Activity3.class);
                    i.putExtra("n3", n3);
                    i.putExtra("bmi1", bmi1);
                    i.putExtra("mtr1", mtr1);
                    startActivity(i);

                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.facebook.com"));
            startActivity(i);


        }

        if (item.getItemId() == R.id.contact) {
            Intent d = new Intent(Intent.ACTION_DIAL);
            d.setData(Uri.parse("tel:" + "8424055568"));
            startActivity(d);

        }
        if (item.getItemId() == R.id.about) {
            Toast toast = Toast.makeText(Activity2.this, "App developed by Smart-Fit-Bros™", Toast.LENGTH_SHORT);
            View view1 = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter

            view1.getBackground().setColorFilter(Color.rgb(1, 185, 245), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
            TextView text = (TextView) view1.findViewById(android.R.id.message);
            text.setTextColor(Color.rgb(1, 43, 116));

            toast.show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (gac != null)
            gac.connect(); //iska matlab tu client ban gaya idar is tum check kr rhe ho connection

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gac != null) gac.disconnect();

    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc != null)
        {
            double lat= loc.getLatitude();
            double lon= loc.getLongitude();

            Geocoder g= new Geocoder(this, Locale.ENGLISH);

            try {
                List<Address> la= g.getFromLocation(lat,lon,1);
                android.location.Address add= la.get(0);
                String msg= add.getLocality();

                tvCity.setText(msg);

                //To get current temprature

                String c= msg;
                String url="http://api.openweathermap.org/";
                String sp= "data/2.5/weather?units=metric";
                String qu="&q="+c;
                String id="bcc9e2b6e81b2b5477e685a90fc77bb3";
                String m=url+sp+qu+"&appid="+id;

                Mytask t=new Mytask();
                t.execute(m);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            Toast.makeText(this, "Unable to get your City name", Toast.LENGTH_SHORT).show();

        }


    }

    class Mytask extends AsyncTask<String,Void,Double> {

        @Override
        protected Double doInBackground(String... strings) {
            double temp=0.0;
            String line="",json="";
            try
            {
                URL url= new URL(strings[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr= new InputStreamReader(con.getInputStream());
                BufferedReader br= new BufferedReader(isr);

                while ((line = br.readLine() )!= null){

                    json= json+line+"\n";
                }

                JSONObject o =new JSONObject(json);
                JSONObject p =o.getJSONObject("main");
                temp = p.getDouble("temp");

            }

            catch (Exception e){


            }
            return  temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText(aDouble+"\u00b0"+"C");

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner= new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.chat:
                            Intent vw=new Intent(Activity2.this,ChatAct.class);
                            startActivity(vw);
                            break;
                        case R.id.viewhistory:
                            Intent in = new Intent(Activity2.this, ViewActivity.class);
                            startActivity(in);
                            break;
                        case R.id.graphview:
                            Intent i = new Intent(Activity2.this, graphAct.class);
                            startActivity(i);
                            break;
                    }
                    return true;
                }
            };

    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Do Want to Exit app? ");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog a = builder.create();
        a.setTitle("Exit");
        a.show();
    }

}
