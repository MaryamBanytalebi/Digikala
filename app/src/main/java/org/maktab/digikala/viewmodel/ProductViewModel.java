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

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private List<Product> mProductListMostVisited;
    private List<Product> mProductListLatest;
    private List<Product> mProductListHighestScore;
    private Context mContext;
    private Product mDetailedProduct;


    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository();
    }

    public void getProductItems(int productId){
        mRepository.fetchProductItemsAsync(productId);
    }

    public void getMostVisitedProductItems(){
        mRepository.fetchMostVisitedItemsAsync();
    }

    public void getLatestProductItems(){
        mRepository.fetchLatestItemsAsync();
    }

    public void getHighestScoreProductItems(){
        mRepository.fetchHighestScoreItemsAsync();
    }

    public ProductRepository getRepository() {
        return mRepository;
    }

    public void setRepository(ProductRepository repository) {
        mRepository = repository;
    }

    public List<Product> getProductListMostVisited() {
        return mProductListMostVisited;
    }

    public void setProductListMostVisited(List<Product> productListMostVisited) {
        mProductListMostVisited = productListMostVisited;
    }

    public List<Product> getProductListLatest() {
        return mProductListLatest;
    }

    public void setProductListLatest(List<Product> productListLatest) {
        mProductListLatest = productListLatest;
    }

    public List<Product> getProductListHighestScore() {
        return mProductListHighestScore;
    }

    public void setProductListHighestScore(List<Product> productListHighestScore) {
        mProductListHighestScore = productListHighestScore;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Product getDetailedProduct() {
        return mDetailedProduct;
    }

    public void setDetailedProduct(Product detailedProduct) {
        mDetailedProduct = detailedProduct;
    }

    public void fetchProductItems(int productId){
        mRepository.fetchProductItemsAsync(productId);
    }

    public void fetchMostVisitedProductItems(){
        mRepository.fetchMostVisitedItemsAsync();
    }

    public void fetchLatestProductItems(){
        mRepository.fetchLatestItemsAsync();
    }

    public void fetchHighestScoreProductItems(){
        mRepository.fetchHighestScoreItemsAsync();
    }

    public LiveData<List<Product>> getLiveDateMostVisitedProducts(){
        return mRepository.getMostVisitedProductsLiveData();
    }

    public LiveData<List<Product>> getLiveDateLatestProducts(){
        return mRepository.getLatestProductsLiveData();
    }

    public LiveData<Product> getLiveDateProduct(){
        return mRepository.getProductLiveData();
    }

    public LiveData<List<Product>> getLiveDateHighestScoreProducts(){
        return mRepository.getHighestScoreProductsLiveData();
    }

    /*public LiveData<List<Product>> getSearchItemsLiveData() {
        return mRepository.getSearchProductsLiveData();
    }*/

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }

    public void fetchSearchItemsAsync(String query) {
        mRepository.fetchSearchItemsAsync(query);
    }

    public void setQueryInPreferences(String query) {
        QueryPreferences.setSearchQuery(getApplication(), query);
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }
}
