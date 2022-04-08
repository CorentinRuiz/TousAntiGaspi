package edu.poly.tousantigaspi.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.poly.tousantigaspi.fragment.login.LoginFragment;
import edu.poly.tousantigaspi.fragment.login.RegisterFragment;

public class LoginViewPagerAdapter extends FragmentStateAdapter {

    public LoginViewPagerAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
