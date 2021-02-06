package org.maktab.digikala.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.BuyProductAdapter;
import org.maktab.digikala.databinding.FragmentBuyBinding;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.view.activities.LocationActivity;
import org.maktab.digikala.viewmodel.OrderViewModel;
import org.maktab.digikala.viewmodel.SettingViewModel;

import java.util.ArrayList;
import java.util.List;


public class BuyFragment extends Fragment {

    private FragmentBuyBinding mBuyBinding;
    private SettingViewModel mSettingViewModel;
    private OrderViewModel mOrderViewModel;
    private LiveData<Product> mProductLiveData;
    private LiveData<Customer> mCustomerLiveData;
    private BuyProductAdapter mBuyProductAdapter;
    private List<Product> mProductList;

    public static final int REQUEST_CODE_LOCATION = 0;



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
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mProductList = new ArrayList<>();
        mOrderViewModel.getOrderedProduct();
        mProductLiveData = mOrderViewModel.getLiveDateProduct();
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
        listeners();
        return mBuyBinding.getRoot();
    }

    private void observer() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProductList.add(product);
                mOrderViewModel.setProductList(mProductList);
                mBuyProductAdapter = new BuyProductAdapter(getActivity(), getActivity(), mOrderViewModel);
                mBuyBinding.recyclerCart.setAdapter(mBuyProductAdapter);
                int totalPrice = 0;
                for (int i = 0; i < mProductList.size(); i++) {
                    int price = Integer.parseInt(mProductList.get(i).getPrice());
                    int count = mOrderViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
                    totalPrice += (price * count);
                }
                mBuyBinding.totalPrice.setText(String.valueOf(totalPrice));

            }
        });
    }

    private void listeners() {
        mBuyBinding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(LocationActivity.newIntent(getActivity()), REQUEST_CODE_LOCATION);
            }
        });

        mBuyBinding.buttonContinueBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSettingViewModel.getSelectedAddress() == null)
                    Toast.makeText(getContext(), R.string.enter_address, Toast.LENGTH_SHORT).show();
                else {
                    mOrderViewModel.onclickBuy();
                    mCustomerLiveData = mOrderViewModel.getLiveDataCustomer();
                    observerCustomer();
                    Toast.makeText(getActivity(),"in else",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_LOCATION){
            setLocation();
        }
    }

    private void observerCustomer() {
        mCustomerLiveData.observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                if (customer != null)
                    Toast.makeText(getActivity(),customer.getFirst_name().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setLocation();
    }

    private void initView() {
        setLocation();

        mBuyBinding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingViewModel.setContext(getActivity());
        mBuyBinding.setSettingViewModel(mSettingViewModel);

    }

    private void setLocation() {
        if (mSettingViewModel.getSelectedAddress() != null) {
            String[] name = mSettingViewModel.getSelectedAddress().getAddressName().split("\n");
            mBuyBinding.textViewAddressName.setText(name[0] + "\t" + name[1]);
        }
    }
}