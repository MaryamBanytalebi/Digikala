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

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.view.activities.ProductDetailActivity;
import org.maktab.digikala.databinding.ItemMostVisitedBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.ProductViewModel;

public class MostVisitedProductAdapter extends RecyclerView.Adapter<MostVisitedProductAdapter.ProductHolder>{

    private final LifecycleOwner mOwner;
    private ProductViewModel mProductViewModel;
    private OnBottomReachedListener mOnBottomReachedListener;

    public MostVisitedProductAdapter(LifecycleOwner owner, Context context, ProductViewModel productViewModel) {
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
        ItemMostVisitedBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mProductViewModel.getApplication()),
                        R.layout.item_most_visited,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mProductViewModel.getProductListMostVisited().get(position);
        holder.bindMostVisitedProductItem(product);
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getProductListMostVisited().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private final ItemMostVisitedBinding mItemMostVisitedBinding;
        private Product mProduct;

        public ProductHolder(ItemMostVisitedBinding binding) {
            super(binding.getRoot());
            mItemMostVisitedBinding = binding;
            //Product product = binding.getProduct();
            mItemMostVisitedBinding.setProductViewModel(mProductViewModel);
            mItemMostVisitedBinding.setLifecycleOwner(mOwner);
        }

        private void bindMostVisitedProductItem(Product product){

            mItemMostVisitedBinding.setProductId(product.getId());
            mItemMostVisitedBinding.textViewNameMostVisited.setText(product.getTitle());
            mItemMostVisitedBinding.textViewPriceMostVisited.setText(product.getPrice());

            if (product.getImages().size()==0)
                return;
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mItemMostVisitedBinding.imageMostVisited);
        }
    }

}

