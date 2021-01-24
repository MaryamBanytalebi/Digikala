package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.CategoryAdapter;
import org.maktab.digikala.databinding.FragmentCategoryBinding;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private CategoryAdapter mCategoryAdapter;
    private LiveData<List<ProductCategory>> mCategoryItemsLiveData;
    private FragmentCategoryBinding mCategoryBinding;
    private CategoryViewModel mCategoryViewModel;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*mCategoryList = new ArrayList<>();
        mRepository = new ProductRepository();
        mRepository.fetchCategoryItemsAsync();
        mCategoryItemsLiveData = mRepository.getProductCategoryLiveData();*/

        getCategoryFromCategoryViewModel();
        setObserver();
    }

    private void setObserver() {
        mCategoryItemsLiveData.observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(List<ProductCategory> categories) {
                mCategoryViewModel.setCategoryList(categories);
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        mCategoryAdapter = new CategoryAdapter(this, getActivity(), mCategoryViewModel);
        mCategoryBinding.recyclerCategory.setAdapter(mCategoryAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCategoryBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category, container,
                false);

        initViews();
        return mCategoryBinding.getRoot();
    }

    private void initViews() {
        mCategoryBinding.recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getCategoryFromCategoryViewModel() {
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.fetchCategoryItems();
        mCategoryItemsLiveData = mCategoryViewModel.getLiveDataCategoryItems();
    }
}