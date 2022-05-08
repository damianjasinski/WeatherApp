package pl.damianj.weatherapp.fragments.tablet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.util.Locale;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.model.oneapi.Daily;
import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyFragmentTabletItem extends Fragment {

    private Integer day;
    private WeatherDataViewModel viewModel;
    private TextView dayTextView;
    private TextView tempTextView;
    private TextView windTextView;
    private TextView humidTextView;
    private TextView pressTextView;
    private ImageView weatherIcon;

    public WeeklyFragmentTabletItem() {
        // Required empty public constructor
    }
    public static WeeklyFragmentTabletItem newInstance(Integer day) {
        WeeklyFragmentTabletItem fragment = new WeeklyFragmentTabletItem();
        Bundle args = new Bundle();
        args.putInt("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getInt("day");
        }
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.weekly_fragment_tablet_item, container, false);
        dayTextView = root.findViewById(R.id.day_name_text);
        tempTextView = root.findViewById(R.id.temperature_text_view);
        windTextView = root.findViewById(R.id.wind_text_view);
        humidTextView = root.findViewById(R.id.humidity_text_view);
        pressTextView = root.findViewById(R.id.pressure_text_view);
        weatherIcon = root.findViewById(R.id.weather_icon2);
        setDayInfo();
        return root;
    }


    private void setDayInfo() {
        Daily daily = viewModel.getWeatherData().getValue().getDaily().get(day + 1);
        LocalDate today = LocalDate.now().plusDays(day);
        int temp = daily.getTemp().getDay().intValue();
        tempTextView.setText(temp + " C\n" + daily.getWeather().get(0).getMain());
        tempTextView.setText(temp + " C\n" + daily.getWeather().get(0).getMain());
        windTextView.setText(daily.getWindSpeed().toString() + "m/s");
        humidTextView.setText("Humid. " + daily.getHumidity().toString() + "%");
        pressTextView.setText(daily.getPressure().toString() + "hpA");
        setDayName(today.getDayOfWeek().toString());
        setWeatherIcon(daily.getWeather().get(0).getIcon());
    }

    private void setWeatherIcon(String iconId) {
        String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
        Glide.with(getActivity())
                .load(url)
                .override(350, 400)
                .into(weatherIcon);
    }

    private void setDayName(String dayName ) {
        if (day == 0) {
            dayTextView.setText("Tommorow");
        }
        else {
            dayName = dayName.toLowerCase(Locale.ROOT);
            String dayTitle = dayName.substring(0, 1).toUpperCase(Locale.ROOT);
            dayTextView.setText(dayTitle + dayName.substring(1));
        }
    }

}
