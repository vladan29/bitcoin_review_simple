package com.example.udimitest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.udimitest.viewmodels.CoinViewModel;
import com.example.udimitest.R;
import com.example.udimitest.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CoinViewModel coinViewModel;
    private CoinsAdapter coinsAdapter;
    private ActivityMainBinding binding;
    private RecyclerView coinRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        coinViewModel = new ViewModelProvider(this).get(CoinViewModel.class);
        coinsAdapter = new CoinsAdapter(coinViewModel, this);
        TextView tvCrypto = binding.tvCryptoHeader;
        tvCrypto.setOnClickListener(this);
        TextView tvPrice = binding.tvPriceHeader;
        tvPrice.setOnClickListener(this);
        TextView tv24H = binding.tv24HHeader;
        tv24H.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        coinRecyclerView = binding.rvCoins;
        coinRecyclerView.setAdapter(coinsAdapter);
        coinRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinViewModel.init();
        coinsAdapter.observeList();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCryptoHeader:{
                coinViewModel.init();
                coinsAdapter.observeList();
                break;
            }
            case R.id.tvPriceHeader:{
                coinViewModel.sortByPrice();
                coinsAdapter.observeList();
                break;
            }

            case R.id.tv24HHeader:{
                coinViewModel.sortBy24hChange();
                coinsAdapter.observeList();
                break;
            }

        }

    }
}