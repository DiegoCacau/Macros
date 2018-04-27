package diegocacau.macros;


public class food {
    private int id;
    private String name;
    private String unit;
    private float size;
    private float cal;
    private float prot;
    private float carb;
    private float fib;
    private float gord;

    public food(int id, String name, String unit,float size,float cal,
                float prot,float carb, float fib, float gord){

        this.id = id;
        this.name = name;
        this.unit = unit;
        this.size = size;
        this.cal = cal;
        this.prot = prot;
        this.carb = carb;
        this.fib = fib;
        this.gord = gord;

    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String geUnit(){
        return this.unit;
    }

    public float getCal(){
        return this.cal;
    }

    public float getProt(){
        return this.prot;
    }

    public float getCarb(){
        return this.carb;
    }

    public float getFib(){
        return this.fib;
    }

    public float getGord(){
        return this.gord;
    }

    public float getSize(){
        return this.size;
    }


}
