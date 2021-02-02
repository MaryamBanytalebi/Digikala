package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.ProductAdapter;
import org.maktab.digikala.adapter.SubCategoryProductAdapter;
import org.maktab.digikala.databinding.FragmentSubCategoryBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.view.activities.SearchActivity;
import org.maktab.digikala.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment {

    public static final String BUNDLE_PARENT_ID = "bundle_parent_id";
    public static final String BUNDLE_PARENT_NAME = "bundle_parent_name";
    private SubCategoryProductAdapter mCategoryAdapter;
    private ProductAdapter mProductAdapter;
    private LiveData<List<ProductCategory>> mCategoryItemsLiveData;
    private LiveData<List<Product>> mProductsLiveData;
    private CategoryViewModel mCategoryViewModel;
    private int mParentId;
    private int mProductId;
    private FragmentSubCategoryBinding mSubCategoryBinding;

    public SubCategoryFragment() {

    }


    public static SubCategoryFragment newInstance(int id) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_PARENT_ID,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentId = getArguments().getInt(BUNDLE_PARENT_ID);
        setHasOptionsMenu(true);
        getSubCategoryFromCategoryViewModel();
        setObserver();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.home, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        setSearchViewListeners(searchView);
    }

    private void setSearchViewListeners(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(SearchActivity.newIntent(getActivity(),query,"cateegory"));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mCategoryViewModel.getQueryFromPreferences();
                if (query != null)
                    searchView.setQuery(query, false);
            }
        });
    }

    private void setObserver() {
        mCategoryItemsLiveData.observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(List<ProductCategory> categories) {
                if (categories.size() != 0) {
//                    mSubCategoryList.addAll(categories);
                    mCategoryViewModel.setCategoryList(categories);
                    setSubCategoryAdapter();
                } else {
                    mCategoryViewModel.fetchProductItemsWithParentId(String.valueOf(mParentId));
                    mProductsLiveData = mCategoryViewModel.getLiveDataProductWithParentId();
                    setObserverForProduct();
                }
            }
        });
    }

    private void getSubCategoryFromCategoryViewModel() {
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.fetchSubCategoryItems(String.valueOf(mParentId));
        mCategoryItemsLiveData = mCategoryViewModel.getLiveDataCategoryItems();
    }

    private void setObserverForProduct() {
        mProductsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mCategoryViewModel.setProductList(productList);
                setProductAdapter();
                mProductId=productList.get(0).getId();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSubCategoryBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sub_category,
                container,
                false);

        initView();

        return mSubCategoryBinding.getRoot();

    }

    private void setProductAdapter() {
        mProductAdapter = new ProductAdapter(this, getActivity(), mCategoryViewModel);
        mSubCategoryBinding.recyclerSubCategory.setAdapter(mProductAdapter);
    }

    private void setSubCategoryAdapter() {
        mCategoryAdapter = new SubCategoryProductAdapter(this, getActivity(), mCategoryViewModel);
        mSubCategoryBinding.recyclerSubCategory.setAdapter(mCategoryAdapter);
    }

    private void initView() {
        mSubCategoryBinding.recyclerSubCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}