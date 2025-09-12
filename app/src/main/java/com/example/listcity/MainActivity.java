package com.example.listcity;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




// Need to add functionality to add/remove countries from the  current List

public class MainActivity extends AppCompatActivity {



   private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private EditText inputCity;
    private int selectedPosition = -1;
    private Button addButton, deleteButton, confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        inputCity = findViewById(R.id.city_input);
        addButton = findViewById(R.id.ButtonA);
        deleteButton = findViewById(R.id.ButtonB);
        confirmButton = findViewById(R.id.confirm_button);



        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney","Berlin", "Vienna", "Tokyo","Beijing", "Osaka","New Delhi"};
        dataList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        addButton.setEnabled(true);
        deleteButton.setEnabled(false);

        confirmButton.setOnClickListener( v -> {
            String newCity = inputCity.getText().toString().trim();
            if (!TextUtils.isEmpty(newCity)) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
                inputCity.setText(""); // Clear the input field
                inputCity.setVisibility(View.GONE); // Hide the input field
                confirmButton.setEnabled(false); // Disable "CONFIRM" button
                addButton.setEnabled(true); // Re-enable the initial "ADD CITY" button
                hideKeyboard();
                Toast.makeText(this, newCity + " added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a city name first", Toast.LENGTH_SHORT).show();
            }

        });


        addButton.setOnClickListener( v -> {

            inputCity.setVisibility(View.VISIBLE); // Show the input field
            confirmButton.setEnabled(true);
            addButton.setEnabled(false); // Disable "ADD CITY" button after it's pressed
            Toast.makeText(this, "Type a city and press 'CONFIRM'", Toast.LENGTH_SHORT).show();
        });

        deleteButton.setOnClickListener( v -> {
            if (selectedPosition != -1) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1; // Reset selection
                deleteButton.setEnabled(false);
                Toast.makeText(this, "City deleted", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            } else {
                Toast.makeText(this, "Please select a city to delete", Toast.LENGTH_SHORT).show();
            }
        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            deleteButton.setEnabled(true);
            Toast.makeText(this, dataList.get(position) + " selected", Toast.LENGTH_SHORT).show();
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    }