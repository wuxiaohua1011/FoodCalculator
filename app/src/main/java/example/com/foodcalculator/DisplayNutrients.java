package example.com.foodcalculator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayNutrients extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_nutrients);
        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("NutrientFragment")== null)
            fm.beginTransaction()
                    .add(R.id.activity_display_nutrients_frameLayout, new NutrientFragment(), "NutrientFragment")
                    .commit();
        else{
            // TODO: 3/19/2017 revise this part so that multiple search can be done
            fm.beginTransaction()
                    .add(R.id.activity_display_nutrients_frameLayout, new NutrientFragment(), "NutrientFragment")
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Quit or do another search?");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));finish();
            }
        });
        alertDialog.show();
    }
}
