package org.maktab.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ItemProductBinding;
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

    public ProductAdapter(Context context, List<Product> items) {
        mContext = context;
        mProductList = items;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemProductBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_product,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        holder.bindProductItem(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private ItemProductBinding mBinding;

        public ProductHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bindProductItem(Product product){

            mBinding.textProduct.setText(product.getTitle());
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mBinding.imageProduct);
        }
    }
}

