package au.edu.unsw.infs3634.unitconverter;

import android.util.Log;

import java.util.ArrayList;

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

    public void setFormula(String countryCode) {
        this.formula = formula;
    }

    public static ArrayList<Formula> getFormulas() {
        ArrayList<Formula> formulas = new ArrayList<>();
        formulas.add(new Formula(km, m,  "1 " + km + " = " + "1000 " +  m));
        formulas.add(new Formula(km, cm,  "1 " + km + " = " + "100000 " +  cm));
        formulas.add(new Formula(km, mm,  "1 " + km + " = " + "1000000 " +  mm));
        formulas.add(new Formula(km, mi,  "1 " + mi + " = " + "1.609344 " + km));
        formulas.add(new Formula(km, yd,  "1 " + km + " = " + "1093.61 " + yd));
        formulas.add(new Formula(km, ft,  "1 " + km + " = " + "3280.84 " + ft));
        formulas.add(new Formula(km, in,  "1 " + km + " = " + "39370.07874 " + in));
        formulas.add(new Formula(m, cm,  "1 " + m + " = " + "100 " +  cm));
        formulas.add(new Formula(m, mm,  "1 " + m + " = " + "1000 " +  mm));
        formulas.add(new Formula(m, mi,  "1 " + mi + " = " + "1609.344 " + m));
        formulas.add(new Formula(m, yd,  "1 " + m + " = " + "1.09361 " + yd));
        formulas.add(new Formula(m, ft,  "1 " + m + " = " + "3.28084 " + ft));
        formulas.add(new Formula(m, in,  "1 " + m + " = " + "39.37007874 " + in));
        formulas.add(new Formula(cm, mm,  "1 " + cm + " = " + "10 " +  mm));
        formulas.add(new Formula(cm, mi,  "1 " + mi + " = " + "160934.4 " +  cm));
        formulas.add(new Formula(cm, yd,  "1 " + yd + " = " + "91.44 " +  cm));
        formulas.add(new Formula(cm, ft,  "1 " + ft + " = " + "30.48 " +  cm));
        formulas.add(new Formula(cm, in,  "1 " + in + " = " + "2.54 " +  cm));
        formulas.add(new Formula(mm, mi,  "1 " + mi + " = " + "1609344 " +  mm));
        formulas.add(new Formula(mm, yd,  "1 " + yd + " = " + "914.4 " +  mm));
        formulas.add(new Formula(mm, ft,  "1 " + ft + " = " + "304.8 " +  mm));
        formulas.add(new Formula(mm, in,  "1 " + in + " = " + "25.4 " +  mm));
        formulas.add(new Formula(mi, yd,  "1 " + mi + " = " + "1760 " +  yd));
        formulas.add(new Formula(mi, ft,  "1 " + mi + " = " + "5280 " +  ft));
        formulas.add(new Formula(mi, in,  "1 " + mi + " = " + "63360 " +  in));
        formulas.add(new Formula(yd, ft,  "1 " + yd + " = " + "3 " +  ft));
        formulas.add(new Formula(yd, in,  "1 " + yd + " = " + "36 " +  in));
        formulas.add(new Formula(ft, in,  "1 " + ft + " = " + "12 " +  in));

        formulas.add(new Formula(t, kg,  "1 " + t + " = " + "1000 " +  kg));
        formulas.add(new Formula(t, g,  "1 " + t + " = " + "1000000 " +  g));
        formulas.add(new Formula(t, mg,  "1 " + t + " = " + "1000000000 " +  mg));
        formulas.add(new Formula(t, st,  "1 " + t + " = " + "157.47304442 " +  st));
        formulas.add(new Formula(t, lb,  "1 " + t + " = " + "2204.6226218 " +  lb));
        formulas.add(new Formula(t, oz,  "1 " + t + " = " + "35273.96194958 " +  oz));
        formulas.add(new Formula(kg, g,  "1 " + kg + " = " + "1000 " +  g));
        formulas.add(new Formula(kg, mg,  "1 " + kg + " = " + "1000000 " +  mg));
        formulas.add(new Formula(kg, st,  "1 " + st + " = " + "6.35029318 " +  kg));
        formulas.add(new Formula(kg, lb,  "1 " + kg + " = " + "2.2046226218 " +  lb));
        formulas.add(new Formula(kg, oz,  "1 " + kg + " = " + "35.27396195 " +  oz));
        formulas.add(new Formula(g, mg,  "1 " + g + " = " + "1000 " +  mg));
        formulas.add(new Formula(g, st,  "1 " + st + " = " + "6350.29318 " +  g));
        formulas.add(new Formula(g, lb,  "1 " + lb + " = " + "453.59237 " +  g));
        formulas.add(new Formula(g, oz,  "1 " + oz + " = " + "28.349523125 " +  g));
        formulas.add(new Formula(mg, st,  "1 " + st + " = " + "6350293.18 " +  mg));
        formulas.add(new Formula(mg, lb,  "1 " + lb + " = " + "453592.37 " +  mg));
        formulas.add(new Formula(mg, oz,  "1 " + oz + " = " + "28349.523125 " +  mg));
        formulas.add(new Formula(st, lb,  "1 " + st + " = " + "14 " +  lb));
        formulas.add(new Formula(st, lb,  "1 " + st + " = " + "224 " +  oz));
        formulas.add(new Formula(lb, oz,  "1 " + lb + " = " + "16 " +  oz));

        formulas.add(new Formula(fah, cel, "(°C × 9/5) + 32 = °F"));
        formulas.add(new Formula(fah, ke, "(K − 273.15) × 9/5 + 32 = °F"));
        formulas.add(new Formula(cel, ke, "K − 273.15 = °C"));

        return formulas;
    }

    public static String getFormula(String inputUnit, String outputUnit) {
        // Implement a method that returns one formula based on the units chosen
        ArrayList<Formula> list = getFormulas();
        for (Formula f : list) {
            if ((f.getInputUnit().equals(inputUnit)
                    && f.getOutputUnit().equals(outputUnit)) ||
                    (f.getInputUnit().equals(outputUnit)
                            && f.getOutputUnit().equals(inputUnit))) {
                Log.i("Formula", f.getFormula());
                return f.getFormula();
            }
        }
        Log.i("Where", "end of getFormula() -> no matches");
        return null;
    }
}
