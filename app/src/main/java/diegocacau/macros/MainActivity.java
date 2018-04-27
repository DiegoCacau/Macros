package diegocacau.macros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private String tomorrow, yesterday, today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title)).setText("Home");

        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        addDrawerItems();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        final boolean data_received = getIntent().hasExtra("date");



        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseAccess.close();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 2){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(MainActivity.this, Perfil.class);
                    MainActivity.this.startActivity(myIntent);

                }
                else if(position == 1){
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
                else if(position == 3){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(MainActivity.this, Refeicao.class);

                    if(data_received) {
                        String data_defined = getIntent().getExtras().getString("date");
                        myIntent.putExtra("date", data_defined);
                    }
                    else{
                        myIntent.putExtra("date", today);
                    }

                    MainActivity.this.startActivity(myIntent);
                }
                else if(position == 4){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(MainActivity.this, new_food.class);
                    MainActivity.this.startActivity(myIntent);
                }
                else if(position == 5){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(MainActivity.this, Graphs.class);
                    MainActivity.this.startActivity(myIntent);
                }



            }
        });

        Button mButton = (Button)findViewById(R.id.date);
        Button mButton_1 = (Button)findViewById(R.id.date_1);
        Button mButton_2 = (Button)findViewById(R.id.date_2);



        if(data_received){
            String data_defined = getIntent().getExtras().getString("date");
            mButton.setText(data_defined);

            try{
                today = getDate(data_defined,0);
                yesterday = getDate(data_defined,-1);
                tomorrow = getDate(data_defined,1);
            } catch (ParseException e){
                today = getDate(0);
                yesterday = getDate(-1);
                tomorrow = getDate(1);
            }

            mButton.setText(today);
            mButton_1.setText(yesterday);
            mButton_2.setText(tomorrow);

        }
        else{
            today = getDate(0);
            yesterday = getDate(-1);
            tomorrow = getDate(1);


            mButton.setText(today);
            mButton_1.setText(yesterday);
            mButton_2.setText(tomorrow);
        }


        mButton_1.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("date",yesterday);
                finish();
                startActivity(intent);
            }
        });

        mButton_2.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("date",tomorrow);
                finish();
                startActivity(intent);
            }
        });


        Button refei = (Button)findViewById(R.id.button2);

        refei.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Refeicao.class);
                if(data_received) {
                    String data_defined = getIntent().getExtras().getString("date");
                    myIntent.putExtra("date", data_defined);
                }
                else{
                    myIntent.putExtra("date", today);
                }
                MainActivity.this.startActivity(myIntent);
            }
        });

        DatabaseAccess dbA = DatabaseAccess.getInstance(this);
        dbA.open();
        String name = dbA.getName();
        String userCal = dbA.getCal();
        String userCarb = dbA.getCarb();
        String userProt = dbA.getProt();
        String userGord = dbA.getGord();
        String userFib = dbA.getFib();

        try {
            food F = dbA.getRefeicoesDiario(today);

            ProgressBar barCal = (ProgressBar) findViewById(R.id.barCal);
            ProgressBar barProt = (ProgressBar) findViewById(R.id.barProt);
            ProgressBar barCarb = (ProgressBar) findViewById(R.id.barCarb);
            ProgressBar barGord = (ProgressBar) findViewById(R.id.barGord);
            ProgressBar barFib = (ProgressBar) findViewById(R.id.barFib);

            if(!userCal.equals("0.0")){
                barCal.setMax((Float.valueOf(userCal).intValue()));
                barCal.setProgress((int)F.getCal());

            }
            else{
                barCal.setMax(0);
                barCal.setProgress(0);
            }

            if(!userProt.equals("0.0")){
                barProt.setMax(Float.valueOf(userProt).intValue());
                barProt.setProgress((int)F.getProt());

            }
            else{
                barProt.setMax(0);
                barProt.setProgress(0);
            }

            if(!userCarb.equals("0.0")){
                barCarb.setMax(Float.valueOf(userCarb).intValue());
                barCarb.setProgress((int)F.getCarb());

            }
            else{
                barCarb.setMax(0);
                barCarb.setProgress(0);
            }

            if(!userFib.equals("0.0")){
                barFib.setMax(Float.valueOf(userFib).intValue());
                barFib.setProgress((int)F.getFib());

            }
            else{
                barFib.setMax(0);
                barFib.setProgress(0);
            }

            if(!userGord.equals("0.0")){
                barGord.setMax(Float.valueOf(userGord).intValue());
                barGord.setProgress((int)F.getGord());

            }
            else{
                barGord.setMax(0);
                barGord.setProgress(0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error",e.toString());
        }


        dbA.close();

        if(!name.equals("\"\"")){
            TextView nameHeader   = (TextView)findViewById(R.id.header);
            nameHeader.setText(name);

        }

    }

    public String getDate(int days){
        Date cDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(cDate);

        cal.add(Calendar.DATE, days);
        Date newDate = cal.getTime();

        return new SimpleDateFormat("dd/MM/yyyy").format(newDate);
    }

    public String getDate(String day,int days) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date cDate = dateFormat.parse(day);

        Calendar cal = Calendar.getInstance();
        cal.setTime(cDate);

        cal.add(Calendar.DATE, days);
        Date newDate = cal.getTime();

        return new SimpleDateFormat("dd/MM/yyyy").format(newDate);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        mDrawerLayout.openDrawer(mDrawerList);

        return super.onOptionsItemSelected(item);
    }

//    public void startProgressDialog(View V)
//    {
//        ProgressDialog progressBar;
//        progressBar = new ProgressDialog(V.getContext());
//        // Get the Drawable custom_progressbar
//        Resources res = getResources();
//        Drawable draw= res.getDrawable(R.drawable.customprogressbar);
//        // set the drawable as progress drawavle
//
//        progressBar.setProgressDrawable(draw);
//        progressBar.show();
//
//
//    }

    private void addDrawerItems() {
        String[] osArray = { "Home", "Perfil", "Refeições", "Alimentos", "Estatística" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, osArray)
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





}
