package com.ardigitalsolutions.urbandesitv.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ardigitalsolutions.urbandesitv.R;
import com.ardigitalsolutions.urbandesitv.view.allFragments.ContactFragment;
import com.ardigitalsolutions.urbandesitv.view.allFragments.NewsFragment;
import com.ardigitalsolutions.urbandesitv.view.allFragments.SocialFragment;
import com.ardigitalsolutions.urbandesitv.databinding.ActivityDashBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashBinding binding = ActivityDashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView bottom = binding.dashBottomBar;
        bottom.setOnNavigationItemSelectedListener(this::bottomClick);
        switch (getIntent().getStringExtra("click")) {
            case "news":
                bottom.setSelectedItemId(R.id.bottom_news);
                break;
            case "social":
                bottom.setSelectedItemId(R.id.bottom_social);
                break;
            case "contact":
                bottom.setSelectedItemId(R.id.bottom_contact);
                break;
        }
        binding.dashBack.setOnClickListener((View v) -> onBackPressed());
    }

    private void setupFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.dash_frame, fragment).commit();
    }

    public boolean bottomClick(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.bottom_news:
                fragment = new NewsFragment();
                break;
            case R.id.bottom_social:
                fragment = new SocialFragment();
                break;
            case R.id.bottom_contact:
                fragment = new ContactFragment();
                break;
            case R.id.bottom_live:
                onBackPressed();
                break;
        }
        if (fragment != null)
            setupFragment(fragment);
        return true ;
    }
}