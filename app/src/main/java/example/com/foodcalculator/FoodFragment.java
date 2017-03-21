package example.com.foodcalculator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static example.com.foodcalculator.MainActivity.foodEntry;

/**
 * Created by micha on 3/15/2017.
 */

public class FoodFragment extends ListFragment{
    private List<Food> foodList;
    private String entry = foodEntry;
    private String TAG = "FoodFragment";
    private FoodAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        foodList = new ArrayList<>();
        populate();


        return rootView;
    }

    private void populate() {
//        Food food = new Food("S1","NoFoodFound","S1",1);
//        foodList.add(food);
        String fullUrl = "https://api.nal.usda.gov/ndb/search/?" +
                "format=json&" +
                "q=" + foodEntry+"&" +
                "sort=n&" +
                "max=25&" +
                "offset=0&" +
                "api_key=hFBevPIX4DbhPChcLwunQaqoUfPYwHGQxrRQlqQj ";
       new FoodSearch().execute(fullUrl);
    }

    private class FoodSearch extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            adapter = new FoodAdapter(getActivity(),foodList);
            setListAdapter(adapter);
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

                    while((line = reader.readLine()) != null) {
                        jsonString += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onCreate: IT CRASHED BECAUSE FILE NOT FOUND");
                }
                //All Data is in jsonString at this point
                // start casting data into JsonObject

                JSONObject jsonObject= new JSONObject(jsonString);
               // Log.d(TAG, "doInBackground: "+(jsonObject.optJSONObject("errors").optJSONArray("error").optJSONObject(0).optString("status")));
                if (jsonObject.optJSONObject("errors") != null)
                {
                    Food food = new Food("S1","NoFoodFound","S1",1);
                    foodList.add(food);
///                    adapter.notifyDataSetChanged();
                }

                else {
                    JSONArray jsonArray = jsonObject.optJSONObject("list").optJSONArray("item");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Food food = new Food();
                        JSONObject tempJsonObject = jsonArray.optJSONObject(i);
                        food.setGroup(tempJsonObject.optString("group"));
                        food.setName(tempJsonObject.optString("name"));
                        food.setNdbno(tempJsonObject.optString("ndbno"));
                        food.setOffset(tempJsonObject.optInt("offset"));
                        foodList.add(i, food);
                    }

                   // adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
