package au.edu.unsw.infs3634.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String inputUnit, outputUnit, unitType;
    private Spinner unitTypeSpinner, inputSpinner, outputSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText input = findViewById(R.id.inputNumber);

        unitTypeSpinner = findViewById(R.id.unitTypeChoice);
        inputSpinner = findViewById(R.id.inputChoices);
        outputSpinner = findViewById(R.id.outputChoices);

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
                    case "Mass":
                        setSpinners(R.array.mass_array, inputSpinner);
                        setSpinners(R.array.mass_array, outputSpinner);
                        break;
                    case "Temperature":
                        setSpinners(R.array.temp_array, inputSpinner);
                        setSpinners(R.array.temp_array, outputSpinner);
                        break;
                    default:
                        throw new RuntimeException("Cannot find unitType: " + unitType);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                showToast("Please select a unit type");
            }
        });

        //when individual units are selected, assign selected units
        inputSpinner.setOnItemSelectedListener(myListener);
        outputSpinner.setOnItemSelectedListener(myListener);

        //when random button is clicked
        Button btnRandom = findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    case "Temperature":
                        randomUnits = generateRandomUnits(getResources().getStringArray(R.array.temp_array));
                        setRandomUnits(randomUnits);
                        break;
                    default:
                        throw new RuntimeException("Cannot find unitType: " + unitType);
                }
            }
        });

        //when convert button is clicked
        Button btnConvert = findViewById(R.id.btnBack);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputValue = input.getText().toString();
                //user input validation - checking string is not empty
                if (inputValue != null && inputValue.length() > 0) {
                    try {
                        //user input validation - checking if input is a number
                        double num = Double.parseDouble(inputValue);
                        //user input validation - checking for length and mass, the input number is positive
                        if ((unitType.equals("Length") || unitType.equals("Mass")) && num < 0) {
                            showToast("Please enter a positive number");
                        }

                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("Input value", inputValue);
                        intent.putExtra("Unit type", unitType);
                        intent.putExtra("Input unit", inputUnit);
                        intent.putExtra("Output unit", outputUnit);

                        //checking if 2 same units are chosen e.g. km and km
                        if (!unitsSame()) {
                            startActivity(intent);
                        }
                    } catch (NumberFormatException ex) {
                        Log.e("INPUT ERROR", "Entered value is not a number");
                        showToast("Please enter a number");
                    }
                } else {
                    showToast("Please enter a number");
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

    AdapterView.OnItemSelectedListener myListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
            int spinnerId = parent.getId();

            if (spinnerId == R.id.inputChoices) {
                inputUnit = parent.getItemAtPosition(pos).toString();
            } else if (spinnerId == R.id.outputChoices) {
                outputUnit  = parent.getItemAtPosition(pos).toString();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            showToast("Please select a unit");
        }
    };

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
            showToast("Please select different units");
            return true;
        }
        return false;
    }

    private void showToast(String message) {
        Toast toast= Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}