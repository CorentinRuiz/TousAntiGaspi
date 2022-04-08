
package edu.poly.tousantigaspi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.LoginViewPagerAdapter;
import edu.poly.tousantigaspi.util.ViewPagerAdapter;

public class LoginRegisterActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager2 viewPager2;
    LoginViewPagerAdapter pagerAdapter;
    String[] tabsName = {
            "Connexion",
            "S'inscrire"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        tabs = findViewById(R.id.loginRegisterTabs);
        viewPager2 = findViewById(R.id.tabsPages);

        pagerAdapter = new LoginViewPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabs,viewPager2,((tab, position) -> tab.setText(tabsName[position]))).attach();
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
    }

}