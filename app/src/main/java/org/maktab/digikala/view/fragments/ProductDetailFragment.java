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
import org.maktab.digikala.adapter.ProductDetailAdapter;
import org.maktab.digikala.databinding.FragmentProductDetailBinding;
import org.maktab.digikala.model.Images;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.viewmodel.ProductViewModel;

import java.util.List;

public class ProductDetailFragment extends Fragment {

    public static final String BUNDLE_KEY_PRODUCT_ID = "bundle key product id";

    private int mProductId;
    private ProductDetailAdapter mDetailAdapter;
    private LiveData<Product> mProductLiveData;
    private FragmentProductDetailBinding mProductDetailBinding;
    private ProductViewModel mProductViewModel;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(int id) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_PRODUCT_ID,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mProductId = getArguments().getInt(BUNDLE_KEY_PRODUCT_ID,0);
        }

        getProductFromProductViewModel();
        setObserver();
    }

    private void getProductFromProductViewModel() {
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductViewModel.fetchProductItems(mProductId);
        mProductLiveData = mProductViewModel.getLiveDateProduct();
    }

    private void setObserver() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {

                mProductViewModel.setDetailedProduct(product);
                setAdapterProductDetail();
                mProductDetailBinding.textProductName.setText(product.getTitle());
                String detail = product.getShortDescription() + "\n" + product.getDescription()
                        + "\n" + " Average Rating: \t " + product.getAverageRating() + "\n\n"
                        + " Price: \t" + product.getPrice() + "\n\n";
                mProductDetailBinding.textViewProductDetail.setText(detail);
            }
        });
    }

    private void setAdapterProductDetail() {
        mDetailAdapter = new ProductDetailAdapter(this,mProductViewModel);
        mProductDetailBinding.recyclerProductDetail.setAdapter(mDetailAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentProductDetailBinding binding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_product_detail,
                        container,
                        false);
        initView();

        return binding.getRoot();
    }

    private void initView() {
        mProductDetailBinding.recyclerProductDetail
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
    }
}