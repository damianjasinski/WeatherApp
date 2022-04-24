package pl.damianj.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import pl.damianj.weatherapp.fragments.ConfigurationFragment;
import pl.damianj.weatherapp.fragments.WeatherDataFragment;

public class MainActivity extends AppCompatActivity {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.config_fragment, ConfigurationFragment.class, null)
                    .add(R.id.details_fragment, WeatherDataFragment.class, null)
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