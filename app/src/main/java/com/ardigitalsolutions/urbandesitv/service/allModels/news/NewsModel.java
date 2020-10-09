
package com.ardigitalsolutions.urbandesitv.service.allModels.news;

import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.ardigitalsolutions.urbandesitv.R;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsModel extends BaseObservable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).error(R.drawable.no_image).centerCrop().into(imageView);
    }

    @BindingAdapter("isVisible")
    public static void isVisible(View view, boolean flag) {
        view.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("newsTitle")
    public static void setTitle(TextView view, String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            view.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
        else
            view.setText(Html.fromHtml(text));
    }
}
