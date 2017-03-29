package example.com.foodcalculator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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


/**
 * Created by micha on 3/15/2017.
 */

public class FoodAdapter extends ArrayAdapter<Food> {
    private String TAG = "foodAdapter";
    public FoodAdapter(Context context, List<Food> objects) {
        super(context, 0, objects);
    }
     Food food;
    ProgressDialog progressDialog;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_adapter, null);
        }
        food = getItem(position);
        TextView textViewNum = (TextView) convertView.findViewById(R.id.food_adapter_textview_item_number);
        TextView textViewName = (TextView) convertView.findViewById(R.id.food_adapter_textview_foodname);

        textViewNum.setText("#" + food.getOffset());
        textViewName.setText("Name: " + food.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseURLf = "https://api.nal.usda.gov/ndb/reports/?ndbno=";
                String baseURLl = "&type=b&format=json&api_key=hFBevPIX4DbhPChcLwunQaqoUfPYwHGQxrRQlqQj";
                String finalURL = baseURLf+ food.getNdbno() + baseURLl;


                new NutrientIdSearch().execute(finalURL);


            }
        });
        return convertView;
    }

    private class NutrientIdSearch extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            getContext().startActivity(new Intent(getContext(),DisplayNutrients.class));
        }

        @Override
        protected String doInBackground(String... urls) {
            //make a new URL object
            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();

                String jsonString = "";
                try {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        jsonString += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onCreate: IT CRASHED BECAUSE FILE NOT FOUND");
                }
                //All Data is in jsonString at this point
                // start casting data into JsonObject

                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray jsonArray = jsonObject.optJSONObject("report").optJSONObject("food").optJSONArray("nutrients");
                List<String> nutrientIdList = new ArrayList<>();
                if (jsonObject != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tempJsonObject = jsonArray.optJSONObject(i);
                        nutrientIdList.add(i,tempJsonObject.optString("nutrient_id"));
                    }
                }

                Set<String> temp = new HashSet<String>(nutrientIdList);
                SharedPreferences.Editor editor = getContext().getApplicationContext().getSharedPreferences("nutrientIdList",Context.MODE_PRIVATE).edit();
                editor.putStringSet("nutrientIdList", temp);
                editor.putString("ndbno",food.getNdbno());
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Todo add food name and amount here
            return null;
        }

    }
}