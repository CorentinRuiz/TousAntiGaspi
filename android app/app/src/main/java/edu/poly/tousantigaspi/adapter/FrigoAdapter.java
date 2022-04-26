package edu.poly.tousantigaspi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Frigo;

public class FrigoAdapter extends BaseAdapter {

     Context context;
     List<Frigo> frigos;

    public FrigoAdapter(@NonNull Context context, List<Frigo> frigos) {
        this.context = context;
        this.frigos = frigos;
    }

    @Override
    public int getCount() {
        return frigos.size();
    }

    @Override
    public Frigo getItem(int position) {
        return frigos.get(position);
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
        name.setText(frigos.get(position).getName());

        return layoutItem;
    }

    public void refresh(FrigoModel model) {
        updateModel(model);
        notifyDataSetChanged();
    }

    public void updateModel(FrigoModel model) {
        this.frigos = model.getFrigos();
    }
}
