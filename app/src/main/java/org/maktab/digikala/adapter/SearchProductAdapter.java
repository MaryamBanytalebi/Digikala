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
import org.maktab.digikala.databinding.ItemSearchBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.ProductViewModel;
import org.maktab.digikala.viewmodel.SearchViewModel;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ProductHolder>{

    private final SearchViewModel mSearchViewModel;
    private final LifecycleOwner mOwner;

    public SearchProductAdapter(LifecycleOwner owner, Context context, SearchViewModel searchViewModel) {
        mOwner = owner;
        mSearchViewModel = searchViewModel;
        mSearchViewModel.setContext(context);
    }
    @Override
    public int getItemCount() {
        return mSearchViewModel.getSearchProduct().size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mSearchViewModel.getApplication());
        ItemSearchBinding itemSearchBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_search,
                parent,
                false);

        ProductHolder productHolder = new ProductHolder(itemSearchBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mSearchViewModel.getSearchProduct().get(position);
        holder.bindProduct(product);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        ItemSearchBinding mItemSearchBinding;

        public ProductHolder(ItemSearchBinding itemSearchBinding) {
            super(itemSearchBinding.getRoot());
            mItemSearchBinding = itemSearchBinding;
            mItemSearchBinding.setSearchViewModel(mSearchViewModel);
            mItemSearchBinding.setLifecycleOwner(mOwner);


        }

        public void bindProduct(Product product) {
            mItemSearchBinding.setProductId(product.getId());

            mItemSearchBinding.textSearchProduct.setText(product.getTitle());
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mItemSearchBinding.imageSearchProduct);
        }
    }
}
