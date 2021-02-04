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
import org.maktab.digikala.databinding.ItemImageDetailBinding;
import org.maktab.digikala.model.Images;
import org.maktab.digikala.viewmodel.ProductViewModel;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ProductHolder>{

    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;

    public ProductDetailAdapter(LifecycleOwner owner, ProductViewModel productViewModel ) {
        mOwner = owner;
        mProductViewModel = productViewModel;
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageDetailBinding itemImageDetailBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mProductViewModel.getApplication()),
                        R.layout.item_image_detail,
                        parent,
                        false);
        return new ProductHolder(itemImageDetailBinding);
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

        ItemImageDetailBinding mItemImageDetailBinding;
        private Images mImage;

        public ProductHolder(ItemImageDetailBinding binding) {
            super(binding.getRoot());
            mItemImageDetailBinding = binding;
            mItemImageDetailBinding.setLifecycleOwner(mOwner);

        }

        public void bindProduct(Images image) {
            mImage = image;

            Picasso.get()
                    .load(image.getSrc())
                    .into(mItemImageDetailBinding.imageDetail);
        }
    }
}
