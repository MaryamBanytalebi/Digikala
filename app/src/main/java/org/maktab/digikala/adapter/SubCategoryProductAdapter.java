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
import org.maktab.digikala.databinding.ItemCategoryBinding;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.viewmodel.CategoryViewModel;

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductAdapter.CategoryHolder> {

    private final LifecycleOwner mOwner;
    private final CategoryViewModel mCategoryViewModel;
    private OnBottomReachedListener mOnBottomReachedListener;

    public SubCategoryProductAdapter(LifecycleOwner owner, Context context, CategoryViewModel categoryViewModel) {
        mOwner = owner;
        mCategoryViewModel = categoryViewModel;
        mCategoryViewModel.setContext(context);
    }


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mCategoryViewModel.getApplication()),
                        R.layout.item_category,
                        parent,
                        false);
        return new CategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        holder.bindSubCategoryProductItem(mCategoryViewModel.getCategoryList().get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoryViewModel.getCategoryList().size();
    }


    class CategoryHolder extends RecyclerView.ViewHolder{

        private ItemCategoryBinding mItemCategoryBinding;
        private ProductCategory mProductCategory;

        public CategoryHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            mItemCategoryBinding = binding;
            //ProductCategory productCategory = binding.getCategory();
            mItemCategoryBinding.setCategoryViewModel(mCategoryViewModel);
            mItemCategoryBinding.setState("category");
            mItemCategoryBinding.setLifecycleOwner(mOwner);
        }

        private void bindSubCategoryProductItem(ProductCategory category){

            mItemCategoryBinding.setParentId(category.getId());
            mItemCategoryBinding.textCategory.setText(category.getName());
            Picasso.get()
                    .load(category.getImage())
                    .into(mItemCategoryBinding.imageCategory);
        }
    }
}
