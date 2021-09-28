package au.edu.unsw.infs3634.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String inputUnit, outputUnit, unitType, selectedItem;
    private Spinner unitTypeSpinner, inputSpinner, outputSpinner;
    private TextView tvSameUnitAlert, tvNoNumberAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText input = (EditText)findViewById(R.id.inputNumber);
        tvSameUnitAlert = findViewById(R.id.tvSameUnitAlert);
        tvSameUnitAlert.setVisibility(View.INVISIBLE);
        tvNoNumberAlert = findViewById(R.id.tvNoNumberAlert);
        tvNoNumberAlert.setVisibility(View.INVISIBLE);

        //spinner code adapted from https://developer.android.com/guide/topics/ui/controls/spinner
        unitTypeSpinner = (Spinner) findViewById(R.id.unitTypeChoice);
        inputSpinner = (Spinner) findViewById(R.id.inputChoices);
        outputSpinner = (Spinner) findViewById(R.id.outputChoices);

        //changing the unit options based on the unit type chosen
        setSpinners(R.array.unit_types_array, unitTypeSpinner);
        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) parent.getChildAt(0)).setTextSize(20);

                unitType = parent.getItemAtPosition(pos).toString();
                switch (unitType) {
                    case "Length":
                        setSpinners(R.array.length_array, inputSpinner);
                        setSpinners(R.array.length_array, outputSpinner);
                        break;
                    case "Time":
                        setSpinners(R.array.time_array, inputSpinner);
                        setSpinners(R.array.time_array, outputSpinner);
                        break;
                    case "Mass":
                        setSpinners(R.array.mass_array, inputSpinner);
                        setSpinners(R.array.mass_array, outputSpinner);
                        break;
                    case "Temperature":
                        setSpinners(R.array.temp_array, inputSpinner);
                        setSpinners(R.array.temp_array, outputSpinner);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do something
            }
        });

        //when individual units are selected, assign to variables
        inputSpinner.setOnItemSelectedListener(myListener);
        outputSpinner.setOnItemSelectedListener(myListener);

        //if random button is clicked
        Button btnRandom = findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomA, randomB;
                int[] randomUnits;
                switch (unitType) {
                    case "Length":
                        randomUnits = generateRandomUnits(getResources().getStringArray(R.array.length_array));
                        setRandomUnits(randomUnits);
                        break;
                    case "Mass":
                        randomUnits = generateRandomUnits(getResources().getStringArray(R.array.mass_array));
                        setRandomUnits(randomUnits);
                        break;
                    case "Time":
                        randomUnits = generateRandomUnits(getResources().getStringArray(R.array.time_array));
                        setRandomUnits(randomUnits);
                        break;
                    case "Temperature":
                        randomUnits = generateRandomUnits(getResources().getStringArray(R.array.temp_array));
                        setRandomUnits(randomUnits);
                        break;
                }
            }
        });

        //if convert button is clicked
        Button btnConvert = findViewById(R.id.btnBack);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSameUnitAlert.setVisibility(View.INVISIBLE);
                try {
                    //Checking if input is a number
                    Double number = Double.parseDouble(input.getText().toString());

                    tvNoNumberAlert.setVisibility(View.INVISIBLE);
                    String value = input.getText().toString();
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("Input value", value);
                    intent.putExtra("Unit type", unitType);
                    intent.putExtra("Input unit", inputUnit);
                    intent.putExtra("Output unit", outputUnit);
                    //Checking if 2 same units are chosen e.g. km and km
                    if (!unitsSame()) {
                        startActivity(intent);
                    }
                } catch (NumberFormatException ex) {
                    Log.e("INPUT ERROR", "Entered value is not a number");
                    tvNoNumberAlert.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setSpinners(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private int[] generateRandomUnits(String[] array) {
        int[] randomUnits = new int[2];
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            randomUnits[i] = rand.nextInt(array.length);
        }
        return randomUnits;
    }

    private void setRandomUnits(int[] randomUnits) {
        inputSpinner.setSelection(randomUnits[0]);
        outputSpinner.setSelection(randomUnits[1]);
    }

    private boolean unitsSame() {
        //Showing a message when the user selects 2 same units
        if (inputUnit.equals(outputUnit)) {
            tvSameUnitAlert.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    AdapterView.OnItemSelectedListener myListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
            int spinnerId = parent.getId();
            switch(spinnerId) {
                case R.id.inputChoices:
                    inputUnit = parent.getItemAtPosition(pos).toString();
                case R.id.outputChoices:
                    outputUnit  = parent.getItemAtPosition(pos).toString();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //write error message
        }
    };

}