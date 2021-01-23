package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.ProductAdapter;
import org.maktab.digikala.adapter.SubCategoryProductAdapter;
import org.maktab.digikala.databinding.FragmentSubCategoryBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment {

    public static final String BUNDLE_PARENT_ID = "bundle_parent_id";
    public static final String BUNDLE_PARENT_NAME = "bundle_parent_name";
    private ProductRepository mRepository;
    private SubCategoryProductAdapter mCategoryAdapter;
    private ProductAdapter mProductAdapter;
    private List<ProductCategory> mSubCategoryList;
    private LiveData<List<ProductCategory>> mCategoryItemsLiveData;
    private LiveData<List<Product>> mProductsLiveData;
    private String mParentName;
    private int mParentId;
    private FragmentSubCategoryBinding mBinding;

    public SubCategoryFragment() {

    }


    public static SubCategoryFragment newInstance(int id, String parentName) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_PARENT_ID,id);
        args.putString(BUNDLE_PARENT_NAME,parentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentId = getArguments().getInt(BUNDLE_PARENT_ID);
        mParentName = getArguments().getString(BUNDLE_PARENT_NAME);
        mSubCategoryList = new ArrayList<>();
        mRepository = new ProductRepository();
        mRepository.fetchSubCategoryItemsAsync(String.valueOf(mParentId));
        mCategoryItemsLiveData = mRepository.getProductCategoryLiveData();
        setObserver();

    }

    private void setObserver() {
        mCategoryItemsLiveData.observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(List<ProductCategory> categories) {
                if (categories.size() != 0) {
//                    mSubCategoryList.addAll(categories);
                    setSubCategoryAdapter(categories);
                } else {
                    mRepository.fetchGetProductWithIdItemsAsync(String.valueOf(mParentId));
                    mProductsLiveData = mRepository.getProductWithParentIdLiveData();
                    setObserverForProduct();
                }
            }
        });
    }

    private void setObserverForProduct() {
        mProductsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {

                setProductAdapter(productList);

            }
        });
    }

    private void setSubCategoryAdapter(List<ProductCategory> categories) {
        mCategoryAdapter = new SubCategoryProductAdapter(getActivity(), categories);
        mBinding.recyclerSubCategory.setAdapter(mCategoryAdapter);
    }

    private void setProductAdapter(List<Product> productList) {
        mProductAdapter = new ProductAdapter(productList, getActivity());
        mBinding.recyclerSubCategory.setAdapter(mProductAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_category, container, false);

    }

    private void initView() {
        mBinding.recyclerSubCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}