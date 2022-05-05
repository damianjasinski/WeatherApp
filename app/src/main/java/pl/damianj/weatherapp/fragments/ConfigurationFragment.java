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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.fragments.dialog.CustomDialog;
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
    private TextView cityNameTextView;
    private ImageView weatherIcon;
    private ImageButton cityConfig;
    private WeatherDataViewModel viewModel;
    private WeatherApiRepository weatherApiRepository;

    private CustomDialog cityDialog;


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
        weatherIcon = root.findViewById(R.id.weather_icon);
        cityNameTextView = root.findViewById(R.id.city_text_view);
        cityConfig = root.findViewById(R.id.dialog_button);
        cityDialog =  new CustomDialog();
        setEditTextListener(weatherApiRepository);
        setConfigClickListener();
        observeError();
        observeCityName();
        observeWeatherData();
        return root;
    }

    private void setConfigClickListener() {
        cityConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityDialog.show(
                        getChildFragmentManager(), "CityDialog");
            }
        });
    }

    private void setEditTextListener(WeatherApiRepository weatherApiRepository) {
        cityInputTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) {
                    if (!textView.getText().toString().equals(cityNameTextView.getText().toString())) {
                        weatherApiRepository.getCityCoords(textView.getText().toString(), viewModel);
                    }
                    hideSoftKeyboard(getActivity());
                    requireView().clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
            //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception e) {
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
            cityNameTextView.setText(weatherData.getCityName());
            cityInputTextView.setText(weatherData.getCityName());
            setWeatherIcon(weatherData.getCurrent().getWeather().get(0).getIcon());
        });
    }

    private void observeError() {
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            this.cityNameTextView.setText(error);
        });
    }

    private void setWeatherIcon(String iconId) {
        String url = "https://openweathermap.org/img/wn/" + iconId + "@4x.png";
        Glide.with(getActivity())
                .load(url)
                .override(200, 250)
                .into(weatherIcon);
    }
}