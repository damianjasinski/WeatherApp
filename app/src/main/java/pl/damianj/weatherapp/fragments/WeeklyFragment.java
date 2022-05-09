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

import java.time.LocalDate;
import java.util.Locale;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.model.oneapi.Daily;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private Integer pageNumber;
    private TextView dayTextView;
    private TextView tempTextView;
    private TextView windTextView;
    private TextView humidTextView;
    private TextView pressureTextView;

    private TextView tempTextHint;
    private TextView windTextHint;
    private TextView humidTextHint;
    private TextView pressureTextHint;

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
        tempTextView = root.findViewById(R.id.temperature_text_view);
        windTextView = root.findViewById(R.id.wind_text_view);
        humidTextView = root.findViewById(R.id.humidity_text_view);
        pressureTextView = root.findViewById(R.id.pressure_text_view);
        tempTextHint = root.findViewById(R.id.temperature_text_hint);
        windTextHint = root.findViewById(R.id.wind_text_hint);
        humidTextHint = root.findViewById(R.id.humidity_text_hint);
        pressureTextHint = root.findViewById(R.id.pressure_text_hint);
        weatherIcon = root.findViewById(R.id.weather_icon2);
        observeWeatherPage();
        return root;
    }

    public void observeWeatherPage() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherForecast -> {
            Daily daily = weatherForecast.getDaily().get(pageNumber + 1) ;
            LocalDate today = LocalDate.now().plusDays(pageNumber);
            int temp = daily.getTemp().getDay().intValue();
            setDayName(today.getDayOfWeek().toString());
            setWeatherIcon(daily.getWeather().get(0).getIcon());
            tempTextView.setText(temp + " " + viewModel.getUnitSystem().get("temp") + daily.getWeather().get(0).getMain());
            windTextView.setText(Double.toString(daily.getWindSpeed()) + viewModel.getUnitSystem().get("temp"));
            humidTextView.setText(daily.getHumidity().toString() + "%");
            pressureTextView.setText(daily.getPressure().toString() + "hpA");
            setHintsVisible();
        });
    }

    private void setDayName(String day ) {
        if (pageNumber == 0) {
            dayTextView.setText("Tommorow");
        }
        else {
            day = day.toLowerCase(Locale.ROOT);
            String dayTitle = day.substring(0, 1).toUpperCase(Locale.ROOT);
            dayTextView.setText(dayTitle + day.substring(1));
        }
    }

    private void setWeatherIcon(String iconId) {
        String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
        Glide.with(getActivity())
                .load(url)
                .override(350, 400)
                .into(weatherIcon);
    }

    private void setHintsVisible() {
        tempTextHint.setVisibility(View.VISIBLE);
        windTextHint.setVisibility(View.VISIBLE);
        humidTextHint.setVisibility(View.VISIBLE);
        pressureTextHint.setVisibility(View.VISIBLE);
    }
}