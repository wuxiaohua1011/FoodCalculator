package example.com.foodcalculator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by micha on 3/15/2017.
 */

public class FoodAdapter extends ArrayAdapter<Food>{
    public FoodAdapter(Context context, List<Food> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_adapter, null);
        }
       final Food food = getItem(position);
        TextView textViewNum = (TextView)convertView.findViewById(R.id.food_adapter_textview_item_number);
        TextView textViewName = (TextView)convertView.findViewById(R.id.food_adapter_textview_foodname);

        textViewNum.setText("#" + food.getOffset());
        textViewName.setText("Name: " + food.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Nutrient.class);
                intent.putExtra("nbdno",food.getNdbno());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
