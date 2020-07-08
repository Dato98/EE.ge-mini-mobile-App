package com.example.shop.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shop.PurchaseActivity;
import com.example.shop.R;
import com.example.shop.helpers.HtmlParser;
import com.example.shop.models.Product;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductList extends Fragment {

    int qNumber;
    View view;
    Map<Integer,String> queryTable;
    IUserRepository repository;
    ListView lvProducts;
    ProductArrayAdapter arrayAdapter;
    User user;


    public ProductList() {

    }

    private void fillMap(){
        queryTable = new HashMap<>();
        queryTable.put(0,"monitori");
        queryTable.put(1,"leptopi");
        queryTable.put(2,"paneluri-kompiuteri");
        queryTable.put(3,"printeri");
        queryTable.put(4,"plansheti");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        fillMap();
        repository = new UserRepository();
        lvProducts = view.findViewById(R.id.lvProducts);
        arrayAdapter = new ProductArrayAdapter(getContext(),0,new ArrayList<Product>());
        lvProducts.setAdapter(arrayAdapter);
        Bundle args = getArguments();
        qNumber = args.getInt("position");
        user = new Gson().fromJson(args.getString("user"),User.class);
        LoadList();
        return view;
    }



    private void LoadList(){
        if(arrayAdapter.getCount() == 0) {
            String url = String.format("https://ee.ge/kompiuteruli-teqnika/%s?page=1", queryTable.get(qNumber));
            HtmlParser htmlParser = new HtmlParser();
            HtmlParser.Callback callback = new HtmlParser.Callback() {
                @Override
                public void onDataReceived(List<Product> list) {
                    List<Product> productList = list;
                    arrayAdapter.addAll(productList);
                }
            };
            htmlParser.setCallback(callback);
            htmlParser.execute(url);
        }
    }

    private void ChooseItem(Product product){
        Intent intent = new Intent(getContext(),PurchaseActivity.class);
        intent.putExtra("user",new Gson().toJson(user));
        intent.putExtra("product",new Gson().toJson(product));
        startActivity(intent);
    }

    class ProductArrayAdapter extends ArrayAdapter<Product>{
        private Context context;

        public ProductArrayAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Product product = getItem(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.product_list_item,parent,false);
            ImageView imageView = view.findViewById(R.id.imgProduct);
            TextView lblItemName = view.findViewById(R.id.lblItemName);
            TextView lblItemBrand = view.findViewById(R.id.lblItemBrand);
            TextView lblItemPrice = view.findViewById(R.id.lblItemPrice);
            LinearLayout layoutDate = view.findViewById(R.id.layoutDate);
            layoutDate.setVisibility(View.INVISIBLE);

            Picasso.with(getContext()).load(product.getThumb()).into(imageView);
            lblItemName.setText(product.getName());
            lblItemBrand.setText(product.getBrand());
            lblItemPrice.setText(String.valueOf(product.getPrice()));

            Button btnChoose = view.findViewById(R.id.btnItemChoose);
            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChooseItem(product);
                }
            });
            return view;
        }
    }
}
