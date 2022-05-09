package pl.damianj.weatherapp.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;


import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.storage.StorageService;
import pl.damianj.weatherapp.util.UnitConverter;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class CustomDialog extends DialogFragment {

    private WeatherDataViewModel viewModel;
    private StorageService storageService;
    List<String> savedCities;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        storageService = StorageService.getInstance();
        savedCities = new ArrayList<>(storageService.getAllKeys().stream().filter(x -> !x.equals("LastSelected")).collect(Collectors.toList()));

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogeTheme);
        builder.setPositiveButton("Add current city", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                StorageService storageService = StorageService.getInstance();
                if (viewModel.getWeatherData().getValue() == null) {
                    Toast.makeText(requireActivity(), "No city selected!", Toast.LENGTH_SHORT).show();
                } else if (viewModel.getWeatherData().getValue() != null && !savedCities.contains(viewModel.getWeatherData().getValue().getCityName())) {
                    storageService.saveToFile(viewModel.getWeatherData().getValue());
                    Toast.makeText(requireActivity(), "City " + viewModel.getWeatherData().getValue().getCityName() + " saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), viewModel.getWeatherData().getValue().getCityName() + " is already saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Change Unit System", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (viewModel.isUnitSystemMetric()) {
                    UnitConverter.getUnitCoverter().convertMetricToImperial(viewModel);
                    viewModel.setUnitSystemMetric(false);
                }
                else {
                    UnitConverter.getUnitCoverter().convertImperialToMetric(viewModel);
                    viewModel.setUnitSystemMetric(true);
                }
            }
        });

        builder.setTitle("Select City")
                .setItems(savedCities.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        WeatherForecast selectedCity = storageService.loadWeatherData(savedCities.get(which));
                        viewModel.setWeatherData(selectedCity);
                    }

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

