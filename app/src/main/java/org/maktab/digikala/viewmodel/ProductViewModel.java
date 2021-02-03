package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.SalesReport;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.utilities.QueryPreferences;
import org.maktab.digikala.view.activities.ProductDetailActivity;
import org.maktab.digikala.worker.PollWorker;

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

    public SalesReport fetchProductItemsSize(){
        return mRepository.fetchProductItems();
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

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }

    public void fetchSpecialProductItems(String parentId, String page) {
        mRepository.fetchSpecialProductItemsAsync(parentId , page);
    }

    public LiveData<List<Product>> getLiveDataSpecialProduct1(){
        return mRepository.getSpecialProductsLiveData1();
    }

    public LiveData<List<Product>> getLiveDataSpecialProduct2(){
        return mRepository.getSpecialProductsLiveData2();
    }

    public LiveData<List<Product>> getLiveDataSpecialProduct3(){
        return mRepository.getSpecialProductsLiveData3();
    }

    public void setColorInPreferences(String color) {
        QueryPreferences.setFilterColor(getApplication(), color);
    }

    //
    public String getColorFromPreferences() {
        return QueryPreferences.getFilterColor(getApplication());
    }

    public void setTotalItemsInPreferences(int totalItems) {
        QueryPreferences.setTotalItems(getApplication(), totalItems);
    }

    //
    public int getTotalItemsFromPreferences() {
        return QueryPreferences.getTotalItems(getApplication());
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

}
