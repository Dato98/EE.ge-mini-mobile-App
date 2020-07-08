package com.example.shop;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shop.fragments.Cards;
import com.example.shop.fragments.Home;
import com.example.shop.fragments.PurchaseList;
import com.example.shop.fragments.Settings;
import com.example.shop.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;
    private User user;
    private TextView txtHeadName, txtHeadSname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().hasExtra("user")) {
            user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
        }
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        View header = navigationView.getHeaderView(0);
        txtHeadName = header.findViewById(R.id.txtheadName);
        txtHeadSname = header.findViewById(R.id.txtheadSname);

        txtHeadName.setText(user.getName());
        txtHeadSname.setText(user.getSurname());

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                SelectFragment(menuItem.getItemId());
                return true;
            }
        });
        SelectFragment(R.id.nav_main);
    }

    public void SelectFragment(int menuItem){
        Fragment fragment = null;
        switch (menuItem){
            case R.id.nav_main:
                fragment = new Home(user);
                break;
            case  R.id.nav_purchases:
                fragment = new PurchaseList(user);
                break;
            case  R.id.nav_cards:
                fragment = new Cards(user);
                break;
            case R.id.nav_settings:
                fragment = new Settings(user);
                break;
        }
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame,fragment);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void Load(View view) {
        final GetData getData = new GetData();
        getData.execute();
    }


    class GetData extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://ee.ge/kompiuteruli-teqnika/monitori?page=1";//"https://ee.ge/kompiuteruli-teqnika/leptopi?page=1";
                Document doc = Jsoup.connect(url).get();
                String htmlString = doc.toString();

                Elements newsHeadlines = doc.getElementsByClass("product-list");
                for (Element headline : newsHeadlines) {
                    String thumb = headline.getElementsByClass("thumb-wrap").first().select("img").first().absUrl("src");
                    Elements elements = headline.getElementsByTag("p");
                    String Brand =  elements.get(0).text();
                    String Name = elements.get(1).text();
                    String price = headline.getElementsByClass("price-section").first().getElementsByTag("span").first().text();
                    Log.d("Title",String.format("%s\n\t%s",
                            headline.attr("title"), headline.absUrl("href")));
                }
                return null;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

    }
}
