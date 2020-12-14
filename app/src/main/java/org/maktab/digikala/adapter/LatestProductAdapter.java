package org.maktab.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ItemLatestBinding;
import org.maktab.digikala.model.Product;

import java.util.List;

public class LatestProductAdapter extends RecyclerView.Adapter<LatestProductAdapter.productHolder>{

    private Context mContext;
    private List<Product> mProductList;

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

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLatestBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_latest,
                        parent,
                        false);

        return new productHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder holder, int position) {

        holder.bindLatestProductItem(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class productHolder extends RecyclerView.ViewHolder{

        private ItemLatestBinding mBinding;

        public productHolder(ItemLatestBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bindLatestProductItem(Product product){

            mBinding.textLatest.setText(product.getPrice());
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mBinding.imageLatest);

        }
    }
}
