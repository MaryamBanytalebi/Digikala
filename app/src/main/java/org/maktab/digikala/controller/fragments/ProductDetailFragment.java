package org.maktab.digikala.controller.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.adapter.ProductDetailAdapter;
import org.maktab.digikala.databinding.FragmentProductDetailBinding;
import org.maktab.digikala.model.Images;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.ProductRepository;

import java.util.List;

public class ProductDetailFragment extends Fragment {

    public static final String BUNDLE_KEY_PRODUCT_ID = "bundle key product id";

    private int mProductId;
    private ProductRepository mRepository;
    private ProductDetailAdapter mDetailAdapter;
    private LiveData<Product> mProductLiveData;
    private Product mProduct;
    private FragmentProductDetailBinding mBinding;

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

        mRepository = new ProductRepository();
        mRepository.fetchProductItemsAsync(mProductId);
        mProductLiveData = mRepository.getProductLiveData();
        setObserver();
    }

    private void setObserver() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {

                mProduct = product;
                List<Images> imagesList = product.getImages();
                setAdapterProductDetail(imagesList);
                mBinding.textProductName.setText(mProduct.getTitle());
                String detail = mProduct.getShortDescription() + "\n" + mProduct.getDescription()
                        + "\n" + " Average Rating: \t " + mProduct.getAverageRating() + "\n\n"
                        + " Price: \t" + mProduct.getPrice() + "\n\n";
                mBinding.textViewProductDetail.setText(detail);
            }
        });
    }

    private void setAdapterProductDetail(List<Images> imagesList) {
        mDetailAdapter = new ProductDetailAdapter(imagesList, getActivity());
        mBinding.recyclerProductDetail.setAdapter(mDetailAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentProductDetailBinding binding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_product_detail,
                        container,
                        false);

        return binding.getRoot();
    }
}