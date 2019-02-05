package com.lonchi.andrej.newsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            //  Choose fragment
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    selectedFragment = new FragmentNews();
                    break;

                case R.id.navigation_bookmarks:
                    selectedFragment = new FragmentBookmarks();
                    break;

                case R.id.navigation_settings:
                    selectedFragment = new FragmentSettings();
                    break;
            }

            //  Open fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //  Default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentNews()).commit();
        navigation.setSelectedItemId(R.id.navigation_news);
    }

}
