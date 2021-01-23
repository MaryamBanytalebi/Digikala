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
import org.maktab.digikala.view.activities.ProductDetailActivity;
import org.maktab.digikala.databinding.ItemHighestScoreBinding;
import org.maktab.digikala.model.Product;

import java.util.List;

public class HighestScoreProductAdapter extends RecyclerView.Adapter<HighestScoreProductAdapter.ProductHolder>{

    private Context mContext;
    private List<Product> mProductList;

    public HighestScoreProductAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProductList = productList;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHighestScoreBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_highest_score,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindHighestScoreProductItem(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private ItemHighestScoreBinding mBinding;
        private Product mProduct;

        public ProductHolder(ItemHighestScoreBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            //Product product = binding.getProduct();

            binding.imageHighestScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ProductDetailActivity.newIntent(mContext,mProduct.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        private void bindHighestScoreProductItem(Product product){

            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mBinding.imageHighestScore);
        }
    }
}

