package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.model.BillingAddress;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Order;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ShippingAddress;
import org.maktab.digikala.repository.OrderDBRepository;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.view.activities.OrderActivity;
import org.maktab.digikala.view.activities.ProductDetailActivity;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderViewModel extends AndroidViewModel {
    private OrderDBRepository mOrderDBRepository;
    private ProductRepository mProductRepository;
    private LiveData<Product> mProductLiveData;
    private LiveData<Customer> mCustomerLiveData;
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
        mProductRepository = new ProductRepository();

    }
    public void insertToOrder(Order order){
        mOrderDBRepository.insertOrder(order);
    }

    public void getOrderedProduct(){

        List<Order> carts = mOrderDBRepository.getOrders();
        for (int i = 0; i < carts.size(); i++) {
            mProductRepository.fetchProductItemsAsync(carts.get(i).getProduct_id());
            mProductLiveData = mProductRepository.getProductLiveData();

        }

    }

    public LiveData<Product> getLiveDateProduct(){
        return mProductRepository.getProductLiveData();
    }

    public LiveData<Customer> getLiveDataCustomer() {
        return mProductRepository.getCustomerLiveData();
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

    public void onClickToGoToCart() {
        mContext.startActivity(OrderActivity.newIntent(getApplication()));
    }

    public void onClickToBuy(int productId) {
        insertToOrder(new Order(productId));
        Toast.makeText(mContext,"add to cart",Toast.LENGTH_SHORT).show();
    }

    public void onClickContinueBuy(){

        Random random = new Random();
        BillingAddress billingAddresses = new BillingAddress("maryam","banitalebi","maktab",
                "poonak","nateghnoori","tehran","north","1473165569",
                "iran","banytalebi.m@gmail.com","9013330520");

        ShippingAddress shippingAddresses = new ShippingAddress("maryam","banitalebi","maktab",
                "poonak","nateghnoori","tehran","north","1473165569",
                "iran");
        Customer customer = new Customer("banytalebi.m@gmail.com","maryam","banitalebi",
                "maryam.banitalebi", billingAddresses,
                shippingAddresses);

        mProductRepository.postCreateCustomerAsync(customer);
    }
}
