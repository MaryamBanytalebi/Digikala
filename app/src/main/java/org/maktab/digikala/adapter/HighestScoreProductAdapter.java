package org.maktab.digikala.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.view.activities.ProductDetailActivity;
import org.maktab.digikala.databinding.ItemHighestScoreBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.ProductViewModel;

public class HighestScoreProductAdapter extends RecyclerView.Adapter<HighestScoreProductAdapter.ProductHolder>{

    private final LifecycleOwner mOwner;
    private ProductViewModel mProductViewModel;
    private OnBottomReachedListener mOnBottomReachedListener;

    public HighestScoreProductAdapter(LifecycleOwner owner, Context context, ProductViewModel productViewModel) {
        mOwner = owner;
        mProductViewModel = productViewModel;
        mProductViewModel.setContext(context);
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        mOnBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHighestScoreBinding highestScoreBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mProductViewModel.getApplication()),
                        R.layout.item_highest_score,
                        parent,
                        false);

        return new ProductHolder(highestScoreBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        //holder.bindHighestScoreProductItem(mProductList.get(position));
        Product product = mProductViewModel.getProductListHighestScore().get(position);
        holder.bindHighestScoreProductItem(product);

    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getProductListHighestScore().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private final ItemHighestScoreBinding mItemHighestScoreBinding;
        private Product mProduct;

        public ProductHolder(ItemHighestScoreBinding itemHighestScoreBinding) {
            super(itemHighestScoreBinding.getRoot());
            mItemHighestScoreBinding = itemHighestScoreBinding;
            mItemHighestScoreBinding.setProductViewModel(mProductViewModel);
            mItemHighestScoreBinding.setLifecycleOwner(mOwner);
        }

        private void bindHighestScoreProductItem(Product product){
            mItemHighestScoreBinding.setProductId(product.getId());
            mItemHighestScoreBinding.textViewNameHighestScore.setText(product.getTitle());
            mItemHighestScoreBinding.textViewPriceHighestScore.setText(product.getPrice());


            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mItemHighestScoreBinding.imageHighestScore);
        }
    }
}

