package com.example.shop.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.shop.models.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class HtmlParser extends AsyncTask<String,Void,List<Product>> {

    Callback callback;


    @Override
    protected List<Product> doInBackground(String... strings) {
        List<Product> productList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(strings[0]).get();
            String htmlString = doc.toString();
            Elements newsHeadlines = doc.getElementsByClass("product-list");
            for (Element headline : newsHeadlines) {
                String thumb = headline.getElementsByClass("thumb-wrap").first().select("img").first().absUrl("src");
                Elements elements = headline.getElementsByTag("p");
                String Brand = elements.get(0).text();
                String Name = elements.get(1).text();
                String price = headline.getElementsByClass("price-section").first().getElementsByTag("span").first().text();
                if(price.contains(",")){
                   price = price.replace(",","");
                }
                if(thumb.length()!=0 && Brand.length() != 0 && Name.length() !=0 && price.length() != 0) {
                    Product product = new Product(Brand, Name, Double.parseDouble(price), thumb);
                    productList.add(product);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }
    @Override
    protected void onPostExecute(List<Product> products) {
        super.onPostExecute(products);
        if(callback != null)
            callback.onDataReceived(products);
    }

    public interface Callback{
        void onDataReceived(List<Product> list);
    }

    public void setCallback(Callback callback){this.callback = callback;}
}
