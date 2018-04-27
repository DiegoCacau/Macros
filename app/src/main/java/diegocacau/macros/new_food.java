package diegocacau.macros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

public class new_food extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title)).setText("Novo Alimento");



        final EditText nome   = (EditText)findViewById(R.id.alimentoNome);
        final EditText cal   = (EditText)findViewById(R.id.alimentoCal);
        final EditText prot   = (EditText)findViewById(R.id.alimentoProt);
        final EditText carb   = (EditText)findViewById(R.id.alimentoCarb);
        final EditText gord   = (EditText)findViewById(R.id.alimentoGord);
        final EditText fib   = (EditText)findViewById(R.id.alimentoFib);
        final EditText size   = (EditText)findViewById(R.id.alimentoSize);
        final EditText uni   = (EditText)findViewById(R.id.alimentoUni);

        Button cancel = (Button)findViewById(R.id.cancel);

        cancel.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if((!nome.getText().toString().matches("")) &&(!uni.getText().toString().matches(""))&&
                        (!size.getText().toString().matches(""))&&(!cal.getText().toString().matches(""))&&
                        (!prot.getText().toString().matches(""))&&(!carb.getText().toString().matches(""))&&
                        (!fib.getText().toString().matches(""))&&(!gord.getText().toString().matches(""))){

                    food novoAlimento = new food(0, nome.getText().toString(), uni.getText().toString(),
                            Float.valueOf(size.getText().toString()), Float.valueOf(cal.getText().toString()),
                            Float.valueOf(prot.getText().toString()),Float.valueOf(carb.getText().toString()),
                            Float.valueOf(fib.getText().toString()),Float.valueOf(gord.getText().toString()));

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(new_food.this);
                    databaseAccess.open();

                    try {
                        databaseAccess.insertFood(novoAlimento);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finally {
                        databaseAccess.close();
                        finish();
                    }

                }

            }
        });
    }
}
