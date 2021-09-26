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

    public String inputUnit, outputUnit, unitType;
    public Spinner inputSpinner, outputSpinner;
    public TextView tvSameUnitAlert, tvNoNumberAlert;

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
        Spinner unitTypeSpinner = (Spinner) findViewById(R.id.unitTypeChoice);
        inputSpinner = (Spinner) findViewById(R.id.inputChoices);
        outputSpinner = (Spinner) findViewById(R.id.outputChoices);

        //Unit type spinner
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.unit_types_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitTypeSpinner.setAdapter(adapter3);

        //changing the unit options based on the unit type chosen
        unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) parent.getChildAt(0)).setTextSize(20);

                unitType = parent.getItemAtPosition(pos).toString();
                if (unitType.equals("Length")) {
                    setSpinners(R.array.length_array);
                } else if (unitType.equals("Time")) {
                    setSpinners(R.array.time_array);
                } else if (unitType.equals("Mass")) {
                    setSpinners(R.array.mass_array);
                } else if (unitType.equals("Temperature")) {
                    setSpinners(R.array.temp_array);
                } else {
                    //do something
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do something
            }
        });

        //getting the input unit
        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                inputUnit = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //write error message
            }
        });

        //getting the output unit
        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                outputUnit = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //write error message
            }
        });

        //if random button is clicked
        Button btnRandom = findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomA, randomB;
                switch (unitType) {
                    //maybe split the inside
                    case "Length":
                        //getting 2 random units within the length array
                        randomA = randomUnit(getResources().getStringArray(R.array.length_array));
                        randomB = randomUnit(getResources().getStringArray(R.array.length_array));
                        //setting the spinners with the random units
                        inputSpinner.setSelection(randomA);
                        outputSpinner.setSelection(randomB);
                        break;
                    case "Mass":
                        //getting 2 random units within the mass array
                        randomA = randomUnit(getResources().getStringArray(R.array.mass_array));
                        randomB = randomUnit(getResources().getStringArray(R.array.mass_array));
                        //setting the spinners with the random units
                        inputSpinner.setSelection(randomA);
                        outputSpinner.setSelection(randomB);
                        break;
                    case "Time":
                        //getting 2 random units within the time array
                        randomA = randomUnit(getResources().getStringArray(R.array.time_array));
                        randomB = randomUnit(getResources().getStringArray(R.array.time_array));
                        //setting the spinners with the random units
                        inputSpinner.setSelection(randomA);
                        outputSpinner.setSelection(randomB);
                        break;
                    case "Temperature":
                        //getting 2 random units within the temperature array
                        randomA = randomUnit(getResources().getStringArray(R.array.temp_array));
                        randomB = randomUnit(getResources().getStringArray(R.array.temp_array));
                        //setting the spinners with the random units
                        inputSpinner.setSelection(randomA);
                        outputSpinner.setSelection(randomB);
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

    private void setSpinners(int array) {
        //setting input spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSpinner.setAdapter(adapter);

        //setting output spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(MainActivity.this,
                array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputSpinner.setAdapter(adapter1);
    }

    private int randomUnit(String[] array) {
        Random rand = new Random();
        //randomNo is redundant
        int randomNo = rand.nextInt(array.length);
//        String randomUnit = array[randomNo];
        return randomNo;
    }

    private boolean unitsSame() {
        //Showing a message when the user selects 2 same units
        if (inputUnit.equals(outputUnit)) {
            tvSameUnitAlert.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
}