package com.ardigitalsolutions.urbandesitv.view.allFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardigitalsolutions.urbandesitv.databinding.FragmentContactBinding;


public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentContactBinding binding = FragmentContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}