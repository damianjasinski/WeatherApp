package pl.damianj.weatherapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.model.Sys;
import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.repository.WeatherApiRepository;
import pl.damianj.weatherapp.service.WeatherDataService;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigurationFragment extends Fragment {


    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);
    private EditText cityInputTextView;
    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView cityNameTextView;
    private TextView coordsTextView;
    private TextView timeTextView;
    private ImageView weatherIcon;
    private WeatherDataViewModel viewModel;
    private WeatherApiRepository weatherApiRepository;

    public ConfigurationFragment() {
    }

    public static ConfigurationFragment newInstance(String param1, String param2) {
        ConfigurationFragment fragment = new ConfigurationFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.configuration_fragment, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        weatherApiRepository = WeatherApiRepository.getInstance();
        cityInputTextView = root.findViewById(R.id.input_city);
        coordsTextView = root.findViewById(R.id.coords_text_view);
        timeTextView = root.findViewById(R.id.time_text_view);
        pressureTextView = root.findViewById(R.id.pressure_text_view);
        weatherIcon = root.findViewById(R.id.weather_icon);
        temperatureTextView = root.findViewById(R.id.temp_text_view);
        cityNameTextView = root.findViewById(R.id.city_text_view);
        setEditTextListener(weatherApiRepository);
        observeError();
        observeCityName();
        observeWeatherData();
        return root;
    }

    private void setEditTextListener(WeatherApiRepository weatherApiRepository) {
        cityInputTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) {
                    if ( !textView.getText().toString().equals(cityNameTextView.getText().toString())) {
                        weatherApiRepository.getGeoCity(textView.getText().toString(), viewModel);
                    }
                    hideSoftKeyboard(getActivity());
                    return true;
                }

                return false;
            }
        });
    }
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
            //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void observeCityName() {
        viewModel.getCityName().observe(getViewLifecycleOwner(), cityName -> {
            weatherApiRepository.getWeatherData(viewModel.getCoord(), viewModel);
        });
    }

    private void observeWeatherData() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            cityNameTextView.setText(cityInputTextView.getText().toString());
            coordsTextView.setText(weatherData.getLat() + "\n" + weatherData.getLon().toString());
            timeTextView.setText(weatherData.getCurrent().getRequestTime().getHour() + ":" + weatherData.getCurrent().getRequestTime().getMinute()
                    + "\n" + weatherData.getCurrent().getRequestTime().getDayOfMonth() + "/" + weatherData.getCurrent().getRequestTime().getMonth());
            int temp = weatherData.getCurrent().getTemp().intValue();
            temperatureTextView.setText(Integer.toString(temp) + " C");
            pressureTextView.setText(weatherData.getCurrent().getPressure().toString() + " hPa");
            setWeatherIcon(weatherData.getCurrent().getWeather().get(0).getIcon());

        });
    }

    private void setWeatherIcon(String iconId) {
        String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
        Glide.with(getActivity())
                .load(url)
                .override(200, 250)
                .into(weatherIcon);

    }

    private void observeError() {
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            this.cityNameTextView.setText(error);
            coordsTextView.setText("");
            timeTextView.setText("");
            temperatureTextView.setText("");
            pressureTextView.setText("");
        });
    }


}