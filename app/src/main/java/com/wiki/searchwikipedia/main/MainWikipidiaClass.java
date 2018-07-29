package com.wiki.searchwikipedia.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.wiki.searchwikipedia.R;
import com.wiki.searchwikipedia.eventbus.SearchLaunchEvent;
import com.wiki.searchwikipedia.eventbus.WikiPageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainWikipidiaClass extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SearchScreenFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(SearchLaunchEvent event){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SearchScreenFragment())
                .addToBackStack(null)
                .commit();
    }

    @Subscribe
    public void onEvent(WikiPageEvent event){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, WikiWebViewFragment.newInstance(event.getTitle()))
                .addToBackStack(null)
                .commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
