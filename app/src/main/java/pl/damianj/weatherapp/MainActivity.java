package pl.damianj.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import pl.damianj.weatherapp.fragments.AdditionalDataFragment;
import pl.damianj.weatherapp.fragments.ConfigurationFragment;

public class MainActivity extends FragmentActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private Integer numOfPages = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePageAdapter(this, numOfPages);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(pagerAdapter);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.config_fragment, ConfigurationFragment.class, null)
                    .add(R.id.additional_data_fragment, AdditionalDataFragment.class, null)
                    .commit();
        }
    }



//    @Override
//    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 0) {
//            super.onBackPressed();
//        } else {
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }


}