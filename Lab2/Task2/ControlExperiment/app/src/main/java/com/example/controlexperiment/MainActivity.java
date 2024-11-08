package com.example.controlexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Initialize UI elements
        EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        CheckBox checkBox = findViewById(R.id.checkBox);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Spinner spinner = findViewById(R.id.spinner);
// Set up spinner data
        String[] options = {"Option A", "Option B", "Option C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
// Button click listener
        button.setOnClickListener(v -> {
            String input = editText.getText().toString().trim();

            //Validate EditText input
            if(input.isEmpty()){
                editText.setError("Field cannot be empty");
                return;
            }

            boolean isChecked = checkBox.isChecked();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton =
                    findViewById(selectedId);
            String selectedRadioText = selectedRadioButton != null ?
                    selectedRadioButton.getText().toString() : "None";
            String selectedSpinner =
                    spinner.getSelectedItem().toString();
// Show a Toast message with the collected input
            Toast.makeText(this, "Input: " + input + "\nChecked: " +
                            isChecked +
                            "\nSelected Radio: " + selectedRadioText +
                            "\nSelected Spinner: " + selectedSpinner,
                    Toast.LENGTH_LONG).show();
        });
    }
}