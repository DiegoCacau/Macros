package diegocacau.macros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class TemplateFoods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_foods);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        DatabaseAccess dbA = DatabaseAccess.getInstance(this);
        dbA.open();

        boolean data_received = getIntent().hasExtra("id");
        final boolean day_received = getIntent().hasExtra("date");

        if(data_received){
            String data_defined = getIntent().getExtras().getString("id");
            food F;
            try {
                F = dbA.getFoodById(Integer.parseInt(data_defined));
                ((TextView) findViewById(R.id.realName)).setText(F.getName());
                ((TextView) findViewById(R.id.sizeUnit)).setText(" - "+F.getSize()+F.geUnit());
                ((TextView) findViewById(R.id.Itemcal)).setText(F.getCal()+"cal");
                ((TextView) findViewById(R.id.Itemprot)).setText(F.getProt()+"g");
                ((TextView) findViewById(R.id.Itemcarb)).setText(F.getCarb()+"g");
                ((TextView) findViewById(R.id.Itemgord)).setText(F.getGord()+"g");
                ((TextView) findViewById(R.id.Itemfib)).setText(F.getFib()+"g");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ((TextView) findViewById(R.id.toolbar_title)).setText("Alimento");

        final Float user_cal = Float.valueOf(dbA.getCal());
        final Float user_pro = Float.valueOf(dbA.getProt());
        final Float user_carb = Float.valueOf(dbA.getCarb());
        final Float user_gord = Float.valueOf(dbA.getGord());
        final Float user_fib = Float.valueOf(dbA.getFib());

        ((TextView) findViewById(R.id.FutureCal)).setText(user_cal+"cal");
        ((TextView) findViewById(R.id.FutureProt)).setText(user_pro+"g");
        ((TextView) findViewById(R.id.FutureCarb)).setText(user_carb+"g");
        ((TextView) findViewById(R.id.FutureGord)).setText(user_gord+"g");
        ((TextView) findViewById(R.id.FutureFib)).setText(user_fib+"g");






        Button cancel = (Button)findViewById(R.id.cancel);

        cancel.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button save = (Button)findViewById(R.id.submit);

        save.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText size   = (EditText)findViewById(R.id.porcoes);
                if(!size.getText().toString().matches("")){
                    String data;

                    if(day_received){
                        data = getIntent().getExtras().getString("date");
                    }
                    else{
                        MainActivity ac = new MainActivity();
                        data = ac.getDate(0);
                    }

                    String data_defined = getIntent().getExtras().getString("id");
                    try {
                        DatabaseAccess dbA = DatabaseAccess.getInstance(TemplateFoods.this);
                        dbA.open();
                        dbA.addRefeicao(Integer.parseInt(data_defined),Float.valueOf(size.getText().toString()) ,data);
                        Log.d("Erroooou",dbA.getRefeicoesByDate(data));
                        dbA.close();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.d("Erroooou","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n\n\n");
                    }


                }

                finish();
            }
        });

        final EditText porcoes = (EditText)findViewById(R.id.porcoes);

        porcoes.addTextChangedListener(new TextWatcher() {

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
                    Float quant = Float.valueOf(porcoes.getText().toString());
                    Float calFinal, protFinal, carbFinal, fibFinal, gordFinal;

                    TextView alimentoCal = (TextView) findViewById(R.id.Itemcal);
                    TextView alimentoPro = (TextView) findViewById(R.id.Itemprot);
                    TextView alimentoCarb = (TextView) findViewById(R.id.Itemcarb);
                    TextView alimentoGord = (TextView) findViewById(R.id.Itemgord);
                    TextView alimentoFib = (TextView) findViewById(R.id.Itemfib);

                    calFinal = user_cal - quant * Float.parseFloat(alimentoCal.getText().toString().replace("cal",""));
                    protFinal = user_pro - quant * Float.parseFloat(alimentoPro.getText().toString().replace("g",""));
                    carbFinal = user_carb - quant * Float.parseFloat(alimentoCarb.getText().toString().replace("g",""));
                    fibFinal = user_fib - quant * Float.parseFloat(alimentoFib.getText().toString().replace("g",""));
                    gordFinal = user_gord - quant * Float.parseFloat(alimentoGord.getText().toString().replace("g",""));

                    ((TextView) findViewById(R.id.FutureCal)).setText(String.format("%.2f",calFinal)+"cal");
                    ((TextView) findViewById(R.id.FutureProt)).setText(String.format("%.2f", protFinal)+"g");
                    ((TextView) findViewById(R.id.FutureCarb)).setText(String.format("%.2f", carbFinal)+"g");
                    ((TextView) findViewById(R.id.FutureGord)).setText(String.format("%.2f", gordFinal)+"g");
                    ((TextView) findViewById(R.id.FutureFib)).setText(String.format("%.2f", fibFinal)+"g");

                }

            }

        });

        dbA.close();
    }
}
