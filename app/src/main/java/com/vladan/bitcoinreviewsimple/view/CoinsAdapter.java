package com.vladan.bitcoinreviewsimple.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.vladan.bitcoinreviewsimple.BR;
import com.vladan.bitcoinreviewsimple.R;
import com.vladan.bitcoinreviewsimple.databinding.CoinListItemBinding;
import com.vladan.bitcoinreviewsimple.viewmodels.CoinViewModel;

import com.vladan.bitcoinreviewsimple.models.CoinModel;

import java.util.ArrayList;
import java.util.List;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.CoinViewHolder> {
    CoinViewModel mCoinViewModel;
    LifecycleOwner mLifecycleOwner;
    private List<CoinModel> mCoinModelList = new ArrayList<>();

    CoinsAdapter(CoinViewModel coinViewModel,LifecycleOwner lifecycleOwner){
        this.mCoinViewModel = coinViewModel;
        this.mLifecycleOwner = lifecycleOwner;
    }
    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CoinListItemBinding itemBinding = CoinListItemBinding.inflate(layoutInflater,parent,false);
        return new CoinViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        String no = String.valueOf(position + 1);
        CoinModel coinModel = mCoinModelList.get(position);
        holder.bind(coinModel);
        holder.tvNo.setText(no);

    }

    @Override
    public int getItemCount() {
        return mCoinModelList.size();
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder{
      private final ViewDataBinding mBinding;
      TextView tvNo;
      public CoinViewHolder(ViewDataBinding binding){
          super(binding.getRoot());
          this.mBinding = binding;
          tvNo = binding.getRoot().findViewById(R.id.tvNo);
      }

      public void bind(Object obj){
          mBinding.setVariable(BR.coinListItem, obj);
          mBinding.executePendingBindings();
      }

    }

    public void observeList(){
        mCoinViewModel.coins.observe(mLifecycleOwner,coinModels ->{
            mCoinModelList.clear();
            mCoinModelList.addAll(coinModels);
            this.notifyDataSetChanged();
        });

    }
}
