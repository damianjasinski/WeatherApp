package pl.damianj.weatherapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import pl.damianj.weatherapp.fragments.AdditionalDataFragment;
import pl.damianj.weatherapp.fragments.ConfigurationFragment;
import pl.damianj.weatherapp.fragments.PrimaryDataFragment;
import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.repository.WeatherApiRepository;
import pl.damianj.weatherapp.storage.StorageService;
import pl.damianj.weatherapp.util.NetworkUtils;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class MainActivity extends FragmentActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private Integer numOfPages = 6;
    private WeatherDataViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize StorageService
        StorageService.createInstance(this.getPreferences(Context.MODE_PRIVATE));
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePageAdapter(this, numOfPages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setFocusedByDefault(false);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        setSwipeRefreshAction();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.config_fragment, ConfigurationFragment.class, null)
                .add(R.id.primary_fragment, PrimaryDataFragment.class, null)
                .add(R.id.additional_data_fragment, AdditionalDataFragment.class, null)
                .commit();

        viewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);
        viewModel.getWeatherData().observe(this, weatherData -> {
            viewPager.setCurrentItem(0);
        });

        WeatherForecast lastSelected = StorageService.getInstance().loadLastSelected();
        if (lastSelected != null) {
            viewModel.setWeatherData(lastSelected);
        }
        checkIfDataIsOld();
    }

    private void checkIfDataIsOld() {
        viewModel.getWeatherData().observe(this, weatherForecast -> {
            LocalDateTime now = LocalDateTime.now();
            long epochTime = weatherForecast.getCurrent().getDt();
            int temp = weatherForecast.getCurrent().getTemp().intValue();
            LocalDateTime requestDateTime = LocalDateTime.ofEpochSecond(epochTime, 0, OffsetDateTime.now().getOffset());
            if (Duration.between(requestDateTime, now).toMinutes() > 60) {
                Toast.makeText(this, viewModel.getWeatherData().getValue().getCityName() + " was last updated " + Duration.between(requestDateTime, now).toHours() + " hours ago\nSwipe to refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSwipeRefreshAction() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isNetworkConnected(peekAvailableContext())) {
                    WeatherApiRepository.getInstance().refreshWeatherForecast(viewModel);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(peekAvailableContext(), "No connection available" , Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        StorageService storageService = StorageService.getInstance();
        storageService.saveLastSelected(viewModel.getWeatherData().getValue());
    }
}