package au.edu.unsw.infs3634.unitconverter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Formula {

    private static String km = "Kilometer";
    private static String m = "Meter";
    private static String cm = "Centimeter";
    private static String mm = "Millimeter";
    private static String mi = "Mile";
    private static String yd = "Yard";
    private static String ft = "Foot";
    private static String in = "Inch";
    private static String t = "Tonne";
    private static String kg = "Kilogram";
    private static String g = "Gram";
    private static String mg = "Milligram";
    private static String st = "Stone";
    private static String lb = "Pound";
    private static String oz = "Ounce";
    private static String sec = "Second";
    private static String min = "Minute";
    private static String hr = "Hour";
    private static String day = "Day";
    private static String wk = "Week";
    private static String mo = "Month";
    private static String year = "Calendar year";
    private static String cel = "Celsius";
    private static String fah = "Fahrenheit";
    private static String ke = "Kelvin";

    public Formula(String inputUnit, String outputUnit, String formula) {
        this.inputUnit = inputUnit;
        this.outputUnit = outputUnit;
        this.formula = formula;
    }

    private String inputUnit;
    private String outputUnit;
    private String formula;

    public String getInputUnit() {
        return inputUnit;
    }

    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }

    public String getOutputUnit() {
        return outputUnit;
    }

    public void setOutputUnit(String outputUnit) {
        this.outputUnit = outputUnit;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public static ArrayList<Formula> getFormulas() {
        ArrayList<Formula> formulas = new ArrayList<>();
        //length formulas
        formulas.add(new Formula(km, m,  "1 " + km + " = " + "1000 " +  m + "s"));
        formulas.add(new Formula(km, cm,  "1 " + km + " = " + "100000 " +  cm + "s"));
        formulas.add(new Formula(km, mm,  "1 " + km + " = " + "1000000 " +  mm + "s"));
        formulas.add(new Formula(km, mi,  "1 " + mi + " = " + "1.609344 " + km + "s"));
        formulas.add(new Formula(km, yd,  "1 " + km + " = " + "1093.61 " + yd + "s"));
        formulas.add(new Formula(km, ft,  "1 " + km + " = " + "3280.84 " + "Feet"));
        formulas.add(new Formula(km, in,  "1 " + km + " = " + "39370.07874 " + in + "es"));
        formulas.add(new Formula(m, cm,  "1 " + m + " = " + "100 " +  cm + "s"));
        formulas.add(new Formula(m, mm,  "1 " + m + " = " + "1000 " +  mm + "s"));
        formulas.add(new Formula(m, mi,  "1 " + mi + " = " + "1609.344 " + m + "s"));
        formulas.add(new Formula(m, yd,  "1 " + m + " = " + "1.09361 " + yd + "s"));
        formulas.add(new Formula(m, ft,  "1 " + m + " = " + "3.28084 " +  "Feet"));
        formulas.add(new Formula(m, in,  "1 " + m + " = " + "39.37007874 " + in + "es"));
        formulas.add(new Formula(cm, mm,  "1 " + cm + " = " + "10 " +  mm + "s"));
        formulas.add(new Formula(cm, mi,  "1 " + mi + " = " + "160934.4 " +  cm + "s"));
        formulas.add(new Formula(cm, yd,  "1 " + yd + " = " + "91.44 " +  cm + "s"));
        formulas.add(new Formula(cm, ft,  "1 " + ft + " = " + "30.48 " +  cm + "s"));
        formulas.add(new Formula(cm, in,  "1 " + in + " = " + "2.54 " +  cm + "s"));
        formulas.add(new Formula(mm, mi,  "1 " + mi + " = " + "1609344 " +  mm + "s"));
        formulas.add(new Formula(mm, yd,  "1 " + yd + " = " + "914.4 " +  mm + "s"));
        formulas.add(new Formula(mm, ft,  "1 " + ft + " = " + "304.8 " +  mm + "s"));
        formulas.add(new Formula(mm, in,  "1 " + in + " = " + "25.4 " +  mm + "s"));
        formulas.add(new Formula(mi, yd,  "1 " + mi + " = " + "1760 " +  yd + "s"));
        formulas.add(new Formula(mi, ft,  "1 " + mi + " = " + "5280 " +   "Feet"));
        formulas.add(new Formula(mi, in,  "1 " + mi + " = " + "63360 " +  in + "es"));
        formulas.add(new Formula(yd, ft,  "1 " + yd + " = " + "3 " +   "Feet"));
        formulas.add(new Formula(yd, in,  "1 " + yd + " = " + "36 " +  in + "es"));
        formulas.add(new Formula(ft, in,  "1 " + ft + " = " + "12 " +  in + "es"));

        //mass formulas
        formulas.add(new Formula(t, kg,  "1 " + t + " = " + "1000 " +  kg + "s"));
        formulas.add(new Formula(t, g,  "1 " + t + " = " + "1000000 " +  g + "s"));
        formulas.add(new Formula(t, mg,  "1 " + t + " = " + "1000000000 " +  mg + "s"));
        formulas.add(new Formula(t, st,  "1 " + t + " = " + "157.47304442 " +  st));
        formulas.add(new Formula(t, lb,  "1 " + t + " = " + "2204.6226218 " +  lb + "s"));
        formulas.add(new Formula(t, oz,  "1 " + t + " = " + "35273.96194958 " +  oz + "es"));
        formulas.add(new Formula(kg, g,  "1 " + kg + " = " + "1000 " +  g + "s"));
        formulas.add(new Formula(kg, mg,  "1 " + kg + " = " + "1000000 " +  mg + "s"));
        formulas.add(new Formula(kg, st,  "1 " + st + " = " + "6.35029318 " +  kg + "s"));
        formulas.add(new Formula(kg, lb,  "1 " + kg + " = " + "2.2046226218 " +  lb + "s"));
        formulas.add(new Formula(kg, oz,  "1 " + kg + " = " + "35.27396195 " +  oz + "es"));
        formulas.add(new Formula(g, mg,  "1 " + g + " = " + "1000 " +  mg + "s"));
        formulas.add(new Formula(g, st,  "1 " + st + " = " + "6350.29318 " +  g + "s"));
        formulas.add(new Formula(g, lb,  "1 " + lb + " = " + "453.59237 " +  g + "s"));
        formulas.add(new Formula(g, oz,  "1 " + oz + " = " + "28.349523125 " +  g + "s"));
        formulas.add(new Formula(mg, st,  "1 " + st + " = " + "6350293.18 " +  mg + "s"));
        formulas.add(new Formula(mg, lb,  "1 " + lb + " = " + "453592.37 " +  mg + "s"));
        formulas.add(new Formula(mg, oz,  "1 " + oz + " = " + "28349.523125 " +  mg + "s"));
        formulas.add(new Formula(st, lb,  "1 " + st + " = " + "14 " +  lb + "s"));
        formulas.add(new Formula(st, lb,  "1 " + st + " = " + "224 " +  oz + "es"));
        formulas.add(new Formula(lb, oz,  "1 " + lb + " = " + "16 " +  oz + "es"));

        //temperature formulas
        formulas.add(new Formula(fah, cel, "(°C × 9/5) + 32 = °F"));
        formulas.add(new Formula(fah, ke, "(K − 273.15) × 9/5 + 32 = °F"));
        formulas.add(new Formula(cel, ke, "K − 273.15 = °C"));

        return formulas;
    }

    //returns one formula based on the units chosen
    public static String getFormulaFromInput(String inputUnit, String outputUnit) {
        ArrayList<Formula> list = getFormulas();
        String[] inputUnits = {inputUnit, outputUnit};

        for (Formula f : list) {
            String[] formulaUnits = {f.getInputUnit(), f.getOutputUnit()};
            //checks that the units match the formula
            if (findMatch(inputUnits, formulaUnits)) {
                return f.getFormula();
            }
        }
        Log.i("Where", "end of getFormulaFromInput() -> no matches");
        return null;
    }

    private static boolean findMatch(String[] arr1, String[] arr2) {
        if (arr1.length == arr2.length) {
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            return Arrays.equals(arr1, arr2);
        }
        return false;
    }

}
