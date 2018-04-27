package diegocacau.macros;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final EditText name   = (EditText)findViewById(R.id.textName);
        final EditText peso   = (EditText)findViewById(R.id.textPeso);
        final EditText cal   = (EditText)findViewById(R.id.textCal);
        final EditText prot   = (EditText)findViewById(R.id.textProt);
        final EditText carb   = (EditText)findViewById(R.id.textCarb);
        final EditText gord   = (EditText)findViewById(R.id.textGord);
        final EditText fib   = (EditText)findViewById(R.id.textFib);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title)).setText("Perfil");


        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        addDrawerItems();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] osArray = { "Home", "Perfil", "Refeições", "Alimentos", "Estatística" };

                if(position == 1){
                    mDrawerList.setVisibility(View.GONE);

                    Intent myIntent = new Intent(Perfil.this, MainActivity.class);
                    //myIntent.putExtra("key", value);
                    Perfil.this.startActivity(myIntent);
                    mDrawerLayout.closeDrawer(mDrawerList);

                }
                else if(position == 2){

                    mDrawerLayout.closeDrawer(mDrawerList);
                }
                else if(position == 3){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Perfil.this, Refeicao.class);
                    Perfil.this.startActivity(myIntent);
                }
                else if(position == 4){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Perfil.this, new_food.class);
                    Perfil.this.startActivity(myIntent);

                }
                else if(position == 5){
                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Perfil.this, Graphs.class);
                    Perfil.this.startActivity(myIntent);
                }



            }
        });

        Button mButton = (Button)findViewById(R.id.buttonSave);

        mButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(Perfil.this);
                databaseAccess.open();
                databaseAccess.setName(name.getText().toString().trim());

                if(!(peso.getText().toString()).equals("\"\"")) {
                    databaseAccess.setPeso(Float.valueOf(peso.getText().toString()));
                }

                if(!(cal.getText().toString()).equals("\"\"")){
                    databaseAccess.setCal(Float.valueOf(cal.getText().toString()));
                }


                if(!(prot.getText().toString()).equals("\"\"")){
                    databaseAccess.setProt(Float.valueOf(prot.getText().toString()));

                }


                if(!(carb.getText().toString()).equals("\"\"")){
                    databaseAccess.setCarb(Float.valueOf(carb.getText().toString()));
                }


                if(!(gord.getText().toString()).equals("\"\"")){
                    databaseAccess.setGord(Float.valueOf(gord.getText().toString()));
                }


                if(!(fib.getText().toString()).equals("\"\"")){
                    databaseAccess.setFib(Float.valueOf(fib.getText().toString()));
                }

                databaseAccess.close();
                finish();
            }
        });

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(Perfil.this);
        databaseAccess.open();
        String nam = databaseAccess.getName();
        String peso_ = databaseAccess.getPeso();
        String cal_ = databaseAccess.getCal();
        String prot_ = databaseAccess.getProt();
        String carb_ = databaseAccess.getCarb();
        String gord_ = databaseAccess.getGord();
        String fib_ = databaseAccess.getFib();
        databaseAccess.close();

        if(!peso_.equals("\"\"")){
            peso.setText(peso_);

        }
        if(!cal_.equals("\"\"")){
            cal.setText(cal_);

        }
        if(!prot_.equals("\"\"")){
            prot.setText(prot_);

        }
        if(!carb_.equals("\"\"")){
            carb.setText(carb_);

        }
        if(!gord_.equals("\"\"")){
            gord.setText(gord_);

        }
        if(!fib_.equals("\"\"")){
            fib.setText(fib_);

        }
        if(!nam.equals("\"\"")){
            EditText nameT   = (EditText)findViewById(R.id.textName);
            TextView nameHeader   = (TextView)findViewById(R.id.header);
            nameT.setText(nam);
            nameHeader.setText(nam);

        }

    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        mDrawerLayout.openDrawer(mDrawerList);

        return super.onOptionsItemSelected(item);
    }


}
