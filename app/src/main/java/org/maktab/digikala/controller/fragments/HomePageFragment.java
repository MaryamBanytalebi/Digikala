package org.maktab.digikala.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.HighestScoreProductAdapter;
import org.maktab.digikala.adapter.LatestProductAdapter;
import org.maktab.digikala.adapter.MostVisitedProductAdapter;
import org.maktab.digikala.databinding.FragmentHomepageBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.ProductRepository;

import java.util.List;

public class HomePageFragment extends Fragment {

    private FragmentHomepageBinding mBinding;
    private Context mContext;
    private ProductRepository mRepository;
    private HighestScoreProductAdapter mHighestScoreProductAdapter;
    private LatestProductAdapter mLatestProductAdapter;
    private MostVisitedProductAdapter mMostVisitedProductAdapter;
    private LiveData<List<Product>> mMostVisitedProductItemsLiveData;
    private LiveData<List<Product>> mLatestProductItemsLiveData;
    private LiveData<List<Product>> mHighestScoreProductItemsLiveData;
    private LinearLayoutManager mLinearLayoutManager;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = new ProductRepository();
        mRepository.fetchMostVisitedItemsAsync();
        mRepository.fetchHighestScoreItemsAsync();
        mRepository.fetchLatestItemsAsync();
        mMostVisitedProductItemsLiveData = mRepository.getMostVisitedProductsLiveData();
        mHighestScoreProductItemsLiveData = mRepository.getHighestScoreProductsLiveData();
        mLatestProductItemsLiveData = mRepository.getLatestProductsLiveData();

        setObserver();
    }

    private void setObserver() {
        mMostVisitedProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setAdapterMostVisited(products);
            }
        });
        mLatestProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setAdapterLatest(products);
            }
        });
        mHighestScoreProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setAdapterHighestScore(products);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_homepage,
                container,
                false);

        findViews();
        initViews();

        return mBinding.getRoot();
    }

    private void initViews() {

        mBinding.recyclerHighestScoreProduct.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
        mBinding.recyclerMostVisited.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerMostVisited.addItemDecoration(
                new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
        mBinding.recyclerLatest.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    private void setAdapterMostVisited(List<Product> products) {
        mMostVisitedProductAdapter = new MostVisitedProductAdapter(getActivity(), products);
        mBinding.recyclerMostVisited.setAdapter(mMostVisitedProductAdapter);
    }

    private void setAdapterLatest(List<Product> products) {
        mLatestProductAdapter = new LatestProductAdapter(getActivity(), products);
        mBinding.recyclerLatest.setAdapter(mLatestProductAdapter);
    }

    private void setAdapterHighestScore(List<Product> products) {
        mHighestScoreProductAdapter = new HighestScoreProductAdapter(getActivity(), products);
        mBinding.recyclerHighestScoreProduct.setAdapter(mHighestScoreProductAdapter);
    }

    private void findViews(){
        mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
    }
}