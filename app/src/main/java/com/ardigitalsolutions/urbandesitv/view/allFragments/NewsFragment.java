package com.ardigitalsolutions.urbandesitv.view.allFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardigitalsolutions.urbandesitv.service.allModels.news.NewsModel;
import com.ardigitalsolutions.urbandesitv.view.allAdapters.NewsAdapter;
import com.ardigitalsolutions.urbandesitv.service.apiClient.Client;
import com.ardigitalsolutions.urbandesitv.R;
import com.ardigitalsolutions.urbandesitv.databinding.FragmentNewsBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {
    private Context context;
    private ShimmerFrameLayout shimmerFrameLayout;
    private int pages, page = 1;
    private NewsAdapter adapter;
    private boolean isLoading = true;
    private FragmentNewsBinding binding;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        shimmerFrameLayout = binding.newsShimmer;
        RecyclerView recyclerView = binding.newsRecycler;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        adapter = new NewsAdapter(this::callFragment);
        recyclerView.setAdapter(adapter);
        callNews();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                if (dy > 0)
                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >=
                            totalItemCount && page <= pages)
                        callNews();

            }
        });
        return binding.getRoot();
    }

    private void callNews() {
        isLoading = true;
        Map<String, Integer> map = new HashMap<>();
        map.put("page", page);
        List<NewsModel> list = new ArrayList<>();
        if (page > 1)
            binding.newsProgress.setVisibility(View.VISIBLE);
        new Client(context).getNews((List<?> content, String error, String pag) -> {
            isLoading = false;
            if (content != null) {
                if (content.size() > 0) {
                    for (int i = 0; i < content.size(); i++)
                        list.add((NewsModel) content.get(i));
                    adapter.updateList(list);
                    page++;
                    pages = Integer.parseInt(pag);
                }
            } else {
                if (error.equals("NetCon"))
                    Snackbar.make(binding.getRoot().getRootView(), "Please check your internet connection", BaseTransientBottomBar.LENGTH_LONG).show();
            }
            if (shimmerFrameLayout.getVisibility() == View.VISIBLE) {
               // shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
            if (binding.newsProgress.getVisibility() == View.VISIBLE)
                binding.newsProgress.setVisibility(View.GONE);
        }, map);
    }

    private void callFragment(String content, String title) {
        FullNewsFragment fragment = new FullNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        bundle.putBoolean("flag", false);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        if (getFragmentManager() != null)
            getFragmentManager().beginTransaction().add(R.id.dash_frame, fragment).addToBackStack("tag").commit();

    }

    @Override
    public void onResume() {
        super.onResume();
       //shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
      //  shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }
}