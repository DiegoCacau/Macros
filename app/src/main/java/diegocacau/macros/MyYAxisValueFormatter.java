package diegocacau.macros;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by diegocacau on 5/27/17.
 */

public class MyYAxisValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return String.valueOf((int) value);
    }



    /*
    @Override
    public int getDecimalDigits() {
        return 0;
    }
    */
}
