package org.maktab.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ItemHighestScoreBinding;
import org.maktab.digikala.model.Images;
import org.maktab.digikala.viewmodel.ProductViewModel;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ProductHolder>{

    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;

    public ProductDetailAdapter(LifecycleOwner owner, ProductViewModel productViewModel ) {
        mProductViewModel = productViewModel;
        mOwner = owner;
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHighestScoreBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mProductViewModel.getApplication()),
                        R.layout.item_highest_score,
                        parent,
                        false);
        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Images images = mProductViewModel.getDetailedProduct().getImages().get(position);
        holder.bindProduct(images);
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getDetailedProduct().getImages().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ItemHighestScoreBinding mItemHighestScoreBinding;
        private Images mImage;

        public ProductHolder(ItemHighestScoreBinding binding) {
            super(binding.getRoot());
            mItemHighestScoreBinding = binding;
            mItemHighestScoreBinding.setLifecycleOwner(mOwner);

        }

        public void bindProduct(Images image) {
            mImage = image;

            Picasso.get()
                    .load(image.getSrc())
                    .into(mItemHighestScoreBinding.imageHighestScore);
        }
    }
}
