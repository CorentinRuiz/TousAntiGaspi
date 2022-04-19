package edu.poly.tousantigaspi.util.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.object.Product;

public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> products;

    public ProductListAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v = View.inflate(context,R.layout.card_view_list_item,null);

       TextView itemName = v.findViewById(R.id.list_item_text);
       TextView itemDate = v.findViewById(R.id.list_item_date);

       Product product = products.get(position);

       itemName.setText(product.getName());
       itemDate.setText(product.getDateRemaining());

       return v;
    }
}
