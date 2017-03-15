package example.com.foodcalculator;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private EditText editTextEntry;
    private Button buttonSearch;
    public static String foodEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidget();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                foodEntry = editTextEntry.getText().toString();
                FragmentManager fm = getSupportFragmentManager();
                if(fm.findFragmentByTag("FoodFragment")== null)
                    fm.beginTransaction()
                            .add(R.id.activity_main_framelayout_food_result, new FoodFragment(), "FoodFragment")
                            .commit();

            }
        });
    }

    private void wireWidget() {
        textViewTitle = (TextView)findViewById(R.id.activity_main_textview_title);
        editTextEntry = (EditText)findViewById(R.id.activity_main_edittext_entry);
        buttonSearch = (Button)findViewById(R.id.activity_main_button_search);
    }
}
