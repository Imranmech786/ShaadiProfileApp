package com.imran.shaaditestapp.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.imran.shaaditestapp.listener.OnItemClickListener;
import com.imran.shaaditestapp.R;
import com.imran.shaaditestapp.adapter.UserProfileAdapter;
import com.imran.shaaditestapp.model.Result;
import com.imran.shaaditestapp.utils.Network;
import com.imran.shaaditestapp.utils.StateData;
import com.imran.shaaditestapp.viewModel.MainActivityViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements OnItemClickListener {

    @Inject
    MainActivityViewModel mainActivityViewModel;

    private RecyclerView recyclerView;
    private UserProfileAdapter userProfileAdapter;
    private ProgressBar progressBar;
    private TextView retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeWidgets();
        setUpActionBar();
        callListApi();
        mainActivityViewModel.getJsonResponseLiveData().observe(this, new Observer<StateData<List<Result>>>() {
            @Override
            public void onChanged(@Nullable StateData<List<Result>> jsonResponseStateData) {

                assert jsonResponseStateData != null;
                if (jsonResponseStateData.getStatus() == StateData.DataStatus.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    userProfileAdapter = new UserProfileAdapter(MainActivity.this, jsonResponseStateData.getData(), MainActivity.this);
                    recyclerView.setAdapter(userProfileAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initializeWidgets() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);
        retry = findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callListApi();
            }
        });
    }

    private void callListApi() {
        if (Network.isConnected(getApplicationContext())) {
            retry.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            mainActivityViewModel.getListFromApi(10);
        } else {
            progressBar.setVisibility(View.GONE);
            retry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position, Object movie) {
        switch (view.getId()) {
            case R.id.decline_layout:
                userProfileAdapter.getmList().remove(position);
                userProfileAdapter.notifyItemRemoved(position);
                break;
        }
    }

    protected void setUpActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_title));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
