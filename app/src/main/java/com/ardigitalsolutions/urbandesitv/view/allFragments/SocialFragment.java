package com.ardigitalsolutions.urbandesitv.view.allFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardigitalsolutions.urbandesitv.R;
import com.ardigitalsolutions.urbandesitv.databinding.FragmentSocialBinding;

public class SocialFragment extends Fragment {


    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSocialBinding binding = FragmentSocialBinding.inflate(inflater, container, false);
        binding.socialFace.setOnClickListener(this::socialClick);
        binding.socialYou.setOnClickListener(this::socialClick);
        binding.socialInsta.setOnClickListener(this::socialClick);
        return binding.getRoot();
    }

    private void socialClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.social_face:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/urbandesitv/"));
                break;
            case R.id.social_insta:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/urban_desi_tv/"));
                break;
            case R.id.social_you:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC20itWP2ELMztiEQFouE3mw"));
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}