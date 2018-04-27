package diegocacau.macros; /**
 * Created by diegocacau on 5/28/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of diegocacau.macros.DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }



    public String getName(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'user'", null);
        cursor.moveToFirst();
        String nome = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return nome;
    }

    public void setName(String name){
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ name +"' WHERE nome = 'user'", null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void setPeso(float peso){
        String s = Float.toString(peso);
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ s +"' WHERE nome = 'peso'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setCal(float cal){
        String s = Float.toString(cal);
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ s +"' WHERE nome = 'user_cal'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setProt(float prot){
        String s = Float.toString(prot);
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ s +"' WHERE nome = 'user_prot'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setCarb(float carb){
        String s = Float.toString(carb);
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ s +"' WHERE nome = 'user_carb'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setGord(float gord){
        String s = Float.toString(gord);
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ s +"' WHERE nome = 'user_gord'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setFib(float fib){
        String s = Float.toString(fib);
        Cursor cursor = database.rawQuery("UPDATE opcoes SET dado = '"+ s +"' WHERE nome = 'user_fib'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public String getPeso(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'peso'", null);
        cursor.moveToFirst();
        String peso = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return peso;
    }

    public String getCal(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'user_cal'", null);
        cursor.moveToFirst();
        String cal = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return cal;
    }

    public String getProt(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'user_prot'", null);
        cursor.moveToFirst();
        String prot = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return prot;
    }

    public String getGord(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'user_gord'", null);
        cursor.moveToFirst();
        String gord = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return gord;
    }

    public String getFib(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'user_fib'", null);
        cursor.moveToFirst();
        String fib = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return fib;
    }

    public String getCarb(){
        Cursor cursor = database.rawQuery("SELECT dado FROM opcoes WHERE nome = 'user_carb'", null);
        cursor.moveToFirst();
        String carb = cursor.getString(cursor.getColumnIndex("dado"));
        cursor.close();

        return carb;
    }


    public ArrayList<food> getNfoods(int n) throws JSONException {
        ArrayList<food> foods = new ArrayList<>();

        String s = Integer.toString(n+1);
        Cursor cursor = database.rawQuery("SELECT * FROM alimentos WHERE _id < "+s, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String jsn = cursor.getString(cursor.getColumnIndex("info"));

            JSONObject reader = new JSONObject(jsn);

            JSONObject cal  = reader.getJSONObject("cal");
            double valueCal = cal.getDouble("val");

            JSONObject carb  = reader.getJSONObject("carb");
            double valueCarb = carb.getDouble("val");

            JSONObject gd  = reader.getJSONObject("gd");
            double valueGd = gd.getDouble("val");

            JSONObject pt  = reader.getJSONObject("pt");
            double valuePT = pt.getDouble("val");

            JSONObject fb  = reader.getJSONObject("pt");
            double valueFb = fb.getDouble("val");

            JSONObject size  = reader.getJSONObject("size");
            double valueSize = size.getDouble("val");
            String valueUnit = size.getString("uni");



            foods.add(new food(id, nome, valueUnit, (float)valueSize, (float)valueCal,
                    (float)valuePT, (float)valueCarb, (float)valueFb, (float)valueGd));
            cursor.moveToNext();
        }
        cursor.close();

        return foods;
    }

    public food getFoodById(int n) throws JSONException {

        Cursor cursor = database.rawQuery("SELECT * FROM alimentos WHERE _id = "+n, null);
        cursor.moveToFirst();

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String jsn = cursor.getString(cursor.getColumnIndex("info"));

            JSONObject reader = new JSONObject(jsn);

            JSONObject cal  = reader.getJSONObject("cal");
            double valueCal = cal.getDouble("val");

            JSONObject carb  = reader.getJSONObject("carb");
            double valueCarb = carb.getDouble("val");

            JSONObject gd  = reader.getJSONObject("gd");
            double valueGd = gd.getDouble("val");

            JSONObject pt  = reader.getJSONObject("pt");
            double valuePT = pt.getDouble("val");

            JSONObject fb  = reader.getJSONObject("pt");
            double valueFb = fb.getDouble("val");

            JSONObject size  = reader.getJSONObject("size");
            double valueSize = size.getDouble("val");
            String valueUnit = size.getString("uni");



            food foods = new food(id, nome, valueUnit, (float)valueSize, (float)valueCal,
                    (float)valuePT, (float)valueCarb, (float)valueFb, (float)valueGd);


        cursor.close();

        return foods;
    }

    public ArrayList<food> getFoodByString(String palavra) throws JSONException {
        ArrayList<food> foods = new ArrayList<>();
        int contador=0;

        Cursor cursor = database.rawQuery("SELECT * FROM alimentos WHERE nome LIKE '%"+palavra+"%'" ,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if(contador>40)
                break;

            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String jsn = cursor.getString(cursor.getColumnIndex("info"));
            JSONObject reader = new JSONObject(jsn);

            JSONObject cal  = reader.getJSONObject("cal");
            double valueCal = cal.getDouble("val");

            JSONObject carb  = reader.getJSONObject("carb");
            double valueCarb = carb.getDouble("val");

            JSONObject gd  = reader.getJSONObject("gd");
            double valueGd = gd.getDouble("val");

            JSONObject pt  = reader.getJSONObject("pt");
            double valuePT = pt.getDouble("val");

            JSONObject fb  = reader.getJSONObject("pt");
            double valueFb = fb.getDouble("val");

            JSONObject size  = reader.getJSONObject("size");
            double valueSize = size.getDouble("val");
            String valueUnit = size.getString("uni");



            foods.add(new food(id, nome, valueUnit, (float)valueSize, (float)valueCal,
                    (float)valuePT, (float)valueCarb, (float)valueFb, (float)valueGd));
            contador++;
            cursor.moveToNext();
        }
        cursor.close();

        return foods;
    }



    public void insertFood(food F) throws JSONException {

        JSONObject info = new JSONObject();

        JSONObject pt = new JSONObject();
        pt.put("uni","g");
        pt.put("val",Float.toString(F.getProt()));

        JSONObject cal = new JSONObject();
        cal.put("uni","kcal");
        cal.put("val",Float.toString(F.getCal()));

        JSONObject carb = new JSONObject();
        carb.put("uni","g");
        carb.put("val",Float.toString(F.getCarb()));

        JSONObject size = new JSONObject();
        size.put("uni","g");
        size.put("val",Float.toString(F.getSize()));

        JSONObject gd = new JSONObject();
        gd.put("uni","g");
        gd.put("val",Float.toString(F.getGord()));

        JSONObject fib = new JSONObject();
        fib.put("uni","g");
        fib.put("val",Float.toString(F.getFib()));

        info.put("pt",pt);
        info.put("cal",cal);
        info.put("carb",carb);
        info.put("size",size);
        info.put("gd",gd);
        info.put("fib",fib);

        try {
            String sql = "INSERT INTO alimentos(nome, info)"
                    + " VALUES (?,?)";
            Cursor cursor = database.rawQuery(sql,new String[]{F.getName(), info.toString()});
            cursor.moveToFirst();
            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    public String getRefeicoesByDate(String data){

        Cursor cursor = database.rawQuery("SELECT * FROM refeicoes WHERE data = '"+data+"'",null);

        cursor.moveToFirst();

        String jsn;

        try{
            jsn = cursor.getString(cursor.getColumnIndex("dados"));

        }
        catch (Exception ex){
            jsn = "";

        }
        finally {
            cursor.close();
        }


        return jsn;


    }



    public void addRefeicao(int id, float quantidade, String data) throws JSONException {

        String atual = getRefeicoesByDate(data);

        int count=0;

        JSONObject refeicao;

        if(atual.length() > 0){
            JSONObject jsonArray = new JSONObject(atual );

            count = jsonArray.length();

            refeicao = new JSONObject(atual);

        }
        else{
            refeicao = new JSONObject();
        }


        JSONObject info = new JSONObject();

        info.put("id", id);
        info.put("quant", quantidade);

        refeicao.put(Integer.toString(count), info);

        Cursor cursor;

        if(count==0){
            String sql = "INSERT INTO refeicoes(nome, dados, data)"
                    + " VALUES (?,?,?)";
            cursor = database.rawQuery(sql,new String[]{"", refeicao.toString(), data});
        }
        else{
           cursor = database.rawQuery("UPDATE refeicoes SET dados = '"+ refeicao.toString() +"' WHERE data = '"+data+"'", null);
        }


        cursor.moveToFirst();
        cursor.close();

    }

    public food getRefeicoesDiario(String data) throws JSONException {

        String atual = getRefeicoesByDate(data);
        float total_cal=0, total_prot=0,total_gord=0,total_carb=0, total_fib=0;

        int count;

        if(atual.length() > 0){
            JSONObject jsonArray = new JSONObject(atual );

            count = jsonArray.length();

            for(int i=0; i<count;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(Integer.toString(i));
                food F = getFoodById(jsonObject.getInt("id"));
                total_cal = total_cal + F.getCal() * (float)(jsonObject.getDouble("quant"));
                total_prot = total_prot + F.getProt() * (float)(jsonObject.getDouble("quant"));
                total_gord = total_gord + F.getGord() * (float)(jsonObject.getDouble("quant"));
                total_carb = total_carb + F.getCarb() * (float)(jsonObject.getDouble("quant"));
                total_fib = total_fib + F.getFib() * (float)(jsonObject.getDouble("quant"));
            }

        }

        food result = new food(0," "," ",0,total_cal, total_prot, total_carb,total_fib,total_gord);

        return result;

    }



}