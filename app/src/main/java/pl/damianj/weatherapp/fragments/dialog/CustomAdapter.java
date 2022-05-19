package pl.damianj.weatherapp.fragments.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import pl.damianj.weatherapp.R;
import pl.damianj.weatherapp.storage.StorageService;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

class CustomAdapter extends ArrayAdapter<String> {
    private OnDeleteButtonListener listener;
    public interface OnDeleteButtonListener {
        public void onDeleteButtonClick();
    }

    private final Activity context;
    private List<String> cities;

    public CustomAdapter(Activity context, List<String> cities, CustomDialog customDialog) {
        super(context, R.layout.custom_item_layout, cities);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.cities = cities;
        listener = customDialog;
    }


    @Nullable
    @Override
    public String getItem(int position) {
        return cities.get(position);
    }

    public View getView(int position, View view, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_item_layout, null, true);

        TextView cityName = (TextView) rowView.findViewById(R.id.city_text);
        ImageView deleteButton = (ImageView)rowView.findViewById(R.id.delete_button);

        cityName.setText(cities.get(position));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, cities.get(position) + " removed", Toast.LENGTH_SHORT).show();
                StorageService.getInstance().removeCity(cities.get(position));
                listener.onDeleteButtonClick();
            }
        });
        return rowView;

    }
}