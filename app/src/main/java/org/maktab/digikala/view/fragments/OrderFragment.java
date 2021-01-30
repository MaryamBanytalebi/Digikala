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
import org.maktab.digikala.adapter.OrderProductAdapter;
import org.maktab.digikala.databinding.FragmentOrderBinding;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    FragmentOrderBinding mFragmentOrderBinding;
    private OrderViewModel mOrderViewModel;
    private LiveData<Product> mProductLiveData;
    private List<Product> mProductList;
    private OrderProductAdapter mOrderedProductAdapter;

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
                mOrderedProductAdapter = new OrderProductAdapter(getActivity(),getActivity(), mOrderViewModel);
                mFragmentOrderBinding.recyclerOrder.setAdapter(mOrderedProductAdapter);
                int totalPrice = 0;
                for (int i = 0; i < mProductList.size(); i++) {
                    totalPrice += Integer.parseInt(mProductList.get(i).getPrice());
                }
                mFragmentOrderBinding.totalPrice.setText(String.valueOf(totalPrice));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentOrderBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_order,
                container,
                false);

        initView();
        return mFragmentOrderBinding.getRoot();
    }

    private void initView() {
        mFragmentOrderBinding.recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}