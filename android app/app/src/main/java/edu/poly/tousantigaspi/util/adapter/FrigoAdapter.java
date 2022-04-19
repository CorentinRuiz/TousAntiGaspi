package edu.poly.tousantigaspi.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;
import edu.poly.tousantigaspi.object.Product;

public class FrigoAdapter extends BaseAdapter {

     Context context;
     FrigoModel model;

    public FrigoAdapter(@NonNull Context context, FrigoModel model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.getFrigos().size();
    }

    @Override
    public Frigo getItem(int position) {
        return model.getFrigo(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;

        layoutItem = (ConstraintLayout) (convertView == null ? View.inflate(context,R.layout.frigo_list_adapter, null) : convertView);
        TextView name = layoutItem.findViewById(R.id.frigoName);
        name.setText(model.getFrigo(position).getName());

        return layoutItem;
    }
}
