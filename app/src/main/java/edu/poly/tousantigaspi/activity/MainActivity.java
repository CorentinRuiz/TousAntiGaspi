package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.fragment.ListFragment;
import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {


    Fragment fragment ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

       findViewById(R.id.HomeItem).setOnClickListener(click ->{
           fragment = new MainFragment();

           fragmentManager
                   .beginTransaction()
                   .replace(R.id.fragment_container,fragment)
                   .commit();
       });

        findViewById(R.id.ListItem).setOnClickListener(click ->{
            fragment = new ListFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
        });

        findViewById(R.id.SettingsItem).setOnClickListener(click ->{
            fragment = new SettingsFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
        });


        fragment  = new MainFragment();

        if (savedInstanceState == null) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .add(R.id.fragment_container, fragment, "fragment_name")
                   .commit();
        }

    }

    private void underlineMenuItem(MenuItem item) {
        SpannableString content = new SpannableString(item.getTitle());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        item.setTitle(content);
    }
}