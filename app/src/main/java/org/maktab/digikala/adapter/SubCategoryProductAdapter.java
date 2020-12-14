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
        ItemProductBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),
                        R.layout.item_product,
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

        private ItemProductBinding mBinding;

        public CategoryHolder(ItemProductBinding binding) {
            super(binding.getRoot());
        }

        private void bindSubCategoryProductItem(ProductCategory category){

            mBinding.textProduct.setText(category.getName());
            Picasso.get()
                    .load(category.getImage())
                    .into(mBinding.imageProduct);
        }
    }
}
