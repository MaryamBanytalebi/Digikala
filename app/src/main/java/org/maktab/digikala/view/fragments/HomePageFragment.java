package org.maktab.digikala.view.fragments;

import android.content.Context;
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
import org.maktab.digikala.adapter.HighestScoreProductAdapter;
import org.maktab.digikala.adapter.LatestProductAdapter;
import org.maktab.digikala.adapter.MostVisitedProductAdapter;
import org.maktab.digikala.databinding.FragmentHomepageBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.ProductViewModel;

import java.util.List;

public class HomePageFragment extends Fragment {

    private FragmentHomepageBinding mHomepageBinding;
    private Context mContext;
    private HighestScoreProductAdapter mHighestScoreProductAdapter;
    private LatestProductAdapter mLatestProductAdapter;
    private MostVisitedProductAdapter mMostVisitedProductAdapter;
    private ProductViewModel mProductViewModel;
    private LiveData<List<Product>> mMostVisitedProductItemsLiveData;
    private LiveData<List<Product>> mLatestProductItemsLiveData;
    private LiveData<List<Product>> mHighestScoreProductItemsLiveData;
    private LinearLayoutManager mLinearLayoutManager;
    private int loading = 1;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

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
        getProductsFromProductViewModel();

        setObserver();
    }

    private void setObserver() {
        mMostVisitedProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mProductViewModel.setProductListMostVisited(products);
                setAdapterMostVisited();
            }
        });
        mLatestProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mProductViewModel.setProductListLatest(products);
                setAdapterLatest();
            }
        });
        mHighestScoreProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mProductViewModel.setProductListHighestScore(products);
                setAdapterHighestScore();
            }
        });
    }

    private void getProductsFromProductViewModel() {
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductViewModel.getMostVisitedProductItems();
        mProductViewModel.getLatestProductItems();
        mProductViewModel.getHighestScoreProductItems();
        mMostVisitedProductItemsLiveData = mProductViewModel.getLiveDateMostVisitedProducts();
        mLatestProductItemsLiveData = mProductViewModel.getLiveDateLatestProducts();
        mHighestScoreProductItemsLiveData = mProductViewModel.getLiveDateHighestScoreProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mHomepageBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_homepage,
                container,
                false);

        initViews();

        return mHomepageBinding.getRoot();
    }

    private void initViews() {

        mHomepageBinding.recyclerHighestScoreProduct
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));

        mHomepageBinding.recyclerMostVisited
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));

        mHomepageBinding.recyclerLatest
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
    }

    private void setAdapterMostVisited() {
        mMostVisitedProductAdapter = new MostVisitedProductAdapter(this, getActivity(), mProductViewModel);
        mHomepageBinding.recyclerMostVisited.setAdapter(mMostVisitedProductAdapter);
    }

    private void setAdapterLatest() {
        mLatestProductAdapter = new LatestProductAdapter(this, getActivity(), mProductViewModel);
        mHomepageBinding.recyclerLatest.setAdapter(mLatestProductAdapter);
    }

    private void setAdapterHighestScore() {
        mHighestScoreProductAdapter = new HighestScoreProductAdapter(this, getActivity(), mProductViewModel);
        mHomepageBinding.recyclerHighestScoreProduct.setAdapter(mHighestScoreProductAdapter);
    }
}