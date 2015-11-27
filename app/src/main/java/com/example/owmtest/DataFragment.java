package com.example.owmtest;


import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.owmtest.retrofit.OwmApi;
import com.example.owmtest.retrofit.entities.Clouds;
import com.example.owmtest.retrofit.entities.Coord;
import com.example.owmtest.retrofit.entities.Main;
import com.example.owmtest.retrofit.entities.OwmResponse;
import com.example.owmtest.retrofit.entities.Precipitation;
import com.example.owmtest.retrofit.entities.Sys;
import com.example.owmtest.retrofit.entities.Weather;
import com.example.owmtest.retrofit.entities.Wind;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RestAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class DataFragment extends Fragment implements GoogleMap.OnMapLoadedCallback {

    private static final String TAG = "data";
    private static final String KEY_QUERY = "query";
    private static final String KEY_API_KEY = "key";
    public static final String HH_MM = "HH:mm";

    private OwmApi mApi;
    private String mQuery;
    private String mApiKey;

    private GoogleMap mMap;
    private LatLngBounds mBounds;

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.scroll_view)
    ScrollView scrollView;

    @Bind(R.id.error)
    TextView error;

    @Bind(R.id.location_name)
    TextView location;

    @Bind(R.id.conditions)
    TextView conditions;

    @Bind(R.id.desc)
    TextView description;

    @Bind(R.id.cloudiness)
    TextView cloudiness;

    @Bind(R.id.pic)
    ImageView pic;

    @Bind(R.id.weather_temp)
    TextView temp;

    @Bind(R.id.weather_pressure)
    TextView pressure;

    @Bind(R.id.weather_humidity)
    TextView humidity;

    @Bind(R.id.sunrise)
    TextView sunrise;

    @Bind(R.id.sunset)
    TextView sunset;

    @Bind(R.id.wind_speed)
    TextView windSpeed;

    @Bind(R.id.wind_direction)
    TextView windDirection;

    @Bind(R.id.precip_view)
    CardView precipView;

    @Bind(R.id.precipitation)
    TextView precipitation;

    public static DataFragment create(@NonNull String query, @NonNull String key) {
        Bundle args = new Bundle();
        args.putString(KEY_QUERY, query);
        args.putString(KEY_API_KEY, key);

        DataFragment fragment = new DataFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mQuery = args.getString(KEY_QUERY);
        mApiKey = args.getString(KEY_API_KEY);

        mApi = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://api.openweathermap.org")
                .build()
                .create(OwmApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mApi.searchWeatherForCity(mQuery, "metric", mApiKey, Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<OwmResponse>() {
                            @Override
                            public void call(OwmResponse owmResponse) {
                                displayData(owmResponse);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "Could not get data", throwable);
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.GONE);
                                error.setVisibility(View.VISIBLE);
                            }
                        }
                );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
                childFragmentManager.setAccessible(true);
                childFragmentManager.set(this, null);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager()
                .beginTransaction()
                .remove(getMapFragment())
                .commit();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMapLoaded() {
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(
                mBounds,
                convertDpToPixel(20)
        );
        mMap.moveCamera(update);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(9.5f));
    }

    private void displayData(OwmResponse response) {
        displayLocationName(
                response.getName(), response.getSys().getCountry(), response.getCoord()
        );
        displayWeather(response.getWeather(), response.getClouds());
        displayMain(response.getMain());
        displayWind(response.getWind());
        displayPrecipitation(response.getRain(), response.getSnow());
        displayAstronomicalData(response.getSys());

        progressBar.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void displayAstronomicalData(Sys sys) {
        Locale locale = Locale.getDefault();
        DateTime sunriseTime = new DateTime(sys.getSunrise() * 1000);
        sunrise.setText(String.format(
                locale,
                "%s %s",
                getString(R.string.sunrise),
                sunriseTime.toString(HH_MM)
        ));

        DateTime sunsetTime = new DateTime(sys.getSunset() * 1000);
        sunset.setText(String.format(
                locale,
                "%s %s",
                getString(R.string.sunset),
                sunsetTime.toString(HH_MM)
        ));
    }

    private void displayPrecipitation(Precipitation rain, Precipitation snow) {
        String precipitationType;
        Double volume;
        if (rain == null && snow == null) {
            precipView.setVisibility(View.GONE);
            return;
        } else if (rain != null) {
            precipitationType = getString(R.string.rain);
            volume = rain.getVolume();
        } else {
            precipitationType = getString(R.string.snow);
            volume = snow.getVolume();
        }

        precipitation.setText(String.format(
                "%s %.1f %s",
                precipitationType,
                volume,
                getString(R.string.unit_mm)
        ));
    }

    private void displayWind(Wind wind) {
        windSpeed.setText(String.format(
                "%s %.1f %s",
                getString(R.string.wind_speed),
                wind.getSpeed(),
                getString(R.string.wind_speed_kph)
        ));

        windDirection.setText(String.format(
                "%s %.1f%s (%s)",
                getString(R.string.wind_direction),
                wind.getDeg(),
                "°",
                DirectionConverter.convertToCardinalDirection(wind.getDeg())
        ));

    }

    private void displayMain(Main main) {
        Locale locale = Locale.getDefault();
        temp.setText(String.format(
                locale,
                "%s %.1f %s",
                getString(R.string.temp),
                main.getTemp(),
                getString(R.string.temp_c)
        ));
        humidity.setText(String.format(
                locale,
                "%s %d %%",
                getString(R.string.humidity),
                main.getHumidity()
        ));
        pressure.setText(String.format(
                locale,
                "%s %.1f %s",
                getString(R.string.pressure),
                main.getPressure(),
                getString(R.string.pressure_bar)
        ));
    }

    private void displayWeather(List<Weather> weather, Clouds clouds) {
        Weather w = weather.get(0);
        conditions.setText(w.getMain());
        description.setText(w.getDescription());
        cloudiness.setText(String.format(
                Locale.getDefault(),
                "%s %d %%",
                getString(R.string.cloudiness),
                clouds.getAll()
        ));

        Picasso.with(pic.getContext())
                .load(Uri.parse(String.format(
                        "http://api.openweathermap.org/img/w/%s.png",
                        w.getIcon()
                )))
                .fit()
                .centerInside()
                .into(pic);
    }

    private void displayLocationName(String city, String country, Coord coord) {
        location.setText(String.format(Locale.getDefault(), "%s, %s", city, country));
        addPosition(coord.getLat(), coord.getLon());
    }

    private void addPosition(Double lat, Double lon) {
        mMap = getMapFragment().getMap();

        UiSettings settings = mMap.getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setCompassEnabled(false);

        LatLng position = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(position));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(position);

        mBounds = builder.build();
        mMap.setOnMapLoadedCallback(this);
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

    private int convertDpToPixel(int dp) {
        return (int) (dp * getActivity().getResources().getDisplayMetrics().density + 0.5f);
    }
}
