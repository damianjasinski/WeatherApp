package pl.damianj.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.model.oneapi.Daily;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private Integer pageNumber;
    private TextView dayTextView;
    private TextView tempTextView;
    private ImageView weatherIcon;
    private WeatherDataViewModel viewModel;

    public WeeklyFragment() {
        // Required empty public constructor
    }

    public static WeeklyFragment newInstance(Integer pageNumber) {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putInt("pageNumber", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt("pageNumber");
        }
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.weekly_fragment, container, false);
        dayTextView = root.findViewById(R.id.day_name_text);
        tempTextView = root.findViewById(R.id.temperature_text);
        weatherIcon = root.findViewById(R.id.weather_icon2);
        observeWeatherPage();
        return root;
    }

    public void observeWeatherPage() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherForecast -> {
            Daily daily = weatherForecast.getDaily().get(pageNumber);
            LocalDate today = LocalDate.now().plusDays(pageNumber);
            dayTextView.setText(today.getDayOfWeek().toString());
            int temp = daily.getTemp().getDay().intValue();
            tempTextView.setText(Integer.toString(temp) + " C");
            setWeatherIcon(daily.getWeather().get(0).getIcon());
        });
    }

    private void setWeatherIcon(String iconId) {
        String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
        Glide.with(getActivity())
                .load(url)
                .override(400, 450)
                .into(weatherIcon);

    }
}