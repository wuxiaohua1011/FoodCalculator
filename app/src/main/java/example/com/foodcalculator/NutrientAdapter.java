package example.com.foodcalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NutrientAdapter extends ArrayAdapter<Nutrient> {

    public NutrientAdapter(Context context, List<Nutrient> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_nutrient_adapter, null);
        }

        TextView name,id,unit,value;
        name = (TextView)convertView.findViewById(R.id.activity_nutrient_adapter_textview_nutrient_name);
        id  = (TextView)convertView.findViewById(R.id.activity_nutrient_adapter_textview_nutrient_id);
        unit = (TextView)convertView.findViewById(R.id.activity_nutrient_adapter_textview_unit);
        value  = (TextView)convertView.findViewById(R.id.activity_nutrient_adapter_textview_value);


        Nutrient nutrient = getItem(position);
        name.setText(nutrient.getNutrient());
        id.setText("Id: " + nutrient.getNutrient_id());
        unit.setText(nutrient.getUnit());
        value.setText(nutrient.getValue());

        Log.d("NutrientAdapter", nutrient.getNutrient());
        return convertView;
    }
}
