package com.example.shop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shop.R;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    View view;
    TabLayout tabLayout;
    User user;
    public Home(User user) {
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = (TabLayout)view.findViewById(R.id.categorytabs);
        //tabLayout.addTab(tabLayout.newTab().setText("მონიტორი"));
        tabLayout.addTab(tabLayout.newTab().setText("ნოუთბუქი"));
        tabLayout.addTab(tabLayout.newTab().setText("პანელური კომპიუტერი"));
        tabLayout.addTab(tabLayout.newTab().setText("პრინტერი"));
        tabLayout.addTab(tabLayout.newTab().setText("პლანშეტი"));
        user = new UserRepository().getUser(user.getUserName(),user.getPassword(),getContext().getApplicationContext());

        final ViewPager viewPager = view.findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new TabPageAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


    class TabPageAdapter extends FragmentPagerAdapter{
        int tabcount;

        public TabPageAdapter(FragmentManager fm,int count){
            super(fm);
            tabcount = count;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ProductList();
            Bundle args = new Bundle();
            args.putInt("position",position+1);
            args.putString("user",new Gson().toJson(user));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return tabcount;
        }
    }
}
