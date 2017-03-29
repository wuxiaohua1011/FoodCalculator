package example.com.foodcalculator;

import android.content.SharedPreferences;
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
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class NutrientFragment extends ListFragment {
    List<String> nutrientIdList;
    String ndbno;
    String TAG = "NutrientFragment: ";
    List<Nutrient> listOfNutrients = new ArrayList<>();
    private NutrientAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        populate();
        return rootView;
    }

    private void populate() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("nutrientIdList", MODE_PRIVATE);
        Set<String> stringSet = sharedPreferences.getStringSet("nutrientIdList", null);
        ndbno = sharedPreferences.getString("ndbno", null);

        nutrientIdList = new ArrayList<String>(stringSet);

        String baseURLf = "https://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=hFBevPIX4DbhPChcLwunQaqoUfPYwHGQxrRQlqQj";
        String baseURLl = "&ndbno=" + ndbno;
        String URLm = "";
        for (String nutrientId : nutrientIdList) {
            URLm = URLm + "&nutrients=" + nutrientId;
        }
        String finalURL = baseURLf + URLm + baseURLl;
        Log.d("URL: ", finalURL);
        new NutrientSearch().execute(finalURL);
    }
    private class NutrientSearch extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "size is: " + listOfNutrients.size());
            adapter = new NutrientAdapter(getActivity(),listOfNutrients);
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

                    while ((line = reader.readLine()) != null) {
                        jsonString += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onCreate: IT CRASHED BECAUSE FILE NOT FOUND");
                }

                Log.d(TAG, "doInBackground: " + jsonString);
                //All Data is in jsonString at this point
                // start casting data into JsonObject

                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.optJSONObject("report").optJSONArray("foods").optJSONObject(0).optJSONArray("nutrients");
                String jsonFoodName = jsonObject.optJSONObject("report").optJSONArray("foods").optJSONObject(0).optString("name");
                String jsonFoodMeasure = jsonObject.optJSONObject("report").optJSONArray("foods").optJSONObject(0).optString("measure");
                SharedPreferences.Editor editor = getContext().getApplicationContext().getSharedPreferences("FoodName",MODE_PRIVATE).edit();
                editor.putString("jsonFoodName", jsonFoodName);
                editor.putString("jsonFoodMeasure", jsonFoodMeasure);
                editor.commit();
                for (int i = 0; i < jsonArray.length(); i ++){
                    Nutrient nutrient = new Nutrient();
                    nutrient.setNutrient_id( jsonArray.optJSONObject(i).optString("nutrient_id"));
                    nutrient.setNutrient( jsonArray.optJSONObject(i).optString("nutrient"));
                    nutrient.setUnit( jsonArray.optJSONObject(i).optString("unit"));
                    nutrient.setValue( jsonArray.optJSONObject(i).optString("value"));
                    nutrient.setGm( jsonArray.optJSONObject(i).optString("gm"));

                    listOfNutrients.add(i,nutrient);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
