package diegocacau.macros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<food> {

    //private ArrayList<food> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtSize;
        TextView txtCal;
        TextView txtProt;
        TextView txtCarb;
        TextView txtFib;
        TextView txtGord;
    }

    public CustomAdapter(ArrayList<food> data, Context context) {
        super(context, R.layout.food_list, data);
        //this.dataSet = data;
        this.mContext = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        food model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        //final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.food_list, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.Itemname);
            viewHolder.txtSize = (TextView) convertView.findViewById(R.id.Itemsize);
            viewHolder.txtCal = (TextView) convertView.findViewById(R.id.Itemcal);
            viewHolder.txtProt = (TextView) convertView.findViewById(R.id.Itemprot);
            viewHolder.txtCarb = (TextView) convertView.findViewById(R.id.Itemcarb);
            viewHolder.txtGord = (TextView) convertView.findViewById(R.id.Itemgord);
            viewHolder.txtFib = (TextView) convertView.findViewById(R.id.Itemfib);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setTag(model.getId());
        viewHolder.txtName.setText(model.getName());
        viewHolder.txtSize.setText(model.getSize()+ model.geUnit());
        viewHolder.txtCal.setText(Float.toString(model.getCal())+"cal");
        viewHolder.txtProt.setText(Float.toString(model.getProt())+"g");
        viewHolder.txtCarb.setText(Float.toString(model.getCarb())+"g");
        viewHolder.txtGord.setText(Float.toString(model.getGord())+"g");
        viewHolder.txtFib.setText(Float.toString(model.getFib())+"g");

        // Return the completed view to render on screen
        return convertView;
    }


}