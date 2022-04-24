package pl.damianj.weatherapp.fragments;

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
import android.widget.EditText;
import android.widget.TextView;

import pl.damianj.weatherapp.R;
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
    private EditText cityNameText;
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
        cityNameText = root.findViewById(R.id.input_city);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        this.weatherApiRepository = WeatherApiRepository.getInstance();
        setEditTextListener(weatherApiRepository);
        return root;
    }

    private void setEditTextListener (WeatherApiRepository weatherApiRepository) {
        cityNameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    weatherApiRepository.getGeoCity(textView.getText().toString(), viewModel);
                    return false;
                }
                return true;
            }
        });
    }

}