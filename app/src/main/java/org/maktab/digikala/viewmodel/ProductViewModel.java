package org.maktab.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;


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
}
