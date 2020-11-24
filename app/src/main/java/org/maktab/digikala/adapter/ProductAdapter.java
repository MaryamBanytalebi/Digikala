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
import org.maktab.digikala.databinding.ListItemProductBinding;
import org.maktab.digikala.model.ProductItem;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{

    private Context mContext;
    private List<ProductItem> mItems;

    public List<ProductItem> getItems() {
        return mItems;
    }

    public void setItems(List<ProductItem> items) {
        mItems = items;
    }

    public ProductAdapter(Context context, List<ProductItem> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListItemProductBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.list_item_product,
                        parent,
                        false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        holder.bindProductItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private ListItemProductBinding mBinding;

        public ProductHolder(ListItemProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        private void bindProductItem(ProductItem item){

            Picasso.get()
                    .load(item.getUrl())
                    .into(mBinding.itemImageProduct);
        }
    }
}

