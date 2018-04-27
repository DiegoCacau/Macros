package diegocacau.macros;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;


public class Refeicao extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;

    ArrayList<food> foods;
    ListView listView;
    private static CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title)).setText("Refeição");


        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        addDrawerItems();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] osArray = { "Home", "Perfil", "Refeições", "Alimentos", "Estatística" };
                //Toast.makeText(getApplicationContext(), "selected Item Name is " + osArray[position], Toast.LENGTH_LONG).show();

                if(position == 1){
                    mDrawerList.setVisibility(View.GONE);

                    Intent myIntent = new Intent(Refeicao.this, MainActivity.class);
                    //myIntent.putExtra("key", value);
                    Refeicao.this.startActivity(myIntent);
                    mDrawerLayout.closeDrawer(mDrawerList);

                }
                else if(position == 2){

                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Refeicao.this, Perfil.class);
                    //myIntent.putExtra("key", value);
                    Refeicao.this.startActivity(myIntent);
                }
                else if(position == 3){
                    mDrawerLayout.closeDrawer(mDrawerList);

                }
                else if(position == 4){

                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Refeicao.this, new_food.class);
                    Refeicao.this.startActivity(myIntent);

                }
                else if(position == 5){

                    mDrawerLayout.closeDrawer(mDrawerList);

                    Intent myIntent = new Intent(Refeicao.this, Graphs.class);
                    //myIntent.putExtra("key", value);
                    Refeicao.this.startActivity(myIntent);
                }



            }
        });

        final boolean data_received = getIntent().hasExtra("date");
        if(data_received){
            String data_defined = getIntent().getExtras().getString("date");
        }

        listView=(ListView)findViewById(R.id.list);

        DatabaseAccess dbA = DatabaseAccess.getInstance(this);
        dbA.open();

        try {
            foods = dbA.getNfoods(5);
        } catch (JSONException e) {
            foods = new ArrayList<>();
        }


        adapter= new CustomAdapter(foods,getApplicationContext());


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItem = listView.getSelectedItem().toString();

                TextView textview1 = (TextView) view.findViewById(R.id.Itemname);

                Log.d("ViewTest",textview1.getTag().toString());

                Intent intent = new Intent(Refeicao.this, TemplateFoods.class);
                intent.putExtra("id", textview1.getTag().toString());
                if(data_received){
                    String data_defined = getIntent().getExtras().getString("date");
                    intent.putExtra("date", data_defined);
                }

                startActivity(intent);

            }


        });



        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Button mButton = (Button)findViewById(R.id.button2);

        mButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Refeicao.this, new_food.class);
                //myIntent.putExtra("key", value);
                Refeicao.this.startActivity(myIntent);
            }
        });


        String name = dbA.getName();
        dbA.close();

        if(!name.equals("\"\"")){
            TextView nameHeader   = (TextView)findViewById(R.id.header);
            nameHeader.setText(name);

        }

        EditText search = (EditText)findViewById(R.id.inputSearch);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    Log.d("Textoooo",s.toString());
                    DatabaseAccess dbA = DatabaseAccess.getInstance(Refeicao.this);
                    dbA.open();

                    listView.setAdapter(null);

                    try {
                        foods = dbA.getFoodByString(s.toString());
                    } catch (JSONException e) {
                        foods = new ArrayList<>();
                    }


                    adapter= new CustomAdapter(foods,getApplicationContext());
                    listView.setAdapter(adapter);
                    dbA.close();
                }
                else{
                    DatabaseAccess dbA = DatabaseAccess.getInstance(Refeicao.this);
                    dbA.open();
                    try {
                        foods = dbA.getNfoods(5);
                    } catch (JSONException e) {
                        foods = new ArrayList<>();
                    }


                    adapter= new CustomAdapter(foods,getApplicationContext());


                    listView.setAdapter(adapter);
                    dbA.close();

                }

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        mDrawerLayout.openDrawer(mDrawerList);

        return super.onOptionsItemSelected(item);
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



}
