package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import org.maktab.digikala.adapter.ProductDetailAdapter;
import org.maktab.digikala.databinding.FragmentProductDetailBinding;
import org.maktab.digikala.model.Images;
import org.maktab.digikala.model.Order;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.viewmodel.OrderViewModel;
import org.maktab.digikala.viewmodel.ProductViewModel;

import java.util.List;

public class ProductDetailFragment extends Fragment {

    public static final String BUNDLE_KEY_PRODUCT_ID = "bundle key product id";

    private int mProductId;
    private ProductDetailAdapter mDetailAdapter;
    private LiveData<Product> mProductLiveData;
    private FragmentProductDetailBinding mProductDetailBinding;
    private ProductViewModel mProductViewModel;
    private OrderViewModel mOrderViewModel;

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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

        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mOrderViewModel.setContext(getActivity());
        getProductFromProductViewModel();
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mProductDetailBinding = DataBindingUtil.inflate(inflater,
                        R.layout.fragment_product_detail,
                        container,
                        false);
        initView();
        listeners();

        return mProductDetailBinding.getRoot();
    }

    private void checkRating(Product product) {
        if (product.getAverageRating().equalsIgnoreCase("0.5")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        }else if (product.getAverageRating().equalsIgnoreCase("1.00")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
        }else if (product.getAverageRating().equalsIgnoreCase("1.5")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        }else if (product.getAverageRating().equalsIgnoreCase("2.00")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
        }else if (product.getAverageRating().equalsIgnoreCase("2.5")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        }else if (product.getAverageRating().equalsIgnoreCase("3.00")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
        }else if (product.getAverageRating().equalsIgnoreCase("3.5")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        }else if (product.getAverageRating().equalsIgnoreCase("4.00")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
        }else if (product.getAverageRating().equalsIgnoreCase("4.5")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        }else if (product.getAverageRating().equalsIgnoreCase("5.00")){
            mProductDetailBinding.imageViewStar1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
            mProductDetailBinding.imageViewStar5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_rate));
        }
        mProductDetailBinding.textViewRate.setText(String.valueOf(product.getRatingCount()));
    }

    private void listeners() {
        mProductDetailBinding.setOrderViewModel(mOrderViewModel);
        mProductDetailBinding.setLifecycleOwner(getActivity());
        mProductDetailBinding.setProductId(mProductId);
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
                checkRating(product);
                mProductViewModel.setDetailedProduct(product);
                setAdapterProductDetail();
                mProductDetailBinding.textProductName.setText(product.getTitle());
                String detail = product.getShortDescription() + "\n" + product.getDescription()
                        + "\n\n";
                mProductDetailBinding.textviewDescription.setText(detail);
                mProductDetailBinding.textViewPrice.setText(product.getPrice());

            }
        });
    }

    private void setAdapterProductDetail() {
        mDetailAdapter = new ProductDetailAdapter(this,mProductViewModel);
        mProductDetailBinding.recyclerProductDetail.setAdapter(mDetailAdapter);
    }

    private void initView() {
        mProductDetailBinding.recyclerProductDetail
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
    }
}