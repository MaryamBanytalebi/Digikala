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
import org.maktab.digikala.adapter.BuyProductAdapter;
import org.maktab.digikala.databinding.FragmentBuyBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.OrderViewModel;
import org.maktab.digikala.viewmodel.SettingViewModel;

import java.util.ArrayList;
import java.util.List;


public class BuyFragment extends Fragment {

    private FragmentBuyBinding mBuyBinding;
    private SettingViewModel mSettingViewModel;
    private OrderViewModel mCartViewModel;
    private LiveData<Product> mProductLiveData;
    private BuyProductAdapter mBuyProductAdapter;
    private List<Product> mProductList;


    public BuyFragment() {
        // Required empty public constructor
    }


    public static BuyFragment newInstance() {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mCartViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mProductList = new ArrayList<>();
        mCartViewModel.getOrderedProduct();
        mProductLiveData = mCartViewModel.getLiveDateProduct();
        observer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBuyBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_buy,
                container,
                false);

        initView();
        return mBuyBinding.getRoot();
    }

    private void observer() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProductList.add(product);
                mCartViewModel.setProductList(mProductList);
                mBuyProductAdapter = new BuyProductAdapter(getActivity(), getActivity(), mCartViewModel);
                mBuyBinding.recyclerCart.setAdapter(mBuyProductAdapter);
                int totalPrice = 0;
                for (int i = 0; i < mProductList.size(); i++) {
                    int price = Integer.parseInt(mProductList.get(i).getPrice());
                    int count = mCartViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
                    totalPrice += (price * count);
                }
                mBuyBinding.totalPrice.setText(String.valueOf(totalPrice));

            }
        });
    }

    private void initView() {
        String[] name = mSettingViewModel.getSelectedAddress().getAddressName().split("\n");
        mBuyBinding.address.setText(name[0] + "\t" + name[1]);
        mBuyBinding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}