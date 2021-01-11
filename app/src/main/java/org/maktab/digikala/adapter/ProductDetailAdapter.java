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
import org.maktab.digikala.databinding.ItemHighestScoreBinding;
import org.maktab.digikala.model.Images;
import org.maktab.digikala.model.Product;

import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ProductHolder>{

    private List<Images> mImages;
    private Context mContext;

    public List<Images> getImages() {
        return mImages;
    }

    public void setImages(List<Images> images) {
        mImages = images;
    }

    public ProductDetailAdapter(List<Images> images, Context context) {
        mImages = images;
        mContext = context;
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

        Images images = mImages.get(position);
        holder.bindProduct(images);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ItemHighestScoreBinding mBinding;
        private Images mImage;

        public ProductHolder(ItemHighestScoreBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Images image) {
            mImage = image;

            Picasso.get()
                    .load(image.getSrc())
                    .into(mBinding.imageHighestScore);
        }
    }
}
