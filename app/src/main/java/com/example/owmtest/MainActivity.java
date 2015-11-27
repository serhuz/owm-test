package com.example.owmtest;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    private static final String TAG = "main";
    private static final String KEY_API_KEY = "api_key";

    private String mApiKey;

    @Bind(R.id.instruction)
    TextView instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mApiKey = savedInstanceState.getString(KEY_API_KEY);
        } else {
            mApiKey = retrieveApiKey();
            if (mApiKey == null) throw new IllegalStateException("no key found in app manifest");
        }
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
        instruction.setVisibility(View.GONE);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, DataFragment.create(query, mApiKey), "weather")
                .commit();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fragment fragment = getFragmentManager().findFragmentByTag("weather");
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }
}
