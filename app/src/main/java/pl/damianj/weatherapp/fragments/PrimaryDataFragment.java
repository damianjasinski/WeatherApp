package pl.damianj.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        observeWeatherData();
        observeError();
        return root;
    }

    private void observeWeatherData() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            String day = String.format("%02d", weatherData.getCurrent().getRequestTime().getDayOfMonth());
            String month = String.format("%02d", weatherData.getCurrent().getRequestTime().getMonthValue());
            int temp = weatherData.getCurrent().getTemp().intValue();
            timeTextView.setText(weatherData.getCurrent().getRequestTime().getHour() + ":" + weatherData.getCurrent().getRequestTime().getMinute()
                    + "\n" + day + "/" + month);
            coordsTextView.setText(weatherData.getLat() + "\n" + weatherData.getLon().toString());
            temperatureTextView.setText(Integer.toString(temp) + " C" + "\n" + weatherData.getCurrent().getWeather().get(0).getMain());
            pressureTextView.setText(weatherData.getCurrent().getPressure().toString() + " hPa");
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