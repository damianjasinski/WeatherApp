package pl.damianj.weatherapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import pl.damianj.weatherapp.fragments.AdditionalDataFragment;
import pl.damianj.weatherapp.fragments.ConfigurationFragment;
import pl.damianj.weatherapp.fragments.WeatherDataFragment;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.config_fragment, ConfigurationFragment.class, null)
                    .add(R.id.additional_data_fragment, AdditionalDataFragment.class, null)
                    .commit();
        }
    }

    // Możliwość wywołania metody z activity poprzez fragment
//    @Override
//    public void onChange() {
//        fragmentManager.beginTransaction()
//                .setReorderingAllowed(true)
//                .replace(R.id.config_fragment, WeatherDataFragment.class,null)
//        .commit();
//    }
}