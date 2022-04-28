package pl.damianj.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import pl.damianj.weatherapp.fragments.WeeklyFragment;


public class ScreenSlidePageAdapter extends FragmentStateAdapter {

    private Integer numOfPages;

    public ScreenSlidePageAdapter(@NonNull FragmentActivity activity, Integer numOfPages) {
        super(activity);
        this.numOfPages = numOfPages;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WeeklyFragment.newInstance(position);
    }

    // Returns the fragment to display for that page



    @Override
    public int getItemCount() {
        return numOfPages;
    }
}