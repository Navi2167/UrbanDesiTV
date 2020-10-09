package com.ardigitalsolutions.urbandesitv.view.allAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ardigitalsolutions.urbandesitv.R;
import com.ardigitalsolutions.urbandesitv.databinding.AdapterLayoutBinding;
import com.ardigitalsolutions.urbandesitv.service.allModels.news.NewsModel;
import com.ardigitalsolutions.urbandesitv.service.apiClient.Client;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context context;
    private List<NewsModel> newsModelList;
    private Client client;
    private ClickEvent clickEvent;

    public NewsAdapter(ClickEvent clickEvent) {
        newsModelList = new ArrayList<>();
        client = new Client(context);
        this.clickEvent = clickEvent;
    }

//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        AdapterLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.adapter_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(newsModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsModelList != null ? newsModelList.size() : 0;
    }

    public void updateList(List<NewsModel> newsModelList) {
        this.newsModelList.addAll(newsModelList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AdapterLayoutBinding binding;

        public MyViewHolder(@NonNull AdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsModel model) {
            binding.setClick(clickEvent);
            binding.setData(model);
            binding.setUrl(model.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl());
            binding.executePendingBindings();
        }
    }

    public interface ClickEvent {
        void onclick(String content, String title);
    }
}
