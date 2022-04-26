package pl.damianj.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private Integer pageNumber;
    private TextView dayTextView;
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
        dayTextView = root.findViewById(R.id.day_text_view);
        observeWeatherPage();
        Log.i("XD", "XD");
        return root;
    }

    public void observeWeatherPage() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherForecast -> {
            dayTextView.setText(weatherForecast.getDaily().get(pageNumber).toString());
        });
    }
}