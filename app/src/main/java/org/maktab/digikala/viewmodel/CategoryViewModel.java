package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.view.activities.ProductDetailActivity;
import org.maktab.digikala.view.activities.SubCategoryActivity;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private List<Product> mProductList;
    private List<ProductCategory> mCategoryList;
    private Context mContext;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public List<ProductCategory> getCategoryList() {
        return mCategoryList;
    }

    public void setCategoryList(List<ProductCategory> categoryList) {
        mCategoryList = categoryList;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public LiveData<List<ProductCategory>> getLiveDataCategoryItems(){
        return mRepository.getProductCategoryLiveData();
    }
    public LiveData<List<Product>> getLiveDataProductWithParentId(){
        return mRepository.getProductWithParentIdLiveData();
    }

    public void fetchCategoryItems() {
        mRepository.fetchCategoryItemsAsync();
    }

    public void fetchSubCategoryItems(String parentId) {
        mRepository.fetchSubCategoryItemsAsync(parentId);
    }

    public void fetchProductItemsWithParentId(String parentId) {
        mRepository.fetchGetProductWithIdItemsAsync(parentId);
    }

    public void onClickListItem(int productId,String state) {
        if (state.equalsIgnoreCase("product"))
            mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
        else if (state.equalsIgnoreCase("category"))
            mContext.startActivity(SubCategoryActivity.newIntent(mContext,productId));
    }
}
