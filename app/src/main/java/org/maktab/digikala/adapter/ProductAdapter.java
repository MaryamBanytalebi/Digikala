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
import org.maktab.digikala.databinding.ItemCategoryBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.CategoryViewModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{

    private final LifecycleOwner mOwner;
    private final CategoryViewModel mCategoryViewModel;

    public ProductAdapter(LifecycleOwner owner, Context context, CategoryViewModel categoryViewModel) {
        mOwner = owner;
        mCategoryViewModel = categoryViewModel;
        mCategoryViewModel.setContext(context);
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemCategoryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mCategoryViewModel.getApplication()),
                        R.layout.item_category,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProductItem(mCategoryViewModel.getProductList().get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoryViewModel.getProductList().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private ItemCategoryBinding mItemCategoryBinding;
        private Product mProduct;

        public ProductHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            mItemCategoryBinding = binding;
            //Product product = binding.getProduct();
            // as this :  mProduct = product;
            mItemCategoryBinding.setCategoryViewModel(mCategoryViewModel);
            mItemCategoryBinding.setState("product");
            mItemCategoryBinding.setLifecycleOwner(mOwner);
        }

        private void bindProductItem(Product product){
            mItemCategoryBinding.setParentId(product.getId());
            //mBinding.setProduct(product);
            mItemCategoryBinding.textCategory.setText(product.getTitle());
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mItemCategoryBinding.imageCategory);
        }
    }
}

