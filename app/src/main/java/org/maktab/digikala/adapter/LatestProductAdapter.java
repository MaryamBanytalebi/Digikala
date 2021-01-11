package org.maktab.digikala.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.controller.activities.ProductDetailActivity;
import org.maktab.digikala.databinding.ItemLatestBinding;
import org.maktab.digikala.model.Product;

import java.util.List;

public class LatestProductAdapter extends RecyclerView.Adapter<LatestProductAdapter.ProductHolder>{

    private Context mContext;
    private List<Product> mProductList;
    private OnBottomReachedListener mOnBottomReachedListener;

    public LatestProductAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProductList = productList;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        mOnBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLatestBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_latest,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        holder.bindLatestProductItem(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private ItemLatestBinding mBinding;
        private Product mProduct;

        public ProductHolder(ItemLatestBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            //Product product = mBinding.getProduct();

            binding.textLatest.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            binding.textLatest.setSingleLine(true);
            binding.textLatest.setSelected(true);
            binding.textLatest.setMarqueeRepeatLimit(-1);

            binding.imageLatest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ProductDetailActivity.newIntent(mContext, mProduct.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        private void bindLatestProductItem(Product product){

            mBinding.textLatest.setText(product.getPrice());
            //mBinding.setProduct(product);
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mBinding.imageLatest);

        }
    }
}
