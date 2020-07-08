package com.example.shop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.models.PurchaseProduct;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseList extends Fragment {
    User user;
    ListView lvPurchaseList;
    PurchaseArrayAdapter arrayAdapter;


    public PurchaseList(User user) {
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_list, container, false);
        //repository = new UserRepository();
        lvPurchaseList = view.findViewById(R.id.lvPurchaseProducts);
        arrayAdapter = new PurchaseArrayAdapter(getContext(),0,new ArrayList<PurchaseProduct>());
        lvPurchaseList.setAdapter(arrayAdapter);
        SyncUser();
        return view;
    }

    void SyncUser(){
        IUserRepository repository = new UserRepository();
        user = repository.getUser(user.getUserName(),user.getPassword(),getContext());
        if(arrayAdapter.getCount() > 0)
            arrayAdapter.clear();
        arrayAdapter.addAll(user.getProducts());
    }


    public class PurchaseArrayAdapter extends ArrayAdapter<PurchaseProduct>{
        private Context context;
        public PurchaseArrayAdapter(@NonNull Context context, int resource, @NonNull List<PurchaseProduct> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final PurchaseProduct purchaseProduct = getItem(position);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.product_list_item,parent,false);
            ImageView imageView = view.findViewById(R.id.imgProduct);
            TextView lblItemName = view.findViewById(R.id.lblItemName);
            TextView lblItemBrand = view.findViewById(R.id.lblItemBrand);
            TextView lblItemPrice = view.findViewById(R.id.lblItemPrice);
            TextView lblItemPurchaseDate = view.findViewById(R.id.lblItemPurchaseDate);

            Picasso.with(getContext()).load(purchaseProduct.getProduct().getThumb()).into(imageView);
            lblItemName.setText(purchaseProduct.getProduct().getName());
            lblItemBrand.setText(purchaseProduct.getProduct().getBrand());
            lblItemPrice.setText(String.valueOf(purchaseProduct.getProduct().getPrice()));
            lblItemPurchaseDate.setText(purchaseProduct.getPurchaseDate());

            Button btn = view.findViewById(R.id.btnItemChoose);
            btn.setVisibility(View.INVISIBLE);
            return view;
        }
    }
}
