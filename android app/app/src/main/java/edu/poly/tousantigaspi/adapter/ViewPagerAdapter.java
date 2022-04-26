package edu.poly.tousantigaspi.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.poly.tousantigaspi.activity.MainActivity;
import edu.poly.tousantigaspi.fragment.ListFragment;
import edu.poly.tousantigaspi.fragment.MainFragment;
import edu.poly.tousantigaspi.fragment.SettingsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    FragmentActivity fragmentActivity;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity =fragmentActivity;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new ListFragment();
            case 2:
                return new SettingsFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
