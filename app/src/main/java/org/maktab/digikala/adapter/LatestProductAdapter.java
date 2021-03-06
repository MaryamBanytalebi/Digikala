package org.maktab.digikala.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import org.maktab.digikala.databinding.ItemLatestBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.ProductViewModel;

public class LatestProductAdapter extends RecyclerView.Adapter<LatestProductAdapter.ProductHolder>{

    private final LifecycleOwner mOwner;
    private ProductViewModel mProductViewModel;
    private OnBottomReachedListener mOnBottomReachedListener;

    public LatestProductAdapter(LifecycleOwner owner, Context context, ProductViewModel productViewModel) {
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
        ItemLatestBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mProductViewModel.getApplication()),
                        R.layout.item_latest,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mProductViewModel.getProductListLatest().get(position);
        holder.bindLatestProductItem(product);
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getProductListLatest().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private final ItemLatestBinding mItemLatestBinding;
        private Product mProduct;

        public ProductHolder(ItemLatestBinding binding) {
            super(binding.getRoot());
            mItemLatestBinding = binding;
            //Product product = mBinding.getProduct();

            binding.setProductViewModel(mProductViewModel);
            binding.setLifecycleOwner(mOwner);
        }

        private void bindLatestProductItem(Product product){

            mItemLatestBinding.setProductId(product.getId());
            mItemLatestBinding.textViewPriceLatest.setText(product.getPrice());
            mItemLatestBinding.textViewNameLatest.setText(product.getTitle());

            //mBinding.setProduct(product);
            /*if (product.getImages().size() == 0){
                Glide.with(mItemLatestBinding.getRoot())
                        .load(R.drawable.ic_image)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(mItemLatestBinding.imageLatest);
            }else {*/
            if (product.getImages().size()==0)
                return;

                Picasso.get()
                        .load(product.getImages().get(0).getSrc())
                        .into(mItemLatestBinding.imageLatest);
        }
    }
}
