package com.ardigitalsolutions.urbandesitv.view.allFragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.ardigitalsolutions.urbandesitv.R;
import com.ardigitalsolutions.urbandesitv.databinding.FragmentFullNewsBinding;

public class FullNewsFragment extends Fragment {
    private String html, title;
    private boolean flag;

    public FullNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        html = getArguments().getString("content");
        title = getArguments().getString("title");
        flag = getArguments().getBoolean("flag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFullNewsBinding binding = FragmentFullNewsBinding.inflate(inflater, container, false);
        WebView webView = binding.fullWebView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            binding.fullTitle.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT));
        else
            binding.fullTitle.setText(Html.fromHtml(title));
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        getActivity().findViewById(R.id.dash_back).setOnClickListener((View v) -> {
            if (!flag && getFragmentManager() != null)
                getFragmentManager().popBackStackImmediate();
        });

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        return binding.getRoot();
    }
}