package com.vladan.bitcoinreviewsimple.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vladan.bitcoinreviewsimple.AppApplication;
import com.vladan.bitcoinreviewsimple.viewmodels.CoinViewModel;
import com.vladan.bitcoinreviewsimple.R;
import com.vladan.bitcoinreviewsimple.databinding.ActivityMainBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vladan.networkchecker.NetworkLiveData;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity1";
    private CoinViewModel coinViewModel;
    private CoinsAdapter coinsAdapter;
    private ActivityMainBinding binding;
    private RecyclerView coinRecyclerView;
    public ShimmerFrameLayout shimmerFrameLayout;
    private NetworkLiveData networkLiveData = NetworkLiveData.get();
    private boolean isConnected;
    private boolean isLoadedFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = getLayoutInflater();
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());
        networkLiveData.observe(this, networkState -> {
            isConnected = networkState.isConnected();
            Log.d(TAG, String.valueOf(isConnected));
            if (isConnected) {
                loadViews();
            } else {
                Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
        coinViewModel = new ViewModelProvider(this).get(CoinViewModel.class);
        coinsAdapter = new CoinsAdapter(coinViewModel, this);
        TextView tvCrypto = binding.tvCryptoHeader;
        tvCrypto.setOnClickListener(this);
        TextView tvPrice = binding.tvPriceHeader;
        tvPrice.setOnClickListener(this);
        TextView tv24H = binding.tv24HHeader;
        tv24H.setOnClickListener(this);
        shimmerFrameLayout = binding.shimmer;
    }

    private void loadViews() {
        if (!isLoadedFirstTime) {
            coinViewModel.init();
            observeCoinList(coinViewModel);
            coinsAdapter.observeList();
            isLoadedFirstTime = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        coinRecyclerView = binding.rvCoins;
        coinRecyclerView.setAdapter(coinsAdapter);
        coinRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shimmerFrameLayout.startShimmerAnimation();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (isConnected) {
            switch (v.getId()) {
                case R.id.tvCryptoHeader: {
                    coinViewModel.init();
                    coinsAdapter.observeList();
                    logAnalyticsEvent("crypto_clicked", coinsAdapter.getFirstName());
                    break;
                }
                case R.id.tvPriceHeader: {
                    coinViewModel.sortByPrice();
                    coinsAdapter.observeList();
                    logAnalyticsEvent("price_clicked", coinsAdapter.getFirstName());
                    break;
                }

                case R.id.tv24HHeader: {
                    coinViewModel.sortBy24hChange();
                    coinsAdapter.observeList();
                    logAnalyticsEvent("h_clicked", coinsAdapter.getFirstName());
                    break;
                }

            }
        } else {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }

    }

    private void observeCoinList(CoinViewModel coinViewModel) {
        coinViewModel.coins.observe(this, coinModels -> {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        });
    }

    private void logAnalyticsEvent( String eventName, String label){
        Bundle params = new Bundle();
        params.putString("name", label);
        params.putString("price", coinsAdapter.getFirstPrice());
        ((AppApplication) getApplication()).getFirebaseAnalytics().logEvent(eventName, params);
    }
}