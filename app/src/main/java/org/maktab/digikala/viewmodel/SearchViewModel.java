package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.utilities.QueryPreferences;
import org.maktab.digikala.view.activities.ProductDetailActivity;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private List<Product> mProductListMostVisited;
    private List<Product> mProductListLatest;
    private List<Product> mProductListHighestScore;
    private List<Product> mProductList;
    private List<Product> mSearchProduct;
    private Context mContext;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository();

    }

    //
    public List<Product> getSearchProduct() {
        return mSearchProduct;
    }

    ///
    public void setSearchProduct(List<Product> searchProduct) {
        mSearchProduct = searchProduct;
    }

    //
    public void setContext(Context context) {
        mContext = context;
    }

    ///
    public LiveData<List<Product>> getSearchItemsLiveData() {
        return mRepository.getSearchProductsLiveData();
    }

    public LiveData<List<Product>> getSortedLowToHighSearchItemsLiveData() {
        return mRepository.getSortedLowToHighSearchProductsLiveData();
    }

    public LiveData<List<Product>> getSortedHighToLowSearchItemsLiveData() {
        return mRepository.getSortedHighToLowSearchProductsLiveData();
    }

    public LiveData<List<Product>> getSortedTopSellersSearchItemsLiveData() {
        return mRepository.getSortedTopSellersSearchProductsLiveData();
    }

    //
    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }

    public void fetchProductItems(int productId){
        mRepository.fetchProductItemsAsync(productId);
    }

    public LiveData<Product> getLiveDateProduct(){
        return mRepository.getProductLiveData();
    }

    ///
    public void fetchSearchItemsAsync(String query) {
        mRepository.fetchSearchItemsAsync(query);
    }

    public void fetchSortedLowToHighSearchItemsAsync(String query) {
        mRepository.fetchSortedLowToHighSearchItemsAsync(query);
    }

    public void fetchSortedHighToLowSearchItemsAsync(String query) {
        mRepository.fetchSortedHighToLowSearchItemsAsync(query);
    }

    ///
    public void setQueryInPreferences(String query) {
        QueryPreferences.setSearchQuery(getApplication(), query);
    }

    //
    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }

    public void setSort(int sortId){
        mRepository.setSort(sortId);
    }

    public int getSort(){
        return mRepository.getSort();
    }
}
