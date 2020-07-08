package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.models.CreditCard;
import com.example.shop.models.Product;
import com.example.shop.models.PurchaseProduct;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {
    User user;
    Product product;
    ImageView imgProduct;
    TextView txtName,txtBrand,txtPrice;
    Spinner spCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        user = new Gson().fromJson(intent.getStringExtra("user"),User.class);
        product = new Gson().fromJson(intent.getStringExtra("product"),Product.class);
        imgProduct = findViewById(R.id.imgPurchaseProduct);
        txtName = findViewById(R.id.purchaseName);
        txtBrand = findViewById(R.id.purchaseBrand);
        txtPrice = findViewById(R.id.purchasePrice);
        spCards = findViewById(R.id.purchaseCard);
        DisplayInformation(product);

    }

    void DisplayInformation(Product product){
        Picasso.with(this).load(product.getThumb()).into(imgProduct);
        txtName.setText(product.getName());
        txtBrand.setText(product.getBrand());
        txtPrice.setText(String.valueOf(product.getPrice()));
        List<String> cardsNumbers = new ArrayList<>();
        for(int i = 0;i<user.getCreditCards().size();i++)
            cardsNumbers.add(user.getCreditCards().get(i).getCardNumber());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,cardsNumbers);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spCards.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void Purchase(View view) {
        try {
            IUserRepository repository = new UserRepository();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            PurchaseProduct purchaseProduct = new PurchaseProduct(product, dateFormat.format(cal.getTime()));
            repository.addPurchaseProduct(user,purchaseProduct,getApplicationContext());
            repository.Save(user,getApplicationContext());
            Toast.makeText(this, "ნივთი შეძენილია", Toast.LENGTH_LONG).show();

            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
