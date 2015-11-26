package com.example.owmtest.weather;


import android.app.Fragment;
import android.app.FragmentManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.owmtest.R;
import com.example.owmtest.retrofit.OwmApi;
import com.example.owmtest.retrofit.entities.OwmResponse;
import com.example.owmtest.retrofit.entities.Weather;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements GoogleMap.OnMapLoadedCallback {

    private static final String TAG = "weather";

    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";

    ImageView pic;

    private OwmApi api;
    private double lat;
    private double lng;
    private LatLngBounds mBounds;
    private GoogleMap map;

    public static WeatherFragment create(Location location) {
        Bundle args = new Bundle();

        args.putDouble(KEY_LAT, location.getLatitude());
        args.putDouble(KEY_LNG, location.getLongitude());

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        lat = args.getDouble(KEY_LAT);
        lng = args.getDouble(KEY_LNG);

        api = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient())
                .setEndpoint("http://api.openweathermap.org")
                .build()
                .create(OwmApi.class);

        api.getWeatherForLocation(lat, lng, "metric", "3263c514ac3b66d8dbba2cca67766b87")
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<OwmResponse>() {
                            @Override
                            public void call(OwmResponse owmResponse) {


                                List<Weather> weather = owmResponse.getWeather();


                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "Error loading data", throwable);
                            }
                        }
                );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager()
                .beginTransaction()
                .remove(getMapFragment())
                .commit();
    }

    private void loadPic(String icon) {
        Picasso.with(getActivity())
                .load(Uri.parse(String.format("http://api.openweathermap.org/img/w/%s.png", icon)))
                .into(pic);
    }

    private void addPosition() {
        map = getMapFragment().getMap();

        UiSettings settings = map.getUiSettings();
        settings.setRotateGesturesEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setCompassEnabled(true);

        LatLng position = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions().position(position).title("You are here"));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(position);

        mBounds = builder.build();
        map.setOnMapLoadedCallback(this);
    }

    private MapFragment getMapFragment() {
        FragmentManager fm;

        Log.d(TAG, "sdk: " + Build.VERSION.SDK_INT);
        Log.d(TAG, "release: " + Build.VERSION.RELEASE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "using getFragmentManager");
            fm = getFragmentManager();
        } else {
            Log.d(TAG, "using getChildFragmentManager");
            fm = getChildFragmentManager();
        }

        return (MapFragment) fm.findFragmentById(R.id.map);
    }

    @Override
    public void onMapLoaded() {
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(
                mBounds,
                convertDpToPixel(20)
        );
        map.moveCamera(update);
        map.animateCamera(CameraUpdateFactory.zoomTo(9.5f));
    }

    private int convertDpToPixel(int dp) {
        return (int) (dp * getActivity().getResources().getDisplayMetrics().density + 0.5f);
    }
}
