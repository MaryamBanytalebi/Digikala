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
import org.maktab.digikala.view.activities.SubCategoryActivity;
import org.maktab.digikala.databinding.ItemCategoryBinding;
import org.maktab.digikala.model.ProductCategory;

import java.util.List;

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductAdapter.CategoryHolder> {

    private Context mContext;
    private List<ProductCategory> mCategoryList;

    public SubCategoryProductAdapter(Context context, List<ProductCategory> categoryList) {
        mContext = context;
        mCategoryList = categoryList;
    }

    public List<ProductCategory> getCategoryList() {
        return mCategoryList;
    }

    public void setCategoryList(List<ProductCategory> categoryList) {
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_category,
                        parent,
                        false);
        return new CategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        holder.bindSubCategoryProductItem(mCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    class CategoryHolder extends RecyclerView.ViewHolder{

        private ItemCategoryBinding mBinding;
        private ProductCategory mProductCategory;

        public CategoryHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            //ProductCategory productCategory = binding.getCategory();

            binding.imageCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mContext.startActivity(SubCategoryActivity.newIntent(
                            mContext,
                            mProductCategory.getId()));
                }
            });
        }

        private void bindSubCategoryProductItem(ProductCategory category){

            mBinding.textCategory.setText(category.getName());
            Picasso.get()
                    .load(category.getImage())
                    .into(mBinding.imageCategory);
        }
    }
}
