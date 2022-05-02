package edu.poly.tousantigaspi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.model.FrigoModel;
import edu.poly.tousantigaspi.object.Product;

public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductListAdapter(Context context, List<Product> products) {
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
       TextView itemQuantity = v.findViewById(R.id.list_item_quantity);

        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                Integer dateP1 = Integer.parseInt(p1.getDateRemaining().split(" ")[0]);
                Integer dateP2 = Integer.parseInt(p2.getDateRemaining().split(" ")[0]);
                return dateP1.compareTo(dateP2);
            }
        });

       Product product = products.get(position);
       Integer days = Integer.parseInt(product.getDateRemaining().split(" ")[0]);

       if(days < 5) {
        itemDate.setTextColor(context.getResources().getColor(R.color.red));
       }
       else if(days < 10 && days > 5){
           itemDate.setTextColor(context.getResources().getColor(R.color.orange));
       }

       itemName.setText(product.getName());
       itemDate.setText(product.getDateRemaining());
       itemQuantity.setText("x"+ product.getQuantity());

       return v;
    }

    public void refresh(FrigoModel model,int position,boolean pastDlc) {
        updateModel(model,position,pastDlc);
        notifyDataSetChanged();
    }

    public void updateModel(FrigoModel model,int position,boolean pastDlc) {
        if(pastDlc){
            this.products = model.getCurrentPastDlcProduct().get(model.getFrigos().get(position).getName());
        }else{
            System.out.println(position);
            this.products = model.getFrigo(position).getProducts();
        }

    }
}
