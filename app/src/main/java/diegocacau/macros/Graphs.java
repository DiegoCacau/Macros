package diegocacau.macros;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;


import org.json.JSONException;

import java.util.List;

public class Graphs extends AppCompatActivity {


    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title)).setText("Estatística");

        MainActivity act = new MainActivity();

        DatabaseAccess dbA = DatabaseAccess.getInstance(this);
        dbA.open();

        float prot=0f, carb=0f, gord=0f;
        int prot_porcent=0,gord_porcent=0,carb_porcent=0;


        try {

            food F = dbA.getRefeicoesDiario(act.getDate(0));
            prot = F.getProt() * 4f;
            gord = F.getGord() * 9f;
            carb = F.getCarb() * 4f;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if((prot + gord + carb) != 0){
            prot_porcent = (int)((prot*100)/(prot + gord + carb));
            gord_porcent = (int)((gord*100)/(prot + gord + carb));
            carb_porcent = (int)((carb*100)/(prot + gord + carb));

            if((prot_porcent+gord_porcent+carb_porcent)<100){
                prot_porcent = prot_porcent + (100 - (prot_porcent+gord_porcent+carb_porcent));
            }
        }



        ((TextView) findViewById(R.id.porcProt)).setText("Proteínas - "+prot_porcent+"%");
        ((TextView) findViewById(R.id.porcGord)).setText("Gorduras - "+gord_porcent+"%");
        ((TextView) findViewById(R.id.porcCarb)).setText("Carboidratos - "+carb_porcent+"%");

        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add(new PieEntry(gord, 0));
        yvalues.add(new PieEntry(carb, 1));
        yvalues.add(new PieEntry(prot, 2));


        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setDrawValues(false);


        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.parseColor("#1E90FF"));
        colors.add(Color.parseColor("#32CD32"));
        colors.add(Color.parseColor("#FF1493"));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawCenterText(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getData().setDrawValues(false);


        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        addDrawerItems();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);



        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 2) {
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Graphs.this, Perfil.class);

                    Graphs.this.startActivity(myIntent);

                } else if (position == 1) {
                    mDrawerLayout.closeDrawer(mDrawerList);

                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Graphs.this, MainActivity.class);

                    Graphs.this.startActivity(myIntent);
                } else if (position == 3) {
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Graphs.this, Refeicao.class);

                    Graphs.this.startActivity(myIntent);
                } else if (position == 4) {
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Graphs.this, new_food.class);
                    Graphs.this.startActivity(myIntent);

                } else if (position == 5) {
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Graphs.this, new_food.class);

                    Graphs.this.startActivity(myIntent);

                }


            }
        });



        /*              Grafico de Barras                   */




        float groupSpace = 0.1f;
        float barSpace = 0.1f;
        float barWidth = 0.20f;


        List<BarEntry> yVals1 = new ArrayList<>();
        List<BarEntry> yVals2 = new ArrayList<>();
        List<BarEntry> yVals3 = new ArrayList<>();

        final BarChart barChart = (BarChart) this.findViewById(R.id.barchart);
        ArrayList<String> labelsBar = new ArrayList<>();


        for(int i=-7;i<1;i++) {

            try {
                food F = dbA.getRefeicoesDiario(act.getDate(i));

                prot = F.getProt() * 4f;
                gord = F.getGord() * 9f;
                carb = F.getCarb() * 4f;



            } catch (JSONException e) {
                e.printStackTrace();
                prot=0f;
                gord=0f;
                carb=0;
            }

            if((prot + gord + carb) != 0){
                prot_porcent = (int)((prot*100)/(prot + gord + carb));
                gord_porcent = (int)((gord*100)/(prot + gord + carb));
                carb_porcent = (int)((carb*100)/(prot + gord + carb));

                if((prot_porcent+gord_porcent+carb_porcent)<100){
                    prot_porcent = prot_porcent + (100 - (prot_porcent+gord_porcent+carb_porcent));
                }
            }
            else{
                prot_porcent =0;
                gord_porcent=0;
                carb_porcent=0;

            }

            yVals1.add(new BarEntry(7+i, gord_porcent));
            yVals2.add(new BarEntry(7+i, carb_porcent));
            yVals3.add(new BarEntry(7+i, prot_porcent));

            labelsBar.add(act.getDate(i));
        }

        labelsBar.add("");

        BarDataSet set1, set2, set3;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)barChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet)barChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Gorduras");
            set1.setColor(Color.parseColor("#1E90FF"));
            set2 = new BarDataSet(yVals2, "Carboidrato");
            set2.setColor(Color.parseColor("#32CD32"));
            set3 = new BarDataSet(yVals3, "Proteínas");
            set3.setColor(Color.parseColor("#FF1493"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);
            dataSets.add(set3);

            BarData dataBar = new BarData(dataSets);
            barChart.setData(dataBar);

        }

        XAxis xl = barChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);
        xl.setValueFormatter(new MyYAxisValueFormatter());
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setValueFormatter(new IndexAxisValueFormatter(labelsBar));


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        barChart.getAxisRight().setEnabled(false);

        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(0, groupSpace, barSpace);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setVisibleXRangeMaximum(4);
        barChart.setPinchZoom(false);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setTouchEnabled(false);

        barChart.setDrawGridBackground(false);
        barChart.moveViewToX(1);
        barChart.setClickable(false);
        barChart.getLegend().setEnabled(false);


        XAxis xAxis=barChart.getXAxis();
        xAxis.setLabelRotationAngle(-45);

        xAxis.setSpaceMin(barChart.getData().getBarWidth() / 2f);
        xAxis.setSpaceMax(barChart.getData().getBarWidth() / 2f);



        barChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                    {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        barChart.setTouchEnabled(false);
                        break;
                    }
                }
                barChart.setTouchEnabled(true);
                return false;
            }
        });





        String name = dbA.getName();
        dbA.close();

        if(!name.equals("\"\"")){
            TextView nameHeader   = (TextView)findViewById(R.id.header);
            nameHeader.setText(name);

        }


    }


    private void addDrawerItems() {
        String[] osArray = { "Home", "Perfil", "Refeições", "Alimentos", "Estatística" };
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, osArray)
        {

            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position, convertView, parent);

                TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);

                ListItemShow.setTextColor(Color.parseColor("#000000"));
                ListItemShow.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                return view;
            }

        };

        View header = getLayoutInflater().inflate(R.layout.menu_header, null);

        mDrawerList.addHeaderView(header);

        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mDrawerLayout.openDrawer(mDrawerList);

        return super.onOptionsItemSelected(item);
    }
}
