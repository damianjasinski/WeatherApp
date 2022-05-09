package pl.damianj.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;


public class PrimaryDataFragment extends Fragment {

    private WeatherDataViewModel viewModel;
    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView coordsTextView;
    private TextView timeTextView;


    public PrimaryDataFragment() {
    }

    public static PrimaryDataFragment newInstance(String param1, String param2) {
        PrimaryDataFragment fragment = new PrimaryDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.primary_fragment, container, false);
        coordsTextView = root.findViewById(R.id.humidity_text_view);
        timeTextView = root.findViewById(R.id.temperature_text_view);
        pressureTextView = root.findViewById(R.id.pressure_text_view);
        temperatureTextView = root.findViewById(R.id.wind_text_view);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        observeWeatherForecast();
        observeError();
        return root;
    }

    private void observeWeatherForecast() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherForecast -> {
            long epochTime = weatherForecast.getCurrent().getDt();
            int temp = weatherForecast.getCurrent().getTemp().intValue();
            LocalDateTime requestDateTime = LocalDateTime.ofEpochSecond(epochTime, 0, OffsetDateTime.now().getOffset());
            String day = String.format("%02d", requestDateTime.getDayOfMonth());
            String month = String.format("%02d", requestDateTime.getMonthValue());
            String hour = String.format("%02d",requestDateTime.getHour());
            String minute = String.format("%02d",requestDateTime.getMinute());
            timeTextView.setText(hour + ":" + minute
                    + "\n" + day + "/" + month);
            coordsTextView.setText(weatherForecast.getLat() + "\n" + weatherForecast.getLon().toString());
            temperatureTextView.setText(Integer.toString(temp) + " " + viewModel.getUnitSystem().get("temp") + "\n" + weatherForecast.getCurrent().getWeather().get(0).getMain());
            pressureTextView.setText(weatherForecast.getCurrent().getPressure().toString() + " hPa");
        });
    }

    private void observeError() {
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            coordsTextView.setText("");
            timeTextView.setText("");
            temperatureTextView.setText("");
            pressureTextView.setText("");
        });
    }
}