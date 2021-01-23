package org.maktab.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ProductCategory;
import org.maktab.digikala.repository.ProductRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private ProductRepository mRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository();
    }

    public void getCategoryItems() {
        mRepository.fetchCategoryItemsAsync();
    }
    public void getSubCategoryItems(String parentId) {
        mRepository.fetchSubCategoryItemsAsync(parentId);
    }
    public void getProductItemsWithParentId(String parentId) {
        mRepository.fetchGetProductWithIdItemsAsync(parentId);
    }

    public LiveData<List<ProductCategory>> getLiveDataCategoryItems(){
        return mRepository.getProductCategoryLiveData();
    }
    public LiveData<List<Product>> getLiveDataProductWithParentId(){
        return mRepository.getProductWithParentIdLiveData();
    }
}
