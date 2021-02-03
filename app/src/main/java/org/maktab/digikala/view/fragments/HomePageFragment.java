package org.maktab.digikala.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Worker;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.HighestScoreProductAdapter;
import org.maktab.digikala.adapter.LatestProductAdapter;
import org.maktab.digikala.adapter.MostVisitedProductAdapter;
import org.maktab.digikala.databinding.FragmentHomepageBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.view.activities.ProductDetailActivity;
import org.maktab.digikala.view.activities.SearchActivity;
import org.maktab.digikala.viewmodel.ProductViewModel;
import org.maktab.digikala.viewmodel.SettingViewModel;
import org.maktab.digikala.worker.PollWorker;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends VisibleFragment {

    private FragmentHomepageBinding mHomepageBinding;
    private Context mContext;
    private HighestScoreProductAdapter mHighestScoreProductAdapter;
    private LatestProductAdapter mLatestProductAdapter;
    private MostVisitedProductAdapter mMostVisitedProductAdapter;
    private ProductViewModel mProductViewModel;
    private SettingViewModel mSettingViewModel;
    private LiveData<List<Product>> mMostVisitedProductItemsLiveData;
    private LiveData<List<Product>> mLatestProductItemsLiveData;
    private LiveData<List<Product>> mHighestScoreProductItemsLiveData;
    private LiveData<List<Product>> mSpecialProductsLiveData1;
    private LiveData<List<Product>> mSpecialProductsLiveData2;
    private LiveData<List<Product>> mSpecialProductsLiveData3;
    private List<Product> mSpecialProducts;
    List<SlideModel> mSlideModels;
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

        setHasOptionsMenu(true);
        mSpecialProducts = new ArrayList<>();
        mSlideModels = new ArrayList<>();
        getProductsFromProductViewModel();
        setObserver();
    }

    private void getProductsFromProductViewModel() {
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mProductViewModel.fetchMostVisitedProductItems();
        mProductViewModel.fetchLatestProductItems();
        mProductViewModel.fetchHighestScoreProductItems();
        mMostVisitedProductItemsLiveData = mProductViewModel.getLiveDateMostVisitedProducts();
        mLatestProductItemsLiveData = mProductViewModel.getLiveDateLatestProducts();
        mHighestScoreProductItemsLiveData = mProductViewModel.getLiveDateHighestScoreProducts();
        for (int i = 1; i < 4; i++) {
            mProductViewModel.fetchSpecialProductItems(String.valueOf(119), i + "");
            if (i == 1)
                mSpecialProductsLiveData1 = mProductViewModel.getLiveDataSpecialProduct1();
            else if (i==2)
                mSpecialProductsLiveData2 = mProductViewModel.getLiveDataSpecialProduct2();
            else if (i==3)
                mSpecialProductsLiveData3 = mProductViewModel.getLiveDataSpecialProduct3();
        }

    }

    private void setObserver() {
        mMostVisitedProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mProductViewModel.setProductListMostVisited(products);
                setAdapterMostVisited();
                showSlideImage(products);
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

        mSpecialProductsLiveData1.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSpecialProducts.addAll(productList);

            }
        });
        mSpecialProductsLiveData2.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSpecialProducts.addAll(productList);

            }
        });

        mSpecialProductsLiveData3.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSpecialProducts.addAll(productList);
                showSlideImage(mSpecialProducts);
            }
        });
    }

    private void showSlideImage(List<Product> products) {
        List<SlideModel> slideModels = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            String uri = products.get(i).getImages().get(0).getSrc();
            SlideModel slideModel = new SlideModel(uri,i + 1 + "", ScaleTypes.CENTER_CROP);
            mSlideModels.add(slideModel);
        }
        mHomepageBinding.imageSlider.setImageList(mSlideModels);
        mHomepageBinding.imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                String url = mSlideModels.get(i).getImageUrl();
                for (int j = 0; j < mSpecialProducts.size(); j++) {
                    if (mSpecialProducts.get(j).getImages().get(0).getSrc().equalsIgnoreCase(url))
                        startActivity(ProductDetailActivity.newIntent(getActivity(),mSpecialProducts.get(j).getId()));
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.home, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        setSearchViewListeners(searchView);

        MenuItem togglePollingItem = menu.findItem(R.id.menu_item_poll_toggling);
        if (mProductViewModel.isTaskScheduled()) {
            togglePollingItem.setIcon(R.drawable.ic_notifications_off);
        } else {
            togglePollingItem.setIcon(R.drawable.ic_notifications_active);
        }
        if (mSettingViewModel.getNotificationTime() == 0){
            mSettingViewModel.setNotificationTime(3);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_poll_toggling:
                mProductViewModel.togglePolling();
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSearchViewListeners(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(SearchActivity.newIntent(getActivity(),query,"home"));
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
                String query = mProductViewModel.getQueryFromPreferences();
                if (query != null)
                    searchView.setQuery(query, false);
            }
        });
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

        mHomepageBinding.recyclerHighestScore
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
        mHomepageBinding.recyclerHighestScore.setAdapter(mHighestScoreProductAdapter);
    }
}