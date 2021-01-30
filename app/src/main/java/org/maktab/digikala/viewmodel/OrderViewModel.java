package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.Order;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.OrderDBRepository;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.view.activities.ProductDetailActivity;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderDBRepository mOrderDBRepository;
    private ProductRepository mStoreRepository;
    private LiveData<Product> mProductLiveData;
    private LiveData<List<Product>> mProductListLiveData;
    private List<Product> mProductList;
    private List<Product> mProductListMostVisited;
    private List<Product> mProductListLatest;
    private List<Product> mProductListHighestScore;
    private List<Product> mSearchProduct;
    private Context mContext;


    public OrderViewModel(@NonNull Application application) {
        super(application);
        mOrderDBRepository = OrderDBRepository.getInstance(application);
        mStoreRepository = new ProductRepository();

    }
    public void insertToOrder(Order order){
        mOrderDBRepository.insertOrder(order);
    }

    public void getOrderedProduct(){

        List<Order> carts = mOrderDBRepository.getOrders();
        for (int i = 0; i < carts.size(); i++) {
            mStoreRepository.fetchProductItemsAsync(carts.get(i).getProduct_id());
            mProductLiveData = mStoreRepository.getProductLiveData();

        }

    }

    public LiveData<Product> getLiveDateProduct(){
        return mStoreRepository.getProductLiveData();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }
}
