package au.edu.unsw.infs3634.unitconverter;


import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ResultActivity extends AppCompatActivity {

    private ToggleButton twodp, fourdp, sixdp;
    private Boolean twodpClicked = true, fourdpClicked = false, sixdpClicked = false;
    private Double result;
    private TextView tvInputUnit, tvOutputUnit, tvInput, tvOutput, tvFormula;
    private String input, unitType, inputUnit, outputUnit, formattedResult;
    int animateCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //receive input from MainActivity
        Bundle bundle = getIntent().getExtras();
        input = bundle.getString("Input value");
        unitType = bundle.getString("Unit type");
        inputUnit = bundle.getString("Input unit");
        outputUnit = bundle.getString("Output unit");

        Log.i("INPUT", input);
        Log.i("INPUT UNIT", inputUnit);
        Log.i("OUTPUT UNIT", outputUnit);

        //Convert input from string to double
        double inputNum = Double.parseDouble(input);
        result = 0.0;

        //Calculate the result based on type of unit selected
        switch(unitType) {
            case "Length":
                result = lengthCalculate(inputUnit, outputUnit, inputNum);
                break;
            case "Mass":
                result = massCalculate(inputUnit, outputUnit, inputNum);
                break;
            case "Temperature":
                result = tempCalculate(inputUnit, outputUnit, inputNum);
                break;
        }

        Log.i("RESULT", String.valueOf(result));

        //toggle buttons to choose decimal places
        twodp = findViewById(R.id.tBtn2dp);
        fourdp = findViewById(R.id.tBtn4dp);
        sixdp = findViewById(R.id.tBtn6dp);
        //make 2 dp the default
        twodp.setChecked(true);
        twodp.setClickable(false);

        twodp.setOnCheckedChangeListener(changeChecker);
        fourdp.setOnCheckedChangeListener(changeChecker);
        sixdp.setOnCheckedChangeListener(changeChecker);

        //display all the numbers and text
        tvInputUnit = findViewById(R.id.tvInputUnit);
        tvOutputUnit = findViewById(R.id.tvOutputUnit);
        tvInput = findViewById(R.id.tvInput);
        tvOutput = findViewById(R.id.tvOutput);

        tvInputUnit.setText(inputUnit);
        tvOutputUnit.setText(outputUnit);

        int digits = numberOfDigits(input);
        changeTextSize(digits, tvInput);
        tvInput.setText(input);
        displayResults();

        //display formula
        tvFormula = findViewById(R.id.tvForumla);
        tvFormula.setText(Formula.getFormulaFromInput(inputUnit, outputUnit));

        //when the back button is clicked
        Button back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String formatNumber(Double num, int dp) {
        return String.format("%."+ dp +"f", num);
    }

    private void displayResults() {
        int digits;
        if (result % 1 == 0) {
            //If the result is a whole number don't show the decimal toggle buttons
            hideToggleButtons();
            formattedResult = formatNumber(result, 0);
            animateTextView("0", formattedResult, tvOutput, false);
        } else {
            //If the result has decimals, format the number to show the selected number of decimals
            formattedResult = formatNumber(result, chosenDecimalNum());
            //Animate the results only once
            if (animateCount == 0) {
                animateTextView("0", formattedResult, tvOutput, true);
                animateCount++;
            }
        }

        //If the number of digits exceed a certain number, the font needs to be smaller to fit the screen
        digits = numberOfDigits(formattedResult);
        changeTextSize(digits, tvOutput);

        tvOutput.setText(formattedResult);
    }

    private int numberOfDigits(String num) {
        int digits;
        if (Double.parseDouble(num) % 1 == 0) {
            digits = num.length();
        } else {
            String[] splitter = num.split("\\.");
            digits = splitter[0].length() + splitter[1].length();
        }
        return digits;
    }

    //Change text size depending on the number of digits so they fit on the screen
    private void changeTextSize(int digits, TextView tv) {
        if (digits > 13) {
            tv.setTextSize(30);
        } else if (digits > 9) {
            tv.setTextSize(50);
        } else {
            tv.setTextSize(70);
        }
    }

    //handling the toggle buttons
    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == twodp) {
                    twodpClicked = true;
                    sixdpClicked = false;
                    fourdpClicked = false;
                    //prevent double clicking
                    twodp.setClickable(false);

                    toggleClickManager(fourdp, sixdp);
                }
                if (buttonView == fourdp) {
                    fourdpClicked = true;
                    twodpClicked = false;
                    sixdpClicked = false;
                    //prevent double clicking
                    fourdp.setClickable(false);

                    toggleClickManager(twodp, sixdp);
                }
                if (buttonView == sixdp) {
                    sixdpClicked = true;
                    twodpClicked = false;
                    fourdpClicked = false;
                    //prevent double clicking
                    sixdp.setClickable(false);

                    toggleClickManager(twodp, fourdp);
                }
                displayResults();
            }
        }
    };

    public void toggleClickManager(ToggleButton unclicked1, ToggleButton unclicked2) {
        //making sure only one button is clicked at a time
        unclicked1.setChecked(false);
        unclicked2.setChecked(false);
        //making sure user always has one button clicked
        unclicked1.setClickable(true);
        unclicked2.setClickable(true);
    }

    private void hideToggleButtons() {
        ImageView background = findViewById(R.id.toggleBackground);
        background.setVisibility(View.INVISIBLE);
        twodp.setVisibility(View.INVISIBLE);
        fourdp.setVisibility(View.INVISIBLE);
        sixdp.setVisibility(View.INVISIBLE);
    }

    private int chosenDecimalNum() {
        if (twodpClicked) {
            return 2;
        } else if (fourdpClicked) {
            return 4;
        } else if (sixdpClicked) {
            return 6;
        } else {
            Log.e("DECIMAL ERROR", "Nothing is selected");
        }
        return 0;
    }

    private void animateTextView(String initialValue, String finalValue, final TextView textView, boolean hasDecimals) {
        ValueAnimator valueAnimator;

        if (hasDecimals) {
            valueAnimator = ValueAnimator.ofFloat(Float.parseFloat(initialValue), Float.parseFloat(finalValue));
        } else {
            valueAnimator = ValueAnimator.ofInt(Integer.parseInt(initialValue), Integer.parseInt(finalValue));
        }

        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textView.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();
    }

    //Calculation formulas sourced from https://www.unitconverters.net/
    //-------------------------------CALCULATE LENGTH UNITS-------------------------------------//
    private double lengthCalculate(String inputUnit, String outputUnit, double value) {
        if (inputUnit.equals("Kilometer")) {
           return kmTo(outputUnit, value);
        } else if (inputUnit.equals("Meter")) {
            return mTo(outputUnit, value);
        } else if (inputUnit.equals("Centimeter")) {
            return cmTo(outputUnit, value);
        } else if (inputUnit.equals("Millimeter")) {
            return mmTo(outputUnit, value);
        } else if (inputUnit.equals("Mile")) {
            return miTo(outputUnit, value);
        } else if (inputUnit.equals("Yard")) {
            return ydTo(outputUnit, value);
        } else if (inputUnit.equals("Foot")) {
            return ftTo(outputUnit, value);
        } else if (inputUnit.equals("Inch")) {
            return inTo(outputUnit, value);
        }
        return 0;
    }

    private double kmTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value;
            case "Meter":
                return value*1000;
            case "Centimeter":
                return value*1000*100;
            case "Millimeter":
                return value*1000*100*10;
            case "Mile":
                return value/1.609344;
            case "Yard":
                return value*1093.61;
            case "Foot":
                return value*3280.84;
            case "Inch":
                return value*39370.07874;
        }
        return 0;
    }

    private double mTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value/1000;
            case "Meter":
                return value;
            case "Centimeter":
                return value*100;
            case "Millimeter":
                return value*100*10;
            case "Mile":
                return value/1609.344;
            case "Yard":
                return value*1.09361;
            case "Foot":
                return value*3.28084;
            case "Inch":
                return value*39.37007874;
        }
        return 0;
    }

    private double cmTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value/100000;
            case "Meter":
                return value/100;
            case "Centimeter":
                return value;
            case "Millimeter":
                return value*10;
            case "Mile":
                return value/160934.4;
            case "Yard":
                return value/91.44;
            case "Foot":
                return value/30.48;
            case "Inch":
                return value/2.54;
        }
        return 0;
    }

    private double mmTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value/1000000;
            case "Meter":
                return value/1000;
            case "Centimeter":
                return value*10;
            case "Millimeter":
                return value;
                //check the mile
            case "Mile":
                return value/1609344;
            case "Yard":
                return value/914.4;
            case "Foot":
                return value/304.8;
            case "Inch":
                return value/25.4;
        }
        return 0;
    }

    private double miTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value*1.609344;
            case "Meter":
                return value*1609.34;
            case "Centimeter":
                return value*160934.4;
            case "Millimeter":
                return value*1609344;
            case "Mile":
                return value;
            case "Yard":
                return value*1760;
            case "Foot":
                return value*5280;
            case "Inch":
                return value*63360;
        }
        return 0;
    }

    private double ydTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value/1093.61;
            case "Meter":
                return value/1.09361;
            case "Centimeter":
                return value*91.44;
            case "Millimeter":
                return value*914.4;
            case "Mile":
                return value/1760;
            case "Yard":
                return value;
            case "Foot":
                return value*3;
            case "Inch":
                return value*36;
        }
        return 0;
    }

    private double ftTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value/3280.84;
            case "Meter":
                return value/3.28084;
            case "Centimeter":
                return value*30.48;
            case "Millimeter":
                return value*304.8;
            case "Mile":
                return value/5280;
            case "Yard":
                return value/3;
            case "Foot":
                return value;
            case "Inch":
                return value*12;
        }
        return 0;
    }

    private double inTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Kilometer":
                return value/39370.1;
            case "Meter":
                return value/39.3701;
            case "Centimeter":
                return value*2.54;
            case "Millimeter":
                return value*25.4;
            case "Mile":
                return value/63360;
            case "Yard":
                return value/36;
            case "Foot":
                return value/12;
            case "Inch":
                return value;
        }
        return 0;
    }

    //-------------------------------CALCULATE MASS UNITS-------------------------------------//
    private double massCalculate(String inputUnit, String outputUnit, double value) {
        if (inputUnit.equals("Tonne")) {
            return tTo(outputUnit, value);
        } else if (inputUnit.equals("Kilogram")) {
            return kgTo(outputUnit, value);
        } else if (inputUnit.equals("Gram")) {
            return gTo(outputUnit, value);
        } else if (inputUnit.equals("Milligram")) {
            return mgTo(outputUnit, value);
        } else if (inputUnit.equals("Stone")) {
            return stTo(outputUnit, value);
        } else if (inputUnit.equals("Pound")) {
            return lbTo(outputUnit, value);
        } else if (inputUnit.equals("Ounce")) {
            return ozTo(outputUnit, value);
        }
        return 0;
    }

    private double tTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value;
            case "Kilogram":
                return value*1000;
            case "Gram":
                return value*1000000;
            case "Milligram":
                return value*1000000000;
            case "Stone":
                return value*157.47304442;
            case "Pound":
                return value*2204.6226218;
            case "Ounce":
                return value*35273.96194958;
        }
        return 0;
    }

    private double kgTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value/1000;
            case "Kilogram":
                return value;
            case "Gram":
                return value*1000;
            case "Milligram":
                return value*1000000;
            case "Stone":
                return value/6.35029318;
            case "Pound":
                return value*2.2046226218;
            case "Ounce":
                return value*35.27396195;
        }
        return 0;
    }

    private double gTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value/1000000;
            case "Kilogram":
                return value/1000;
            case "Gram":
                return value;
            case "Milligram":
                return value*1000;
            case "Stone":
                return value/6350.29318;
            case "Pound":
                return value/453.59237;
            case "Ounce":
                return value/28.349523125;
        }
        return 0;
    }

    private double mgTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value*1000000000;
            case "Kilogram":
                return value*1000000;
            case "Gram":
                return value*1000;
            case "Milligram":
                return value;
            case "Stone":
                return value/6350293.18;
            case "Pound":
                return value/453592.37;
            case "Ounce":
                return value/28349.523125;
        }
        return 0;
    }

    private double stTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value/157.47304442;
            case "Kilogram":
                return value*6.35029318;
            case "Gram":
                return value*6350.29318;
            case "Milligram":
                return value*6350293.18;
            case "Stone":
                return value;
            case "Pound":
                return value*14;
            case "Ounce":
                return value*224;
        }
        return 0;
    }

    private double lbTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value/2204.6226218;
            case "Kilogram":
                return value/2.2046226218;
            case "Gram":
                return value*453.59237;
            case "Milligram":
                return value*453592.37;
            case "Stone":
                return value/14;
            case "Pound":
                return value;
            case "Ounce":
                return value*16;
        }
        return 0;
    }

    private double ozTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Tonne":
                return value/35273.96194958;
            case "Kilogram":
                return value/35.27396195;
            case "Gram":
                return value*28.349523125;
            case "Milligram":
                return value*28349.523125;
            case "Stone":
                return value/224;
            case "Pound":
                return value/16;
            case "Ounce":
                return value;
        }
        return 0;
    }

    //-------------------------------CALCULATE TEMPERATURE UNITS-------------------------------//
    private double tempCalculate(String inputUnit, String outputUnit, double value) {
        if (inputUnit.equals("Celsius")) {
            return cTo(outputUnit, value);
        } else if (inputUnit.equals("Fahrenheit")) {
            return fTo(outputUnit, value);
        } else if (inputUnit.equals("Kelvin")) {
            return kTo(outputUnit, value);
        }
        return 0;
    }

    private double cTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Celsius":
                return value;
            case "Fahrenheit":
                return (value*(9.0/5.0)) + 32.0;
            case "Kelvin":
                return value + 273.15;
        }
        return 0;
    }

    private double fTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Celsius":
                return (value - 32.0)*(5.0/9.0);
            case "Fahrenheit":
                return value;
            case "Kelvin":
                return (value - 32.0)*(5.0/9.0) + 273.15;
        }
        return 0;
    }

    private double kTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Celsius":
                return value - 273.15;
            case "Fahrenheit":
                return (value - 273.15)*(9.0/5.0) + 32.0;
            case "Kelvin":
                return value;
        }
        return 0;
    }

}