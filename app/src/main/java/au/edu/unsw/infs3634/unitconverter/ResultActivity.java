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

    //make all the variables private?
    private ToggleButton twodp, fourdp, sixdp;
    Boolean twodpClicked = true, fourdpClicked = false, sixdpClicked = false;
    Double result;
    TextView tvInputUnit, tvOutputUnit, tvInput, tvOutput, tvFormula;
    String input, unitType, inputUnit, outputUnit, formattedResult;
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

        Log.i("TEST", "Hi");
        Log.i("INPUT", input);
        Log.i("INPUT UNIT", inputUnit);
        Log.i("OUTPUT UNIT", outputUnit);

        //Convert input from string to double
        Double inputNum = Double.parseDouble(input);
        result = 0.0;

        //depends on if i use it
        String[] unitTypes = getResources().getStringArray(R.array.unit_types_array);

        //Calculate the result based on type of unit selected
        if (unitType.equals("Length")) { //unitTypes[0]???
            result = lengthCalculate(inputUnit, outputUnit, inputNum);
        } else if (unitType.equals("Mass")) {
            result = massCalculate(inputUnit, outputUnit, inputNum);
        } else if (unitType.equals("Time")) {
            result = timeCalculate(inputUnit, outputUnit, inputNum);
        } else if (unitType.equals("Temperature")) {
            result = tempCalculate(inputUnit, outputUnit, inputNum);
        } else {
           //do something
        }

        Log.i("RESULT", String.valueOf(result));

        //toggle buttons to choose decimal places
        twodp = (ToggleButton)findViewById(R.id.tBtn2dp);
        fourdp = (ToggleButton)findViewById(R.id.tBtn4dp);
        sixdp = (ToggleButton)findViewById(R.id.tBtn6dp);
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
        tvFormula.setText(Formula.getFormula(inputUnit, outputUnit));

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
        String formatted = String.format("%."+ Integer.toString(dp) +"f", num);
        return formatted;
    }

    private void displayResults() {
        int digits = 0;
        if (result % 1 == 0) {
            //If the result is a whole number don't show the decimal toggle buttons
            hideToggleButtons();
            formattedResult = formatNumber(result, 0);
            animateIntTextView(0, Integer.valueOf(formattedResult), tvOutput);
        } else {
            //If the result has decimals, format the number to show the selected number of decimals
            formattedResult = formatNumber(result, chosenDecimalNum());
            //Animate the results only once
            if (animateCount == 0) {
                animateTextView("0", formattedResult, tvOutput);
                animateCount++;
            }
        }

        //If the number of digits exceed a certain number, the font needs to be smaller to fit the screen
        digits = numberOfDigits(formattedResult);
        changeTextSize(digits, tvOutput);

        tvOutput.setText(formattedResult);
        Log.i("formatted result", formattedResult);
    }

    private int numberOfDigits(String num) {
        int digits = 0;
        if (Double.valueOf(num) % 1 == 0) {
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
                    //making sure only one button is clicked at a time
                    fourdp.setChecked(false);
                    sixdp.setChecked(false);
                    twodpClicked = true;
                    sixdpClicked = false;
                    fourdpClicked = false;
                    //making sure user always has one button clicked
                    twodp.setClickable(false);
                    fourdp.setClickable(true);
                    sixdp.setClickable(true);
                }
                if (buttonView == fourdp) {
                    //making sure only one button is clicked at a time
                    twodp.setChecked(false);
                    sixdp.setChecked(false);
                    fourdpClicked = true;
                    twodpClicked = false;
                    sixdpClicked = false;
                    //making sure user always has one button clicked
                    twodp.setClickable(true);
                    fourdp.setClickable(false);
                    sixdp.setClickable(true);
                }
                if (buttonView == sixdp) {
                    //making sure only one button is clicked at a time
                    fourdp.setChecked(false);
                    twodp.setChecked(false);
                    sixdpClicked = true;
                    twodpClicked = false;
                    fourdpClicked = false;
                    //making sure user always has one button clicked
                    twodp.setClickable(true);
                    fourdp.setClickable(true);
                    sixdp.setClickable(false);
                } else {
                    // The toggle is disabled
                }
                displayResults();
            }
        }
    };

    public void hideToggleButtons() {
        ImageView background = (ImageView)findViewById(R.id.toggleBackground);
        background.setVisibility(View.INVISIBLE);
        twodp.setVisibility(View.INVISIBLE);
        fourdp.setVisibility(View.INVISIBLE);
        sixdp.setVisibility(View.INVISIBLE);
    }

    public int chosenDecimalNum() {
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

    public void animateTextView(String initialValue, String finalValue, final TextView textView) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(Float.valueOf(initialValue), Float.valueOf(finalValue));
        valueAnimator.setDuration(1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textView.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();
    }

    public void animateIntTextView(int initialValue, int finalValue, final TextView textView) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
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

    //-------------------------------CALCULATE TIME UNITS-------------------------------------//
    private double timeCalculate(String inputUnit, String outputUnit, double value) {
        if (inputUnit.equals("Second")) {
            return secTo(outputUnit, value);
        } else if (inputUnit.equals("Minute")) {
            return minTo(outputUnit, value);
        } else if (inputUnit.equals("Hour")) {
            return hrTo(outputUnit, value);
        } else if (inputUnit.equals("Day")) {
            return dayTo(outputUnit, value);
        } else if (inputUnit.equals("Week")) {
            return weekTo(outputUnit, value);
        } else if (inputUnit.equals("Month")) {
            return monthTo(outputUnit, value);
        } else if (inputUnit.equals("Calendar year")) {
            return yearTo(outputUnit, value);
        }
        return 0;
    }

    private double secTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value;
            case "Minute":
                return value/60;
            case "Hour":
                return value/3600;
            case "Day":
                return value/86400;
            case "Week":
                return value/604800;
            case "Month":
                return value/2628000;
            case "Calendar year":
                //this is for non-leap years - do one for leap years
                return value/31536000;
        }
        return 0;
    }

    private double minTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value*60;
            case "Minute":
                return value;
            case "Hour":
                return value/60;
            case "Day":
                return value/1440;
            case "Week":
                return value/10080;
            case "Month":
                //this is approximate
                return value/43800;
            case "Calendar year":
                return value/525600;
        }
        return 0;
    }

    private double hrTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value*3600;
            case "Minute":
                return value*60;
            case "Hour":
                return value;
            case "Day":
                return value/24;
            case "Week":
                return value/168;
            case "Month":
                return value/730;
            case "Calendar year":
                return value/8760;
        }
        return 0;
    }

    private double dayTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value*86400;
            case "Minute":
                return value*1440;
            case "Hour":
                return value*24;
            case "Day":
                return value;
            case "Week":
                return value/7;
            case "Month":
                //change it depending on the month??
                return value/30.417;
            case "Calendar year":
                return value/365;
        }
        return 0;
    }

    private double weekTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value*604800;
            case "Minute":
                return value*10080;
            case "Hour":
                return value*168;
            case "Day":
                return value*7;
            case "Week":
                return value;
            case "Month":
                return value/4.345;
            case "Calendar year":
                return value/52.143;
        }
        return 0;
    }
    //this whole thing is approximate
    private double monthTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value*2628000;
            case "Minute":
                return value*43800;
            case "Hour":
                return value*730;
            case "Day":
                return value*30.417;
            case "Week":
                return value/4.345;
            case "Month":
                return value;
            case "Calendar year":
                return value/12;
        }
        return 0;
    }

    private double yearTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Second":
                return value/31540000;
            case "Minute":
                return value*525600;
            case "Hour":
                return value*8760;
            case "Day":
                return value*365;
            case "Week":
                //depending on leap year
                return value*52.143;
            case "Month":
                return value*12;
            case "Calendar year":
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
                return (value*(9/5)) + 32.0;
            case "Kelvin":
                return value + 273.15;
        }
        return 0;
    }

    private double fTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Celsius":
                return (value - 32.0)*(5/9);
            case "Fahrenheit":
                return value;
            case "Kelvin":
                return (value - 32.0)*(5/9) + 273.15;
        }
        return 0;
    }

    private double kTo(String outputUnit, double value) {
        switch (outputUnit) {
            case "Celsius":
                return value - 273.15;
            case "Fahrenheit":
                return (value - 273.15)*(9/5) + 32;
            case "Kelvin":
                return value;
        }
        return 0;
    }

}