package com.vladan.bitcoinreviewsimple.models;

import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.vladan.bitcoinreviewsimple.utils.AdapterSpark;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.google.gson.annotations.SerializedName;
import com.robinhood.spark.SparkView;
import com.vladan.udimitest.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lombok.Data;


@Data
public class CoinModel {
    private static String TAG = "CoinModel";

    public CoinModel() {
    }

    @SerializedName("uuid")
    String uuid;
    @SerializedName("symbol")
    String symbol;
    @SerializedName("name")
    String name;
    @SerializedName("color")
    String color;
    @SerializedName("iconUrl")
    String iconUrl;
    @SerializedName("marketCap")
    String marketCap;
    @SerializedName("price")
    String price;
    @SerializedName("listedAt")
    String listedAt;
    @SerializedName("tier")
    Integer tier;
    @SerializedName("change")
    String change;
    @SerializedName("rank")
    Integer rank;
    @SerializedName("sparkline")
    ArrayList<String> sparkLine;
    @SerializedName("lowVolume")
    boolean lowVolume;
    @SerializedName("coinrankingUrl")
    String coinrankingUrl;
    @SerializedName("24hVolume")
    String hVolume;
    @SerializedName("btcPrice")
    String btcPrice;

    public ArrayList<String> getSparkLine() {
        sparkLine.add(change);
        return sparkLine;
    }

    @BindingAdapter("loadIcon")
    public static void loadIcon(@NotNull ImageView imageView, String link) {
        RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                .init()
                .with(imageView.getContext())
                .setPlaceHolder(R.drawable.ic_bitcoin_placeholder_svgrepo_com, R.drawable.ic_baseline_error_outline_24)
                .withListener(new GlideToVectorYouListener() {
                    @Override
                    public void onLoadFailed() {
                        Log.d(TAG, "onLoadFailed");
                    }

                    @Override
                    public void onResourceReady() {
                        Log.d(TAG, "onResourceReady");
                    }
                })
                .getRequestBuilder();
        requestBuilder
                .override(30, 30)
                .load(link)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .centerInside())
                .into(imageView);


    }

    @BindingAdapter("drawChart")
    public static void drawChart(SparkView sparkView, ArrayList<String> points) {
        String behavior = points.get(points.size() - 1);
        float flBehavior = Float.parseFloat(behavior);
        points.remove(points.size() - 1);
        float[] yData = new float[points.size()];
        for (int i = 0; i < yData.length; i++) {
            float item = Float.parseFloat(points.get(i));
            yData[i] = item;
        }
        sparkView.setAdapter(new AdapterSpark(yData));
        if (flBehavior > 0) {
            sparkView.setLineColor(Color.GREEN);
        } else {
            sparkView.setLineColor(Color.RED);
        }

    }

    @BindingAdapter("setPrice")
    public static void setPrice(TextView textView, String price) {
        double dPrice = Double.parseDouble(price);
        DecimalFormat df = new DecimalFormat("0.00");
        String tvPrice = "$" + " " + df.format(dPrice);
        textView.setText(tvPrice);

    }

    @BindingAdapter("setBtcPrice")
    public static void setBtcPrice(TextView textView, String price) {
        double dPrice = Double.parseDouble(price);
        DecimalFormat df = new DecimalFormat("0.00");
        String tvPrice = "$" + " " + df.format(dPrice) + "B";
        textView.setText(tvPrice);

    }

    @BindingAdapter("showChange")
    public static void showChange(TextView textView, String changes) {
        String text;
        DecimalFormat df = new DecimalFormat("0.00");
        float behavior = Float.parseFloat(changes);
        if (behavior > 0) {
            text = "+" + df.format(behavior) + "%";
            textView.setTextColor(Color.GREEN);
        } else {
            text = df.format(behavior) + "%";
            textView.setTextColor(Color.RED);
        }
        textView.setText(text);
    }

}