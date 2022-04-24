package pl.damianj.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.repository.WeatherApiRepository;
import pl.damianj.weatherapp.service.WeatherDataService;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataFragment extends Fragment {

    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);
    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView geolocationTextView;
    private TextView cityNameTextView;
    private WeatherDataViewModel viewModel;

    public static WeatherDataFragment newInstance(String arg) {
        WeatherDataFragment fragment = new WeatherDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getCityName().observe(getViewLifecycleOwner(), cityName -> {
            WeatherApiRepository weatherApiRepository = WeatherApiRepository.getInstance();
            weatherApiRepository.getWeatherData(viewModel.getCoord(), viewModel);
        });
        observeWeatherData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.weather_data_fragment, container, false);
        pressureTextView = root.findViewById(R.id.pressure_text_view);
        temperatureTextView = root.findViewById(R.id.temp_text_view);
        geolocationTextView = root.findViewById(R.id.geolocation_text_view);
        cityNameTextView = root.findViewById(R.id.city_text_view);
        return root;
    }

    private void observeWeatherData() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            temperatureTextView.setText(weatherData.getMain().getTemp().toString());
            geolocationTextView.setText(weatherData.getCoord().getLat() + weatherData.getCoord().getLon().toString());
            cityNameTextView.setText(weatherData.getName());
        });
    }
}