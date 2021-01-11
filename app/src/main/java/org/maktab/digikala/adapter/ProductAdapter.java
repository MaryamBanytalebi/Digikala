package org.maktab.digikala.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.controller.activities.ProductDetailActivity;
import org.maktab.digikala.databinding.ItemCategoryBinding;
import org.maktab.digikala.model.Product;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{

    private Context mContext;
    private List<Product> mProductList;

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public ProductAdapter( List<Product> items,Context context) {
        mContext = context;
        mProductList = items;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemCategoryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_category,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProductItem(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private ItemCategoryBinding mBinding;
        private Product mProduct;

        public ProductHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            //Product product = binding.getProduct();
            // as this :  mProduct = product;

            binding.imageCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = ProductDetailActivity.newIntent(mContext,mProduct.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        private void bindProductItem(Product product){

            //mBinding.setProduct(product);
            mBinding.textCategory.setText(product.getTitle());
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mBinding.imageCategory);
        }
    }
}

