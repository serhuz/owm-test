package com.example.owmtest;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import com.example.owmtest.retrofit.OwmApi;
import com.example.owmtest.retrofit.entities.OwmResponse;

import butterknife.ButterKnife;
import retrofit.RestAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    private static final String TAG = "main";
    public static final String KEY_API_KEY = "api_key";

    private OwmApi mApi;
    private String mApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mApiKey = savedInstanceState.getString(KEY_API_KEY);
        } else {
            mApiKey = retrieveApiKey();
            if (mApiKey == null) throw new IllegalStateException("no key found in app manifest");
        }

        ButterKnife.bind(this);

        mApi = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://api.openweathermap.org")
                .build()
                .create(OwmApi.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(KEY_API_KEY, mApiKey);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mApi.searchWeatherForCity(query, "metric", mApiKey)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<OwmResponse>() {
                            @Override
                            public void call(OwmResponse owmResponse) {
                                // TODO: show
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "Could not get data", throwable);
                            }
                        }
                );

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private String retrieveApiKey() {
        ApplicationInfo info;
        try {
            info = getPackageManager().getApplicationInfo(
                    MainActivity.this.getPackageName(),
                    PackageManager.GET_META_DATA
            );
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "NameNotFoundException", e);
            return null;
        }

        Bundle metaData = info.metaData;

        return metaData.getString("com.example.owmtest.owm.key");
    }
}
