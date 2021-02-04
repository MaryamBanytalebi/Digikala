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
import android.widget.Toast;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.OrderProductAdapter;
import org.maktab.digikala.databinding.FragmentOrderBinding;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends VisibleFragment {

    FragmentOrderBinding mFragmentOrderBinding;
    private OrderViewModel mOrderViewModel;
    private LiveData<Product> mProductLiveData;
    private LiveData<Customer> mCustomerLiveData;
    private List<Product> mProductList;
    private OrderProductAdapter mOrderProductAdapter;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductList = new ArrayList<>();
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mOrderViewModel.getOrderedProduct();
        mProductLiveData = mOrderViewModel.getLiveDateProduct();
        observer();

    }

    private void observer() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProductList.add(product);
                mOrderViewModel.setProductList(mProductList);
                mOrderProductAdapter = new OrderProductAdapter(getActivity(),getActivity(), mOrderViewModel);
                mFragmentOrderBinding.recyclerOrder.setAdapter(mOrderProductAdapter);
                mOrderViewModel.setOrderedProductAdapter(mOrderProductAdapter);
                int totalPrice = 0;
                for (int i = 0; i < mProductList.size(); i++) {
                    if (!mProductList.get(i).getPrice().equals("")) {
                        int price = Integer.parseInt(mProductList.get(i).getPrice());
                        int count = mOrderViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
                        totalPrice += (price * count);
                    }
                }
                mFragmentOrderBinding.totalPrice.setText(String.valueOf(totalPrice));

                if (mProductList.size() == 0) {
                    mFragmentOrderBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
                    mFragmentOrderBinding.constraintLayoutContinue.setVisibility(View.GONE);
                }
                else {
                    mFragmentOrderBinding.layoutEmptyCart.setVisibility(View.GONE);
                    mFragmentOrderBinding.constraintLayoutContinue.setVisibility(View.VISIBLE);
                }
            }
        });

        /*mCustomerLiveData.observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                Toast.makeText(getActivity(),customer.getFirst_name() + "\t" + customer.getLast_name()
                        + "\t" + customer.getEmail() + "\t" , Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentOrderBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_order,
                container,
                false);
        mOrderViewModel.setFragmentCartBinding(mFragmentOrderBinding);

        initView();
        return mFragmentOrderBinding.getRoot();
    }

    private void initView() {
        mFragmentOrderBinding.recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentOrderBinding.setOrderViewModel(mOrderViewModel);
        mFragmentOrderBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
        mFragmentOrderBinding.constraintLayoutContinue.setVisibility(View.GONE);

    }
}