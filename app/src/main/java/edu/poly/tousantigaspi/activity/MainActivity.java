package edu.poly.tousantigaspi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.util.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    Fragment fragment ;
    private ViewPager2 viewPager;
    private BottomNavigationView mBottomNavigation;
    private ViewPagerAdapter pagerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment  = new MainFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, "fragment_name")
                    .commit();
        }

        mBottomNavigation = findViewById(R.id.BottomNavBar);
        mBottomNavigation.setOnItemSelectedListener(this);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomNavigation.getMenu().findItem(R.id.HomeItem).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigation.getMenu().findItem(R.id.ListItem).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigation.getMenu().findItem(R.id.SettingsItem).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.HomeItem ) {
            viewPager.setCurrentItem(0);
        }
        else if(item.getItemId() == R.id.ListItem){
            viewPager.setCurrentItem(1);
        }
        else if(item.getItemId() == R.id.SettingsItem){
            viewPager.setCurrentItem(2);
        }

        return true;
    }
}