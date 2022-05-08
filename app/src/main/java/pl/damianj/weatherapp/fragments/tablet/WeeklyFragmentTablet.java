package pl.damianj.weatherapp.fragments.tablet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.util.Locale;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.fragments.AdditionalDataFragment;
import pl.damianj.weatherapp.fragments.ConfigurationFragment;
import pl.damianj.weatherapp.fragments.PrimaryDataFragment;
import pl.damianj.weatherapp.fragments.WeeklyFragment;
import pl.damianj.weatherapp.model.oneapi.Daily;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyFragmentTablet extends Fragment {

    private FragmentManager fragmentManager;
    private WeatherDataViewModel viewModel;

    public WeeklyFragmentTablet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.weekly_fragment, container, false);
        setDailyItems();
        fragmentManager = getChildFragmentManager();
        return root;
    }

    private void setDailyItems() {
        viewModel.getWeatherData().observe(requireActivity(), weatherForecast -> {
            WeeklyFragmentTabletItem dayOne = WeeklyFragmentTabletItem.newInstance(1);
            WeeklyFragmentTabletItem dayTwo = WeeklyFragmentTabletItem.newInstance(2);
            WeeklyFragmentTabletItem dayThree = WeeklyFragmentTabletItem.newInstance(3);
            WeeklyFragmentTabletItem dayFour = WeeklyFragmentTabletItem.newInstance(4);
            WeeklyFragmentTabletItem dayFive = WeeklyFragmentTabletItem.newInstance(5);
            WeeklyFragmentTabletItem daySix = WeeklyFragmentTabletItem.newInstance(6);

            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.day_one, dayOne, null)
                    .replace(R.id.day_two, dayTwo, null)
                    .replace(R.id.day_three, dayThree, null)
                    .replace(R.id.day_four, dayFour, null)
                    .replace(R.id.day_five, dayFive, null)
                    .replace(R.id.day_six, daySix, null)
                    .commit();

        });

    }

}