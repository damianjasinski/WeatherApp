package pl.damianj.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;


public class AdditionalDataFragment extends Fragment {

    private TextView windTextView;
    private TextView humidityTextView;
    private TextView visibilityTextView;
    private WeatherDataViewModel viewModel;

    public AdditionalDataFragment() {
    }

    public static AdditionalDataFragment newInstance(String param1, String param2) {
        AdditionalDataFragment fragment = new AdditionalDataFragment();
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
        View root = inflater.inflate(R.layout.additional_fragment, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        windTextView = root.findViewById(R.id.wind_text_view);
        humidityTextView = root.findViewById(R.id.humidity_text_view);
        visibilityTextView = root.findViewById(R.id.visibility_text_view);
        observeWeatherForecast();
        observeError();
        return root;
    }

    private void observeWeatherForecast() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherForecast -> {
            windTextView.setText(String.format("%.2f", weatherForecast.getCurrent().getWindSpeed()) + viewModel.getUnitSystem().get("wind"));
            humidityTextView.setText(weatherForecast.getCurrent().getHumidity().toString() + "%");
            int visibility = weatherForecast.getCurrent().getVisibility()/1000;
            visibilityTextView.setText(Integer.toString(visibility) + "km");
         });
    }

    private void observeError() {
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            windTextView.setText("");
            humidityTextView.setText("");
            visibilityTextView.setText("");
        });
    }
}